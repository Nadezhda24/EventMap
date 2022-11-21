package com.example.map.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.map.android.Models.Point
import com.example.map.android.Models.Category
import com.example.map.android.Models.User
import com.example.map.android.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import io.ktor.util.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var EventList: ArrayList<Event> = ArrayList()
    var CategoryList: ArrayList<Category> =  ArrayList()
    var dataUser: User = User()
    @OptIn(InternalAPI::class)
    val data =  HttpHolder()
    val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        EventList = intent.extras!!.getParcelableArrayList("EventList")!!
        CategoryList = intent.extras!!.getParcelableArrayList("CategoryList")!!
        dataUser = intent.extras!!.getParcelable("dataUser")!!


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("События"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Карта"))
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = FragmentAdapter(supportFragmentManager, binding.tabLayout.tabCount)
        binding.viewPager.adapter = adapter

        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


    }
}
