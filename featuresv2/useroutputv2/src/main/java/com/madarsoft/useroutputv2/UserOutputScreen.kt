package com.madarsoft.useroutputv2

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madarsoft.core.domain.model.Gender
import com.madarsoft.core.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun UserOutputScreen(viewModel: UserOutputViewModel = hiltViewModel(), navigateBack: () -> Unit) {

    UserOutputScreenUI(viewModel.uiState, navigateBack)
}

@Composable
private fun UserOutputScreenUI(uiState: UserOutputViewModel.UiState, navigateBack: () -> Unit) {

    val user by uiState.user.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.user_output_screen),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            color = Color.Black
        )

        DisplayText(
            label = stringResource(R.string.user_name),
            value = user?.name ?: stringResource(R.string.user_name),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        DisplayText(
            label = stringResource(R.string.age),
            value = user?.age?.toString() ?: stringResource(R.string.age),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        DisplayText(
            label = stringResource(R.string.job_title),
            value = user?.jobTitle ?: stringResource(R.string.job_title),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        DisplayText(
            label = stringResource(R.string.gender),
            value = if (user?.gender == Gender.MALE) stringResource(R.string.male) else stringResource(
                R.string.female
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))

        BigButton(
            text = stringResource(R.string.back),
            onClick = navigateBack
        )
    }
}

@Composable
fun DisplayText(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        Text(
            text = "$label:",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray, shape = androidx.compose.foundation.shape.RoundedCornerShape(50))
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}

@Composable
fun BigButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(100.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 9.dp)
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
    val uiState = UserOutputViewModel.UiState(
        user = MutableStateFlow(
            User(
                name = "Ahmed",
                age = 25,
                jobTitle = "Android Developer",
                gender = Gender.MALE
            )
        )
    )

    UserOutputScreenUI(uiState = uiState) {}
}
