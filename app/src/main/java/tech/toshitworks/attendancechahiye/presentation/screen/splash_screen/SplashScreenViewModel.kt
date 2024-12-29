package tech.toshitworks.attendancechahiye.presentation.screen.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.repository.DataStoreRepository
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _screenRoute = MutableStateFlow(ScreenRoutes.OnBoardingScreen.route)
    val screenRoute = _screenRoute.asStateFlow()

    init {
        viewModelScope.launch {
            val screenSelection = dataStoreRepository.readScreenSelection()
            _screenRoute.value = when (screenSelection) {
                0 -> ScreenRoutes.OnBoardingScreen.route
                1 -> ScreenRoutes.FormScreen.route
                2 -> ScreenRoutes.TimetableScreen.route
                3 -> ScreenRoutes.HomeScreen.route
                else -> ScreenRoutes.OnBoardingScreen.route
            }
            delay(200)
            _isLoading.value = false
        }
    }
}
