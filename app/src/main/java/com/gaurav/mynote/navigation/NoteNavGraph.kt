package com.gaurav.mynote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gaurav.mynote.ui.screen.AddNoteScreen
import com.gaurav.mynote.ui.screen.EditNoteScreen
import com.gaurav.mynote.ui.screen.HomeScreen
import com.gaurav.mynote.ui.screen.ReadNoteScreen

@Composable
fun NoteNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(navController = navController,
        startDestination = NoteScreens.HomeScreen.name,
        modifier = modifier
    ){
        composable(route = NoteScreens.HomeScreen.name){
            HomeScreen(
                navigateToAddNote = { navController.navigate(NoteScreens.AddScreen.name) },
                navigateToReadNote = {
                    navController.navigate("${NoteScreens.ReadScreen.name}/$it")
                }
            )
        }

        composable(route = NoteScreens.AddScreen.name){
            AddNoteScreen(
                navigateToHome = { navController.popBackStack() },
                navigateUp = { navController.navigateUp() })
        }

        composable(route = "${NoteScreens.ReadScreen.name}/{noteId}",
            arguments = listOf(navArgument("noteId"){
                type = NavType.StringType
            })
        ){

            ReadNoteScreen(navigateToUpdate = {
                navController.navigate("${NoteScreens.UpdateScreen.name}/$it")
            },
                navigateUp = { navController.navigateUp() } )
        }

        composable(route = "${NoteScreens.UpdateScreen.name}/{noteId}",
            arguments = listOf(navArgument("noteId"){
                type = NavType.StringType
            })
        ){
            EditNoteScreen(
                navigateBack = { navController.popBackStack() },
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}