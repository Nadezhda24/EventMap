package com.example.map.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.map.android.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tabName: Array<String> = arrayOf("События", "Карта")
    //Координаты города Орла
   /* private var lat: Double = 52.9685433
    private var lon: Double = 36.0692477

    private var map: MapView? = null
    private var mapController: IMapController? = null
    private var marker: Marker? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        //Подключение карты
      /*  map = findViewById(R.id.map)
        map?.setMultiTouchControls(true)
        map?.setBuiltInZoomControls(true)
        mapController = map?.controller
        mapController?.setCenter(GeoPoint(lat, lon))
        mapController?.setZoom(14)
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID*/
    }

  /*  private fun setUpMyMarker(lat: Double, lon: Double){
        marker = Marker(map)
        marker?.icon = resources.getDrawable(R.drawable.marker)
        map?.overlays?.add(marker)
        updateMyMarker(lat,lon)
    }

    private fun updateMyMarker(lat: Double, lon: Double){
        val point = org.osmdroid.util.GeoPoint(lat, lon)
        marker?.setPosition(point)
        marker?.setAnchor(5f / 27, 27f / 27)
        mapController?.animateTo(point)
        map?.invalidate()
    }*/


}
