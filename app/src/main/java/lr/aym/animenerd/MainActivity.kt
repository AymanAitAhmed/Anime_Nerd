package lr.aym.animenerd

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import lr.aym.animenerd.core.Screens
import lr.aym.animenerd.presentation.views.SplashScreen
import lr.aym.animenerd.presentation.views.mainMenu.MainMenuScreen
import lr.aym.animenerd.presentation.views.questionScreen.QuestionScreen
import lr.aym.animenerd.presentation.views.questionScreen.QuestionScreenViewModel
import lr.aym.animenerd.ui.theme.AnimeNerdTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)

        setContent {
            AnimeNerdTheme {
                val navController = rememberNavController()
                val questionScreenViewModel: QuestionScreenViewModel = viewModel()
                NavHost(
                    navController = navController,
                    startDestination = Screens.SplashScreen.route
                ) {
                    composable(Screens.SplashScreen.route) {
                        SplashScreen(
                            navController = navController,
                            viewModel = questionScreenViewModel
                        )
                    }
                    composable(Screens.MainMenuScreen.route) {
                        MainMenuScreen(
                            navController = navController,
                            viewModel = questionScreenViewModel
                        )
                    }
                    composable(Screens.QuestionScreen.route) {
                        QuestionScreen(
                            viewModel = questionScreenViewModel,
                            navController = navController,
                            onNextBtnShowInterstitialAd = {
                                mInterstitialAd?.show(this@MainActivity)
                            },
                            onNewLvlLoaded = {
                                admobInterstitial(applicationContext)
                            },
                            onRewardedAdClick = {
                                questionScreenViewModel.onWatchRewardedAdClick()
                                admobRewardedAd(
                                    applicationContext,
                                    onShowedAdSuccessfully = { rewardAmount ->
                                        questionScreenViewModel.incrementUserPoints(rewardAmount)
                                        questionScreenViewModel.onWatchRewardedAdFinish()
                                    },
                                    onFailedToLoadAd = {
                                        Toast.makeText(
                                            applicationContext,
                                            "Error: can't load the Ad",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        questionScreenViewModel.onWatchRewardedAdFinish()
                                    }
                                )
                            }
                        )

                    }
                }
            }
        }
    }

    private fun admobInterstitial(context: Context) {
        InterstitialAd.load(
            context,
            context.getString(R.string.admob_interstitial_id),
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("admobInterstitial", "AdLoaded")
                    mInterstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    Log.d("admobInterstitial", "AdFailedToLoad: ${p0.message}")
                    mInterstitialAd = null
                }
            }
        )

    }

    private fun admobRewardedAd(
        context: Context,
        onShowedAdSuccessfully: (Int) -> Unit,
        onFailedToLoadAd: () -> Unit
    ) {
        RewardedAd.load(
            context,
            context.getString(R.string.admob_rewarded_id),
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(rewardedError: LoadAdError) {
                    Log.d("rewardedAd", "onAdFailedToLoad: ${rewardedError.message}")
                    onFailedToLoadAd()
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    rewardedAd.let {
                        Log.d("rewardedAd", "onAdLoaded: add shows")
                        it.show(this@MainActivity) { reward ->
                            onShowedAdSuccessfully(reward.amount)
                        }
                    }
                }
            }
        )
    }
}