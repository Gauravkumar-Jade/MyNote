package com.gaurav.mynote

import android.app.Application
import com.gaurav.mynote.data.AppContainer
import com.gaurav.mynote.data.AppDataContainer

class NoteApplication:Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(this)
    }
}