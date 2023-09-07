package lr.aym.animenerd.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import lr.aym.animenerd.R
import lr.aym.animenerd.ui.theme.lightOrange

@Composable
fun AdmobBanner(modifier: Modifier = Modifier) {
    val isInEditMode = LocalInspectionMode.current
    if (isInEditMode) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .background(lightOrange)
                .padding(horizontal = 2.dp, vertical = 6.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            text = "Advert Here",
        )
    } else {
        val screenWidth =LocalConfiguration.current.screenWidthDp
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        screenWidth
                    )
                    )
                    adUnitId = context.getString(R.string.admob_banner_id)
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}