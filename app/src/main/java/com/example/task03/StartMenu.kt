package com.example.task03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri

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

        val input = findViewById<EditText>(R.id.inputText)

        // TASK 01
        val btnGo = findViewById<Button>(R.id.goButton2)
        btnGo.setOnClickListener {
            val text = input.text.toString()
            val intent = Intent(this, InputViewer::class.java).apply({
                putExtra("added_text", text)
            })
            startActivity(intent)
        }

        // TASK 02
        val btnCaLL = findViewById<Button>(R.id.callButton)
        btnCaLL.setOnClickListener {
            val raw = input.text.toString()
            if (raw.isEmpty()) {
                android.widget.Toast.makeText(
                    this,
                    "Enter phone number",
                    android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!phoneIsValid(raw)) {
                android.widget.Toast.makeText(
                    this,
                    "Phone number format is invalid",
                    android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:$raw".toUri()
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                android.widget.Toast.makeText(
                    this,
                    "Нет приложения для звонков",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }

        // TASK 03
        val btnShare = findViewById<Button>(R.id.shareButton)
        btnShare.setOnClickListener {
            val raw = input.text.toString()
            if (raw.isEmpty()) {
                android.widget.Toast.makeText(
                    this,
                    "Enter text before sharing it",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, raw)
                putExtra(Intent.EXTRA_SUBJECT, "Share text")
            }

            val chooser = Intent.createChooser(intent, "Share to…")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                android.widget.Toast.makeText(
                    this,
                    "No app to share to...",
                    android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun phoneIsValid(raw: String): Boolean {
    val digits = if (raw.startsWith('+')) raw.substring(1) else raw
    return digits.isNotEmpty() && digits.all { it.isDigit() }
}