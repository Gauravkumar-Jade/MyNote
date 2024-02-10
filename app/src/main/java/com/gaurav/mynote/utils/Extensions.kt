package com.gaurav.mynote.utils

import android.annotation.SuppressLint
import com.gaurav.mynote.model.Note
import com.gaurav.mynote.ui.uiState.NoteDetails
import com.gaurav.mynote.ui.uiState.NoteUiState
import java.text.SimpleDateFormat
import java.util.Date


/**
 * Extension function to convert [Long] value of timestamp to [String].
 */
@SuppressLint("SimpleDateFormat")
fun Long.getDateTime(): String{
    val sdf = SimpleDateFormat("dd/MM/yy, hh:mm a")
    val date = Date(this)
    return sdf.format(date)
}


/**
 * Extension function to convert [NoteDetails] to [Note].
 */
fun NoteDetails.toNote(): Note = Note(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp.toLongOrNull()?:0
)

/**
 * Extension function to convert [Note] to [NoteDetails].
 */
fun Note.toNoteDetails():NoteDetails = NoteDetails(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp.toString()
)


/**
 * Extension function to convert [Note] to [NoteUiState]
 */
fun Note.toNoteUiState(isEntryValid: Boolean):NoteUiState =  NoteUiState(
    noteDetails = this.toNoteDetails(),
    isValidEntry = isEntryValid
)