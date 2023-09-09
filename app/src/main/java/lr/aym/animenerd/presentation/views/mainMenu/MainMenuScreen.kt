package lr.aym.animenerd.presentation.views.mainMenu

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import lr.aym.animenerd.R
import lr.aym.animenerd.core.AdmobBanner
import lr.aym.animenerd.core.Screens
import lr.aym.animenerd.core.nonSP
import lr.aym.animenerd.core.LinearProgressIndicator
import lr.aym.animenerd.presentation.views.questionScreen.QuestionScreenViewModel
import lr.aym.animenerd.ui.theme.darkOrange

@Composable
fun MainMenuScreen(
    navController: NavController,
    viewModel: QuestionScreenViewModel
) {
    val currentLevel by viewModel.currentLevelProgress.collectAsStateWithLifecycle()

    val totalCountLevels by viewModel.totalCountLevels.collectAsStateWithLifecycle()

    val gameIsCompleted by viewModel.gameIsCompleted.collectAsStateWithLifecycle()

    val startClicked = remember {
        mutableStateOf(false)
    }
    val restartClicked = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = startClicked.value){
        if (startClicked.value){
            viewModel.getCorrespondingLevelFromDb(currentLevel)
            navController.navigate(Screens.QuestionScreen.route)
            startClicked.value = false
        }
    }
    LaunchedEffect(key1 = restartClicked.value){
        if (restartClicked.value){
            viewModel.onRestartClick()
            Log.d("mainmenu", "MainMenuScreen: $currentLevel")
            navController.navigate(Screens.QuestionScreen.route)
            restartClicked.value = false
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AdmobBanner()
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.45f),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = if (currentLevel == totalCountLevels) "Completed" else "Level $currentLevel" ,
                    color = darkOrange,
                    fontSize = 15.nonSP
                )

                LinearProgressIndicator(
                    currentLevel = currentLevel,
                    totalLevels = totalCountLevels
                )

                Spacer(modifier = Modifier.height(16.dp))

                OptionsCard(
                    text = if (currentLevel>1)"CONTINUE" else "START",
                    icon = Icons.Default.PlayArrow
                ) {
                    startClicked.value = true
                }

                if (gameIsCompleted){
                    Spacer(modifier = Modifier.height(16.dp))
                    OptionsCard(
                        text = "RESTART",
                        icon = Icons.Default.PlayArrow
                    ) {
                        restartClicked.value = true
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                OptionsCard(
                    text = "SETTINGS",
                    icon = Icons.Default.Settings
                ) {

                }


            }
        }
    }

}