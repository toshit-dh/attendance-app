package tech.toshitworks.attendancechahiye.presentation.screen.home_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
): ViewModel(){

    private val _isAddExtraAttendanceDialogOpen = MutableStateFlow(false)
    val isAddExtraAttendanceDialogOpen = _isAddExtraAttendanceDialogOpen.asStateFlow()

    fun floatingButtonClick(open: Boolean){
        _isAddExtraAttendanceDialogOpen.value = open
    }
}