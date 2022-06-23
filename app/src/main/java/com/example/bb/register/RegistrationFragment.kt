package com.example.bb.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.bb.R
import com.example.bb.database.BBDatabase
import com.example.bb.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRegistrationBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_registration,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = BBDatabase.getInstance(application).bbDatabaseDao
        val viewModelFactory = RegistrationViewModelFactory(dataSource,application)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(RegistrationViewModel::class.java)
        binding.registrationViewModel = viewModel
        binding.lifecycleOwner = this
        binding.purposesSpinner.adapter= viewModel.purposesAdapter
        binding.governoratesSpinner.adapter = viewModel.governoratesAdapter
        binding.casesSpinner.adapter = viewModel.casesAdapter
        binding.countriesSpinner.adapter = viewModel.countriesAdapter
        binding.mobileCodeSpinner.adapter = viewModel.mobileCodesAdapter
        binding.mobileCodeSpinner.isEnabled = false

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.registrationLayout)
        constraintSet.connect(
            R.id.register_button,
            ConstraintSet.TOP,
            R.id.confirmPassword_edit,
            ConstraintSet.BOTTOM,
            16
        )
        constraintSet.applyTo(binding.registrationLayout)

        binding.countriesSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.mobileCodeSpinner.setSelection(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.purposesSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p0!!.selectedItem.equals("Donor"))
                {
                    //Patient Views
                    binding.caseText.visibility = View.GONE
                    binding.casesSpinner.visibility = View.GONE
                    binding.governorateText.visibility = View.GONE
                    binding.governoratesSpinner.visibility = View.GONE
                    binding.nationalIdText.visibility = View.GONE
                    binding.nationalIdEdit.visibility = View.GONE
                    binding.ageText.visibility = View.GONE
                    binding.ageEdit.visibility = View.GONE

                    //Donor Views
                    binding.fnameText.visibility = View.VISIBLE
                    binding.fnameEdit.visibility = View.VISIBLE
                    binding.lnameText.visibility = View.VISIBLE
                    binding.lnameEdit.visibility = View.VISIBLE
                    binding.mobileText.visibility = View.VISIBLE
                    binding.mobileCodeSpinner.visibility = View.VISIBLE
                    binding.rEmailText.visibility = View.VISIBLE
                    binding.rEmailEdit.visibility = View.VISIBLE
                    binding.rPasswordEdit.visibility = View.VISIBLE
                    binding.mobileEdit.visibility= View.VISIBLE
                    binding.passwordText.visibility = View.VISIBLE
                    binding.rPasswordEdit.visibility = View.VISIBLE
                    binding.cPasswordText.visibility = View.VISIBLE
                    binding.confirmPasswordEdit.visibility = View.VISIBLE
                    if(!viewModel.isButtonClicked) {
                        binding.registerButton.text = "Register"
                    }
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(binding.registrationLayout)
                    constraintSet.connect(
                        R.id.register_button,
                        ConstraintSet.TOP,
                        R.id.confirmPassword_edit,
                        ConstraintSet.BOTTOM,
                        16
                    )
                    constraintSet.applyTo(binding.registrationLayout)

                }
                else if(p0!!.selectedItem.equals("Patient"))
                {
                    //1st
                    binding.fnameText.visibility = View.VISIBLE
                    binding.fnameEdit.visibility = View.VISIBLE
                    binding.lnameText.visibility = View.VISIBLE
                    binding.lnameEdit.visibility = View.VISIBLE
                    binding.mobileText.visibility = View.VISIBLE
                    binding.mobileCodeSpinner.visibility = View.VISIBLE
                    binding.rEmailText.visibility = View.VISIBLE
                    binding.rEmailEdit.visibility = View.VISIBLE
                    binding.rPasswordEdit.visibility = View.VISIBLE
                    binding.mobileEdit.visibility= View.VISIBLE
                    binding.passwordText.visibility = View.VISIBLE
                    binding.rPasswordEdit.visibility = View.VISIBLE
                    binding.cPasswordText.visibility = View.VISIBLE
                    binding.confirmPasswordEdit.visibility = View.VISIBLE
                    if(!viewModel.isButtonClicked) {
                        binding.registerButton.text = "Continue"
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        binding.registerButton.setOnClickListener { view ->
            //viewModel.isButtonClicked = true
            if(binding.registerButton.text.equals("Continue"))
            {
                binding.fnameText.visibility = View.GONE
                binding.fnameEdit.visibility = View.GONE
                binding.lnameText.visibility = View.GONE
                binding.lnameEdit.visibility = View.GONE
                binding.mobileText.visibility = View.GONE
                binding.mobileCodeSpinner.visibility = View.GONE
                binding.rEmailEdit.visibility = View.GONE
                binding.rEmailText.visibility = View.GONE
                binding.rPasswordEdit.visibility = View.GONE
                binding.mobileEdit.visibility= View.GONE
                binding.passwordText.visibility = View.GONE
                binding.rPasswordEdit.visibility = View.GONE
                binding.cPasswordText.visibility = View.GONE
                binding.confirmPasswordEdit.visibility = View.GONE

                //2nd
                binding.caseText.visibility = View.VISIBLE
                binding.casesSpinner.visibility = View.VISIBLE
                binding.governorateText.visibility = View.VISIBLE
                binding.governoratesSpinner.visibility = View.VISIBLE
                binding.nationalIdText.visibility = View.VISIBLE
                binding.nationalIdEdit.visibility = View.VISIBLE
                binding.ageText.visibility = View.VISIBLE
                binding.ageEdit.visibility = View.VISIBLE

                binding.registerButton.text = "Register"

                val constraintSet = ConstraintSet()
                constraintSet.clone(binding.registrationLayout)
                constraintSet.connect(
                    R.id.register_button,
                    ConstraintSet.TOP,
                    R.id.age_edit,
                    ConstraintSet.BOTTOM,
                    16
                )
                constraintSet.applyTo(binding.registrationLayout)
            }
            else
            {
                if(binding.purposesSpinner.selectedItem.equals("Patient"))
                {
                    viewModel.setPurpose(0)
                    viewModel.onRegister(binding.fnameEdit.text.toString(),binding.lnameEdit.text.toString(),binding.rEmailEdit.text.toString(),binding.mobileEdit.text.toString(),
                        binding.rPasswordEdit.text.toString(),binding.governoratesSpinner.selectedItem.toString(),binding.casesSpinner.selectedItem.toString(),binding.ageEdit.text.toString().toInt(),binding.nationalIdEdit.text.toString(),0)
                }
                else
                {
                    viewModel.setPurpose(1)
                    viewModel.onRegister(binding.fnameEdit.text.toString(),binding.lnameEdit.text.toString(),binding.rEmailEdit.text.toString(),binding.mobileEdit.text.toString(),
                        binding.rPasswordEdit.text.toString(),binding.governoratesSpinner.selectedItem.toString(),binding.casesSpinner.selectedItem.toString(),0,binding.nationalIdEdit.text.toString(),1)
                }
                //
            }
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            viewModel.updateUserIdInCase()
            this.findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
        })
        return binding.root
    }
    fun setVisibility(purpose:String, binding: FragmentRegistrationBinding)
    {
        if(binding.purposesSpinner.selectedItem.equals("Donor"))
        {
            //Patient Views
            binding.caseText.visibility = View.GONE
            binding.casesSpinner.visibility = View.GONE
            binding.governorateText.visibility = View.GONE
            binding.governoratesSpinner.visibility = View.GONE
            binding.nationalIdText.visibility = View.GONE
            binding.nationalIdEdit.visibility = View.GONE
            binding.ageText.visibility = View.GONE
            binding.ageEdit.visibility = View.GONE

            //Donor Views
            binding.fnameText.visibility = View.VISIBLE
            binding.fnameEdit.visibility = View.VISIBLE
            binding.lnameText.visibility = View.VISIBLE
            binding.lnameEdit.visibility = View.VISIBLE
            binding.mobileText.visibility = View.VISIBLE
            binding.mobileCodeSpinner.visibility = View.VISIBLE
            binding.rEmailText.visibility = View.VISIBLE
            binding.rEmailEdit.visibility = View.VISIBLE
            binding.rPasswordEdit.visibility = View.VISIBLE
            binding.mobileEdit.visibility= View.VISIBLE
            binding.passwordText.visibility = View.VISIBLE
            binding.rPasswordEdit.visibility = View.VISIBLE
            binding.cPasswordText.visibility = View.VISIBLE
            binding.confirmPasswordEdit.visibility = View.VISIBLE
            binding.registerButton.text = "Register"

        }
        else if(binding.purposesSpinner.selectedItem.equals("Patient"))
        {
            //1st
            binding.fnameText.visibility = View.VISIBLE
            binding.fnameEdit.visibility = View.VISIBLE
            binding.lnameText.visibility = View.VISIBLE
            binding.lnameEdit.visibility = View.VISIBLE
            binding.mobileText.visibility = View.VISIBLE
            binding.mobileCodeSpinner.visibility = View.VISIBLE
            binding.rEmailText.visibility = View.VISIBLE
            binding.rEmailEdit.visibility = View.VISIBLE
            binding.rPasswordEdit.visibility = View.VISIBLE
            binding.mobileEdit.visibility= View.VISIBLE
            binding.passwordText.visibility = View.VISIBLE
            binding.rPasswordEdit.visibility = View.VISIBLE
            binding.cPasswordText.visibility = View.VISIBLE
            binding.confirmPasswordEdit.visibility = View.VISIBLE
            binding.registerButton.text = "Continue"
        }
    }
}