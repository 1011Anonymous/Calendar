package com.example.wea_cal_note.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaquo.python.Python
import kotlinx.coroutines.launch

class HolidayViewModel : ViewModel() {
    private val _isHoliday = MutableLiveData<Boolean>()
    val isHoliday: LiveData<Boolean> = _isHoliday

    fun checkIfHoliday(year: Int, month: Int, day:Int) {
        viewModelScope.launch {
            val python = Python.getInstance()
            val pythonModule = python.getModule("festival")
            val result = pythonModule.callAttr("is_date_a_holiday", year, month, day).toBoolean()
            _isHoliday.postValue(result)
        }
    }
}