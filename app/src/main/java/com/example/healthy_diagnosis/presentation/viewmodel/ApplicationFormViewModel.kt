package com.example.healthy_diagnosis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthy_diagnosis.domain.repositories.ApplicationFormRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ApplicationFormViewModel @Inject constructor(
    private val applicationFormRespository: ApplicationFormRespository
): ViewModel() {


}