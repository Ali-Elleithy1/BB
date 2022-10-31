package com.example.bb.donor.charity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.bb.R
import com.example.bb.database.BBDatabase
import com.example.bb.databinding.FragmentCharityBinding
import com.example.bb.donor.adapters.CaseAdapter
import com.example.bb.donor.adapters.CaseClickListener
import com.squareup.picasso.Picasso


class CharityFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentCharityBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_charity,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = BBDatabase.getInstance(application).bbDatabaseDao
        val viewModelFactory = CharityViewModelFactory(dataSource,application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(CharityViewModel::class.java)
        val args = CharityFragmentArgs.fromBundle(requireArguments())
        binding.charityViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.onRetrieveData(args.charityId)

        val adapter = CaseAdapter(CaseClickListener { CaseId ->
            viewModel.onCaseClicked(CaseId)
        })

        viewModel.cases.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.caseList.addItemDecoration(
            DividerItemDecoration(
                binding.caseList.context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.caseList.adapter = adapter

        viewModel.navigateToCaseFragment.observe(this.viewLifecycleOwner, Observer { id ->
            id?.let {
                this.findNavController().navigate(CharityFragmentDirections.actionCharityFragmentToCaseFragment(id!!))
                viewModel.onCaseyFragmentNavigated()
            }
        })

        viewModel.charity.observe(viewLifecycleOwner, Observer {
            binding.charityNameText.text = viewModel.charity.value!!.name
            Picasso.get().load(viewModel.charity.value!!.image).placeholder(R.drawable.ic_launcher_background).into(binding.charityImage)
            binding.charityDescriptionText.text = viewModel.charity.value!!.description
        })
        return binding.root
    }
}