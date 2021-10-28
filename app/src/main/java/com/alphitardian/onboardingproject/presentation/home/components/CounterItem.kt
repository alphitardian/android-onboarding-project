package com.alphitardian.onboardingproject.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
fun CounterItem(icon: Painter, iconDescription: String, counter: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 18.dp)) {
        Icon(painter = icon,
            contentDescription = iconDescription,
            tint = Color.Gray,
            modifier = Modifier
                .padding(end = 4.dp))
        Text(text = counter,
            style = TextStyle(color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.W600))
    }
}