package com.smogunov.navigationcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxText(
    checked: Boolean,
    caption: String = "",
    onCheckedChange: (Boolean) -> Unit = {},
    textRight: Boolean = true
) {
    Row {
        if (textRight) {
            Checkbox(checked = checked, onCheckedChange = onCheckedChange)
            Text(text = caption, modifier = Modifier
                .padding(15.dp)
                .clickable { onCheckedChange(!checked) })
        } else {
            Text(text = caption, modifier = Modifier
                .padding(15.dp)
                .clickable { onCheckedChange(!checked) })
            Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TestCheckBoxText() {
    val (checked, setChecked) = remember {
        mutableStateOf(false)
    }
    val (text, changeText) = remember {
        mutableStateOf("test checkBox")
    }
    val onlyString = remember{
        mutableStateOf("empty")
    }
    val onChangeText = {valueString: String -> onlyString.value = valueString}
    Column {
        CheckBoxText(checked = checked, onlyString.value, setChecked, false)
        TextField(value = onlyString.value, onValueChange = onChangeText)
    }
}
