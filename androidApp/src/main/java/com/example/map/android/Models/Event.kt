package com.example.map.android

import com.example.map.android.Models.Point
import com.example.map.android.Models.Category

data class Event (
    var id: Int = -1,
    var title: String = "",
    var author: String = "",
    var category: Category = Category(),
    var date: String = "",
    var point: Point = Point(),
    var address: String = "",
    var description: String = ""
)