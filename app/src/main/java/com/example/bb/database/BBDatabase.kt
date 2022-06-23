package com.example.bb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class,Case::class,Charity::class,Donation::class,CurrentUser::class], version = 1)
abstract class BBDatabase: RoomDatabase() {

    abstract val bbDatabaseDao: BBDatabaseDao

    companion object {
        @Volatile
        private var Instance: BBDatabase? = null

        fun getInstance(context:Context): BBDatabase
        {
            synchronized(this)
            {
                var instance = Instance
                if(instance == null)
                {
                    instance = Room.databaseBuilder(context.applicationContext,BBDatabase::class.java,"bb_database").fallbackToDestructiveMigration().build()
                    Instance = instance
                }
                return instance
            }
        }


    }

}