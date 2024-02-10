package com.gaurav.mynote.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gaurav.mynote.ui.MyNoteTopAppBar
import com.gaurav.mynote.R
import com.gaurav.mynote.model.Note
import com.gaurav.mynote.ui.theme.MyNoteTheme
import com.gaurav.mynote.utils.AppViewModelProvider
import com.gaurav.mynote.utils.getDateTime
import com.gaurav.mynote.viewmodels.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToAddNote:() -> Unit,
    navigateToReadNote:(Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MyNoteTopAppBar(
                title = stringResource(id = R.string.app_name),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToAddNote.invoke() },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        }

    ) {
        HomeBody(noteList = homeUiState.noteList,
            onNoteClick = {navigateToReadNote.invoke(it)},
            modifier = modifier
                .padding(it))
    }
}


@Composable
fun HomeBody(noteList: List<Note>, onNoteClick:(Int) -> Unit ,modifier: Modifier = Modifier){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        if(noteList.isEmpty()){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ){
                Text(
                    text = stringResource(R.string.no_note_description),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }else{
            NoteGridList(
                noteList = noteList,
                onNoteClick = {onNoteClick(it.id)},
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_extra_small))
            )
        }
    }
}


@Composable
fun NoteGridList(noteList: List<Note>, onNoteClick:(Note) -> Unit ,modifier: Modifier = Modifier){

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ){

        items(items = noteList, key = {noteList -> noteList.id}){
            NoteCard(note = it,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onNoteClick.invoke(it) }
            )
        }
    }
}


@Composable
fun NoteCard(note: Note, modifier: Modifier = Modifier){
    
    Card(modifier = modifier
        .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(text = note.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = note.content,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Justify
            )
            Text(text = note.timestamp.getDateTime(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
    
}

@Preview(showBackground = true)
@Composable
fun NoteCardPreview(){
    MyNoteTheme {
        NoteCard(
            note = Note(
                title = "Sample",
                content = stringResource(R.string.sample_content),
                timestamp = 0L
            )
        )
    }
}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun NoteGridListPreview(){
    MyNoteTheme {
        NoteGridList(noteList = listOf(
            Note(
                id = 0,
                title = "Sample",
                content = stringResource(R.string.sample_content),
                timestamp = 0L
            ),
            Note(
                id = 1,
                title = "Sample",
                content = "India\'s former U19 captain Unmukt Chand is set to face his birth nation when he plays",
                timestamp = 0L
            ),
            Note(
                id = 2,
                title = "Sample",
                content = stringResource(R.string.sample_content),
                timestamp = 0L
            ),
            Note(
                id = 3,
                title = "Sample",
                content = stringResource(R.string.sample_content),
                timestamp = 0L
            )
        ), onNoteClick = {})
    }
}

@Composable
@Preview(showBackground = true,
    showSystemUi = true)
fun HomeScreenPreview(){
    MyNoteTheme {
        HomeScreen(navigateToAddNote = { /*TODO*/ }, navigateToReadNote = {})
    }
}