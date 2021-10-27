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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TopBar(userProfile: UserResponse?) {
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
                userProfile?.picture?.let {
                    GlideImage(
                        imageModel = it,
                        requestOptions = RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .override(128)
                            .circleCrop(),
                        modifier = Modifier.size(60.dp),
                        shimmerParams = ShimmerParams(
                            baseColor = Color.DarkGray,
                            highlightColor = Color.Gray,
                            durationMillis = 750,
                            dropOff = 0.65f,
                            tilt = 20f
                        ),
                        failure = {
                            Image(
                                painter = painterResource(id = R.drawable.placeholder_profile_image),
                                contentDescription = stringResource(id = R.string.content_description_profile_image)
                            )
                        }
                    )
                }
                Column(modifier = Modifier.padding(start = 22.dp)) {
                    Text(text = userProfile?.name
                        ?: stringResource(id = R.string.placeholder_home_user_fullname),
                        color = MaterialTheme.colors.onPrimary,
                        style = TextStyle(fontWeight = FontWeight.W700, fontSize = 18.sp),
                        modifier = Modifier.padding(bottom = 4.dp))
                    Text(text = userProfile?.bio
                        ?: stringResource(id = R.string.placeholder_home_user_bio),
                        color = MaterialTheme.colors.onPrimary,
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W400))
                    Text(text = userProfile?.web
                        ?: stringResource(id = R.string.placeholder_home_user_web),
                        color = MaterialTheme.colors.onPrimary,
                        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W400))
                }
            }
        }
    }
}