import datetime
from chinese_calendar import is_holiday, is_workday

def is_date_a_holiday(year: int, month: int, day: int) -> bool:
    date_to_check = datetime.date(year, month, day)
    return is_holiday(date_to_check)