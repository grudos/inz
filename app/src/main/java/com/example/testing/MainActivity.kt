package com.example.testing

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), AppNavigator {

    private val PERMISSION_REQUEST_CODE = 1
    private val PERMISSIONS = arrayOf(
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE)

        //val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)
        //toolbar.setNavigationOnClickListener { Toast.makeText(applicationContext,"Navigation icon was clicked",Toast.LENGTH_SHORT).show() }


        //setSupportActionBar(findViewById(R.id.toolbar))
        //val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        //val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_menu_24)
        //toolbar.overflowIcon = drawable
        //val menuIcon: ImageButton = toolbar.findViewById(R.id.menuIcon)

/*
        menuIcon.setOnClickListener {
            //Toast.makeText(MainActivity(), "Dziala", Toast.LENGTH_SHORT).show()
            showPopup(menuIcon)
        }
*/
        supportFragmentManager
            .beginTransaction()
            //.add(R.id.root, ToolbarFragment())
            .add(R.id.root, blind_help())
            .commit()

/*
        supportFragmentManager
            .beginTransaction()
            .add(R.id.root, blind_help())
            .commit()
*/



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
            }
            R.id.item2 -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
            }
            R.id.item3 -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun navigateToBlindCall() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root, BlindMakeCallFragment())
            .commit()
    }

    override fun navigateToBlindCallAccepted() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root, BlindCallAcceptedFragment())
            .commit()
    }

    override fun navigateToBlindCallAcceptedVolunteer() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root, StreamVideoAudioFragment())
            .commit()
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.options_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.item1 -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                }
                R.id.item2 -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                }
                R.id.item3 -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                }
            }

            true
        })

        popup.show()
    }

}