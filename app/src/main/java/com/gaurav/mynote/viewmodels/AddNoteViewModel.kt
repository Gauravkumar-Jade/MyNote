package com.gaurav.mynote.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gaurav.mynote.data.NoteRepository
import com.gaurav.mynote.ui.uiState.NoteDetails
import com.gaurav.mynote.ui.uiState.NoteUiState
import com.gaurav.mynote.utils.toNote

class AddNoteViewModel(val repository: NoteRepository):ViewModel() {

    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateUiState(noteDetails: NoteDetails){
        noteUiState =
            NoteUiState(noteDetails = noteDetails, isValidEntry = isValidEntry(noteDetails))
    }

    private fun isValidEntry(noteDetails: NoteDetails = noteUiState.noteDetails):Boolean{
        return with(noteDetails){
            title.isNotBlank() && content.isNotBlank()
        }
    }


    suspend fun saveNote(){
        if(isValidEntry()){
            repository.insertNote(noteUiState.noteDetails.toNote())
        }
    }
}