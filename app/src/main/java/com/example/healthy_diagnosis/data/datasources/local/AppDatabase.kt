package com.example.healthy_diagnosis.data.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.healthy_diagnosis.data.models.AccountEntity
import com.example.healthy_diagnosis.data.models.ApplicationFormEntity
import com.example.healthy_diagnosis.data.models.EducationEntity
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
        ApplicationFormEntity::class
               ],
    version =7,
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
    }
}