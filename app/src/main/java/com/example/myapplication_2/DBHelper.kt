package com.example.myapplication_2

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


data class RecipeModel(val id: Int, val dish: String, val ingredients: String, val instructions: String)
class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "C:\\Users\\Serj\\AndroidStudioProjects\\MyApplication_22\\app\\src\\main\\java\\com\\example\\myapplication_2\\my_database2.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS RecipeTable (
                id INTEGER PRIMARY KEY,
                dish TEXT,
                ingredients TEXT,
                instructions TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Implement any necessary database schema upgrades here
    }
}


class DatabaseHandler(private val context: Context) {

    private val dbHelper: DBHelper = DBHelper(context)

    @SuppressLint("Range")
    fun searchRecipesByIngredients(ingredientsList: List<String>): List<RecipeModel> {
//        val db = SQLiteDatabase.openDatabase("com/example/myapplication_2/my_database.db", null, SQLiteDatabase.OPEN_READWRITE)
        val db = dbHelper.readableDatabase
        val matchingRecipes = mutableListOf<RecipeModel>()

        val selectionArgs = Array(ingredientsList.size) { "%" + ingredientsList[it] + "%" }
        val selection = ingredientsList.joinToString(" OR ", transform = { "ingredients LIKE ?" })

        val query = "SELECT * FROM my_table WHERE $selection"
        val cursor = db.rawQuery(query, selectionArgs)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val dish = cursor.getString(cursor.getColumnIndex("dish"))
            val ingredients = cursor.getString(cursor.getColumnIndex("ingredients"))
            val instructions = cursor.getString(cursor.getColumnIndex("instructions"))

            val recipe = RecipeModel(id, dish, ingredients, instructions)
            matchingRecipes.add(recipe)
        }

        cursor.close()
        db.close()
        return matchingRecipes
    }
}