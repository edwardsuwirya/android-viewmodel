package com.enigmacamp.myviewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.enigmacamp.myviewmodel.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    lateinit var sharedViewModel: MainActivityViewModel
    lateinit var viewModel: RegistrationFragmentViewModel
    lateinit var binding: FragmentRegistrationBinding
    lateinit var loadingDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loadingDialog = LoadingDialog.build(requireContext())
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        binding.apply {
            registrationButton.setOnClickListener {
                val personName = nameEditText.text
                viewModel.inputNameValidation(personName.toString())
            }
        }
        return binding.root
    }

    private fun initViewModel() {
        sharedViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        viewModel = ViewModelProvider(this).get(RegistrationFragmentViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.isNameValid.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> loadingDialog.show()
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    sharedViewModel.sayHello("Hi...")
                    findNavController().navigate(R.id.action_registrationFragment_to_helloFragment)
                }
                ResourceStatus.FAIL -> {
                    loadingDialog.hide()
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegistrationFragment()
    }
}