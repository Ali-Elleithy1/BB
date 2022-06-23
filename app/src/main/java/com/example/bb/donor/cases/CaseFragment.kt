package com.example.bb.donor.cases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bb.R
import com.example.bb.database.BBDatabase
import com.example.bb.databinding.FragmentCaseBinding
import com.squareup.picasso.Picasso

class CaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = CaseFragmentArgs.fromBundle(requireArguments())
        // Inflate the layout for this fragment
        val binding: FragmentCaseBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_case,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = BBDatabase.getInstance(application).bbDatabaseDao
        val viewModelFactory = CaseViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(CaseViewModel::class.java)
        binding.caseViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.onRetrieveCase(args.caseId)

        viewModel.case.observe(this.viewLifecycleOwner, Observer {
            viewModel.retrieveCharity(it.charityId)
            binding.caseNumText.text = viewModel.case.value!!.id.toString()
            binding.caseDescriptionText.text = viewModel.case.value!!.description
            binding.totalAmountText.text = viewModel.case.value!!.totalDonations.toString()
            binding.amountNeddedText.text = viewModel.case.value!!.donationsNeeded.toString()
            binding.amountPaidText.text = viewModel.case.value!!.donationsPaid.toString()
            binding.criticalityLevelText.text = viewModel.case.value!!.criticality
        })

        viewModel.charity.observe(this.viewLifecycleOwner, Observer {
            Picasso.get().load(viewModel.charity.value!!.image).placeholder(R.drawable.ic_launcher_background).into(binding.charityLogoC)
        })
        return binding.root
    }
}