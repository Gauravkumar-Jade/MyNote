package com.gaurav.mynote.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.gaurav.mynote.ui.MyNoteTopAppBar
import com.gaurav.mynote.R
import com.gaurav.mynote.ui.theme.MyNoteTheme
import com.gaurav.mynote.ui.uiState.NoteDetails
import com.gaurav.mynote.ui.uiState.NoteUiState
import com.gaurav.mynote.utils.AppViewModelProvider
import com.gaurav.mynote.viewmodels.AddNoteViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    navigateToHome:() -> Unit,
    navigateUp:() -> Unit,
    canNavigateUp: Boolean = true,
    viewModel: AddNoteViewModel = viewModel(factory = AppViewModelProvider.Factory)
){

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MyNoteTopAppBar(
                title = stringResource(R.string.add_note),
                canNavigateBack = canNavigateUp,
                navigateUp = navigateUp
            )
        }
    ){
        AddNoteBody(
            noteUiState = viewModel.noteUiState,
            onNoteValueChange = {
                                viewModel.updateUiState(it)
            }, onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveNote()
                    navigateToHome.invoke()
                }
            },
            modifier = Modifier.padding(it)
        )
    }
}


@Composable
fun AddNoteBody(
    noteUiState: NoteUiState,
    onNoteValueChange:(NoteDetails) -> Unit,
    onSaveClick:() -> Unit,
    modifier: Modifier = Modifier
){
    Column {
        AddNoteForm(
            noteDetails = noteUiState.noteDetails,
            onValueChange = onNoteValueChange,
            modifier = modifier
                .weight(1f)
        )
        Button(onClick = {
            saveCurrentTimestamp(noteUiState)
            onSaveClick.invoke()
        },
            enabled = noteUiState.isValidEntry,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(text = stringResource(R.string.save_action).uppercase(),
                style = MaterialTheme.typography.bodyLarge)
        }

    }

}


@Composable
fun AddNoteForm(
    noteDetails: NoteDetails,
    modifier: Modifier = Modifier,
    onValueChange:(NoteDetails) -> Unit = {},
  ){
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        OutlinedTextField(
            value = noteDetails.title,
            onValueChange = {onValueChange(noteDetails.copy(title = it))},
            label = { Text(text = stringResource(id = R.string.note_title))},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            textStyle = MaterialTheme.typography.headlineSmall,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
            )

        OutlinedTextField(
            value = noteDetails.content,
            onValueChange = {onValueChange(noteDetails.copy(content = it))},
            label = { Text(text = stringResource(id = R.string.note_content))},
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            shape = MaterialTheme.shapes.small,
            textStyle = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

private fun saveCurrentTimestamp(noteUiState: NoteUiState) {
    val timeStamp = System.currentTimeMillis()
    noteUiState.noteDetails.timestamp = timeStamp.toString()
}


@Composable
@Preview(showBackground = true,
    showSystemUi = true)
fun AddNoteFormPreview(){
    MyNoteTheme {
        AddNoteForm(
            NoteDetails()
        )
    }
}

@Composable
@Preview(showBackground = true,
    showSystemUi = true)
fun AddNoteBodyPreview(){
    MyNoteTheme {
        AddNoteBody(noteUiState = NoteUiState(), onNoteValueChange = {}, onSaveClick = {  })
    }
}

@Composable
@Preview(showBackground = true,
    showSystemUi = true)
fun AddNotePreview(){
    MyNoteTheme {
        AddNoteScreen(navigateToHome = { /*TODO*/ }, navigateUp = { /*TODO*/ })
    }
}