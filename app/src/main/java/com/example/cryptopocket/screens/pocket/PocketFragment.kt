package com.example.cryptopocket.screens.pocket

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.cryptopocket.R
import com.example.cryptopocket.databinding.FragmentPocketBinding
import com.example.cryptopocket.utils.CurrencyRecyclerAdapter

class PocketFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPocketBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pocket, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        val viewModel = ViewModelProvider(this).get(PocketViewModel::class.java)
        binding.viewModel = viewModel

        // Binding recycler view adapter
        val adapter = CurrencyRecyclerAdapter()
        binding.pocketRecycler.adapter = adapter

        //onClick Floating button setup
        viewModel.isNavigatedToSearch.observe(viewLifecycleOwner, {
            if(it == true) {
                findNavController().navigate(PocketFragmentDirections.actionPocketFragmentToSearchFragment())
                viewModel.doneNavigation()
            }
        })



        //Inflate empty pocket view in sub layout if not currency in pocket
        viewModel.currencyList.observe(viewLifecycleOwner, {
            if((it.count()==0)) {
                val emptyPocketView = inflater.inflate(R.layout.empty_pocket, binding.subLayout, false)
                binding.subLayout.addView(emptyPocketView)
            }
        })

        return binding.root
    }
}