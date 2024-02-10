package com.gaurav.mynote.navigation

import androidx.annotation.StringRes
import com.gaurav.mynote.R

enum class NoteScreens(@StringRes val title: Int) {
    HomeScreen(title = R.string.app_name),
    AddScreen(title = R.string.create_note),
    ReadScreen(title = R.string.app_name),
    UpdateScreen(title = R.string.update_note)
}