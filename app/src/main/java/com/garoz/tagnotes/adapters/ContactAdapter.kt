package com.garoz.tagnotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.garoz.lab7.R
import com.garoz.tagnotes.data.Contact
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter : ListAdapter<Contact, ContactAdapter.ContactHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.name == newItem.name && oldItem.email == newItem.email
                        && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val itemViewOneContact: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactHolder(itemViewOneContact)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val currentContact: Contact = getItem(position)

        holder.textViewTitle.text = currentContact.name
        holder.textViewPriority.text = currentContact.priority.toString()
        holder.textViewDescription.text = currentContact.email
        holder.textViewNumber.text = currentContact.phone
    }

    fun getContactAt(position: Int): Contact {
        return getItem(position)
    }

    inner class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_title
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_description
        var textViewNumber: TextView = itemView.number

    }

    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
