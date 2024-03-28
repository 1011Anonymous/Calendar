package com.example.wea_cal_note.weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.versionedparcelable.R
import com.example.wea_cal_note.ui.theme.Blue10
import com.example.wea_cal_note.ui.theme.Blue20
import com.example.wea_cal_note.ui.theme.Cyan10
import com.example.wea_cal_note.ui.theme.Yellow10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherCard() {
    Card(
        onClick = {},
        modifier = Modifier
            .size(width = 390.dp, height = 130.dp)
            .padding(16.dp),

        ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "17°",
                modifier = Modifier.padding(8.dp),
                color = Cyan10,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )

            Column {
                Text(
                    text = "12°/21°",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    Text(
                        text = "阴",
                        modifier = Modifier.padding(end = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "54良",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.background(Yellow10)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column {
                Text(
                    text = "绵阳市",
                    modifier = Modifier.padding(top = 8.dp, start = 50.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "北方2级 | 湿度64%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Blue20)
        ) {
            WeatherInfoSegment(
                day = "明天",
                weatherCondition = "晴",
                wind = "东风2级",
                temperature = "11°/28°"
            )
            Divider(
                color = Color.White, modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .padding(vertical = 4.dp)
            )
            WeatherInfoSegment(
                day = "后天",
                weatherCondition = "多云",
                wind = "东北风2级",
                temperature = "15°/30°"
            )
        }
    }

}

@Composable
fun WeatherInfoSegment(
    day: String,
    weatherCondition: String,
    wind: String,
    temperature: String
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = day,
                modifier = Modifier.padding(horizontal = 4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = weatherCondition,
                modifier = Modifier.padding(end = 40.dp),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            Image(
                painter = painterResource(id = com.example.wea_cal_note.R.drawable.sunny),
                contentDescription = "SUNNY"
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = wind,
                modifier = Modifier.padding(start = 4.dp, end = 40.dp),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Text(
                text = temperature,
                modifier = Modifier.padding(end = 4.dp),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}