package com.madarsoft.userinput

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.madarsoft.core.domain.model.Gender
import com.madarsoft.core.domain.model.User
import com.madarsoft.core.domain.model.ViewState
import com.madarsoft.userinput.databinding.FragmentUserInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInputFragment : Fragment() {

    private val viewModel: UserInputViewModel by viewModels()
    private val genderPickerBottomSheet = GenderPickerBottomSheet(::onGenderSelected)

    private var _binding: FragmentUserInputBinding? = null
    private val binding get() = _binding!!

    companion object {
        var navigateToOutput: () -> Unit = { }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            saveUser()
        }
        binding.genderEditText.setOnClickListener {
            genderPickerBottomSheet.show(childFragmentManager, GenderPickerBottomSheet.TAG)
        }
        observeViewState()
    }

    private fun saveUser() {
        val user = User().apply {
            name = binding.nameEditText.text.toString()
            age = binding.ageEditText.text.toString().toIntOrNull()
            jobTitle = binding.jobTitleEditText.text.toString()
            gender = Gender from binding.genderEditText.text.toString()
        }
        viewModel.saveUser(user)
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.Loading -> binding.addButton.isEnabled = false
                is ViewState.Success -> {
                    if (viewState.data)
                        navigateToOutput.invoke()
                    else
                        Toast.makeText(
                            binding.root.context,
                            getString(R.string.your_request_can_not_be_completed), Toast.LENGTH_LONG
                        ).show()
                    binding.addButton.isEnabled = false
                }

                is ViewState.Error -> {
                    Toast.makeText(binding.root.context, viewState.message, Toast.LENGTH_LONG)
                        .show()
                    binding.addButton.isEnabled = false
                }

                is ViewState.Idle -> binding.addButton.isEnabled = true
                is ViewState.InputError -> {
                    Toast.makeText(binding.root.context,
                        getString(R.string.enter_all_fields), Toast.LENGTH_LONG)
                        .show()
                    binding.addButton.isEnabled = true
                }
            }
            viewModel.clearViewState()
        }
    }

    private fun onGenderSelected(gender: Gender) {
        binding.genderEditText.text =
            when (gender) {
                Gender.MALE -> context?.getString(R.string.male)
                Gender.FEMALE -> context?.getString(R.string.female)
            }
    }
}