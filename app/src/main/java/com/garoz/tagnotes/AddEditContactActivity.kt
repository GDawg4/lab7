package com.garoz.tagnotes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.garoz.lab7.R
import kotlinx.android.synthetic.main.activity_add_contact.*
import java.io.ByteArrayOutputStream
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth



class AddEditContactActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.garoz.tagnotes.EXTRA_ID"
        const val EXTRA_TITLE = "com.garoz.tagnotes.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.garoz.tagnotes.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.garoz.tagnotes.EXTRA_PRIORITY"
        const val EXTRA_NUMBER = "com.garoz.tagnotes.EXTRA_NUMBER"
        const val EXTRA_PHOTO    = "com.garoz.tagnotes.EXTRA_PHOTO"
        private const val GALLERY = 1
        private const val CAMERA = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Contact"
            edit_text_title.setText(intent.getStringExtra(EXTRA_TITLE))
            edit_text_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)

            if (intent.getByteArrayExtra("savedContactPhoto") != null){
                Glide.with(this).load(intent.getByteArrayExtra("savedContactPhoto")).into(edit_photo)
            checkOrSetPermissions()
            }
        } else {
            title = "Add Contact"
        }

        val newImage = findViewById<ImageView>(R.id.edit_photo)
        newImage.setOnClickListener {
            pickPhotoFromDevice()
        }

    }

    private fun pickPhotoFromDevice(){

        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY )
    }

    private fun checkOrSetPermissions(){
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 112)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_contact -> {
                saveContact()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveContact() {


        if (edit_text_title.text.toString().trim().isBlank() || edit_text_description.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty contact!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, edit_text_title.text.toString())
            putExtra(EXTRA_DESCRIPTION, edit_text_description.text.toString())
            putExtra(EXTRA_PRIORITY, number_picker_priority.value)
            putExtra(EXTRA_NUMBER, edit_number.text.toString())


            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}