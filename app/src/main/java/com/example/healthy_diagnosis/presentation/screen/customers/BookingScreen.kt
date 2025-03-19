package com.example.healthy_diagnosis.presentation.screen.customers

import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthy_diagnosis.core.utils.ButtonClick
import com.example.healthy_diagnosis.presentation.screen.doctors.DoctorDropdownField
import com.example.healthy_diagnosis.presentation.viewmodel.MedicalHistoryViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.PatientViewModel
import com.example.healthy_diagnosis.presentation.viewmodel.RoomViewModel
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    roomViewModel: RoomViewModel,
    medicalHistoryViewModel: MedicalHistoryViewModel
) {

    var date by remember {
        mutableStateOf("")
    }
    var roomId by remember {
        mutableStateOf("")
    }
    var medicalHistoryId by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current

    LaunchedEffect (Unit){
        roomViewModel.fetchRoom()
        medicalHistoryViewModel.fetchMedicalHistory()
    }

    val rooms by roomViewModel.roomList.collectAsState()
    val medicalhistories by medicalHistoryViewModel.medicalHistoriesList.collectAsState()

    val roomOptions = rooms.map { it.id to it.name }
    val medicalhistoryOption = medicalhistories.map { it.id to it.description}

    Column {
        DatePickerFieldCustomer(context, "Chọn ngày sinh", date) { date = it }
        Space()
        BookingFieldForRoom(
            label = "Phòng",
            selectedValue = roomId,
            onValueChange = { roomId = it },
            options = roomOptions
        )
        Space()
        DoctorDropdownField(
            label = "Chọn lịch sử khám",
            selectedId = medicalHistoryId,
            onValueChange = {selectedMedicalHistory->
                medicalHistoryId = selectedMedicalHistory
            },
            options = medicalhistoryOption
        )

    }

}

@Composable
fun BookingFieldForRoom(
    label: String,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    options: List<Pair<String, String>>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD0E8FF))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = options.find { it.first == selectedValue }?.second ?: "Chọn $label",
                    fontSize = 14.sp,
                    color = if (selectedValue.isEmpty()) Color.Blue else Color.Black
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (id, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        onValueChange(id) // id là String
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Space(){
    Spacer(modifier = Modifier.height(16.dp))
}