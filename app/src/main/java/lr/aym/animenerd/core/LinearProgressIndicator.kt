package lr.aym.animenerd.domain.usedCases

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import lr.aym.animenerd.ui.theme.darkOrange
import lr.aym.animenerd.ui.theme.lightOrange

@Composable
fun Linearprogressindicator(currentlevel : Int , totallevels : Int) {

    var progress by remember {
        mutableStateOf(0f)
    }

    val progressanimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    LaunchedEffect(key1 = 1 ){
        delay(500)
        progress = currentlevel.toFloat() /totallevels
    }
    LinearProgressIndicator(
        progress = progressanimation,
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .clip(RoundedCornerShape(7.dp))
            .height(7.dp),
        color = lightOrange,
        backgroundColor = Color.LightGray
    )


}
