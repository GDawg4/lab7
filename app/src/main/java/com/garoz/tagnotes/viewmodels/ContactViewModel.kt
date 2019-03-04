package com.garoz.tagnotes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.garoz.tagnotes.data.ContactRepository
import com.garoz.tagnotes.data.Contact

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ContactRepository =
        ContactRepository(application)
    private var allContacts: LiveData<List<Contact>> = repository.getAllContacts()

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun update(contact: Contact) {
        repository.update(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }

    fun deleteAllContacts() {
        repository.deleteAllContact()
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }
}