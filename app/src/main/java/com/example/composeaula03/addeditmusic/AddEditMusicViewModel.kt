package com.example.composeaula03.addeditmusic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeaula03.data.Music

class AddEditMusicViewModel: ViewModel() {

    val id : MutableLiveData<Int> = MutableLiveData(0)
    val name : MutableLiveData<String> = MutableLiveData("")
    val released : MutableLiveData<Int> = MutableLiveData(0)
    val album : MutableLiveData<String> = MutableLiveData("")
    val describe : MutableLiveData<String> = MutableLiveData("")

    fun insertMusic(
        onInsertMusic: (Music) -> Unit
    ){
        val newMusic = Music(
            id.value ?: return,
            name.value ?: return,
            released.value ?: return,
            album.value ?: return,
            describe.value ?: return
        )

        onInsertMusic(newMusic)
        var tempId: Int = id.value ?: return
        id.value = tempId++

        name.value = ""
        released.value = 0
        album.value = ""
        describe.value = ""

    }

    fun updateMusic(
        id: Int,
        onUpdateMusic: (Music) -> Unit
    ){
        val music = Music(
            id,
            name.value ?: return,
            released.value ?: return,
            album.value ?: return,
            describe.value ?: return,
        )
        onUpdateMusic(music)
    }
}