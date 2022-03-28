package com.example.voicenote

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.widget.Toast
import java.io.IOException

class Recorder(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var output: String? = null

    fun start(name: String) {
        output =
            if (name == "") context.externalCacheDirs[0].absolutePath + "/MyVoice_${System.currentTimeMillis()}.m4a"
            else context.externalCacheDirs[0].absolutePath + "/${name}.m4a"

        val mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.setAudioEncodingBitRate(16)
        mediaRecorder.setAudioSamplingRate(44100)
        mediaRecorder.setOutputFile(output)
        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
            Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        this.mediaRecorder = mediaRecorder
    }

    fun stop() {
        mediaRecorder?.stop()
        mediaRecorder?.release()
        Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show()
        mediaRecorder = null
    }

    fun isRecording(): Boolean {
        return mediaRecorder != null
    }
}
