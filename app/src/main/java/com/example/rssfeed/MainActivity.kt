package com.example.rssfeed

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssfeed.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var listQuestions: List<Questions>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listQuestions = listOf()

        binding.recycle.adapter = RecycleAdpter(listQuestions , this)
       binding.recycle.layoutManager= LinearLayoutManager(this)

        parseRSS()
    }

    private fun parseRSS() {
        CoroutineScope(IO).launch {
            val data = async {
                val parser = XmlParser()
                parser.parse()
            }.await()
            try{
                withContext(Main){
                    binding.recycle.adapter = RecycleAdpter(data , applicationContext)
                    binding.recycle.adapter?.notifyDataSetChanged()
                }
            }catch(e: java.lang.Exception){
                Log.d("MAIN", "Unable to get data")
            }
        }
    }
}