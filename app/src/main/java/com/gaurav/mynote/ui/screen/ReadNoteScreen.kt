package com.gaurav.mynote.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gaurav.mynote.ui.MyNoteTopAppBar
import com.gaurav.mynote.R
import com.gaurav.mynote.model.Note
import com.gaurav.mynote.ui.theme.MyNoteTheme
import com.gaurav.mynote.ui.uiState.ReadNoteUiState
import com.gaurav.mynote.utils.AppViewModelProvider
import com.gaurav.mynote.utils.toNote
import com.gaurav.mynote.viewmodels.ReadNoteViewModel
import kotlinx.coroutines.launch


object ReadNoteScreenDestination{
    const val itemIdArg = "noteId"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadNoteScreen(
    navigateToUpdate:(Int) -> Unit,
    navigateUp:() -> Unit,
    canNavigateUp: Boolean = true,
    viewModel: ReadNoteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory)
){
    val readNoteUiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MyNoteTopAppBar(
                title = stringResource(id = R.string.app_name),
                canNavigateBack = canNavigateUp,
                navigateUp = navigateUp
            )
        }
    ) {
        ReadNoteBody(readNoteUiState = readNoteUiState.value,
            onEditNote = {
                         navigateToUpdate.invoke(readNoteUiState.value.noteDetails.id)
            },
            onDeleteNote = {
                coroutineScope.launch {
                    viewModel.deleteNote()
                    navigateUp.invoke()
                }
            },
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        )
    }

}

@Composable
fun ReadNoteBody(
    readNoteUiState: ReadNoteUiState,
    onEditNote:() -> Unit,
    onDeleteNote:() -> Unit,
    modifier: Modifier = Modifier
){
    var deleteConfirmationRequired by remember {
        mutableStateOf(false)
    }
    Column {
        NoteDetails(note = readNoteUiState.noteDetails.toNote(),
            modifier = modifier
                .fillMaxWidth()
                .weight(1f))

        Button(
            onClick = onEditNote,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(stringResource(R.string.edit_action).uppercase(),
                style = MaterialTheme.typography.bodyLarge)
        }

        OutlinedButton(
            onClick = {deleteConfirmationRequired = true},
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(stringResource(R.string.delete_action).uppercase(),
                style = MaterialTheme.typography.bodyLarge)
        }

        if(deleteConfirmationRequired){
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDeleteNote.invoke()
                },
                onDeleteCancel = { deleteConfirmationRequired = false })
        }
    }

}

@Composable
fun NoteDetails(
    note: Note,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))

    ) {
        NoteTitleCard(noteTitle = note.title)
        NoteContentCard(noteContent = note.content)

    }

}



@Composable
fun NoteTitleCard(
    noteTitle: String,
    modifier: Modifier = Modifier
){
    Card(  modifier = modifier, colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
    ){
        Text(text = noteTitle,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )

    }
}


@Composable
fun NoteContentCard(
    noteContent: String,
    modifier: Modifier = Modifier
){
    Card(  modifier = modifier, colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
    ){
        Text(text = noteContent,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )

    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}

@Composable
@Preview(showBackground = true)
fun ReadNoteScreenPreview(){
    MyNoteTheme {

    }
}