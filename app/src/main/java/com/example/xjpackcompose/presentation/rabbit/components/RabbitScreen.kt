package com.example.xjpackcompose.presentation.rabbit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.xjpackcompose.presentation.rabbit.RabbitsViewModel

@Composable
fun RabbitScreen(
    viewModel: RabbitsViewModel = hiltViewModel()
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    verticalArrangement = Arrangement.Center
) {
    if (viewModel.state.value.isLoading)
        CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
    viewModel.state.value.rabbit?.let {
        Image(
            painter = rememberImagePainter(data = it.imgUrl, builder = { crossfade(true) }),
            contentDescription = "RabbitImage"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = it.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = it.desc)
        Spacer(modifier = Modifier.height(8.dp))
    }
    viewModel.state.value.error?.takeIf { it.isNotBlank() }?.let { error ->
        Text(
            text = error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
    Button(
        onClick = viewModel::getRandomRabbit,
        modifier = Modifier.align(Alignment.End)
    ) {
        Text(text = "Next Rabbit!")
    }
    Spacer(modifier = Modifier.height(8.dp))
}