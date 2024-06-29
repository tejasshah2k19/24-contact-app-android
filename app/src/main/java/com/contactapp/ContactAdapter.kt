package com.contactapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.contactapp.model.ContactModel

class ContactAdapter(private val contactList: List<ContactModel>,private val context:Context) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

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


        val (iconRes, lblText) = when (contact.type) {
            "EMAIL" -> R.drawable.email2 to "Tap to email"
            "PHONE" -> R.drawable.call to "Tap to call"
            else -> R.drawable.contact_info to ""
        }
        holder.imageViewIcon.setImageResource(iconRes)
        holder.textViewIconLabel.text = lblText

        holder.imageViewIcon.setOnClickListener {
            when (contact.type) {
                "EMAIL" -> openEmail(contact.contact)
                "PHONE" -> openCall(contact.contact)
            }
        }

    }

    override fun getItemCount() = contactList.size

      fun openEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "Subject here")
            putExtra(Intent.EXTRA_TEXT, "Body here")
        }
        context.startActivity(Intent.createChooser(emailIntent, "Send email using..."))
    }

      fun openCall(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(dialIntent)
    }

}
