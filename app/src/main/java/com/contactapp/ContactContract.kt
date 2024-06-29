package com.contactapp

import android.net.Uri
import android.provider.BaseColumns

object ContactContract {
    const val AUTHORITY = "com.contactapp.ContactProvider"
    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/contacts")

    object ContactEntry : BaseColumns {
        const val TABLE_NAME = "contacts"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_TYPE = "type"
        const val COLUMN_NAME_CONTACT = "contact"

    }
}
