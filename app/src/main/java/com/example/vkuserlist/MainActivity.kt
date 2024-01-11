package com.example.vkuserlist

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vkuserlist.adapter.User
import com.example.vkuserlist.adapter.UserAdapter
import com.example.vkuserlist.databinding.ActivityMainBinding
import com.example.vkuserlist.retrofit.UserApi
import com.example.vkuserlist.room.MainDb
import com.example.vkuserlist.room.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val requestAdd = 0
    private val adapter = UserAdapter()
    private lateinit var userApi: UserApi
    private lateinit var db: MainDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getApi()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usersRv.layoutManager = GridLayoutManager(this@MainActivity, 1)
        binding.usersRv.adapter = adapter

        db = MainDb.getInstance(this)
        addUser()
        setUsers()
    }

    private fun getApi() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.vk.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userApi = retrofit.create(UserApi::class.java)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestAdd && resultCode == RESULT_OK) {
            val id = data?.getStringExtra("ID")

            setUserList(id!!)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setUserList(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val userResponse = userApi.getUserById(id).response[0]
            try {
                db.getDao().
                insertUser(UserInfo(userResponse.id,
                    userResponse.first_name,
                    userResponse.last_name)
                )
            }
            catch (_: SQLiteConstraintException) { }
        }
    }

    private fun addUser() {
        binding.usersFab.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivityForResult(intent, requestAdd)
        }
    }

    private fun setUsers() {
        db.getDao().getInitials().asLiveData().observe(this) {
            adapter.clearUsers()
            for (user in it) {
                adapter.addUser(
                    User(user.id,
                        "Имя: ${user.name}",
                        "Фамилия: ${user.lastName}"
                    )
                )
            }
        }
    }
}