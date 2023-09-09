package lr.aym.animenerd.presentation.views.questionScreen.dialogs

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import lr.aym.animenerd.R
import lr.aym.animenerd.core.AutoResizeText
import lr.aym.animenerd.core.FontSizeRange
import lr.aym.animenerd.ui.theme.lightOrange
import lr.aym.animenerd.ui.theme.mediumOrange

@Composable
fun CongratsDialog(
    levelNum: Int ,
    onNextBtnClicked : ()-> Unit
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
        onDismissRequest = { /*TODO*/ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false, usePlatformDefaultWidth = false
        )
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.25f)
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.congrats_fliped),
                        contentDescription = "",
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxHeight(0.9f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    AutoResizeText(
                        text = "Congrats!",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSizeRange = FontSizeRange(min = 20.sp, max = 30.sp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.congrats),
                        contentDescription = "",
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxHeight(0.9f)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AutoResizeText(
                        text = "LVL $levelNum Completed",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSizeRange = FontSizeRange(min = 15.sp, max = 28.sp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AutoResizeText(
                        text = "+1",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSizeRange = FontSizeRange(min = 20.sp, max = 30.sp)
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "",
                        tint = Color.Yellow,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxHeight(0.9f)
                    )
                }

                Divider(Modifier.fillMaxWidth(), color = Color.White, thickness = 3.dp)
                Button(
                    onClick = {
                              onNextBtnClicked()
                    },
                    colors = ButtonDefaults.buttonColors(mediumOrange),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f),
                    shape = RectangleShape
                ) {
                    AutoResizeText(
                        text = "Next",
                        fontSizeRange = FontSizeRange(min = 20.sp , max = 30.sp),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}