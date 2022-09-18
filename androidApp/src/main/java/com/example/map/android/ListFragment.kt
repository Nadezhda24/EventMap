package com.example.map.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.map.android.Models.Category
import com.example.map.android.Models.Point
import com.example.map.android.databinding.FragmentListBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener

class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var binding: FragmentListBinding
    var EventList: ArrayList<Event> = ArrayList()
    private val data =  HttpHolder()
    private val mainScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

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

                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = EventAdapter(EventList)
            }.onFailure {
                println("Ошибка: " + it.localizedMessage)
            }
        }



        return  binding.root
    }

}