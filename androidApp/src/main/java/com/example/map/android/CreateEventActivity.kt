package com.example.map.android

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.map.android.Models.Category
import com.example.map.android.Models.Point
import com.example.map.android.Models.User
import com.example.map.android.databinding.ActivityCreateEventBinding
import io.ktor.util.*
import kotlinx.android.synthetic.main.calendar.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding
    var CategoryList: ArrayList<Category> =  ArrayList()
    var EventList: ArrayList<Event> =  ArrayList()
    var dataUser: User = User()
    private var calendarView: CalendarView? = null
    var selectedDateTo: String? = null

    @OptIn(InternalAPI::class)
    private val data =  HttpHolder()
    private val mainScope = MainScope()


    @OptIn(InternalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CategoryList = intent.extras!!.getParcelableArrayList("CategoryList")!!
        EventList = intent.extras!!.getParcelableArrayList("EventList")!!
        dataUser = intent.extras!!.getParcelable("dataUser")!!

        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        binding.recyclerView2.adapter = FilterAdapter(CategoryList)

        binding.LoaderView.visibility = android.view.View.GONE

        binding.dataTo.setOnClickListener{
                    val dialog =  Dialog(this)
                    dialog.setContentView(R.layout.calendar)
                    dialog.setCanceledOnTouchOutside(false)
                    val ok: TextView =  dialog.findViewById(R.id.Ok)
                    val cancel: TextView = dialog.findViewById(R.id.Cancel)
                    calendarView = dialog.findViewById(R.id.calendarView)
                    var flag_data_chenge = false


                    calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
                        var d = ""
                        var m = ""
                        if (dayOfMonth <= 9) d = "0"
                        if (month < 9) m = "0"
                        selectedDateTo = d + dayOfMonth + "." + m + (month + 1).toString() + "." + year
                        flag_data_chenge = true
                    }
                    ok.setOnClickListener{
                        if (!flag_data_chenge) {
                            val currentDate= Date()
                            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                            selectedDateTo = dateFormat.format(currentDate)
                        }
                        binding.dataTo.text = selectedDateTo
                        dialog.dismiss()
                    }
                    cancel.setOnClickListener{
                        dialog.dismiss()
                    }
                    dialog.show()
                }

        binding.apply.setOnClickListener{
            var idCategory: Int = 1
            for(i in 0 until CategoryList.size){
                if (CategoryList[i].isCheck) idCategory =  CategoryList[i].id
            }
            mainScope.launch {
                binding.LoaderView.visibility = android.view.View.VISIBLE
                kotlin.runCatching {
                    data.newEvent(
                        10,
                        binding.editTextTitle.text.toString(),
                        binding.multiAutoCompleteTextView.text.toString(),
                        idCategory,
                        binding.dataTo.text.toString(),
                        binding.editTextTitle5.text.toString(),
                        dataUser.id!!
                    )
                }.onSuccess {
                    println(it)
                    EventList= ArrayList()
                    mainScope.launch {
                        binding.LoaderView.visibility = android.view.View.VISIBLE
                        kotlin.runCatching {
                            data.getEvents(0)
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
                                            val intent = Intent(this@CreateEventActivity, MainActivity::class.java)
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


                }.onFailure {
                    binding.LoaderView.visibility = android.view.View.GONE
                    println("Ошибка: " + it.localizedMessage)
                }
            }

        }


    }
}