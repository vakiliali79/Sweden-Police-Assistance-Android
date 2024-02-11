package com.alivakili.sweden_police.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alivakili.sweden_police.R
import com.alivakili.sweden_police.databinding.ActivityMainBinding
import com.alivakili.sweden_police.fragment.AboutFragment
import com.alivakili.sweden_police.fragment.IncidentsFragment
import com.alivakili.sweden_police.fragment.StationsFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var preFragment: Fragment = IncidentsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrFragment(IncidentsFragment())
        configureToolbar("Incidents")
//        binding.map.setOnClickListener(View.OnClickListener {
//            setCurrFragment(MapFragment())
//            configureToolbar("Map")
//
//        })

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.incidents -> {
                    setCurrFragment(IncidentsFragment())
                    configureToolbar("Incidents")
                    true
                }
                R.id.stations -> {
                    setCurrFragment(StationsFragment())
                    configureToolbar("Stations")
                    true
                }
                R.id.about -> {
                    setCurrFragment(AboutFragment())
                    configureToolbar("About")
                    true
                }

                else -> {
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
    }



    private fun configureToolbar(title:String){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            this.title=title
        }
    }

    private fun setCurrFragment(fragment : Fragment){

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,fragment)
            commit()
        }

    }
}