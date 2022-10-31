package com.example.bb.donor.cases

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bb.R
import com.example.bb.database.BBDatabase
import com.example.bb.databinding.FragmentCaseBinding
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

class CaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        viewModel.case.observe(this.viewLifecycleOwner) {
            viewModel.retrieveCharity(it.charityId)
            binding.caseNumText.text = viewModel.case.value!!.id.toString()
            binding.caseDescriptionText.text = viewModel.case.value!!.description
            binding.totalAmountText.text = viewModel.case.value!!.totalDonations.toString()
            binding.amountNeddedText.text = viewModel.case.value!!.donationsNeeded.toString()
            binding.amountPaidText.text = viewModel.case.value!!.donationsPaid.toString()
            binding.criticalityLevelText.text = viewModel.case.value!!.criticality
        }

        viewModel.charity.observe(this.viewLifecycleOwner) {
            Picasso.get().load(viewModel.charity.value!!.image)
                .placeholder(R.drawable.ic_launcher_background).into(binding.charityLogoC)
        }

        binding.donateButton.setOnClickListener {
            showCustomDialog(viewModel)
        }
        return binding.root
    }

    private fun showCustomDialog(viewModel: CaseViewModel) {
        val dialog = Dialog(this.context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.donation_dialog)

        val minButton: Button = dialog.findViewById(R.id.min_button)
        val maxButton: Button = dialog.findViewById(R.id.max_button)
        val donateButton: Button = dialog.findViewById(R.id.d_donate_button)
        val dAmountContainer: TextInputLayout = dialog.findViewById(R.id.donation_container)

        minButton.setOnClickListener {
            if(viewModel.case.value != null)
            {
                if(viewModel.case.value!!.donationsNeeded>0)
                {
                    dAmountContainer.editText!!.setText("1.0")
                }
                else
                {
                    dAmountContainer.editText!!.setText("0.0")
                }
            }
        }

        maxButton.setOnClickListener {
            if(viewModel.case.value != null) {
                dAmountContainer.editText!!.setText(viewModel.case.value!!.donationsNeeded.toString())
            }
        }

        donateButton.setOnClickListener {
            dialog.dismiss()
            //viewModel.updateCase(dAmountEdit.text.toString().toDouble())
            showFinishDialog()
        }

        dialog.show()
    }
    private fun showFinishDialog()
    {
        val dialog = Dialog(this.context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.success_dialog)
        val closeButton: Button = dialog.findViewById(R.id.close_button)

        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}