package com.example.map.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.map.android.databinding.FragmentListBinding

class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var binding: FragmentListBinding
    private var EventList: ArrayList<Event> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)

        for (i in 0 until 10)
        EventList.add(Event(1, "Новое событие",
            "21pgmipait@gmail.com",
            "ДТП",
            "12.12.2022 13:00",
            "ул. Приборостроительная",
            "Эксплуатация ИТ в границах АСУП показала потребность такого ПО посредствам которого оказалось бы возможным не только автоматизация производственных процессов, но и автоматизации управленческих процессов поддержания соответствия между целями предприятия его потенциальными возможностями и ситуацией на рынке на длительный период. Такая способность АСУП фактически открывает возможность формирования важнейшего конкурентного преимущества АСУП - оказание требуемого влияния на структуру рынка.\n"))

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = EventAdapter(EventList)

        return  binding.root
    }

}