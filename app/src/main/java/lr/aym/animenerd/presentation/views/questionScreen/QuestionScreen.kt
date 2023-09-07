package lr.aym.animenerd.presentation.views.questionScreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.ads.interstitial.InterstitialAd
import lr.aym.animenerd.R
import lr.aym.animenerd.core.AdmobBanner
import lr.aym.animenerd.core.Screens

@Composable
fun QuestionScreen(
    viewModel: QuestionScreenViewModel,
    navController: NavController,
    onNextBtnShowInterstitialAd: ()-> Unit,
    onNewLvlLoaded : () -> Unit,
    onRewardedAdClick : ()-> Unit
) {

    val context = LocalContext.current
    val totalPoints by viewModel.userPoints.collectAsStateWithLifecycle()

    val letterGridList by viewModel.lettersGridList.collectAsStateWithLifecycle()

    val userAnswerInput by viewModel.userAnswerInput.collectAsStateWithLifecycle()

    val currentLevel by viewModel.currentLevel.collectAsStateWithLifecycle()

    val answerCorrectness by viewModel.userAnswerCorrectness.collectAsStateWithLifecycle()

    val isGameCompleted by viewModel.gameIsCompleted.collectAsStateWithLifecycle()

    val watchRewardedAdClicked by viewModel.watchRewardedAdClicked.collectAsStateWithLifecycle()

    val nextBtnClicked = remember {
        mutableStateOf(false)
    }
    val showRevealLetterDialog = viewModel.showRevealLetterDialog.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = currentLevel.lvlNum) {
        Log.d("newLevel", "executed")
        viewModel.onNewLevelLoaded()
        onNewLvlLoaded()
    }

    LaunchedEffect(key1 = nextBtnClicked.value) {
        if (nextBtnClicked.value) {
            Log.d("gameProgress", "QuestionScreen: next btn clicked")
            viewModel.onNextBtnClicked()
            nextBtnClicked.value = false
            Log.d("gameProgress", "QuestionScreen: is game completed $isGameCompleted")
            if (isGameCompleted) {
                Log.d("gameProgress", "QuestionScreen: game completed")
                navController.navigate(Screens.MainMenuScreen.route) {
                    popUpTo(Screens.QuestionScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            QuestionTopBar(
                totalPoints = totalPoints,
                currentLevel = currentLevel.lvlNum,
                showWatchAdIcon = !watchRewardedAdClicked,
                onWatchAddClick = {
                    viewModel.onWatchRewardedAdClick()
                    onRewardedAdClick()
                }
            )
        },
        bottomBar = {
            AdmobBanner()
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(0.5f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    QuestionCard(
                        context = context,
                        currentLevel = currentLevel,
                        onRevealLetterClick = viewModel::onRevealLetterClick
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                AnswerField(
                    correctAnswer = currentLevel.correctAnswer,
                    userAnswerInput = userAnswerInput,
                    onAnswerLetterClick = {
                        viewModel.onAnswerFieldBtnClick(it?.index)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight(0.25f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LettersGrid(
                    letterGridList = letterGridList,
                    onLetterClick = {
                        viewModel.onLetterGridBtnClick(it.index)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight(0.6f)
                )
                Spacer(modifier = Modifier.weight(0.5f))
            }
        }

        if (answerCorrectness) {
            CongratsDialog(
                levelNum = currentLevel.lvlNum,
                onNextBtnClicked = {
                    onNextBtnShowInterstitialAd()
                    nextBtnClicked.value = true
                }
            )
            Log.d("btnVisibilty", "correct")
        }

        if (showRevealLetterDialog.value) {
            RevealLetterErrorDialog(
                onDismissClick = viewModel::onDismissRevealLetterDialog,
                showLoadingProgressBar = watchRewardedAdClicked,
                onWatchAdsClick = {
                    onRewardedAdClick()
                }
            )
        }
    }

}