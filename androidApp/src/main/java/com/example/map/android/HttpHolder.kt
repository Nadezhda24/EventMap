package com.example.map.android

import com.example.map.android.Models.Point
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*


class HttpHolder {
    private val httpClient = HttpClient()
    val url: String = "https://nominatim.openstreetmap.org/"
    val localhost: String = "http://danbla6h.beget.tech/api/"

    //по наименованию возвращает координаты
    suspend fun getSearchData(search: String): String{
        val response: HttpResponse = httpClient.get(url+"search/?format=json&q=${search}")
        return response.bodyAsText()
    }

    //по координатам возвращает наименование
    suspend fun getReverseData(address: Point): String{
        val response: HttpResponse = httpClient.get(url+"reverse?format=json&lat=${address.lat}&lon=${address.lon}")
        return response.bodyAsText()
    }

    suspend fun getEvents(): String{
        val response = httpClient.get("http://danbla6h.beget.tech/api/event")
        return response.bodyAsText(Charsets.UTF_8)
    }

    suspend fun getEvents(id: Int): String{
        val response = httpClient.get("http://danbla6h.beget.tech/api/event/${id}")
        return response.bodyAsText(Charsets.UTF_8)
    }


    suspend fun getCategory(): String{
        val response = httpClient.get("http://danbla6h.beget.tech/api/category")
        return response.bodyAsText(Charsets.UTF_8)
    }
}