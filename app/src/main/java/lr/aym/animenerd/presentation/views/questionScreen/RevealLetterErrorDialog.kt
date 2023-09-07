package lr.aym.animenerd.presentation.views.questionScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import lr.aym.animenerd.core.AutoResizeText
import lr.aym.animenerd.core.FontSizeRange
import lr.aym.animenerd.ui.theme.darkOrange
import lr.aym.animenerd.ui.theme.lightOrange

@Composable
fun RevealLetterErrorDialog(
    onDismissClick: () -> Unit,
    onWatchAdsClick: () -> Unit,
    showLoadingProgressBar: Boolean
) {
    val scaleDialog = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = 1) {
        scaleDialog.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Dialog(
        onDismissRequest = onDismissClick,
        properties = DialogProperties(
            dismissOnBackPress = !showLoadingProgressBar,
            dismissOnClickOutside = !showLoadingProgressBar, usePlatformDefaultWidth = false
        )
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.15f)
                .scale(scaleDialog.value),
            shape = RoundedCornerShape(10.dp),
            color = lightOrange
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                AutoResizeText(
                    text = "You need at least 1 point to reveal a letter",
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSizeRange = FontSizeRange(15.sp, max = 25.sp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(darkOrange),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showLoadingProgressBar) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .fillMaxHeight(0.8f),
                                color = Color.White,
                                strokeCap = StrokeCap.Round
                            )
                        }
                    } else {
                        AutoResizeText(
                            text = "Watch Ads",
                            fontSizeRange = FontSizeRange(15.sp, max = 25.sp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .clickable(onClick = onWatchAdsClick),
                            textAlign = TextAlign.Center

                        )
                    }

                    Divider(
                        Modifier
                            .fillMaxHeight()
                            .width(4.dp), color = Color.White
                    )

                    AutoResizeText(
                        text = "Dismiss",
                        fontSizeRange = FontSizeRange(15.sp, max = 25.sp),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable(onClick = onDismissClick, enabled = !showLoadingProgressBar),
                        textAlign = TextAlign.Center

                    )

                }
            }
        }
    }
}