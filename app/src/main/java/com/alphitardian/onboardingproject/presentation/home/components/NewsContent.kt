package com.alphitardian.onboardingproject.presentation.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.data.user.data_source.local.entity.NewsEntity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsContent(news: List<NewsEntity>?) {
    LazyColumn(modifier = Modifier
        .padding(horizontal = 17.dp)
        .testTag(stringResource(R.string.testtag_home_news_content))
    ) {
        item {
            Text(text = stringResource(id = R.string.home_title),
                style = TextStyle(fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    color = MaterialTheme.colors.onBackground),
                modifier = Modifier
                    .padding(top = 29.dp)
                    .testTag(stringResource(id = R.string.home_title)))
            Text(text = stringResource(id = R.string.home_sub_title),
                style = TextStyle(color = MaterialTheme.colors.onBackground),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .testTag(stringResource(id = R.string.home_sub_title)))
            if (news?.size == null) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
                }
            }
        }
        items(news?.size ?: 0) {
            NewsCardItem(newsItem = news?.get(it))
        }
    }
}