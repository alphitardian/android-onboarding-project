package com.alphitardian.onboardingproject.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(161.dp)) {
                Image(
                    painter = rememberImagePainter(data = R.drawable.placeholder_news_image),
                    contentDescription = stringResource(id = R.string.content_description_news_image),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray)
                )
            }
            Column(modifier = Modifier.padding(horizontal = 13.dp, vertical = 8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.placeholder_card_category),
                        style = TextStyle(color = MaterialTheme.colors.secondary, fontSize = 12.sp))
                    Text(text = stringResource(id = R.string.placeholder_card_date),
                        style = TextStyle(color = Color.Gray, fontSize = 12.sp))
                }
                Text(text = stringResource(id = R.string.placeholder_card_news),
                    maxLines = 2,
                    style = TextStyle(fontSize = 14.sp,
                        fontWeight = FontWeight.W700,
                        color = MaterialTheme.colors.onBackground),
                    modifier = Modifier.padding(top = 8.dp))
                Row(Modifier.padding(top = 11.dp)) {
                    CounterItem(icon = painterResource(id = R.drawable.ic_outline_visibility_counter_24),
                        iconDescription = stringResource(id = R.string.content_description_view_icon),
                        counter = "0")
                    CounterItem(icon = painterResource(id = R.drawable.ic_outline_chat_counter_24),
                        iconDescription = stringResource(id = R.string.content_description_comment_icon),
                        counter = "0")
                    CounterItem(icon = painterResource(id = R.drawable.ic_outline_thumb_up_counter_24),
                        iconDescription = stringResource(id = R.string.content_description_like_icon),
                        counter = "0")
                    CounterItem(icon = painterResource(id = R.drawable.ic_outline_thumb_down_counter_24),
                        iconDescription = stringResource(id = R.string.content_description_dislike_icon),
                        counter = "0")
                }
            }
        }
    }
}