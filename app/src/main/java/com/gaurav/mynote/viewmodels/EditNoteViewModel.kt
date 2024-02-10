package com.gaurav.mynote.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.mynote.data.NoteRepository
import com.gaurav.mynote.ui.uiState.NoteDetails
import com.gaurav.mynote.ui.uiState.NoteUiState
import com.gaurav.mynote.utils.toNote
import com.gaurav.mynote.utils.toNoteUiState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditNoteViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NoteRepository):ViewModel() {

    private var noteId: String = checkNotNull(savedStateHandle["noteId"])

    var noteUiState by mutableStateOf(NoteUiState())
        private set


    init{
        viewModelScope.launch {
            noteUiState = repository.getNoteStream(noteId.toInt())
                .filterNotNull()
                .first()
                .toNoteUiState(true)
        }
    }


    /**
     * Update the note in the [NoteRepository]'s data source
     */
    suspend fun updateNote(){
        if(isValidEntry(noteUiState.noteDetails)){
            repository.updateNote(noteUiState.noteDetails.toNote())
        }
    }

    /**
     * Updates the [noteUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(noteDetails: NoteDetails){
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isValidEntry = isValidEntry(noteDetails))
    }

    private fun isValidEntry(noteDetails: NoteDetails = noteUiState.noteDetails):Boolean{
        return with(noteDetails){
            title.isNotBlank() && content.isNotBlank()
        }
    }


}