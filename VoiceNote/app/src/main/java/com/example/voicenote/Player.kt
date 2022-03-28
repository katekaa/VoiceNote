package com.example.voicenote

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.example.voicenote.models.Note

class Player(val note: Note) {
    private var player: MediaPlayer? = null
    fun start() {
        val player = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(note.path)
            prepare()
            start()
        }
        this.player = player
    }

    fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

    fun isPlaying(): Boolean {
        return player != null
    }
}