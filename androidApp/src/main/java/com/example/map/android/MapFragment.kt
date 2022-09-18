package com.example.map.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.map.android.Models.Category
import com.example.map.android.Models.Point
import com.example.map.android.databinding.FragmentMapBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    //Координаты города Орла
     private var lat: Double = 52.9685433
     private var lon: Double = 36.0692477

     private var mapController: IMapController? = null
     private var marker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val activity: MainActivity = activity as MainActivity
        binding = FragmentMapBinding.inflate(layoutInflater)
        //Подключение карты
        binding.map.setMultiTouchControls(true)
        binding.map.setBuiltInZoomControls(true)
        mapController = binding.map.controller
        mapController?.setCenter(GeoPoint(lat, lon))
        mapController?.setZoom(14)
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        setUpMyMarker(activity.EventList[0])
        return binding.root

    }

      private fun setUpMyMarker(event: Event){
          marker = Marker(binding.map)
          marker?.icon = resources.getDrawable(R.drawable.marker)
          binding.map.overlays?.add(marker)
          marker?.setOnMarkerClickListener { marker, mapView ->
              //BottomSheet
              val cardEvent: View = layoutInflater.inflate(R.layout.bottom_sheet_event, null, false)
              var imageView: ImageView = cardEvent.findViewById(R.id.imageView)
              var description: TextView = cardEvent.findViewById(R.id.Description)
              val title: TextView = cardEvent.findViewById(R.id.Title)
              val author: TextView = cardEvent.findViewById(R.id.Author)
              val category: TextView = cardEvent.findViewById(R.id.Category)
              val date: TextView = cardEvent.findViewById(R.id.Date)
              val address: TextView = cardEvent.findViewById(R.id.Address)

              title.text = event.title
              author.text = event.author
              category.text = event.category?.name
              date.text = event.date
              address.text = event.address
              description.text = event.description

              binding.containerBottomSheet.addView(cardEvent)
              val bottomSheetBehaviour = BottomSheetBehavior.from(binding.containerBottomSheet)
              bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED

              return@setOnMarkerClickListener false
          }
          val point = GeoPoint(lat, lon)
          marker?.position = point
          mapController?.animateTo(point)
          binding.map.invalidate()
      }

}