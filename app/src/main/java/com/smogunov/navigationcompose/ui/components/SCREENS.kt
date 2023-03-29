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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.smogunov.navigationcompose.R
import com.smogunov.navigationcompose.ui.theme.Typography

enum class SCREENS {
    HOME(),
    DETAIL(),
    INFO()
}

@Composable
fun Screen(screen: String, modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.Green),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //For check saveState and restoreState
        var fontSize by remember {
            mutableStateOf(Typography.bodyLarge.fontSize.value)
        }
        Text(text = screen, fontSize = fontSize.sp)
        Text(text = "fontSize=$fontSize")
        Button(onClick = { fontSize += 10.0f }) {
            Text(text = "Increse fontsize")
        }

        //For check saveState and restoreState
        var checked by remember {
            mutableStateOf(false)
        }
        CheckBoxText(checked, stringResource(id = R.string.checked), {checked = it})
    }
}