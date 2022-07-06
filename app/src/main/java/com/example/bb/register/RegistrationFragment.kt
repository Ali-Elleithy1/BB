package com.example.bb.register

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bb.R
import com.example.bb.database.BBDatabase
import com.example.bb.databinding.FragmentRegistrationBinding
import com.google.android.material.snackbar.Snackbar


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

        //Set exposed drop-down menu's adapter to the appropriate adapter in the view model
        (binding.purposeDropdown.editText as? AutoCompleteTextView)?.setAdapter(viewModel.purposesAdapter)
        (binding.countriesDropdown.editText as? AutoCompleteTextView)?.setAdapter(viewModel.countriesAdapter)
        (binding.governoratesDropdown.editText as? AutoCompleteTextView)?.setAdapter(viewModel.governoratesAdapter)
        (binding.casesDropdown.editText as? AutoCompleteTextView)?.setAdapter(viewModel.casesAdapter)


        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.registrationLayout)
        constraintSet.connect(
            R.id.register_button,
            ConstraintSet.TOP,
            R.id.c_password_container,
            ConstraintSet.BOTTOM,
            16
        )
        constraintSet.applyTo(binding.registrationLayout)

        //Purpose DropDown Validation
        binding.purposeDropdownAC.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateDropDown(binding.purposeDropdownAC.text.toString()).successful)
                {
                    viewModel.setIsPurposeValid(false)
                    binding.purposeDropdown.error = viewModel.validateDropDown(binding.purposeDropdownAC.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsPurposeValid(true)
                    binding.purposeDropdown.error = null
                }
            }
        }

        //Country DropDown Validation
        binding.countriesDropdownAC.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateDropDown(binding.countriesDropdownAC.text.toString()).successful)
                {
                    viewModel.setIsCountryValid(false)
                    binding.countriesDropdown.error = viewModel.validateDropDown(binding.countriesDropdownAC.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsCountryValid(true)
                    binding.countriesDropdown.error = null
                }
            }
        }

        //First Name Validation
        binding.fnameEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateName(binding.fnameEditText.text.toString()).successful)
                {
                    viewModel.setIsFNameValid(false)
                    binding.fnameContainer.error = viewModel.validateName(binding.fnameEditText.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsFNameValid(true)
                    binding.fnameContainer.error = null
                }
            }
        }

        //Last Name Validation
        binding.lnameEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateName(binding.lnameEditText.text.toString()).successful)
                {
                    viewModel.setIsLNameValid(false)
                    binding.lnameContainer.error = viewModel.validateName(binding.lnameEditText.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsLNameValid(true)
                    binding.lnameContainer.error = null
                }
            }
        }

        //Email Validation
        binding.rEmailEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateEmail(binding.rEmailEditText.text.toString()).successful)
                {
                    viewModel.setIsEmailValid(false)
                    binding.rEmailContainer.error = viewModel.validateEmail(binding.rEmailEditText.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsEmailValid(true)
                    binding.rEmailContainer.error = null
                }
            }
        }

        //Password Validation
        binding.rPasswordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validatePassword(binding.rPasswordEditText.text.toString()).successful)
                {
                    viewModel.setIsPasswordValid(false)
                    binding.rPasswordContainer.error = viewModel.validatePassword(binding.rPasswordEditText.text.toString()).errorMessage
                    //binding.rPasswordContainer.helperText = viewModel.validatePassword(binding.rPasswordEditText.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsPasswordValid(true)
                    binding.rPasswordContainer.error = null
                }

                if(!viewModel.validateConfirmPassword(binding.rPasswordEditText.text.toString(),binding.cPasswordEditText.text.toString()).successful)
                {
                    viewModel.setIsConfirmPasswordValid(false)
                    binding.cPasswordContainer.error = viewModel.validateConfirmPassword(binding.rPasswordEditText.text.toString(),binding.cPasswordEditText.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsConfirmPasswordValid(true)
                    binding.cPasswordContainer.error = null
                }
            }
        }

        //Confirm Password Validation
        binding.cPasswordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateConfirmPassword(binding.rPasswordEditText.text.toString(),binding.cPasswordEditText.text.toString()).successful)
                {
                    viewModel.setIsConfirmPasswordValid(false)
                    binding.cPasswordContainer.error = viewModel.validateConfirmPassword(binding.rPasswordEditText.text.toString(),binding.cPasswordEditText.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsConfirmPasswordValid(true)
                    binding.cPasswordContainer.error = null
                }
            }
        }

        //Mobile Validation
        binding.mobileEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validatePhoneNumber(binding.mobileEditText.text.toString()).successful)
                {
                    viewModel.setIsMobileValid(false)
                    binding.mobileContainer.error = viewModel.validatePhoneNumber(binding.mobileEditText.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsMobileValid(true)
                    binding.mobileContainer.error = null
                }
            }
        }

        //Case DropDown Validation
        binding.casesDropdownAC.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateDropDown(binding.casesDropdownAC.text.toString()).successful)
                {
                    viewModel.setIsCaseValid(false)
                    binding.casesDropdown.error = viewModel.validateDropDown(binding.casesDropdownAC.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsCaseValid(true)
                    binding.casesDropdown.error = null
                }
            }
        }

        //Governorate DropDown Validation
        binding.governorateDropdownAC.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateDropDown(binding.governorateDropdownAC.text.toString()).successful)
                {
                    viewModel.setIsGovernorateValid(false)
                    binding.governoratesDropdown.error = viewModel.validateDropDown(binding.governorateDropdownAC.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsGovernorateValid(true)
                    binding.governoratesDropdown.error = null
                }
            }
        }

        //NationalID Validation
        binding.nationalIdEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateNationalId(binding.nationalIdEditText.text.toString()).successful)
                {
                    viewModel.setIsNatIdValid(false)
                    binding.nationalIdContainer.error = viewModel.validateNationalId(binding.nationalIdEditText.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsNatIdValid(true)
                    binding.nationalIdContainer.error = null
                }
            }
        }

        //Age Validation
        binding.ageEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                if(!viewModel.validateAge(binding.ageEditText.text.toString()).successful)
                {
                    viewModel.setIsAgeValid(false)
                    binding.ageContainer.error = viewModel.validateAge(binding.ageEditText.text.toString()).errorMessage
                }
                else
                {
                    viewModel.setIsAgeValid(true)
                    binding.ageContainer.error = null
                }
            }
        }

        binding.countriesDropdownAC.addTextChangedListener {
            binding.mobileCodeEditAC.setText(viewModel.getCode(binding.countriesDropdownAC.text.toString()))
        }

        binding.purposeDropdownAC.addTextChangedListener {
            if(it.contentEquals("Patient"))
            {
                //1st
                setVisibility(binding)

                if(!viewModel.isButtonClicked) {
                    binding.registerButton.text = "Continue"
                }
            }
            else if(it.contentEquals("Donor"))
            {
                setVisibility(binding)

                if(!viewModel.isButtonClicked) {
                    binding.registerButton.text = "Register"
                }
                val constraintSet = ConstraintSet()
                constraintSet.clone(binding.registrationLayout)
                constraintSet.connect(
                    R.id.register_button,
                    ConstraintSet.TOP,
                    R.id.c_password_container,
                    ConstraintSet.BOTTOM,
                    16
                )
                constraintSet.applyTo(binding.registrationLayout)
            }
        }


        binding.registerButton.setOnClickListener { view ->

            hideSoftKeyboard(context as Activity, view)
            clearFocus(binding)

            if(binding.registerButton.text.equals("Continue"))
            {
                if(!viewModel.isFirstStepFormValid(viewModel.getIsPurposeValid(), viewModel.getIsCountryValid(), viewModel.getIsMobileValid(), viewModel.getIsFNameValid(),
                    viewModel.getIsLNameValid(),viewModel.getIsEmailValid(),viewModel.getIsPasswordValid(),viewModel.getIsConfirmPasswordValid()))
                {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),"Please, fill all fields properly.",Snackbar.LENGTH_LONG).show()
                    //Toast.makeText(context,"Please fill all fields correctly.",Toast.LENGTH_LONG).show()
                }
                else
                {
                    secondStepInRegistration(binding)
                }
            }
            else
            {
                if (binding.purposeDropdownAC.text.toString() == "Patient") {
                    if(!viewModel.isSecondStepFormValid(viewModel.getIsGovernorateValid(), viewModel.getIsCaseValid(), viewModel.getIsNatIdValid(), viewModel.getIsAgeValid()))
                    {
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),"Please, fill all fields properly.",Snackbar.LENGTH_LONG).show()
                        //Toast.makeText(context,"Please fill all fields correctly.",Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        viewModel.onRegister(
                            binding.fnameEditText.text.toString(),
                            binding.lnameEditText.text.toString(),
                            binding.rEmailEditText.text.toString(),
                            binding.mobileEditText.text.toString(),
                            binding.rPasswordEditText.text.toString(),
                            binding.governorateDropdownAC.text.toString(),
                            binding.casesDropdownAC.text.toString(),
                            binding.ageEditText.text.toString(),
                            binding.nationalIdEditText.text.toString(),
                            0
                        )
                    }
                } else {
                    if(!viewModel.isFirstStepFormValid(viewModel.getIsPurposeValid(), viewModel.getIsCountryValid(), viewModel.getIsMobileValid(), viewModel.getIsFNameValid(),
                            viewModel.getIsLNameValid(),viewModel.getIsEmailValid(),viewModel.getIsPasswordValid(),viewModel.getIsConfirmPasswordValid()))
                    {
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),"Please, fill all fields properly.",Snackbar.LENGTH_LONG).show()
                        //Toast.makeText(context,"Please fill all fields correctly.",Toast.LENGTH_LONG).show()
                    }
                    else {
                        viewModel.onRegister(
                            binding.fnameEditText.text.toString(),
                            binding.lnameEditText.text.toString(),
                            binding.rEmailEditText.text.toString(),
                            binding.mobileEditText.text.toString(),
                            binding.rPasswordEditText.text.toString(),
                            binding.governorateDropdownAC.text.toString(),
                            binding.casesDropdownAC.text.toString(),
                            "",
                            binding.nationalIdEditText.text.toString(),
                            1
                        )
                    }
                }
            }
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            viewModel.updateUserIdInCase()
            this.findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
        })
        return binding.root
    }

    private fun clearFocus(binding: FragmentRegistrationBinding) {
        binding.purposeDropdown.clearFocus()
        binding.countriesDropdown.clearFocus()
        binding.mobileContainer.clearFocus()
        binding.fnameContainer.clearFocus()
        binding.lnameContainer.clearFocus()
        binding.rEmailContainer.clearFocus()
        binding.rPasswordContainer.clearFocus()
        binding.cPasswordContainer.clearFocus()
        binding.governoratesDropdown.clearFocus()
        binding.casesDropdown.clearFocus()
        binding.nationalIdContainer.clearFocus()
        binding.ageContainer.clearFocus()
        binding.registerButton.requestFocus()
    }


    private fun secondStepInRegistration(binding: FragmentRegistrationBinding)
    {
        binding.fnameContainer.visibility = View.GONE
        binding.lnameContainer.visibility = View.GONE
        binding.mobileCodeEdit.visibility = View.GONE
        binding.rEmailContainer.visibility = View.GONE
        binding.rPasswordContainer.visibility = View.GONE
        binding.mobileContainer.visibility= View.GONE
        binding.cPasswordContainer.visibility = View.GONE
        binding.purposeDropdown.visibility = View.GONE
        binding.countriesDropdown.visibility = View.GONE

        //2nd
        binding.casesDropdown.visibility = View.VISIBLE
        binding.governoratesDropdown.visibility = View.VISIBLE
        binding.nationalIdContainer.visibility = View.VISIBLE
        binding.ageContainer.visibility = View.VISIBLE

        binding.registerButton.text = "Register"

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.registrationLayout)
        constraintSet.connect(
            R.id.register_button,
            ConstraintSet.TOP,
            R.id.age_container,
            ConstraintSet.BOTTOM,
            16
        )
        constraintSet.applyTo(binding.registrationLayout)
    }
    private fun setVisibility(binding: FragmentRegistrationBinding)
    {
        //Patient Views
        binding.casesDropdown.visibility = View.GONE
        binding.governoratesDropdown.visibility = View.GONE
        binding.nationalIdContainer.visibility = View.GONE
        binding.ageContainer.visibility = View.GONE

        //Donor Views
        binding.fnameContainer.visibility = View.VISIBLE
        binding.lnameContainer.visibility = View.VISIBLE
        binding.mobileCodeEdit.visibility = View.VISIBLE
        binding.rEmailContainer.visibility = View.VISIBLE
        binding.rPasswordContainer.visibility = View.VISIBLE
        binding.mobileContainer.visibility= View.VISIBLE
        binding.cPasswordContainer.visibility = View.VISIBLE
        binding.purposeDropdown.visibility = View.VISIBLE
        binding.countriesDropdown.visibility = View.VISIBLE
    }

    private fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}