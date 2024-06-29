package com.contactapp

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.contactapp.model.ContactModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerViewContacts: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private var contactList: MutableList<ContactModel> = mutableListOf()


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
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewContacts = view.findViewById(R.id.recyclerViewContacts)
        recyclerViewContacts.layoutManager = LinearLayoutManager(context)
        contactAdapter = ContactAdapter(contactList,view.context)
        recyclerViewContacts.adapter = contactAdapter

        loadContacts()
//        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        val drawable = ContextCompat.getDrawable(requireContext(), @android.r.)
//        drawable?.let {
//            divider.setDrawable(it)
//            recyclerView.addItemDecoration(divider)
//        }
    }

    private fun loadContacts() {

        val sharedPreferences = requireActivity().getSharedPreferences("contacts", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("contactList", null)
        val type = object : TypeToken<MutableList<ContactModel>>() {}.type
        if (json != null) {
            val savedContacts: MutableList<ContactModel> = gson.fromJson(json, type)
            contactList.addAll(savedContacts)
         }

            contactAdapter.notifyDataSetChanged()
        }

}