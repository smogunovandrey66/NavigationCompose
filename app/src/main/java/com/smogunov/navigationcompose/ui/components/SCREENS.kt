package com.smogunov.navigationcompose.ui.components

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.smogunov.navigationcompose.R

enum class SCREENS {
    HOME(),
    DETAIL(),
    INFO()
}

@Composable
fun Screen(screen: String, modifier: Modifier = Modifier, navController: NavController) {
    Column(modifier = modifier
        .fillMaxWidth()
        .border(1.dp, Color.Green),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = screen)
        Divider()
        val screens = SCREENS.values().map { it.name }
        var nextScreen by remember {
            mutableStateOf(screens.first())
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()) {
            Text(stringResource(R.string.navigate_to), Modifier.padding(5.dp))
            ComboBox(items = screens) { selected ->
                nextScreen = selected
            }
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()) {
            Button(onClick = {}) {
                Text(text = stringResource(id = R.string.back))
            }
            Button(onClick = {navController.navigate(nextScreen){

            } }) {
                Text(text = stringResource(id = R.string.navigate_to))
            }
        }
    }
}