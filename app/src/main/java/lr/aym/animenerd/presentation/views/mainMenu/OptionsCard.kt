package lr.aym.animenerd.presentation.views.mainMenu

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import lr.aym.animenerd.core.AutoResizeText
import lr.aym.animenerd.core.FontSizeRange
import lr.aym.animenerd.core.nonSP
import lr.aym.animenerd.ui.theme.darkOrange
import lr.aym.animenerd.ui.theme.lightOrange

@Composable
fun OptionsCard(
    text : String,
    icon : ImageVector,
    onClick:()-> Unit
) {
    val scaleCard = remember {
        Animatable(1f)
    }
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(IntrinsicSize.Max)
            .scale(scaleCard.value)
            .clickable {
                coroutineScope.launch {
                    scaleCard.animateTo(
                        targetValue = 0.9f,
                        animationSpec = tween(30)
                    )
                    scaleCard.animateTo(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    onClick()
                }
            },
        backgroundColor = lightOrange,
        elevation = 8.dp,
        shape = RoundedCornerShape(7.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .background(darkOrange)
                    .fillMaxHeight()
                    .fillMaxWidth(0.2f)

            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            AutoResizeText(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSizeRange = FontSizeRange(min = 20.sp, max = 30.sp),
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }
}