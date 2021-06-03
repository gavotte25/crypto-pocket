package com.example.cryptopocket.screens.search

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.cryptopocket.R
import com.example.cryptopocket.databinding.FragmentSearchBinding
import com.example.cryptopocket.utils.CurrencyRecyclerAdapter
import com.example.cryptopocket.utils.Listener

class SearchFragment : Fragment() {

    lateinit var viewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.viewModel = viewModel

        // Binding recycler view adapter
        val adapter = CurrencyRecyclerAdapter(true, Listener{
            Toast.makeText(context, "${it.name} has been added to your pocket!", Toast.LENGTH_SHORT).show()
        })
        binding.pocketRecycler.adapter = adapter

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_option_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.filter(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.filter(newText)
                    return true
                }
            })
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())||super.onOptionsItemSelected(item)
    }
}