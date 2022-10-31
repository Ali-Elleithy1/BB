package com.example.bb.donor.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.bb.R
import com.example.bb.database.BBDatabase
import com.example.bb.databinding.FragmentDonorHomeBinding
import com.example.bb.databinding.FragmentRegistrationBinding
import com.example.bb.donor.adapters.CharityAdapter
import com.example.bb.donor.adapters.CharityClickListener
import com.google.android.material.snackbar.Snackbar

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

        binding.caseNumberEdit.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateCaseNumber(binding.caseNumberEdit.text.toString()).successful)
                {
                    viewModel.setIsCaseNumValid(false)
                    binding.caseNumberContainer.error = viewModel.validateCaseNumber(binding.caseNumberEdit.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsCaseNumValid(true)
                    binding.caseNumberContainer.error = null
                }
            }
        }

        binding.viewCaseButton.setOnClickListener {
            hideSoftKeyboard(context as Activity, it)
            clearFocus(binding)
            if(viewModel.getIsCaseNumValid()) {
                viewModel.getCase(binding.caseNumberEdit.text.toString().toLong())
            }
            else
            {
                Snackbar.make(requireActivity().findViewById(android.R.id.content),"Invalid case number!",Snackbar.LENGTH_LONG).show()
            }
        }


        viewModel.case.observe(viewLifecycleOwner, Observer {
            if(viewModel.case.value != null)
            {
                //viewModel.onViewCaseClicked(binding.caseNumberEdit.text.toString().toLong())
                this.findNavController().navigate(DonorHomeFragmentDirections.actionDonorHomeFragmentToCaseFragment(viewModel.case.value!!.id))
                viewModel.onCaseFragmentNavigated()
            }
        })
        return binding.root
    }

    private fun clearFocus(binding: FragmentDonorHomeBinding) {
        binding.caseNumberContainer.clearFocus()
        binding.viewCaseButton.requestFocus()
    }

    private fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}