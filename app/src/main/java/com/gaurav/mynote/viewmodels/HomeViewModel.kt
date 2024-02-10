package com.gaurav.mynote.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.mynote.data.NoteRepository
import com.gaurav.mynote.ui.uiState.HomeUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(repository: NoteRepository):ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        repository.getAllNoteStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )


    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

}