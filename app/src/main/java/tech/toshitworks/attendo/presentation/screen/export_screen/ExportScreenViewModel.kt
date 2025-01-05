package tech.toshitworks.attendo.presentation.screen.export_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.repository.CsvWorkRepository
import tech.toshitworks.attendo.utils.SnackBarWorkerEvent
import javax.inject.Inject

@HiltViewModel
class ExportScreenViewModel @Inject constructor(
    private val workManager: WorkManager,
    private val csvWorkRepository: CsvWorkRepository
): ViewModel() {

    private val _snackBarEvent = MutableSharedFlow<SnackBarWorkerEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    fun onExport(tables: List<String>){
        val uuid = csvWorkRepository.enqueueCsvWorker(tables)
        workManager.getWorkInfoByIdLiveData(uuid).observeForever { wi ->
            when (wi?.state) {
                WorkInfo.State.ENQUEUED -> {

                }

                WorkInfo.State.RUNNING -> {
                    viewModelScope.launch {
                        _snackBarEvent.emit(
                            SnackBarWorkerEvent.ShowSnackBarForCSVWorker(
                                "Export running..."
                            )
                        )
                    }
                }

                WorkInfo.State.SUCCEEDED -> {
                    viewModelScope.launch {
                        _snackBarEvent.emit(
                            SnackBarWorkerEvent.ShowSnackBarForCSVWorker(
                                "Export succeeded..."
                            )
                        )
                    }
                }

                WorkInfo.State.FAILED -> {
                    viewModelScope.launch {
                        _snackBarEvent.emit(
                            SnackBarWorkerEvent.ShowSnackBarForCSVWorker(
                                "Export failed..."
                            )
                        )
                    }
                }

                WorkInfo.State.BLOCKED -> {

                }

                WorkInfo.State.CANCELLED -> {

                }

                null -> {

                }
            }
        }
    }


}