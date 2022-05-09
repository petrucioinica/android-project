package com.example.myapp


import android.os.Bundle

import android.view.*

import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.itemslist.ItemCard
import com.example.myapp.itemslist.ItemsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {

    private var itemsList = mutableListOf<ItemCard>()

    private lateinit var itemsRecView: RecyclerView
    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        val testItemsList = mutableListOf(ItemCard("Some Title", true), ItemCard("Another Title", false))
        if(itemsList.isEmpty()){
            itemsList.addAll(testItemsList)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val homeView = inflater.inflate(R.layout.fragment_home, container, false)


        itemsRecView = homeView.findViewById(R.id.rvItems)
        itemsAdapter = ItemsAdapter(itemsList)
        itemsRecView.adapter = itemsAdapter

        val takePictureButton = homeView.findViewById<FloatingActionButton>(R.id.takePictureButton)

         val navController = findNavController()

        takePictureButton.setOnClickListener {
            navController.navigate(R.id.createSnapFragment)

        }
        return homeView
    }


    fun  filterItems(searchParam: String){
        val filteredItems = mutableListOf<ItemCard>()
        filteredItems.addAll(itemsList.filter { it.title.uppercase(Locale.getDefault()).contains(
            searchParam.uppercase(Locale.getDefault())
        ) })
        if (searchParam == ""){
            itemsAdapter.filterList(itemsList)
        }

        if(searchParam != "" && filteredItems.isEmpty()){
            Toast.makeText(this.context, "No Data Found..", Toast.LENGTH_SHORT).show()
        }
        itemsAdapter.filterList(filteredItems)

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)

        val item: MenuItem = menu.findItem(R.id.action_search)

        // getting search view of our item.
        val searchView: SearchView = item.actionView as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // inside on query text change method we are
                // calling a method to filter our recycler view.
                if( newText !== null){
                    filterItems(newText)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}