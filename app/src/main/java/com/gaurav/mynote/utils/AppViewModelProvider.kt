package com.gaurav.mynote.utils

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gaurav.mynote.NoteApplication
import com.gaurav.mynote.viewmodels.AddNoteViewModel
import com.gaurav.mynote.viewmodels.EditNoteViewModel
import com.gaurav.mynote.viewmodels.HomeViewModel
import com.gaurav.mynote.viewmodels.ReadNoteViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(noteApplication().container.repository)
        }
        initializer {
            AddNoteViewModel(noteApplication().container.repository)
        }
        initializer {
            ReadNoteViewModel(this.createSavedStateHandle(),
                noteApplication().container.repository)
        }
        initializer {
            EditNoteViewModel(this.createSavedStateHandle(),
                noteApplication().container.repository)
        }

    }

    private fun CreationExtras.noteApplication():NoteApplication{
        return (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteApplication)
    }
}