package com.example.notesapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notesapp.database.Note
import com.example.notesapp.ui.viewModels.NoteViewModel

@Composable
fun EditingNoteScreen(
    id: Int,
    noteTitle: String?,
    noteDetails: String?,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = viewModel()
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 16.dp, end = 16.dp)
    ) {
        var title by remember { mutableStateOf(noteTitle) }
        var details by remember { mutableStateOf(noteDetails) }

        OutlinedTextField(
            value = title!!,
            onValueChange = { title = it },
            label = {
                Text(text = "Note Title")
            },
            modifier = modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = details!!,
            onValueChange = { details = it },
            label = {
                Text(text = "Note Details")
            },
            modifier = modifier.fillMaxWidth()
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            OutlinedButton(
                onClick = {
                    val updatedTitle = if (title.isNullOrEmpty()) "Untitled" else title!!
                    val updatedDetails = if (details.isNullOrEmpty()) "Empty" else details!!
                    viewModel.upsert(Note(id, updatedTitle,updatedDetails))
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    navController.popBackStack()
                },
                modifier = modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(text = "Update")
            }
            OutlinedButton(
                onClick = {
                    viewModel.delete(Note(id, title!!,details!!))
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    navController.popBackStack()
                },
                modifier = modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(text = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditingNoteScreenPreview() {
}