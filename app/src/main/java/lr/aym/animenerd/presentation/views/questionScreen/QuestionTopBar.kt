package lr.aym.animenerd.presentation.views.questionScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import lr.aym.animenerd.R
import lr.aym.animenerd.core.nonSP
import lr.aym.animenerd.ui.theme.lightOrange

@Composable
fun QuestionTopBar(
    totalPoints : Int,
    currentLevel : Int,
    showWatchAdIcon : Boolean,
    onWatchAddClick : ()->Unit
) {

    TopAppBar(backgroundColor = lightOrange) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 3.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star, contentDescription = "",
                    tint = Color.Yellow, modifier = Modifier.size(25.dp)
                )

                Text(
                    text = totalPoints.toString(), fontSize = 25.nonSP, fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Text(
                text = "Level $currentLevel", fontSize = 25.nonSP, fontWeight = FontWeight.Bold,
                color = Color.White
            )
            if (showWatchAdIcon){
                Icon(
                    painter = painterResource(id = R.drawable.baseline_shop_two_24),
                    contentDescription = " ",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(30.dp)
                        .clickable {
                            onWatchAddClick()
                        }
                )
            }else{
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(30.dp))
            }
        }


    }
}