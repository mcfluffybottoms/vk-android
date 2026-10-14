package com.example.task03

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnGo = findViewById<Button>(R.id.goButton2)
        val input = findViewById<EditText>(R.id.inputText)
        btnGo.setOnClickListener {
            Log.d("StartMenu", "Input: ${input.text}")
            val text = input.text.toString()
            val intent = Intent(this, InputViewer::class.java).apply({
                putExtra("added_text", text)
            })
            startActivity(intent)
        }
    }
}