package lr.aym.animenerd.presentation.views.questionScreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import lr.aym.animenerd.R
import lr.aym.animenerd.core.AutoResizeText
import lr.aym.animenerd.core.FontSizeRange
import lr.aym.animenerd.core.nonSP
import lr.aym.animenerd.data.dataStore.CurrentLevel
import lr.aym.animenerd.data.database.Level
import lr.aym.animenerd.ui.theme.darkOrange
import lr.aym.animenerd.ui.theme.lightOrange


@Composable
fun QuestionCard(
    context: Context,
    currentLevel: Level,
    onRevealLetterClick: () -> Unit
) {

    val useImage = currentLevel.drawableImgName != null
    Card(
        backgroundColor = lightOrange,
        shape = RoundedCornerShape(25.dp), elevation = 10.dp,
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()

    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AutoResizeText(
                text = if (useImage) "Who is in the picture" else "Question",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.fillMaxHeight(0.1f),
                fontSizeRange = FontSizeRange(min = 5.sp, max = 20.sp)
            )
            if (useImage) {
                Log.d("dbLevel", "image")
                currentLevel.drawableImgName?.let {
                    AsyncImage(
                        model = context.resources.getIdentifier(it,"drawable",
                            context.packageName),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight(0.85f)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.85f)
                        .fillMaxWidth()
                        .background(color = darkOrange),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Log.d("dbLevel", "text")
                    currentLevel.textQuestion?.let {
                        AutoResizeText(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSizeRange = FontSizeRange(min = 5.sp, max = 25.sp),
                            modifier = Modifier.padding(4.dp),
                            textAlign = TextAlign.Center,
                            lineHeight = 25.nonSP
                        )
                    }
                }
            }

            Button(
                onClick = {
                    onRevealLetterClick()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(lightOrange)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.padding(end = 3.dp)
                    )
                    AutoResizeText(
                        text = "Reveal a Letter ", fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSizeRange = FontSizeRange(min = 5.sp, max = 25.sp),
                    )
                }
            }

        }

    }
}