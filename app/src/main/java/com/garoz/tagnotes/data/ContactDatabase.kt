package com.garoz.tagnotes.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contact::class], version = 3)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao


    companion object {
        private var instance: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase? {
            if (instance == null) {
                synchronized(ContactDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java, "note_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: ContactDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val noteDao = db?.contactDao()

        override fun doInBackground(vararg p0: Unit?) {
            noteDao?.insert(Contact("name 1", "email 1", "1", 1))
            noteDao?.insert(Contact("name 2", "email 2", "2", 2))
            noteDao?.insert(Contact("name 3", "email 3", "3", 3))
        }
    }

}