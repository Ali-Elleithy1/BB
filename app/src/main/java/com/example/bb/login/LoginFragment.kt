package com.example.bb.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.bb.R
import com.example.bb.database.BBDatabase
import com.example.bb.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = BBDatabase.getInstance(application).bbDatabaseDao
        val viewModelFactory = LoginViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.clearUser()
        binding.loginButton.setOnClickListener { view ->
            //viewModel.clearCharities()
            //viewModel.onInsertCharity()
            viewModel.onLogin(binding.lEmailEdit.editText?.text.toString(),binding.lPasswordEdit.editText?.text.toString())
        }

        viewModel.charities.observe(this.viewLifecycleOwner, Observer {
            if(it.isNullOrEmpty())
            {
                viewModel.onInsertCharity()
            }
        })

        viewModel.user.observe(viewLifecycleOwner, Observer {
            if(viewModel.user.value != null)
            {
                viewModel.saveCurrentUser()
                when(viewModel.user.value!!.purpose)
                {
                    0 -> this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToPatientHomeFragment())
                    1 -> this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDonorHomeFragment())
                }
            }
        })

        binding.signUpText.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        return binding.root
    }
}