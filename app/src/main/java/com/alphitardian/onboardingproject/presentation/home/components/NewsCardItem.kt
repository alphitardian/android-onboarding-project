package com.alphitardian.onboardingproject.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.alphitardian.onboardingproject.R

@Composable
fun NewsCardItem() {
    val newsImagePlaceholder =
        "https://socialistmodernism.com/wp-content/uploads/2017/07/placeholder-image.png?w=640"

    Card(elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)) {
        Column {
            Image(
                painter = rememberImagePainter(data = newsImagePlaceholder),
                contentDescription = stringResource(id = R.string.content_description_news_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(161.dp)
                    .background(color = Color.Gray)
            )
            Column(modifier = Modifier.padding(horizontal = 13.dp, vertical = 8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.placeholder_card_category),
                        style = TextStyle(color = Color.Blue, fontSize = 12.sp))
                    Text(text = stringResource(id = R.string.placeholder_card_date),
                        style = TextStyle(color = Color.Gray, fontSize = 12.sp))
                }
                Text(text = stringResource(id = R.string.placeholder_card_news),
                    maxLines = 2,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W700),
                    modifier = Modifier.padding(top = 8.dp))
                Row(Modifier.padding(top = 11.dp)) {
                    CounterItem(icon = Icons.Default.Check, iconDescription = "", counter = "0")
                    CounterItem(icon = Icons.Outlined.Check, iconDescription = "", counter = "0")
                    CounterItem(icon = Icons.Outlined.Check, iconDescription = "", counter = "0")
                    CounterItem(icon = Icons.Outlined.Check, iconDescription = "", counter = "0")
                }
            }
        }
    }
}