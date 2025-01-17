package tech.toshitworks.attendo.utils

import androidx.compose.material3.SnackbarDuration

object Message{
    const val ADDING_DATA_TO_ROOM = "Adding data ... "
    const val DATA_NOT_ADDED = "Error adding data"
    const val DATA_ADDED_SUCCESSFULLY = "Data Added Successfully"
    const val NO_CHANGES_MADE = "No changes made"
    const val DELETING_DATA = "Deleting data ... "
    const val DATA_NOT_DELETED = "Error deleting data"
    const val DATA_DELETED = "Data deleted successfully"
}

sealed class SnackBarAddEvent {
    data class ShowSnackBarForAddingData(
        val message: String = Message.ADDING_DATA_TO_ROOM,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarAddEvent()
    data class ShowSnackBarForDataAdded(
        val message: String = Message.DATA_ADDED_SUCCESSFULLY,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarAddEvent()
    data class ShowSnackBarForDataNotAdded(
        val message: String = Message.DATA_NOT_ADDED,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarAddEvent()
}
sealed class SnackBarWorkerEvent {
    data class ShowSnackBarForCSVWorker(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarWorkerEvent()
}
sealed class SnackBarEditEvent {
    data class ShowSnackBarForNoChange(
        val message: String = Message.NO_CHANGES_MADE,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarEditEvent()
    data class ShowSnackBarForDataEdited(
        val message: String = Message.DATA_ADDED_SUCCESSFULLY,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarEditEvent()
}
sealed class SnackBarDeleteEvent{
    data class ShowSnackBarForDeletingData(
        val message: String = Message.DELETING_DATA,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ): SnackBarDeleteEvent()
    data class ShowSnackBarForDataDeleted(
        val message: String = Message.DATA_DELETED,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarDeleteEvent()
    data class ShowSnackBarForDataNotDeleted(
        val message: String = Message.DATA_NOT_DELETED,
        val duration: SnackbarDuration = SnackbarDuration.Indefinite
    ) : SnackBarDeleteEvent()

}