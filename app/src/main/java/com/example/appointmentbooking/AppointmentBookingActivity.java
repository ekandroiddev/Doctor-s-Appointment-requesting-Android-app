package com.example.appointmentbooking;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AppointmentBookingActivity extends AppCompatActivity {
    EditText clientName, clientAge, clientConNo, clientEmail, clientAddress, clientPurpose;
    TextView back, confirm_msg, doctorName;
    Button requestBtn,date;
    private Calendar calendar;
    Intent intent;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    ProgressBar progressBar;
    String doctor_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment_booking);

        clientName = findViewById(R.id.client_name);
        clientAge = findViewById(R.id.client_age);
        clientConNo = findViewById(R.id.client_mob_no);
        clientEmail = findViewById(R.id.client_Email);
        clientAddress = findViewById(R.id.client_Address);
        clientPurpose = findViewById(R.id.client_purpose);
        date = findViewById(R.id.appointment_date);
        back = findViewById(R.id.back);
        requestBtn = findViewById(R.id.requestBtn);
        confirm_msg = findViewById(R.id.confirm_msg);
        doctorName = findViewById(R.id.doctorName);
        progressBar = findViewById(R.id.progress_circular);
        intent = getIntent();
        if (intent != null) {
            doctor_name = intent.getStringExtra("name");
            if (doctor_name != null) {
                doctorName.setText(doctor_name);

            }

        }
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.green));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        date.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // Create DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                        // Update TextView with selected date
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        date.setText(selectedDate);
                    }, year, month, dayOfMonth);

            // Show DatePickerDialog when TextView is clicked
            datePickerDialog.show();

        });
        requestBtn.setOnClickListener(v -> {
            String client_name = clientName.getText().toString();
            String client_age = clientAge.getText().toString();
            String appointment_date = date.getText().toString();
            String client_contact = clientConNo.getText().toString();
            String client_email = clientEmail.getText().toString();
            String client_purpose = clientPurpose.getText().toString();
            String client_address = clientAddress.getText().toString();
            requestBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            if (client_name.isEmpty() && client_age.isEmpty() && appointment_date.isEmpty() && client_contact.isEmpty() && client_email.isEmpty() && client_address.isEmpty() && client_purpose.isEmpty()) {
                Toast.makeText(this, "Fill All the details", Toast.LENGTH_SHORT).show();
                requestBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                requestAppointment(client_name, client_age, appointment_date, client_contact, client_email, client_purpose, client_address);
            }
        });
        back.setOnClickListener(v -> finish());
    }

    private void requestAppointment(String client_name, String client_age, String appointment_date, String client_contact, String client_email, String client_purpose, String client_address) {
        Map<String, Object> patient = new HashMap<>();
        patient.put("With", doctor_name);
        patient.put("client_name", client_name);
        patient.put("client_age", client_age);
        patient.put("appointment_date", appointment_date);
        patient.put("client_contact", client_contact);
        patient.put("client_email", client_email);
        patient.put("client_purpose", client_purpose);
        patient.put("client_address", client_address);

        database.collection("patients")
                .add(patient)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    requestBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    confirm_msg.setVisibility(View.VISIBLE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Unable to save the data", Toast.LENGTH_SHORT).show();
                    requestBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                });
    }
}