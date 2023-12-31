package com.example.myapplication_2

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication_2.databinding.ActivityMainBinding
import com.example.myapplication_2.DBHelper
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var checkBox: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox
    private lateinit var checkBox6: CheckBox
    private lateinit var checkBox7: CheckBox
    private lateinit var checkBox8: CheckBox
    private lateinit var button: Button
    private lateinit var textView: TextView

    //recipes_tab
    private lateinit var recipes_button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val inputDB = this.assets.open("my_database2.db") // Open the database file from assets
        val outputDBPath = this.getDatabasePath("my_database2.db").absolutePath

        val outputDB = FileOutputStream(outputDBPath)

        val buffer = ByteArray(1024)
        var length: Int

        while (inputDB.read(buffer).also { length = it } > 0) {
            outputDB.write(buffer, 0, length)
        }

        outputDB.flush()
        outputDB.close()
        inputDB.close()

        val dbHandler = DatabaseHandler(this)

        checkBox = findViewById(R.id.checkBox)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)
        checkBox4 = findViewById(R.id.checkBox4)
        checkBox5 = findViewById(R.id.checkBox5)
        checkBox6 = findViewById(R.id.checkBox6)
        checkBox7 = findViewById(R.id.checkBox7)
        checkBox8 = findViewById(R.id.checkBox8)
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)
//        recipes_button = findViewById(R.id.generate_recipes)
//        var ingredients = mutableListOf<String>("")
        button.setOnClickListener {
            var totalAmount = 0
            val result = StringBuilder()
            val ingredientsList = mutableListOf<String>("")
            result.append("Your Selected items are:")
            if (checkBox.isChecked) {
//                result.append("\n Sandwitch 500 Rs")
                ingredientsList.add("Salt")
//                totalAmount += 500
            }
            if (checkBox2.isChecked) {
//                result.append("\n Pizza 1000 Rs")
                ingredientsList.add("Olive Oil")
//                totalAmount += 1000
            }
            if (checkBox3.isChecked) {
//                result.append("\n Burger 300 Rs")
//                totalAmount += 300
                ingredientsList.add("Water")
            }
            if (checkBox4.isChecked) {
//                result.append("\n Coffee 100 Rs")
//                totalAmount += 100
                ingredientsList.add("Yeast")
            }
            if (checkBox5.isChecked) {
//                result.append("\n Coffee 100 Rs")
//                totalAmount += 100
                ingredientsList.add("White Sugar")
            }
            if (checkBox6.isChecked) {
                ingredientsList.add("Bread Flour")
            }
            if (checkBox7.isChecked) {
                ingredientsList.add("All-purpose flour")
            }
            if (checkBox8.isChecked) {
                ingredientsList.add("Eggs")
            }

            val matchingRecipes = dbHandler.searchRecipesByIngredients(ingredientsList)
//            ingredients = ingredientsList
            val number = 0

//            result.append("\nTotal Price is:$totalAmount Rs")
//            textView.text = result.toString()
        }
//        recipes_button.setOnClickListener {
////            val matchingRecipes = dbHandler.searchRecipesByIngredients(ingredients)
//        }




        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}