package com.example.map.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.map.android.Models.Category
import com.example.map.android.Models.Point
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener

class SplashScreen : AppCompatActivity() {
    private val data =  HttpHolder()
    private val mainScope = MainScope()
    var EventList: ArrayList<Event> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        EventList.add(Event(1, "Новое событие",
            "21pgmipait@gmail.com",
            Category("ДТП", "#FFC618", "FFFFFF"),
            "12.12.2022 13:00",
            Point(52.9685433, 36.0692477),
            "",
            "Эксплуатация ИТ в границах АСУП показала потребность такого ПО посредствам которого оказалось бы возможным не только автоматизация производственных процессов, но и автоматизации управленческих процессов поддержания соответствия между целями предприятия его потенциальными возможностями и ситуацией на рынке на длительный период. Такая способность АСУП фактически открывает возможность формирования важнейшего конкурентного преимущества АСУП - оказание требуемого влияния на структуру рынка.\n"))



        Handler().postDelayed({
            //получение данных
            mainScope.launch {
                kotlin.runCatching {
                    data.getReverseData(EventList[0].point!!)
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
                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    intent.putExtra("EventList", EventList)
                    startActivity(intent)
                    finish()
                }.onFailure {
                    println("Ошибка: " + it.localizedMessage)
                }
            }


        }, 3000)

    }
}