package com.gaurav.mynote.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaurav.mynote.ui.MyNoteTopAppBar
import com.gaurav.mynote.R
import com.gaurav.mynote.ui.theme.MyNoteTheme
import com.gaurav.mynote.utils.AppViewModelProvider
import com.gaurav.mynote.viewmodels.EditNoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navigateBack:() -> Unit,
    navigateUp:() ->  Unit,
    modifier: Modifier = Modifier,
    canNavigateUp: Boolean = true,
    viewModel: EditNoteViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MyNoteTopAppBar(
                title = stringResource(id = R.string.edit_action),
                canNavigateBack = canNavigateUp,
                navigateUp = navigateUp
            )
        },
        modifier = modifier
    ) {
        AddNoteBody(
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = {viewModel.updateUiState(it)},
            onSaveClick = { coroutineScope.launch {
                viewModel.updateNote()
                navigateBack.invoke()
            } },
            modifier = Modifier.padding(it)
        )

    }
}

@Composable
@Preview(showSystemUi = true)
fun EditNoteScreenPreview(){
    MyNoteTheme {
        EditNoteScreen(navigateBack = {}, navigateUp = {  })
    }
}