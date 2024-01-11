package com.example.vkuserlist

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vkuserlist.adapter.User
import com.example.vkuserlist.adapter.UserAdapter
import com.example.vkuserlist.databinding.ActivityAddUserBinding
import com.example.vkuserlist.retrofit.UserApi
import com.example.vkuserlist.room.MainDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.NumberFormatException

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val intent = intent
            try {
                binding.idEt.text.toString().toInt()
                binding.errorTv.visibility = View.INVISIBLE
                intent.putExtra("ID", binding.idEt.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
            catch (e: NumberFormatException) {
                binding.errorTv.text = "Неверный формат ввода"
                binding.errorTv.visibility = View.VISIBLE
            }
        }
    }
}
