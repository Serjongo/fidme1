package com.example.myapplication_2

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


data class RecipeModel(val id: Int, val dish: String, val ingredients: String, val instructions: String)
class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "my_database2"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val db = SQLiteDatabase.openOrCreateDatabase(
            "/data/user/0/com.example.myapplication_2/databases/my_database2.db", null
        )
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS my_table (
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
    fun searchRecipesByIngredients(ingredientsList: List<String>): List<String> {
//        val db = SQLiteDatabase.openDatabase("com/example/myapplication_2/my_database.db", null, SQLiteDatabase.OPEN_READWRITE)
        val db_path = context.getDatabasePath("my_database2.db").path
        val db = SQLiteDatabase.openDatabase(context.getDatabasePath("my_database2.db").path, null, SQLiteDatabase.OPEN_READWRITE)
//        val db = dbHelper.readableDatabase
        val matchingInstructions = mutableListOf<String>()

        val cursor = db.query(
            "my_table", // Replace with your actual table name
            null,         // All columns
            null,         // No specific selection
            null,         // No selection arguments
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val ingredientsInRecord = cursor.getString(cursor.getColumnIndex("ingredients"))
            val instructions = cursor.getString(cursor.getColumnIndex("instructions"))

            // Check if ingredients in this record are contained in the provided ingredients list
            val ingredientsInRecordList = ingredientsInRecord.split(",").map { it.trim() }
            if (ingredientsInRecordList.all { it in ingredientsList }) {
                matchingInstructions.add(instructions)
            }
        }

        cursor.close()
        db.close()
        return matchingInstructions
    }
}