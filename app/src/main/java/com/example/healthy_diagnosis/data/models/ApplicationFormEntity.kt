//package com.example.healthy_diagnosis.data.models
//
//import androidx.room.Entity
//import androidx.room.ForeignKey
//import androidx.room.PrimaryKey
//import androidx.room.Room
//
//
//@Entity(
//    tableName = "application_forms",
//    foreignKeys = [
//        ForeignKey(
//            entity = PatientEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["patient_id"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = RoomEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["room_id"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = MedicalHistoryEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["medical_history_id"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class ApplicationFormEntity (
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val content : String,
//    val application_form_date : String,
//    val patient_id : Int,
//    val room_id: String,
//    val medical_history_id: Int
//)