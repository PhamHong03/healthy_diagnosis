package com.example.healthy_diagnosis.data.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.healthy_diagnosis.data.models.AccountEntity
import com.example.healthy_diagnosis.data.models.ApplicationFormEntity
import com.example.healthy_diagnosis.data.models.AppointmentFormEntity
import com.example.healthy_diagnosis.data.models.CategoryDiseaseEntity
import com.example.healthy_diagnosis.data.models.DiseaseEntity
import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.data.models.MedicalHistoryEntity
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.data.models.PhysicianEntity
import com.example.healthy_diagnosis.data.models.RoomEntity
import com.example.healthy_diagnosis.data.models.SpecializationEntity

@Database(
    entities = [
        AccountEntity::class,
        EducationEntity::class,
        PhysicianEntity::class,
        PatientEntity::class,
        SpecializationEntity::class,
        RoomEntity::class,
        MedicalHistoryEntity::class,
        ApplicationFormEntity::class,
        AppointmentFormEntity::class,
        ImagesEntity::class,
        CategoryDiseaseEntity::class,
        DiseaseEntity::class
               ],
    version =15,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDAO
    abstract fun educationDao(): EducationDao
    abstract fun specializationDao(): SpecializationDao
    abstract fun physicianDao(): PhysicianDao
    abstract fun patientDao(): PatientDao
    abstract fun roomDao(): RoomDao
    abstract fun medicalhistoryDao():MedicalHistoryDao
    abstract fun applicationFormDao(): ApplicationFormDao
    abstract fun appointmentFormDao(): AppointmentFormDao
    abstract fun imagesDao() : ImagesDao
    abstract fun categoryDiseaseDao() : CategoryDiseaseDao
    abstract fun diseaseDao(): DiseaseDao
    abstract fun diagnosisDao(): DiagnosisDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).addMigrations(MIGRATION_2_3, MIGRATION_6_7).build()
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1️⃣ Tạo bảng mới với kiểu dữ liệu mới
                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS patients_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                day_of_birth TEXT NOT NULL,   -- Đổi từ Long -> String
                gender TEXT NOT NULL,
                phone TEXT NOT NULL,
                email TEXT NOT NULL,
                job TEXT NOT NULL,
                medical_code_card TEXT NOT NULL,
                code_card_day_start TEXT NOT NULL,  -- Đổi từ Long -> String
                status INTEGER NOT NULL
            )
            """.trimIndent()
                )

                // 2️⃣ Copy dữ liệu từ bảng cũ, chuyển đổi Long -> String
                database.execSQL(
                    """
            INSERT INTO patients_new (id, name, day_of_birth, gender, phone, email, job, medical_code_card, code_card_day_start, status) 
            SELECT id, name, CAST(day_of_birth AS TEXT), gender, phone, email, job, medical_code_card, CAST(code_card_day_start AS TEXT), status 
            FROM patients
            """.trimIndent()
                )

                // 3️⃣ Xóa bảng cũ
                database.execSQL("DROP TABLE patients")

                // 4️⃣ Đổi tên bảng mới thành bảng cũ
                database.execSQL("ALTER TABLE patients_new RENAME TO patients")
            }
        }


        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS patients_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        day_of_birth TEXT NOT NULL,
                        gender TEXT NOT NULL,
                        phone TEXT NOT NULL,
                        email TEXT NOT NULL,
                        job TEXT NOT NULL,
                        medical_code_card TEXT NOT NULL,
                        code_card_day_start TEXT NOT NULL,
                        status INTEGER NOT NULL,
                        account_id INTEGER NOT NULL DEFAULT 0
                    )
                    """.trimIndent()
                )

                database.execSQL(
                    """
                    INSERT INTO patients_new (id, name, day_of_birth, gender, phone, email, job, medical_code_card, code_card_day_start, status, account_id) 
                    SELECT id, name, day_of_birth, gender, phone, email, job, medical_code_card, code_card_day_start, status, 0 FROM patients
                    """.trimIndent()
                )

                database.execSQL("DROP TABLE patients")
                database.execSQL("ALTER TABLE patients_new RENAME TO patients")
            }
        }

//        val MIGRATION_7_8 = object : Migration(7, 8) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // 1️⃣ Tạo bảng mới với cột patient_id
//                database.execSQL(
//                    """
//            CREATE TABLE IF NOT EXISTS medical_histories_new (
//                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                description TEXT NOT NULL,
//                physician_id INTEGER NOT NULL,
//                patient_id INTEGER NOT NULL DEFAULT 0
//            )
//            """.trimIndent()
//                )
//
//                // 2️⃣ Copy dữ liệu từ bảng cũ sang bảng mới, gán patient_id = 0 mặc định
//                database.execSQL(
//                    """
//            INSERT INTO medical_histories_new (id, description, physician_id, patient_id)
//            SELECT id, description, physician_id, 0 FROM medical_histories
//            """.trimIndent()
//                )
//
//                // 3️⃣ Xóa bảng cũ
//                database.execSQL("DROP TABLE medical_histories")
//
//                // 4️⃣ Đổi tên bảng mới thành bảng cũ
//                database.execSQL("ALTER TABLE medical_histories_new RENAME TO medical_histories")
//            }
//        }

    }
}