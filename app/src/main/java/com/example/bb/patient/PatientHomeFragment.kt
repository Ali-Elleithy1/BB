package com.example.bb.patient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bb.R
import com.example.bb.database.BBDatabase
import com.example.bb.databinding.FragmentPatientHomeBinding

class PatientHomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPatientHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_patient_home,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = BBDatabase.getInstance(application).bbDatabaseDao
        val viewModelFactory = PatientViewModelFactory(dataSource,application)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(PatientViewModel::class.java)
        binding.patientViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getCase()
        viewModel.case.observe(viewLifecycleOwner, Observer {
            if(viewModel.case!=null)
            {
                Log.i("this",viewModel.case.value!![0].case.id.toString())
                binding.caseNumTV.text = viewModel.case.value!![0].case.id.toString()
                binding.natIdTV.text = viewModel.case.value!![0].user.natId.toString()
                binding.governorateTV.text = viewModel.case.value!![0].user.governorate
                binding.caseTV.text = viewModel.case.value!![0].user.category
                binding.ageTV.text = viewModel.case.value!![0].user.age.toString()
                binding.statusTV.text = viewModel.case.value!![0].case.status
            }
        })
        return binding.root
    }
}