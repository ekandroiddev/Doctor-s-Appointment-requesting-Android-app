package com.example.appointmentbooking;

public class DoctorModel {
    String doctorName, doctor_occopation;

    public DoctorModel(String doctorName, String doctor_occopation) {
        this.doctorName = doctorName;
        this.doctor_occopation = doctor_occopation;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctor_occopation() {
        return doctor_occopation;
    }

    public void setDoctor_occopation(String doctor_occopation) {
        this.doctor_occopation = doctor_occopation;
    }
}
