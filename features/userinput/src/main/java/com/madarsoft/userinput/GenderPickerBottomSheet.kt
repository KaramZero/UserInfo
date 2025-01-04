package com.madarsoft.userinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.madarsoft.core.domain.model.Gender
import com.madarsoft.userinput.databinding.BottomSheetGenderPickerBinding


class GenderPickerBottomSheet constructor(
    private val onGenderSelected: (Gender) -> Unit,
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetGenderPickerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetGenderPickerBinding.inflate(layoutInflater)
        (dialog as? BottomSheetDialog)?.behavior?.state = STATE_EXPANDED

        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun getTheme() = R.style.CustomBottomSheetDialogTheme

    override fun onResume() {
        super.onResume()
        binding.apply {
            backButton.setOnClickListener {
                dismiss()
            }
            maleButton.setOnClickListener {
                onGenderSelected.invoke(Gender.MALE)
                dismiss()
            }
            femaleButton.setOnClickListener {
                onGenderSelected.invoke(Gender.FEMALE)
                dismiss()
            }
        }
    }
}
