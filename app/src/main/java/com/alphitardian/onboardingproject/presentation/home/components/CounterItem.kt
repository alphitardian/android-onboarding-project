package com.alphitardian.onboardingproject.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CounterItem(
    icon: Painter,
    iconDescription: String,
    counter: Int,
    modifier: Modifier = Modifier,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 18.dp)) {
        Image(painter = icon,
            contentDescription = iconDescription,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 4.dp))
        Text(text = counter.toString(),
            style = TextStyle(color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.W600),
            modifier = modifier
        )
    }
}