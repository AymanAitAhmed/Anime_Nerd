package lr.aym.animenerd.presentation.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import lr.aym.animenerd.R
import lr.aym.animenerd.core.Screens
import lr.aym.animenerd.presentation.views.questionScreen.QuestionScreenViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: QuestionScreenViewModel
) {

    LaunchedEffect(key1 = 1) {
        viewModel.getCurrentLevelDatastore()
        viewModel.getTotalCountLevels()
        viewModel.getGameProgressStatus()
        navController.navigate(Screens.MainMenuScreen.route){
            popUpTo(Screens.SplashScreen.route){
                inclusive = true
            }
        }

    }
    Log.d("splashscreen", "SplashScreen: launched")
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Image(
            painter = painterResource(id = R.drawable.background), contentDescription = "",
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
        )

        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(0.7f)
        )


    }

}