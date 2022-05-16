package com.example.composeaula03.musiclist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeaula03.data.Music

class MusicListViewModel : ViewModel(){
    private val _musicList: MutableLiveData<List<Music>> = MutableLiveData(
        listOf()
    )

    private val _filterBy: MutableLiveData<String> = MutableLiveData("")

    val filterBy: LiveData<String>
        get() = _filterBy

    val musicList: LiveData<List<Music>>
        get() {
            return if(_filterBy.value == "")
                _musicList
            else{
                val list: List<Music> = _musicList.value?.filter { music ->
                    music.name.contains(_filterBy.value ?: "")
                } ?: listOf()
                MutableLiveData(list)
            }
        }

    fun updateFilter(newFilter: String){
        _filterBy.value = newFilter
    }

    fun insertMusic(music: Music){
        val list: MutableList<Music> = _musicList.value?.toMutableList() ?: return
        list.add(music)
        _musicList.value = list
    }

    fun updateMusic(updatedMusic: Music){
        var pos = -1
        _musicList.value?.forEachIndexed { index, music ->
            if(updatedMusic.id == music.id)
                pos = index
        }
        val list: MutableList<Music> = _musicList.value?.toMutableList() ?: return
        list.removeAt(pos)
        list.add(pos, updatedMusic)
        _musicList.value = list
    }

    fun removeMusic(id: Int){
        var pos = -1
        _musicList.value?.forEachIndexed { index, music ->
            if(id == music.id)
                pos = index
        }
        val list: MutableList<Music> = _musicList.value?.toMutableList() ?: return
        list.removeAt(pos)
        _musicList.value = list
    }

    fun getMusic(id: Int): Music {
        _musicList.value?.forEach{ music ->
            if ( id == music.id)
                return music
        }
        return Music(
            -1,
            "",
            0,
            "",
            ""
        )
    }

}