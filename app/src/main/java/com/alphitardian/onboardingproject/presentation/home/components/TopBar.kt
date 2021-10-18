package com.alphitardian.onboardingproject.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.alphitardian.onboardingproject.R

@Composable
fun TopBar() {
    val profilePlaceholder =
        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"

    Card(
        shape = RoundedCornerShape(bottomEnd = 12.dp),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .height(118.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 17.dp, vertical = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()) {
                Image(
                    painter = rememberImagePainter(
                        data = profilePlaceholder,
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = stringResource(id = R.string.content_description_profile_image),
                    modifier = Modifier
                        .size(75.dp)
                        .padding(end = 22.dp)
                )
                Column {
                    Text(text = stringResource(id = R.string.placeholder_home_user_fullname),
                        color = MaterialTheme.colors.onPrimary,
                        style = TextStyle(fontWeight = FontWeight.W700, fontSize = 18.sp),
                        modifier = Modifier.padding(bottom = 4.dp))
                    Text(text = stringResource(id = R.string.placeholder_home_user_bio),
                        color = MaterialTheme.colors.onPrimary,
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W400))
                    Text(text = stringResource(id = R.string.placeholder_home_user_web),
                        color = MaterialTheme.colors.onPrimary,
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W400))
                }
            }
        }
    }
}