package com.example.shchoolmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.shchoolmate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        var navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigation,navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi : MenuInflater = getMenuInflater()
        mi.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add){
            // Toast.makeText(this, "No hay suficiente cantidad", Toast.LENGTH_SHORT).show()
            //val fragmentManager = supportFragmentManager
            val intent = Intent(this, AddAct::class.java)
            this.startActivity(intent)
            //val dialog = Add()
            //dialog.show(supportFragmentManager, "Add Course")
        }
        return super.onOptionsItemSelected(item)
    }
}