package tech.toshitworks.attendo.presentation.screen.onboarding_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class OnBoardingScreenViewModel
    @Inject constructor(
        private val dataStoreRepository: DataStoreRepository
    ): ViewModel() {

        fun onEvent(event: OnBoardingScreenEvents){
            when(event){
                is OnBoardingScreenEvents.OnNextClick -> {
                    viewModelScope.launch {
                        dataStoreRepository.saveScreenSelection(1)
                    }
                }
            }
        }

}