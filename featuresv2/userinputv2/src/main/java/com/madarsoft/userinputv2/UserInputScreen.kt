package com.madarsoft.userinputv2

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madarsoft.core.domain.model.Gender
import com.madarsoft.core.domain.model.ViewState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun UserInputScreen(viewModel: UserInputViewModel = hiltViewModel(), navigateToOutput: () -> Unit) {

    UserInputScreenUI(viewModel.uiState)
    val viewState by viewModel.uiState.viewState.collectAsState()
    val context = LocalContext.current
    when (viewState) {
        is ViewState.Success -> {
            if ((viewState as ViewState.Success<Boolean>).data)
                navigateToOutput.invoke()
            else
                Toast.makeText(
                    context,
                    stringResource(R.string.your_request_can_not_be_completed),
                    Toast.LENGTH_SHORT
                ).show()
            viewModel.uiState.clearState()
        }

        is ViewState.Error -> {
            Toast.makeText(
                context,
                stringResource(R.string.your_request_can_not_be_completed),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.uiState.clearState()
        }

        is ViewState.InputError -> {
            Toast.makeText(context, stringResource(R.string.enter_all_fields), Toast.LENGTH_SHORT)
                .show()
            viewModel.uiState.clearState()
        }

        else -> {}
    }
}

@Composable
private fun UserInputScreenUI(uiState: UserInputViewModel.UiState) {

    val userName by uiState.userName.collectAsState()
    val age by uiState.age.collectAsState()
    val jobTitle by uiState.jobTitle.collectAsState()
    val gender by uiState.gender.collectAsState()

    var isGenderDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.user_input_screen),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            color = Color.Black
        )

        MainEditText(
            value = userName,
            onValueChange = uiState.onNameChanged,
            hint = stringResource(R.string.user_name)
        )
        Spacer(modifier = Modifier.height(16.dp))

        MainEditText(
            value = age,
            onValueChange = uiState.onAgeChanged,
            hint = stringResource(R.string.age),
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(16.dp))

        MainEditText(
            value = jobTitle,
            onValueChange = uiState.onJobTitleChanged,
            hint = stringResource(R.string.job_title)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (gender == Gender.MALE) stringResource(R.string.male) else stringResource(R.string.female),
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(50))
                .padding(horizontal = 16.dp, vertical = 21.dp)
                .clickable { isGenderDialogOpen = true }
        )

        Spacer(modifier = Modifier.height(40.dp))

        BigButton(
            text = stringResource(R.string.next),
            onClick = uiState.onSaveUser
        )
    }

    if (isGenderDialogOpen) {
        GenderSelectionDialog(
            onDismiss = { isGenderDialogOpen = false },
            onGenderSelected = {
                uiState.onGenderChanged(it)
                isGenderDialogOpen = false
            }
        )
    }
}

@Composable
fun GenderSelectionDialog(onDismiss: () -> Unit, onGenderSelected: (Gender) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.select_gender),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { onGenderSelected(Gender.MALE) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(stringResource(R.string.male))
                }
                Button(
                    onClick = { onGenderSelected(Gender.FEMALE) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(stringResource(R.string.female))
                }
            }
        }
    )
}


@Composable
fun MainEditText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint, color = Color.Gray) },
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        shape = RoundedCornerShape(50),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = LocalTextStyle.current.copy(fontSize = 12.sp)
    )
}

@Composable
fun BigButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
        shape = RoundedCornerShape(100.dp),
        contentPadding = PaddingValues(vertical = 9.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UserInputScreenPreview() {
    val uiState = UserInputViewModel.UiState(
        userName = MutableStateFlow(""),
        age = MutableStateFlow(""),
        jobTitle = MutableStateFlow(""),
        gender = MutableStateFlow(Gender.MALE),
        onNameChanged = {},
        onAgeChanged = {},
        onJobTitleChanged = {},
        onGenderChanged = {}
    )

    UserInputScreenUI(uiState = uiState)
}