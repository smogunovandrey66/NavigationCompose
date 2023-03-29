package com.smogunov.navigationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smogunov.navigationcompose.ui.components.SCREENS
import com.smogunov.navigationcompose.ui.components.CheckBoxText
import com.smogunov.navigationcompose.ui.components.ComboBox
import com.smogunov.navigationcompose.ui.components.Screen
import com.smogunov.navigationcompose.ui.theme.NavigationComposeTheme
import kotlinx.coroutines.flow.flowOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComposeTheme {
            // A surface container using the 'background' color from the theme
            NavApp()
            }
        }
    }
}

@Preview
@Composable
fun NavApp() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally, ) {
        //Parems for PopUpTo
        var needPopUpTo by remember {
            mutableStateOf(false)
        }
        var inclusiveOpt by remember {
            mutableStateOf(false)
        }
        var saveStateOpt by remember {
            mutableStateOf(false)
        }
        var restoreStateOpt by remember {
            mutableStateOf(false)
        }
        var launchSingleTopOpt by remember {
            mutableStateOf(false)
        }
        val screens = SCREENS.values().map {
            it.name
        }
        var popUpToScreen by remember {
            mutableStateOf(screens.first())
        }

        //NavController
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
                        CheckBoxText(
                            inclusiveOpt,
                            stringResource(id = R.string.inclusive),
                            { inclusiveOpt = it })
                        CheckBoxText(
                            saveStateOpt,
                            stringResource(id = R.string.save_state),
                            { saveStateOpt = it })
                    }
                    Row {
                        CheckBoxText(
                            restoreStateOpt,
                            stringResource(id = R.string.restore_state),
                            { restoreStateOpt = it })
                        CheckBoxText(
                            launchSingleTopOpt,
                            stringResource(id = R.string.launch_single_top),
                            { launchSingleTopOpt = it })
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(stringResource(R.string.popup_to), Modifier.padding(5.dp))
                        ComboBox(items = screens) { popUpToScreen = it }
                    }
                }
            }
        }

        var nextScreen by remember {
            mutableStateOf(screens.first())
        }

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.navigate_to), Modifier.padding(5.dp))
            ComboBox(items = screens) { selected ->
                nextScreen = selected
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Button(onClick = {navController.popBackStack()}) {
                Text(text = stringResource(id = R.string.back))
            }
            Button(onClick = {
                navController.navigate(nextScreen) {
                    if(needPopUpTo){
                        popUpTo(popUpToScreen){
                            inclusive = inclusiveOpt
                            saveState = saveStateOpt
                        }
                        launchSingleTop = launchSingleTopOpt
                        restoreState = restoreStateOpt
                    }
                }
            }) {
                Text(text = stringResource(id = R.string.navigate_to))
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

        var countChange by remember {
            mutableStateOf(0)
        }
        var navBackStackEntries by remember {
            mutableStateOf("empty")
        }
        Text("countChange=$countChange")
        LaunchedEffect(key1 = navController){
            countChange++

            navController.addOnDestinationChangedListener { _, _, _ ->
                var result = ""
                navController.backQueue.forEach { navBackStackEntry ->
                    result += "${navBackStackEntry.destination.route} ${navBackStackEntry.id}\n"
                }
                navBackStackEntries = result
            }
        }

        Column(
            Modifier
                .border(1.dp, Color.Cyan)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.navBackStackEntries) + ":")
            Text(text = navBackStackEntries)
        }
    }
}



