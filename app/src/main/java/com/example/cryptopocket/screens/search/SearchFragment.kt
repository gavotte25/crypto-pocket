package com.example.cryptopocket.screens.search

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.cryptopocket.R
import com.example.cryptopocket.databinding.FragmentPocketBinding
import com.example.cryptopocket.databinding.FragmentSearchBinding
import com.example.cryptopocket.utils.CurrencyRecyclerAdapter
import com.example.cryptopocket.utils.Listener

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        setHasOptionsMenu(true)
        val viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())||super.onOptionsItemSelected(item)
    }
}