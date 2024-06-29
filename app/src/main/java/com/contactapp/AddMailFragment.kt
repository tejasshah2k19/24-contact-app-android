package com.contactapp

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.contactapp.model.ContactModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddMailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_mail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextName = view.findViewById<EditText>(R.id.edt_contact_name)
        val editTextEmail = view.findViewById<EditText>(R.id.edt_email_address)
        val buttonAddContact = view.findViewById<Button>(R.id.btn_add_email_submit)

        buttonAddContact.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                addContact(name, email)
                Toast.makeText(view.context,"Contact Added",Toast.LENGTH_LONG).show()
                editTextEmail.text.clear()
                editTextName.text.clear()

            } else {
                // Show error message to the user
                Toast.makeText(view.context,"Please Fill All Values",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addContact(name: String, email: String) {
        val newContact = ContactModel(name,"EMAIL",email)
        val sharedPreferences = requireActivity().getSharedPreferences("contacts", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Get existing contacts
        val gson = Gson()
        val json = sharedPreferences.getString("contactList", null)
        val type = object : TypeToken<MutableList<ContactModel>>() {}.type
        val contactList: MutableList<ContactModel> = if (json == null) {
            mutableListOf()
        } else {
            gson.fromJson(json, type)
        }

        // Add new contact
        contactList.add(newContact)

        // Save updated contact list
        val updatedJson = gson.toJson(contactList)
        editor.putString("contactList", updatedJson)
        editor.apply()

        //we can redirect to list but we clear text and
        //user can add another contact

    }
}