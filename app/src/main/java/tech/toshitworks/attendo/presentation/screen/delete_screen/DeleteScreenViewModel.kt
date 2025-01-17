package tech.toshitworks.attendo.presentation.screen.delete_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.toshitworks.attendo.data.local.AttendanceDatabase
import tech.toshitworks.attendo.domain.repository.DataStoreRepository
import tech.toshitworks.attendo.utils.SnackBarDeleteEvent
import javax.inject.Inject

@HiltViewModel
class DeleteScreenViewModel @Inject constructor(
    private val attendanceDatabase: AttendanceDatabase,
    private val dataStoreRepository: DataStoreRepository,
    private val workManager: WorkManager
): ViewModel() {

    private val _event = MutableSharedFlow<SnackBarDeleteEvent>()
    val event = _event.asSharedFlow()

    fun onDelete(){
        viewModelScope.launch {
            try {
                _event.emit(
                    SnackBarDeleteEvent.ShowSnackBarForDeletingData()
                )
                withContext(Dispatchers.IO){
                    attendanceDatabase.clearAllTables()
                }
                dataStoreRepository.onDelete()
                workManager.cancelAllWork()
                _event.emit(
                    SnackBarDeleteEvent.ShowSnackBarForDataDeleted()
                )
            }catch (e: Exception){
                _event.emit(
                    SnackBarDeleteEvent.ShowSnackBarForDataNotDeleted()
                )
                Log.e("deleting exception: ", e.message ?: "null message")
            }

        }
    }

}