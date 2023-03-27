package com.smogunov.navigationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smogunov.navigationcompose.ui.components.SCREENS
import com.smogunov.navigationcompose.ui.components.CheckBoxText
import com.smogunov.navigationcompose.ui.components.ComboBox
import com.smogunov.navigationcompose.ui.components.Screen
import com.smogunov.navigationcompose.ui.theme.NavigationComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            NavigationComposeTheme {
            // A surface container using the 'background' color from the theme
            NavApp()
//            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationComposeTheme {
        Greeting("")
    }
}

@Preview
@Composable
fun NavApp() {
    Column(Modifier.fillMaxSize()) {
        var needPopUpTo by remember {
            mutableStateOf(false)
        }
        var inclusive by remember {
            mutableStateOf(false)
        }
        var saveState by remember {
            mutableStateOf(false)
        }
        var restoreState by remember {
            mutableStateOf(false)
        }
        var launchSingleTop by remember {
            mutableStateOf(false)
        }
        val screens = SCREENS.values().map {
            it.name
        }
        val navController = rememberNavController()

        //GroupBox for popUpTo options
        Box(
            Modifier
                .border(2.dp, Color.Red, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column {
                CheckBoxText(
                    needPopUpTo,
                    stringResource(id = R.string.need_popup),
                    { needPopUpTo = it })

                if (needPopUpTo) {
                    Row {
                        CheckBoxText(inclusive, stringResource(id = R.string.inclusive), { inclusive = it })
                        CheckBoxText(
                            saveState,
                            stringResource(id = R.string.save_state),
                            { saveState = it })
                    }
                    Row {
                        CheckBoxText(
                            restoreState,
                            stringResource(id = R.string.restore_state),
                            { restoreState = it })
                        CheckBoxText(
                            launchSingleTop,
                            stringResource(id = R.string.launch_single_top),
                            { launchSingleTop = it })
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(stringResource(R.string.popup_to), Modifier.padding(5.dp))
                        ComboBox(items = screens)
                    }
                }
            }
        }

        //NavHost
        NavHost(navController = navController, startDestination = screens.first()) {
            screens.forEach { screen ->
                composable(screen) {
                    Screen(screen = screen, navController = navController)
                }
            }
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
        ) {
            Button(onClick = {navController.popBackStack()}) {
                Text(text = stringResource(id = R.string.pop_back_stack))
            }
        }
        PrintBackStack(navController = navController)
    }
}

@Composable
fun PrintBackStack(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .border(1.dp, Color.Black)
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("NavBackStackEntries:")
        Divider()
        navController.backQueue.forEach{navBackStackEntry ->
            Text(text = "route=${navBackStackEntry.destination.route}")
        }
    }

}



