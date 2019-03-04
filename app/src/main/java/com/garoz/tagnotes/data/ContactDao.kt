package com.garoz.tagnotes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {

    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("DELETE FROM note_table")
    fun deleteAllContacts()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllContacts(): LiveData<List<Contact>>

}