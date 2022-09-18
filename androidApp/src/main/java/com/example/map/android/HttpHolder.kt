package com.example.map.android

import com.example.map.android.Models.Point
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*


class HttpHolder {

    private val httpClient =  HttpClient()
    val url: String = "https://nominatim.openstreetmap.org/"

    //по наименованию возвращает координаты
    suspend fun getSearchData(search: String): String{
        val response: HttpResponse = httpClient.get(url+"search/?format=json&q=${search}")
        return response.readText()
    }

    //по координатам возвращает наименование
    suspend fun getReverseData(address: Point): String{
        val response: HttpResponse = httpClient.get(url+"reverse?format=json&lat=${address.lat}&lon=${address.lon}")
        return response.readText()
    }
}