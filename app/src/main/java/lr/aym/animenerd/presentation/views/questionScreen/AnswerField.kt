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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lr.aym.animenerd.core.nonSP
import lr.aym.animenerd.domain.model.LetterGridBtn
import lr.aym.animenerd.ui.theme.lightOrange

@Composable
fun AnswerField(
    correctAnswer: String,
    userAnswerInput: List<LetterGridBtn?>,
    onAnswerLetterClick: (LetterGridBtn?) -> Unit,
    modifier: Modifier = Modifier
) {

    val answerSize = correctAnswer.length

    if (answerSize <= 7) {

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            for (i in 0 until answerSize) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .padding(end = 2.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(lightOrange)
                        .clickable(enabled = userAnswerInput[i] != null && userAnswerInput[i]?.isRevealed?.value == false) {
                            onAnswerLetterClick(userAnswerInput[i])
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userAnswerInput[i]?.letter?.toString() ?: "",
                        fontSize = 20.nonSP,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                for (i in 0..7) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 2.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(lightOrange)
                            .clickable(enabled = userAnswerInput[i] != null) {
                                onAnswerLetterClick(userAnswerInput[i])
                                Log.d("btnVisibilty", "clicked")

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userAnswerInput[i]?.letter?.toString() ?: "",
                            fontSize = 20.nonSP,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                for (i in 7 until answerSize) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 2.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(lightOrange)
                            .clickable(enabled = userAnswerInput[i] != null) {
                                onAnswerLetterClick(userAnswerInput[i])
                                Log.d("btnVisibilty", "clicked")

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userAnswerInput[i]?.letter?.toString() ?: "",
                            fontSize = 20.nonSP,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}