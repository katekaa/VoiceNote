package com.example.voicenote

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.voicenote.adapters.NoteAdapter
import com.example.voicenote.adapters.NoteListener
import com.example.voicenote.databinding.ActivityMainBinding
import com.example.voicenote.viewmodels.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var player: Player
    private val recorder = Recorder(this)
    private lateinit var viewModel: NoteViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        binding.recyclerView.adapter = NoteAdapter(NoteListener { note ->
            viewModel.onNoteClicked(note)
            Log.d("!!", "btn cli")
            player = Player(note)
            if (player.isPlaying()) {
                player.stop()
            } else {
                player.start()
            }
        })
        binding.viewModel = viewModel

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, permissions, 0)
        }

        binding.microButton.setOnClickListener {
            launchMicro()
        }



    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun launchMicro() {
        if (recorder.isRecording()) {
            recorder.stop()
        } else {
            microPermission.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val microPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    recorder.start()
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) -> {
                    Toast.makeText(
                        this,
                        "Доступ к микрофону не разрешен",
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(
                        this,
                        "Чтобы предоставить приложению доступ к микрофону, " +
                                "сбросьте данные приложения в настройках",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    requestPermissions(Array(1) { AUDIO_SERVICE }, 0)
                }
            }
        }
}