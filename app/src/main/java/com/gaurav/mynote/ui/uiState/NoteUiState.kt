package com.gaurav.mynote.ui.uiState


/**
 * Represents Ui State for an Note.
 */
data class NoteUiState(
    val noteDetails: NoteDetails = NoteDetails(),
    val isValidEntry: Boolean = false
)


data class NoteDetails(
    val id:Int = 0,
    val title: String = "",
    val content: String = "",
    var timestamp: String = ""
)
