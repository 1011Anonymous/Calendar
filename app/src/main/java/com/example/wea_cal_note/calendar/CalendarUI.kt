package com.example.wea_cal_note.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wea_cal_note.ui.theme.Blue10
import com.example.wea_cal_note.ui.theme.Red10
import com.example.wea_cal_note.ui.theme.fontFamily
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarUI() {
    val currentMonth = remember {
        YearMonth.now()
    }
    val startMonth = remember {
        currentMonth.minusMonths(600)
    }
    val endMonth = remember {
        currentMonth.plusMonths(600)
    }
    val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid,
    )
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = rememberFirstMostVisibleMonth(state = state, viewportPercent = 90f)
    val today = remember { LocalDate.now() }
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CalendarTitle(
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
            onClick = {
                showDialog = true
            },
        )

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(
                    day,
                    HolidayViewModel(),
                    isSelected = selectedDate == day.date,
                    isToday = today == day.date
                ) { day ->
                    selectedDate = if (selectedDate == day.date) null else day.date
                }
            },
            monthHeader = {
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            }
        )
    }
    MonthPicker(
        visible = showDialog,
        currentMonth = Calendar.getInstance().get(Calendar.MONTH),
        currentYear = Year.now().value,
        confirmClicked = { month, year ->
            coroutineScope.launch {
                state.scrollToMonth(YearMonth.of(year, month))
                showDialog = false
            }
        },
        cancelClicked = {
            showDialog = false
        },
    )
}

@Composable
fun Day(
    day: CalendarDay,
    holidayViewModel: HolidayViewModel,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    val isHoliday by holidayViewModel.isHoliday.observeAsState(false)
    if (day.date.year <= Year.now().value) {
        LaunchedEffect(day) {
            holidayViewModel.checkIfHoliday(day.date.year, day.date.monthValue, day.date.dayOfMonth)
        }
    }

    Box(
        modifier = Modifier
            .then(
                if (isToday) {
                    Modifier
                        .aspectRatio(1f)
                        .clickable(
                            enabled = day.position == DayPosition.MonthDate,
                            onClick = { onClick(day) },
                        )
                        .background(
                            color = if (isSelected) Blue10 else Color.Transparent,
                            shape = CircleShape,
                        )
                        .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = CircleShape
                    )
                } else {
                    Modifier
                        .aspectRatio(1f)
                        .background(
                            color = if (isSelected) Blue10 else Color.Transparent,
                            shape = CircleShape,
                        )
                        .clickable(
                            enabled = day.position == DayPosition.MonthDate,
                            onClick = { onClick(day) },
                        )
                }
            ),
        contentAlignment = Alignment.Center,
    ) {

        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray
        )

        Text(
            modifier = Modifier
                .size(10.dp)
                .absoluteOffset(20.dp, (-20).dp),
            color = if (day.position == DayPosition.MonthDate) Red10 else Color.Gray,
            text = if (isHoliday) "休" else "",
            fontSize = 10.sp,
            fontFamily = fontFamily,
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = convertGregorianToChineseLunar(
                day.date.year,
                day.date.monthValue,
                day.date.dayOfMonth
            ),
            fontSize = 10.sp,
            fontFamily = fontFamily,
        )

    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.SIMPLIFIED_CHINESE),
            )
        }
    }
}

