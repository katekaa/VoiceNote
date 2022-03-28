/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.voicenote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voicenote.models.Note
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


enum class Status { LOADING, ERROR, DONE }
class NoteViewModel() : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    private val _note = MutableLiveData<Note>()

    private val locale = Locale("ru", "RU")

    val notes: LiveData<List<Note>> = _notes
    val note: LiveData<Note> = _note

    init {
        getNotes()
    }

    private fun getNotes() {
        val tempNotes = mutableListOf<Note>()
        viewModelScope.launch {
            try {
                val rootFolder = File("storage/sdcard0/Android/data/com.example.voicenote/cache")
                val filesArray: Array<File> = rootFolder.listFiles()
                for (f in filesArray) {
                    val note = Note(f.name, f.absolutePath)
                    if (f.isFile && f.extension == "wav") {
                        note.date = parseDate(f)
                        tempNotes.add(note)
                    }
                }
                _notes.value = tempNotes
            } catch (e: Exception) {
                _notes.value = listOf()
            }
        }
    }

    private fun parseDate(file: File): String {
        val timestamp = file.lastModified()
        val time = DateFormat.getTimeInstance(DateFormat.SHORT, locale).format(timestamp)
        val currentDate = SimpleDateFormat.getDateInstance().format(Calendar.getInstance().time)
        val fileDate = SimpleDateFormat.getDateInstance().format(timestamp)
        val day = if (currentDate == fileDate) "Сегодня" else DateFormat.getDateInstance(
            DateFormat.LONG,
            locale
        ).format(timestamp)
        return "$day $time"
    }

    fun onNoteClicked(note: Note) {
        _note.value = note
    }
}
