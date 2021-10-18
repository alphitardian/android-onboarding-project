package com.alphitardian.onboardingproject.presentation.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphitardian.onboardingproject.R

@Composable
fun NewsContent() {
    LazyColumn(modifier = Modifier.padding(top = 29.dp, start = 17.dp, end = 17.dp)) {
        item {
            Text(text = stringResource(id = R.string.home_title),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W700))
            Text(text = stringResource(id = R.string.home_sub_title),
                modifier = Modifier.padding(bottom = 16.dp))
        }
        items(5) {
            NewsCardItem()
        }
    }
}