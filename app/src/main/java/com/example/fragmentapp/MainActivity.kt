package com.example.fragmentapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var fm: FragmentManager
    lateinit var ft: FragmentTransaction
    lateinit var fr1: Fragment
    lateinit var fr2: Fragment
    lateinit var toFinishTask: Button
    lateinit var toCurrentTask: Button
    lateinit var weather_data: WeatherApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fm = supportFragmentManager
        ft = fm.beginTransaction()
        fr2 = FinishTaskFragment()
        val fr = fm.findFragmentById(R.id.container_fragm)
        if (fr == null) {
            fr1 = CurrentTaskFragment()
            fm.beginTransaction().add(R.id.container_fragm, fr1)
                .commit()
        } else
            fr1 = fr

        toCurrentTask = findViewById(R.id.currentTask)
        toFinishTask = findViewById(R.id.finishTask)

        toFinishTask.setOnClickListener {

            val ft = fm.beginTransaction()
            fr2=FinishTaskFragment.newInstance(
                weather_data.main.temp.toString(),
                weather_data.weather[0].main,
                weather_data.wind.deg.toString(),
                weather_data.wind.speed.toString(),
                weather_data.main.humidity.toString()
            )
            ft.replace(R.id.container_fragm, fr2)
            ft.commit() }

        toCurrentTask.setOnClickListener {
            val ft = fm.beginTransaction()
            fr1=CurrentTaskFragment.newInstance(
                weather_data.main.temp.toString(),
                weather_data.weather[0].main,
                weather_data.wind.deg.toString(),
                weather_data.wind.speed.toString(),
                weather_data.main.humidity.toString()
            )
            ft.replace(R.id.container_fragm, fr1)
            ft.commit() }
        lifecycleScope.launch (Dispatchers.IO) {
            loadWeather()
        }
    }
    suspend fun loadWeather() {
        val API_KEY = resources.getString(R.string.api) // TODO: ключ загрузить из строковых ресурсов
        // TODO: в строку подставлять API_KEY и город (выбирается из списка или вводится в поле)
        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=Irkutsk&appid="+API_KEY+"&units=metric";
        val stream = URL(weatherURL).getContent() as InputStream
        // JSON отдаётся одной строкой,
        //val data = Scanner(stream).nextLine()
        val gson = Gson() // конвеер для (де)сериализации
        weather_data = gson.fromJson(InputStreamReader(stream), WeatherApi::class.java)
        // TODO: предусмотреть обработку ошибок (нет сети, пустой ответ)
        //Log.d("mytag", data)
    }



}