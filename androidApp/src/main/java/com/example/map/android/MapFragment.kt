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
    private val data =  HttpHolder()
    private val mainScope = MainScope()

    var EventList: ArrayList<Event> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater)
        //Подключение карты
        binding.map.setMultiTouchControls(true)
        binding.map.setBuiltInZoomControls(true)
        mapController = binding.map.controller
        mapController?.setCenter(GeoPoint(lat, lon))
        mapController?.setZoom(14)
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID

        EventList.add(Event(1, "Новое событие",
            "21pgmipait@gmail.com",
            Category("ДТП", "#FFC618", "FFFFFF"),
            "12.12.2022 13:00",
            Point(52.9685433, 36.0692477),
            "",
            "Эксплуатация ИТ в границах АСУП показала потребность такого ПО посредствам которого оказалось бы возможным не только автоматизация производственных процессов, но и автоматизации управленческих процессов поддержания соответствия между целями предприятия его потенциальными возможностями и ситуацией на рынке на длительный период. Такая способность АСУП фактически открывает возможность формирования важнейшего конкурентного преимущества АСУП - оказание требуемого влияния на структуру рынка.\n"))


        //получение данных
        mainScope.launch {
            kotlin.runCatching {
                data.getReverseData(EventList[0].point)
            }.onSuccess {
                val jsonObj = JSONTokener(it).nextValue() as JSONObject
                try {
                    val address = jsonObj.getJSONObject("address")
                    val road = address.getString("road")
                    EventList[0].address = road
                }catch (e:Exception){
                    val display_name = jsonObj.getString("display_name")
                    EventList[0].address = display_name
                }
                setUpMyMarker(EventList[0].point.lat,EventList[0].point.lon)

            }.onFailure {
                println("Ошибка: " + it.localizedMessage)
            }
        }
        return binding.root

    }


      private fun setUpMyMarker(lat: Double, lon: Double){
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

              title.text = EventList[0].title
              author.text = EventList[0].author
              category.text = EventList[0].category.name
              date.text = EventList[0].date
              address.text = EventList[0].address
              description.text = EventList[0].description

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