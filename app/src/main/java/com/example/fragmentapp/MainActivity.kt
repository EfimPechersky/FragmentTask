package com.example.fragmentapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
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
import java.util.Locale


class MainActivity : AppCompatActivity() {
    lateinit var fm: FragmentManager
    lateinit var ft: FragmentTransaction
    lateinit var fr1: Fragment
    lateinit var fr2: Fragment
    lateinit var toFinishTask: Button
    lateinit var toCurrentTask: Button
    lateinit var weather_data: WeatherApi
    lateinit var town:String
    val dialog = MyDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        town="Nothing"
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.languages)
        toolbar.setOnMenuItemClickListener { item ->
            var locale = Locale("ru")
            when(item.toString()){
                "Русский"->locale = Locale("ru")
                "English"->locale = Locale("en")
                else->locale = Locale("ru")
            }
            Locale.setDefault(locale)
            val config: Configuration = baseContext.resources.configuration
            config.locale = locale
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("town", town) // Передача данных
            startActivity(intent) // Запуск новой активности
            finish(); true }
        val extra = intent.getStringExtra("town")
        if (extra is String){
            town=extra
            Log.d("mytag","item: $town")
        }else {
            dialog.show(supportFragmentManager, "MyDialog")
        }
        lifecycleScope.launch (Dispatchers.IO) {
            loadWeather()
        }
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
            lifecycleScope.launch (Dispatchers.IO) {
                loadWeather()
            }
            if (town!="Nothing") {
                val ft = fm.beginTransaction()
                fr2 = FinishTaskFragment.newInstance(
                    weather_data.main.temp.toString(),
                    weather_data.weather[0].main,
                    weather_data.wind.deg.toString(),
                    weather_data.wind.speed.toString(),
                    weather_data.main.humidity.toString()
                )
                ft.replace(R.id.container_fragm, fr2)
                ft.commit()
            }else{
                val ft = fm.beginTransaction()
                fr2 = FinishTaskFragment.newInstance(
                    "Loading",
                    "Loading",
                    "Loading",
                    "Loading",
                    "Loading"
                )
                ft.replace(R.id.container_fragm, fr2)
                ft.commit()
            }
        }

        toCurrentTask.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO) {
                loadWeather()
            }

            if (town!="Nothing") {
                val ft = fm.beginTransaction()
                fr1 = CurrentTaskFragment.newInstance(
                    weather_data.main.temp.toString(),
                    weather_data.weather[0].main,
                    weather_data.wind.deg.toString(),
                    weather_data.wind.speed.toString(),
                    weather_data.main.humidity.toString()
                )
                ft.replace(R.id.container_fragm, fr1)
                ft.commit()
            }else{
                val ft = fm.beginTransaction()
                fr1 = CurrentTaskFragment.newInstance(
                    "Loading",
                    "Loading",
                    "Loading",
                    "Loading",
                    "Loading"
                )
                ft.replace(R.id.container_fragm, fr1)
                ft.commit()
            }
        }


    }
    suspend fun loadWeather() {
        if (town=="Nothing") {
            town = dialog.town
        }
        if (town!="Nothing") {
            val API_KEY = resources.getString(R.string.api) // TODO: ключ загрузить из строковых ресурсов
            // TODO: в строку подставлять API_KEY и город (выбирается из списка или вводится в поле)
            val weatherURL =
                "https://api.openweathermap.org/data/2.5/weather?q=" + town + "&appid=" + API_KEY + "&units=metric";
            val stream = URL(weatherURL).getContent() as InputStream
            // JSON отдаётся одной строкой,
            //val data = Scanner(stream).nextLine()
            val gson = Gson() // конвеер для (де)сериализации
            weather_data = gson.fromJson(InputStreamReader(stream), WeatherApi::class.java)
            // TODO: предусмотреть обработку ошибок (нет сети, пустой ответ)
            //Log.d("mytag", data)
        }
    }





}