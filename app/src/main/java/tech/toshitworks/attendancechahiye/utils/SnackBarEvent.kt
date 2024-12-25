package tech.toshitworks.attendancechahiye.utils

import androidx.compose.material3.SnackbarDuration

object Message{
    const val ADDING_DATA_TO_ROOM = "Adding data ... "
    const val DATA_ADDED_SUCCESSFULLY = "Data Added Successfully"
}

sealed class SnackBarEvent {
    data class ShowSnackBarForAddingData(
        val message: String = Message.ADDING_DATA_TO_ROOM,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarEvent()
    data class ShowSnackBarForDataAdded(
        val message: String = Message.DATA_ADDED_SUCCESSFULLY,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarEvent()
}
sealed class SnackBarWorkerEvent {
    data class ShowSnackBarForCSVWorker(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarWorkerEvent()
}