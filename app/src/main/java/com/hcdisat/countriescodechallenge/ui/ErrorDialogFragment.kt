package com.hcdisat.countriescodechallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hcdisat.countriescodechallenge.databinding.ErrorDialogBinding
import com.hcdisat.countriescodechallenge.ui.viewmodels.CountriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorDialogFragment: DialogFragment() {

    private val binding by lazy {
        ErrorDialogBinding.inflate(layoutInflater)
    }

    private val countriesViewModel: CountriesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind()

        return binding.root
    }

    private fun bind() {
        binding.errorMessage.text = countriesViewModel.errorMessage
        binding.btnDismiss.setOnClickListener { dismissAllowingStateLoss()}
    }

    companion object {
        const val TAG = "ERROR_DIALOG"

        fun newErrorDialogBinding() = ErrorDialogFragment().apply {
            setStyle(STYLE_NO_FRAME, 0)
        }
    }
}