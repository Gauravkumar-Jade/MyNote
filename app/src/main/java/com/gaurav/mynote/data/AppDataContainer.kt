package com.gaurav.mynote.data

import android.content.Context


/**
 * [AppContainer] implementation that provides instance of [NoteRepositoryImpl]
 */
class AppDataContainer(private val context:Context):AppContainer {

    override val repository: NoteRepository by lazy{
        NoteRepositoryImpl(NoteDatabase.getDatabase(context).getNoteDao())
    }
}