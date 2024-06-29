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
 * Use the [AddPhoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPhoneFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_add_phone, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPhoneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPhoneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextName = view.findViewById<EditText>(R.id.edt_contact_name_f3)
        val editPhoneNumber = view.findViewById<EditText>(R.id.edt_phone_number_f3)
        val buttonAddContact = view.findViewById<Button>(R.id.btn_add_phone_submit_f3)

        buttonAddContact.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val phoneNum = editPhoneNumber.text.toString().trim()

            if (name.isNotEmpty() && phoneNum.isNotEmpty()) {
                addContact(name, phoneNum)
                Toast.makeText(view.context,"Contact Added", Toast.LENGTH_LONG).show()
                editPhoneNumber.text.clear()
                editTextName.text.clear()

            } else {
                // Show error message to the user
                Toast.makeText(view.context,"Please Fill All Values", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addContact(name: String, phone: String) {
        val newContact = ContactModel(name, "PHONE", phone)
        val sharedPreferences =
            requireActivity().getSharedPreferences("contacts", Context.MODE_PRIVATE)
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
    }

}