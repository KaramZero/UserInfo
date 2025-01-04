package com.madarsoft.useroutput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.madarsoft.core.domain.model.Gender
import com.madarsoft.core.domain.model.ViewState
import com.madarsoft.useroutput.databinding.FragmentUserOutputBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserOutputFragment : Fragment() {

    private val viewModel: UserOutputViewModel by viewModels()

    private var _binding: FragmentUserOutputBinding? = null
    private val binding get() = _binding!!

    companion object {
        var navigateBack: () -> Unit = {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserOutputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            backButton.setOnClickListener {
                navigateBack.invoke()
            }
        }
        observeViewState()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    binding.apply {
                        nameTextView.text = viewState.data.name
                        ageTextView.text = viewState.data.age.toString()
                        jobTitleTextView.text = viewState.data.jobTitle
                        genderTextView.text =
                            when (viewState.data.gender) {
                                Gender.MALE -> getString(R.string.male)
                                Gender.FEMALE -> getString(R.string.female)
                                else -> "--"
                            }
                    }
                }

                is ViewState.Error -> {
                    Toast.makeText(binding.root.context, viewState.message, Toast.LENGTH_LONG)
                        .show()
                }

                is ViewState.Idle -> {}
                is ViewState.InputError -> {
                    Toast.makeText(binding.root.context, viewState.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
            viewModel.clearViewState()
        }
    }


}