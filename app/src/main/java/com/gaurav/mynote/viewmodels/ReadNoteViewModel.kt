package com.gaurav.mynote.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.mynote.data.NoteRepository
import com.gaurav.mynote.ui.screen.ReadNoteScreenDestination
import com.gaurav.mynote.ui.uiState.ReadNoteUiState
import com.gaurav.mynote.utils.toNote
import com.gaurav.mynote.utils.toNoteDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ReadNoteViewModel(savedStateHandle: SavedStateHandle, private val repository: NoteRepository):ViewModel() {

   private var noteId: String = checkNotNull(savedStateHandle["noteId"])

   val uiState: StateFlow<ReadNoteUiState> =
      repository.getNoteStream(noteId.toInt())
         .filterNotNull()
         .map {
            ReadNoteUiState(noteDetails = it.toNoteDetails())
         }
         .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ReadNoteUiState()
         )

   companion object {
      private const val TIMEOUT_MILLIS = 5_000L
   }


   suspend fun deleteNote(){
      repository.deleteNote(uiState.value.noteDetails.toNote())
   }
}