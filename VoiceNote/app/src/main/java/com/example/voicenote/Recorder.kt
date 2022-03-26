package com.example.voicenote

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.IOException

class Recorder(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var output: String? = null

    fun start() {
        Log.d("!!", "start")

        output = context.externalCacheDirs[0].absolutePath + "/MyVoice_${System.currentTimeMillis()}.wav"

        val mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.setOutputFile(output)
        try{
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
        Log.d("!!", "stop")

        mediaRecorder?.stop()
        mediaRecorder?.release()
        Toast.makeText(context,"Recording stopped", Toast.LENGTH_SHORT).show()
        mediaRecorder = null
    }

    fun isRecording(): Boolean {
        return mediaRecorder!=null
    }
}