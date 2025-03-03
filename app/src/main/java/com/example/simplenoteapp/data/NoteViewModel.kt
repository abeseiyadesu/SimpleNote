package com.example.simplenoteapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplenoteapp.data.freespace.FreeSpace
import com.example.simplenoteapp.data.freespace.FreeSpaceRepository
import com.example.simplenoteapp.data.note.Note
import com.example.simplenoteapp.data.note.NoteRepository
import kotlinx.coroutines.launch


class NoteViewModel(application: Application) : AndroidViewModel(application) {
    // ノートに関するリポジトリ
    private val noteRepository: NoteRepository
    val allNotes: LiveData<List<Note>>

    // フリースペース用リポジトリ
    private val freeSpaceRepository: FreeSpaceRepository
    val freeSpace: LiveData<FreeSpace?>

    init {
        // インスタンスを取得
        val noteDatabase = AppDatabase.getDatabase(application)

        // リポジトリを初期化
        noteRepository = NoteRepository(noteDatabase.noteDao())
        allNotes = noteRepository.getAllNotes()

        // リポジトリを初期化
        freeSpaceRepository= FreeSpaceRepository(noteDatabase.freeSpaceDao())
        freeSpace=freeSpaceRepository.freeSpace

        // 初回起動時にデータがnullの場合データをセットする
        viewModelScope.launch{
            freeSpaceRepository.initializeFreeSpace()
        }
    }

    fun insertNote(note: Note) = viewModelScope.launch {
        noteRepository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.update(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.delete(note)
    }

    fun getNoteById(id: Int): LiveData<Note?> {
        val noteLiveData = MutableLiveData<Note?>()
        viewModelScope.launch {
            val note = noteRepository.getNoteById(id)
            noteLiveData.postValue(note)
        }
        return noteLiveData
    }

    // フリースペースの更新
    fun updateFreeSpace(header: String, detail: String) = viewModelScope.launch {
        freeSpaceRepository.updateFreeSpace(header, detail)
    }
}