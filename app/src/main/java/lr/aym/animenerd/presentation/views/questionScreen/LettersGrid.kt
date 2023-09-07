package lr.aym.animenerd.presentation.views.questionScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import lr.aym.animenerd.core.generateScrambledArray
import lr.aym.animenerd.core.nonSP
import lr.aym.animenerd.domain.model.LetterGridBtn
import lr.aym.animenerd.ui.theme.lightOrange

@Composable
fun LettersGrid(
    modifier: Modifier = Modifier,
    letterGridList: List<LetterGridBtn>,
    onLetterClick : (LetterGridBtn)->Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            for (i in 0 until  7) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 2.dp, top = 2.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .alpha(if (letterGridList[i].isClicked.value) 0f else 1f)
                        .background(lightOrange)
                        .clickable(enabled = !letterGridList[i].isClicked.value) {
                            onLetterClick(letterGridList[i])
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = letterGridList[i].letter.toString(),
                        fontSize = 20.nonSP,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            for (i in 7 until  letterGridList.size) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 2.dp, top = 2.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .alpha(if (letterGridList[i].isClicked.value) 0f else 1f)
                        .background(lightOrange)
                        .clickable(enabled = !letterGridList[i].isClicked.value) {
                            onLetterClick(letterGridList[i])
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = letterGridList[i].letter.toString(),
                        fontSize = 20.nonSP,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}