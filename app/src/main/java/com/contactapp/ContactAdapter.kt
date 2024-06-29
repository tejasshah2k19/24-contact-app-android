package com.contactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.contactapp.model.ContactModel

class ContactAdapter(private val contactList: List<ContactModel>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.tv_db_name)
        val textViewEmail: TextView = view.findViewById(R.id.tv_db_detail)
        val imageViewIcon : ImageView = view.findViewById(R.id.imageViewIcon)
        val textViewIconLabel: TextView = view.findViewById(R.id.textViewIconLabel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.textViewName.text = contact.name
        holder.textViewEmail.text = contact.contact
//        val iconRes = when (contact.type) {
//            "EMAIL" -> R.drawable.email2
//            "PHONE" -> R.drawable.call
//            else -> R.drawable.contact_info
//        }


        val (iconRes, labelText) = when (contact.type) {
            "EMAIL" -> R.drawable.email2 to "Tap to email"
            "PHONE" -> R.drawable.call to "Tap to call"
            else -> R.drawable.contact_info to ""
        }
        holder.imageViewIcon.setImageResource(iconRes)
        holder.textViewIconLabel.text = labelText

    }

    override fun getItemCount() = contactList.size
}
