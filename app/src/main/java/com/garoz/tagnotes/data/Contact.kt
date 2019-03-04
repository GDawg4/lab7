package com.garoz.tagnotes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "note_table")
data class Contact(

    var name: String,

    var email: String,

    var phone: String,

    var priority: Int

) {

    //does it matter if these are private or not?
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var photo: ByteArray? = null

}