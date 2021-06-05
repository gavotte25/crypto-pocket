package com.example.cryptopocket.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.cryptopocket.MainViewModel
import com.example.cryptopocket.R
import com.example.cryptopocket.databinding.FragmentPocketBinding
import com.example.cryptopocket.utils.CurrencyRecyclerAdapter
import com.example.cryptopocket.utils.Listener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PocketFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPocketBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pocket, container, false)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        binding.viewModel = viewModel

        // Binding recycler view adapter
        val adapter = CurrencyRecyclerAdapter(false, Listener{
            viewModel.removeFromPocket(it)
        })
        binding.pocketRecycler.adapter = adapter

        //onClick Floating button setup
        binding.faBtn.setOnClickListener{
            findNavController().navigate(PocketFragmentDirections.actionPocketFragmentToSearchFragment())
        }

        //Inflate empty pocket view in sub layout if not currency in pocket
        viewModel.pocketItems.observe(viewLifecycleOwner, {
            binding.subLayout.visibility = when(it.count()) {
                0 -> View.VISIBLE
                else -> View.GONE
            }
        })

        return binding.root
    }
}