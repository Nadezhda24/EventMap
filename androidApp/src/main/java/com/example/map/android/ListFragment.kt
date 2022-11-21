package com.example.map.android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.map.android.Models.Category
import com.example.map.android.Models.Point
import com.example.map.android.databinding.FragmentListBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        val activity: MainActivity =  activity as MainActivity
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        if (activity.dataUser.id!! > 0){
            binding.User.visibility = View.GONE
        }


        binding.recyclerView.adapter = EventAdapter(activity.EventList)

        binding.User.setOnClickListener{
            val intent = Intent(context, PersonalAccountActivity::class.java)
            intent.putExtra("CategoryList", activity.CategoryList)
            intent.putExtra("EventList", activity.EventList)
            intent.putExtra("dataUser", activity.dataUser)
            startActivity(intent)
        }

        binding.Filter.setOnClickListener{
            val intent = Intent(context, FilterActivity::class.java)
            intent.putExtra("CategoryList", activity.CategoryList)
            intent.putExtra("EventList", activity.EventList)
            intent.putExtra("dataUser", activity.dataUser)
            startActivity(intent)
        }

        binding.AddEvent.setOnClickListener{
            if (activity.dataUser.id!! > 0){
                val intent = Intent(context, CreateEventActivity::class.java)
                intent.putExtra("CategoryList", activity.CategoryList)
                intent.putExtra("EventList", activity.EventList)
                intent.putExtra("dataUser", activity.dataUser)
                startActivity(intent)
            }else{
                val intent = Intent(context, PersonalAccountActivity::class.java)
                intent.putExtra("CategoryList", activity.CategoryList)
                intent.putExtra("EventList", activity.EventList)
                intent.putExtra("dataUser", activity.dataUser)
                startActivity(intent)
            }
        }





        return  binding.root
    }

}