package com.example.showpilot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.showpilot.data.dao.ExpenseDao
import com.example.showpilot.data.dao.LodgingDao
import com.example.showpilot.data.dao.ShowDao
import com.example.showpilot.data.entities.Expense
import com.example.showpilot.data.entities.Lodging
import com.example.showpilot.data.entities.Show

@Database(
    entities = [Show::class, Expense::class, Lodging::class],
    version = 6
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun showDao(): ShowDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun lodgingDao(): LodgingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "show_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}