package com.example.map.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.map.android.Models.Category
import com.example.map.android.Models.Point
import com.example.map.android.Models.User
import com.example.map.android.databinding.ActivityPersonalAccountBinding
import com.example.map.android.databinding.FragmentMapBinding
import com.google.android.gms.common.AccountPicker
import io.ktor.util.*
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener

class PersonalAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalAccountBinding
    @OptIn(InternalAPI::class)
    private val data =  HttpHolder()
    private val mainScope = MainScope()
    var CategoryList: ArrayList<Category> =  ArrayList()
    var EventList: ArrayList<Event> =  ArrayList()
    private var dataUser: User = User()

    @OptIn(InternalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CategoryList = intent.extras!!.getParcelableArrayList("CategoryList")!!
        EventList = intent.extras!!.getParcelableArrayList("EventList")!!
        dataUser = intent.extras!!.getParcelable("dataUser")!!

        binding.LoaderView.visibility = android.view.View.GONE

        binding.LogOn.setOnClickListener{
            val mail = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            mainScope.launch {
                kotlin.runCatching {
                    data.getLogin(mail, password)
                }.onSuccess {
                    println(it)
                    val json = JSONTokener(it).nextValue() as JSONObject
                    dataUser = User (
                        json.getInt("id"),
                        json.getString("mail"),
                        json.getString("first_name"),
                        json.getString("last_name"),
                        json.getInt("role"))

                    val intent = Intent(this@PersonalAccountActivity, MainActivity::class.java)
                    intent.putExtra("EventList", EventList)
                    intent.putExtra("CategoryList", CategoryList)
                    intent.putExtra("dataUser", dataUser)
                    startActivity(intent)
                    finish()

                }.onFailure {
                    binding.LoaderView.visibility = android.view.View.GONE
                    println("Ошибка: " + it.localizedMessage)
                }
            }

        }

    }
}