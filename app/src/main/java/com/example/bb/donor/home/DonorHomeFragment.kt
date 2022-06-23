package com.example.bb.donor.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bb.R
import com.example.bb.database.BBDatabase
import com.example.bb.databinding.FragmentDonorHomeBinding
import com.example.bb.donor.adapters.CharityAdapter
import com.example.bb.donor.adapters.CharityClickListener

class DonorHomeFragment : Fragment() {
//    val args = DonorHomeFragmentArgs.fromBundle(requireArguments())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentDonorHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_donor_home,container,false)

        val application = requireNotNull(this.activity).application

        val dataSource = BBDatabase.getInstance(application).bbDatabaseDao

        val viewModelFactory = DonorHomeViewModelFactory(dataSource,application)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(DonorHomeViewModel::class.java)
        binding.donorHomeViewModel = viewModel

        binding.lifecycleOwner = this

        val adapter = CharityAdapter(CharityClickListener { 
            charityId -> viewModel.onCharityClicked(charityId)
        })

        viewModel.charities.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.charityList.adapter = adapter

        viewModel.navigateToCharityFragment.observe(viewLifecycleOwner, Observer { id ->
            id?.let {
                this.findNavController().navigate(DonorHomeFragmentDirections.actionDonorHomeFragmentToCharityFragment(id!!))
                viewModel.onCharityFragmentNavigated()
            }
        })

        binding.donateByCaseButton.setOnClickListener {
            binding.donateByCaseButton.isEnabled = false
            binding.donateByCaseCard.visibility = View.VISIBLE
        }
        return binding.root
    }
}