package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Query
import com.example.healthy_diagnosis.domain.usecases.result.DiagnosisFullInfo

@Dao
interface DiagnosisDao {
    @Query("""
        SELECT
            a.id AS appointment_id,
            a.description AS appointment_description,
            i.images_path AS image_path,
            i.created_at AS image_created_at,
            d.diagnose_disease_name AS disease_name,
            d.diagnose_disease_description AS disease_description,
            c.category_disease_name AS category_name,
            c.category_disease_description AS category_description,
            af.id AS application_form_id,
            af.content AS application_content,
            af.application_form_date AS application_form_date,
            p.id AS patient_id,
            p.name AS patient_name,
            p.phone,
            p.gender,
            p.day_of_birth
        FROM appointment_forms a
        INNER JOIN application_forms af ON a.application_form_id = af.id
        INNER JOIN patients p ON af.patient_id = p.id
        LEFT JOIN images i ON a.id = i.appointment_id
        LEFT JOIN diagnose_disease d ON i.diseases_id = d.id
        LEFT JOIN category_disease c ON d.category_disease_id = c.id
        WHERE a.id = :appointmentId
    """)
    suspend fun getDiagnosisFullInfo(appointmentId: Int): DiagnosisFullInfo?
}
