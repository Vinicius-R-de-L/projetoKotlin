package com.example.composeaula03.addeditmusic

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composeaula03.data.Music

@Composable
fun AddEditMusicScreen(
    navController: NavController,
    addEditMusicViewModel: AddEditMusicViewModel,
    onInsertMusic: (Music) -> Unit,
    onUpdateMusic: (Music) -> Unit,
    onRemoveMusic: (Int) -> Unit,
    music: Music
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

             if(music.id == -1)
                 addEditMusicViewModel.insertMusic(onInsertMusic)
             else
                addEditMusicViewModel.updateMusic(music.id,onUpdateMusic)
             navController.navigate("musiclist"){
                 popUpTo("musiclist"){
                     inclusive = true
                 }
             }
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Confirmar")
            }
        }
    ) {
        addEditMusicViewModel.name.value = music.name
        addEditMusicViewModel.released.value = music.released
        addEditMusicViewModel.album.value = music.album
        addEditMusicViewModel.describe.value = music.describe

        AddEditMusicForm(addEditMusicViewModel){
            onRemoveMusic(music.id)
        }
    }

}

@Composable
fun AddEditMusicForm(
    addEditMusicViewModel: AddEditMusicViewModel,
    onRemoveMusic: () -> Unit
){
    var name = addEditMusicViewModel.name.observeAsState()
    var released = addEditMusicViewModel.released.observeAsState()
    var album = addEditMusicViewModel.album.observeAsState()
    var describe = addEditMusicViewModel.describe.observeAsState()
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(){
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = {
                    Text(text = "Name")
                },
                value = "${name.value}",
                onValueChange = { newName ->
                    addEditMusicViewModel.name.value = newName
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = {
                    Text(text = "Released")
                },
                value = "${released.value}",
                onValueChange = { newReleased ->
                    addEditMusicViewModel.released.value = newReleased.toInt()
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = {
                    Text(text = "Album")
                },
                value = "${album.value}",
                onValueChange = { newAlbum ->
                    addEditMusicViewModel.album.value = newAlbum
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = {
                    Text(text = "Describe")
                },
                value = "${describe.value}",
                onValueChange = {
                    addEditMusicViewModel.describe.value = it
                }
            )
        }
        FloatingActionButton(
            modifier = Modifier.padding(16.dp),
            onClick = { onRemoveMusic }
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }

}