package com.example.map.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.map.android.Models.Category
import com.example.map.android.Models.Point
import com.example.map.android.Models.User
import com.example.map.android.databinding.ActivityFilterBinding
import com.example.map.android.databinding.ActivityMainBinding
import io.ktor.util.*
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding
    var CategoryList: ArrayList<Category> =  ArrayList()
    var EventList: ArrayList<Event> =  ArrayList()
    var dataUser: User = User()
    @OptIn(InternalAPI::class)
    private val data =  HttpHolder()
    private val mainScope = MainScope()

    @OptIn(InternalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CategoryList = intent.extras!!.getParcelableArrayList("CategoryList")!!
        EventList = intent.extras!!.getParcelableArrayList("EventList")!!
        dataUser = intent.extras!!.getParcelable("dataUser")!!

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = FilterAdapter(CategoryList)
        binding.LoaderView.visibility = android.view.View.GONE

        binding.Apply.setOnClickListener{
            var isCheckId = 0
            for (i in 0 until CategoryList.size){
                if (CategoryList[i].isCheck) isCheckId = CategoryList[i].id
            }

            EventList= ArrayList()
            mainScope.launch {
                binding.LoaderView.visibility = android.view.View.VISIBLE
                kotlin.runCatching {
                    data.getEvents(isCheckId)
                }.onSuccess {
                    val jsonObj = JSONTokener(it).nextValue() as JSONArray
                    for (i in 0 until jsonObj.length()){
                        val id = jsonObj.getJSONObject(i).getInt("id")
                        val title = jsonObj.getJSONObject(i).getString("name")
                        val image = jsonObj.getJSONObject(i).getString("image")
                        val author = jsonObj.getJSONObject(i).getJSONObject("user")
                        val authorId = author.getInt("id")
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

                                EventList.add( Event(id, title, image, User(authorId,email, name, surname, role),
                                    Category(id_category,category_name, color_name, text_color), date,
                                    Point(lat, lon), fillAddress, description))

                                if (EventList.size == jsonObj.length()){
                                    binding.LoaderView.visibility = android.view.View.GONE
                                    val intent = Intent(this@FilterActivity, MainActivity::class.java)
                                    intent.putExtra("EventList", EventList)
                                    intent.putExtra("CategoryList", CategoryList)
                                    intent.putExtra("dataUser", dataUser)
                                    startActivity(intent)
                                    finish()
                                }

                            }.onFailure {
                                binding.LoaderView.visibility = android.view.View.GONE
                                println("Ошибка: " + it.localizedMessage)
                            }
                        }
                    }

                }.onFailure {
                    binding.LoaderView.visibility = android.view.View.GONE
                    println("Ошибка: " + it.localizedMessage)
                }

            }
        }
    }
}