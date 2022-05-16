package com.example.composeaula03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeaula03.addeditmusic.AddEditMusicScreen
import com.example.composeaula03.addeditmusic.AddEditMusicViewModel
import com.example.composeaula03.musiclist.MusicListScreen
import com.example.composeaula03.musiclist.MusicListViewModel
import com.example.composeaula03.ui.theme.ComposeAula03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val musicListViewModel: MusicListViewModel by viewModels()
        val addEditMusicListViewModel: AddEditMusicViewModel by viewModels()

        setContent {
            ComposeAula03Theme {
                // A surface container using the 'background' color from the theme
                MyApp(
                    musicListViewModel,
                    addEditMusicListViewModel
                )
            }
        }
    }
}

@Composable
fun MyApp(
    musicListViewModel: MusicListViewModel,
    addEditMusicListViewModel: AddEditMusicViewModel
) {
    val navController = rememberNavController()

    Scaffold(){
        NavHost(navController = navController, startDestination = "musiclist"){
            composable("musiclist"){
                MusicListScreen(navController, musicListViewModel)
            }
            composable(
                route = "addeditmusic?id={id}",
                arguments = listOf(navArgument("id"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?: -1
                val music = musicListViewModel.getMusic(id)
                AddEditMusicScreen(
                    navController,
                    addEditMusicListViewModel,
                    musicListViewModel::insertMusic,
                    musicListViewModel::updateMusic,
                    musicListViewModel::removeMusic,
                    music
                )
            }
        }
    }
}