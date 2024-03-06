package com.example.wea_cal_note.calendar

import androidx.compose.foundation.gestures.ScrollableDefaults.flingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.Month


@Composable
fun DatePickerDialog(
    currentYear: Int,
    currentMonth: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.padding(16.dp)) {
                    YearSelectionMenu(currentYear = currentYear)
                    //MonthSelectionMenu(currentMonth = currentMonth)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "取消")
                    }
                    TextButton(onClick = onConfirm) {
                        Text(text = "确认")
                    }
                }
            }
        }
    }
}

@Composable
fun YearSelectionMenu(currentYear: Int) {
    val yearsRange = (currentYear - 10)..(currentYear + 10)
    val state = rememberLazyListState()
    var selectedYear by remember { mutableIntStateOf(currentYear) }

    LazyColumn(
        modifier = Modifier
            .height(150.dp),
        state = state,
        flingBehavior = flingBehavior(),
    ) {
        items(yearsRange.toList()) { year ->
            Text(
                text = year.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = if (year == selectedYear) Color.Black else Color.Gray
            )
        }
    }

    LaunchedEffect(key1 = currentYear) {
        state.animateScrollToItem(yearsRange.indexOf(currentYear))
    }

    LaunchedEffect(key1 = state) {
        snapshotFlow { state.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                selectedYear = yearsRange.toList()[index]
            }

    }
}

@Composable
fun MonthSelectionMenu(currentMonth: Month) {

}