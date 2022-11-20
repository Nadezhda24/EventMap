package com.example.map.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.map.android.Models.Category
import com.example.map.android.Models.Point
import com.example.map.android.Models.User
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class SplashScreen : AppCompatActivity() {
    private val data =  HttpHolder()
    private val mainScope = MainScope()
    var EventList: ArrayList<Event> = ArrayList()
    var CategoryList: ArrayList<Category> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mainScope.launch {
            kotlin.runCatching {
                data.getEvents()
            }.onSuccess {
                val jsonObj = JSONTokener(it).nextValue() as JSONArray
                for (i in 0 until jsonObj.length()){
                    val id = jsonObj.getJSONObject(i).getInt("id")
                    val title = jsonObj.getJSONObject(i).getString("name")
                    val image = jsonObj.getJSONObject(i).getString("image")
                    val author = jsonObj.getJSONObject(i).getJSONObject("user")
                    val name = author.getString("first_name")
                    val surname = author.getString("last_name")
                    val email = author.getString("mail")
                    val role = author.getInt("role")
                    val category =  jsonObj.getJSONObject(i).getJSONObject("category")
                    val id_category = category.getInt("id")
                    val category_name = category.getString("name")
                    val color = category.getJSONObject("color")
                    val color_name = color.getString("color")
                    val text_color = color.getString("text")
                    val date = jsonObj.getJSONObject(i).getString("date")
                    val point = jsonObj.getJSONObject(i).getJSONObject("address")
                    val lat = point.getDouble("lat")
                    val lon = point.getDouble("lon")
                    val description = jsonObj.getJSONObject(i).getString("description")
                    var fillAddress = ""

                    mainScope.launch {
                        kotlin.runCatching {
                            data.getReverseData(Point(lat, lon))
                        }.onSuccess {
                            val json = JSONTokener(it).nextValue() as JSONObject
                            fillAddress = try {
                                val address = json.getJSONObject("address")
                                val road = address.getString("road")
                                road
                            }catch (e:Exception){
                                val display_name = json.getString("display_name")
                                display_name
                            }

                            EventList.add( Event(id, title, image, User(email, name, surname, role),
                                Category(id_category,category_name, color_name, text_color), date,
                                Point(lat, lon), fillAddress, description))

                            if (EventList.size == jsonObj.length()){

                                mainScope.launch {
                                    kotlin.runCatching {
                                        data.getCategory()
                                    }.onSuccess {
                                        val jsonObj = JSONTokener(it).nextValue() as JSONArray
                                        for (k in 0 until jsonObj.length()){
                                            val id_category = jsonObj.getJSONObject(k).getInt("id")
                                            val name_category = jsonObj.getJSONObject(k).getString("name")
                                            CategoryList.add(Category(id_category,name_category))
                                        }

                                        val intent = Intent(this@SplashScreen, MainActivity::class.java)
                                        intent.putExtra("EventList", EventList)
                                        intent.putExtra("CategoryList", CategoryList)
                                        startActivity(intent)
                                        finish()

                                    }.onFailure {
                                        println("Ошибка: " + it.localizedMessage)
                                    }
                                }



                            }

                        }.onFailure {
                            println("Ошибка: " + it.localizedMessage)
                        }
                    }
                }

            }.onFailure {
                println("Ошибка: " + it.localizedMessage)
            }


        }


    }
}