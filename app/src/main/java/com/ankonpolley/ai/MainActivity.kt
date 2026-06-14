package com.ankonpolley.ai

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var llmInference: LlmInference
    private lateinit var chatDisplay: TextView
    private lateinit var inputBox: EditText
    private lateinit var sendButton: Button
    private lateinit var chatScroll: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatDisplay = findViewById(R.id.chatDisplay)
        inputBox = findViewById(R.id.inputBox)
        sendButton = findViewById(R.id.sendButton)
        chatScroll = findViewById(R.id.chatScroll)

        chatDisplay.text = "[SYSTEM]: Initializing local environment...\n[SYSTEM]: Extracting model.bin...\n"
        sendButton.isEnabled = false

        // Load the AI model offline in the background
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val modelFile = File(filesDir, "model.bin")
                
                // Copies the model from the assets folder to internal storage so the engine can read it
                if (!modelFile.exists()) {
                    assets.open("model.bin").use { input ->
                        FileOutputStream(modelFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                }

                val options = LlmInference.LlmInferenceOptions.builder()
                    .setModelPath(modelFile.absolutePath)
                    .setMaxTokens(1000)
                    .build()

                llmInference = LlmInference.createFromOptions(this@MainActivity, options)

                withContext(Dispatchers.Main) {
                    chatDisplay.append("[SYSTEM]: Ankon Polley AI is active. No internet required.\n\n")
                    sendButton.isEnabled = true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    chatDisplay.append("\n[ERROR]: Model file not found. Ensure 'model.bin' is in the assets folder.\n")
                }
            }
        }

        sendButton.setOnClickListener {
            val prompt = inputBox.text.toString().trim()
            if (prompt.isNotEmpty()) {
                chatDisplay.append("> USER: $prompt\n> ANKON POLLEY AI: Processing...\n")
                inputBox.text.clear()
                sendButton.isEnabled = false
                scrollToBottom()

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = llmInference.generateResponse(prompt)
                        withContext(Dispatchers.Main) {
                            val currentText = chatDisplay.text.toString()
                            chatDisplay.text = currentText.replace("Processing...", response + "\n")
                            sendButton.isEnabled = true
                            scrollToBottom()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            chatDisplay.append("[ERROR]: Failed to generate response.\n")
                            sendButton.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun scrollToBottom() {
        chatScroll.post {
            chatScroll.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }
}
