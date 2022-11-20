package com.example.map.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.map.android.databinding.ActivityPersonalAccountBinding
import com.example.map.android.databinding.FragmentMapBinding
import com.google.android.gms.common.AccountPicker

class PersonalAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}