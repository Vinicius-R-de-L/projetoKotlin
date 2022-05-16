package com.example.composeaula03.musiclist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composeaula03.data.Music
import java.util.logging.Filter


@Composable
fun MusicListScreen(
    navController: NavController,
    musicListViewModel: MusicListViewModel,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addeditmusic")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Music")
            }
        }
    ) {
        val musicList by musicListViewModel.musicList.observeAsState()
        val filter by musicListViewModel.filterBy.observeAsState()
        Column() {
            SearchMusic(filter, musicListViewModel::updateFilter)
            MusicList(musics = musicList ?: listOf<Music>(), navController)
        }
    }
}

@Composable
fun SearchMusic(
    filter: String?,
    onFilterChange: (String) -> Unit
){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        label = {
                Row() {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    Text(text = "Search")
                }
        },
        value = filter!!,
        onValueChange = onFilterChange
    )
}

@Composable
fun MusicList(
    musics: List<Music>,
    navController: NavController
){
    LazyColumn( ){
        items(musics){ music ->
            MusicEntry(music = music){
                navController.navigate("addeditmusic?id=${music.id}")
            }
        }
    }
}

@Composable
fun MusicEntry(
    music: Music,
    onEdit: () -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                expanded = !expanded
            }
            .background(Color.LightGray)
    ){
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(CircleShape)
                        .size(60.dp)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = music.album[0].uppercase(),
                        style = MaterialTheme.typography.h3.copy(Color.White)
                    )
                }
                Text(
                    text = music.name,
                    style = MaterialTheme.typography.h6
                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                )
                if (expanded){
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                  onEdit()
                            },
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
            if(expanded){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "${music.released}",
                        style = MaterialTheme.typography.overline
                            .copy(color = Color.LightGray)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        text = music.describe,
                        style = MaterialTheme.typography.caption
                            .copy(color = Color.DarkGray)
                    )
                }
            }
        }

    }
}