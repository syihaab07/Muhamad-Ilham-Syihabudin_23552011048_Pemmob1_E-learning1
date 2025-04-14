package com.example.inputcontrol

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editPhone: EditText
    private lateinit var btndatetime: Button
    private lateinit var textdatetime: TextView
    private lateinit var btnSubmit: Button
    private var selectedDateTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editPhone = findViewById(R.id.editPhone)
        btndatetime = findViewById(R.id.btndatetime)
        textdatetime = findViewById(R.id.textdatetime)
        btnSubmit = findViewById(R.id.btnSubmit)

        btndatetime.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val date = "$dayOfMonth/${month + 1}/$year"

                    val timePickerDialog = TimePickerDialog(
                        this,
                        { _, hourOfDay, minute ->
                            val time = "$hourOfDay:${String.format("%02d", minute)}"
                            selectedDateTime = "$date $time"
                            textdatetime.text = "Dipilih: $selectedDateTime"
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )
                    timePickerDialog.show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        btnSubmit.setOnClickListener {
            val phone = editPhone.text.toString().trim()

            when {
                phone.isEmpty() -> {
                    Toast.makeText(this, "Nomor HP tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                }
                !phone.matches(Regex("^[0-9]{10,13}$")) -> {
                    Toast.makeText(this, "Nomor HP tidak valid!", Toast.LENGTH_SHORT).show()
                }
                selectedDateTime.isEmpty() -> {
                    Toast.makeText(this, "Silakan pilih tanggal dan waktu!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Nomor: $phone\nWaktu: $selectedDateTime", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}