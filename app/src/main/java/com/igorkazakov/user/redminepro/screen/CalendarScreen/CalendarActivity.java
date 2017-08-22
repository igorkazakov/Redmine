package com.igorkazakov.user.redminepro.screen.CalendarScreen;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarActivity extends AppCompatActivity {

    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        initCalendarView();

    }

    private void initCalendarView() {

        mCalendarView.setOnDateChangedListener((widget, date, selected) -> {

            // TODO
            //create activity with list time entries
            //with date argument
        });

        mCalendarView.setOnMonthChangedListener((widget, date) -> {

            List<CalendarDayEntity> calendarDayEntityList = DatabaseManager.getDatabaseHelper()
                    .getCalendarDayDAO().getCalendarMonthDaysWithDate(DateUtils.getMonthInterval(date.getMonth()));

            mCalendarView.removeDecorators();
            ArrayList<CalendarDay> listOfHoliday = new ArrayList<>();
            ArrayList<CalendarDay> listOfHospital = new ArrayList<>();
            ArrayList<CalendarDay> listOfVacation = new ArrayList<>();
            for (CalendarDayEntity day: calendarDayEntityList) {

                Date dayDate = DateUtils.dateFromString(day.getDate(),
                        DateUtils.getSimpleFormatter());

                switch (day.getType()) {
                    case CalendarDayEntity.FEAST:
                    case CalendarDayEntity.HOLIDAY:
                        listOfHoliday.add(CalendarDay.from(dayDate));
                        break;

                    case CalendarDayEntity.HOSPITAL:
                        listOfHospital.add(CalendarDay.from(dayDate));
                        break;

                    case CalendarDayEntity.VACATION:
                        listOfVacation.add(CalendarDay.from(dayDate));
                        break;
                }
            }

            int colorHoliday = ContextCompat.getColor(CalendarActivity.this, R.color.color_activity_calendar_holiday);
            int colorHospital = ContextCompat.getColor(CalendarActivity.this, R.color.color_activity_calendar_hospital);
            int colorVacation = ContextCompat.getColor(CalendarActivity.this, R.color.color_activity_calendar_vacation);

            mCalendarView.addDecorator(new EventDecorator(colorHoliday, listOfHoliday));
            mCalendarView.addDecorator(new EventDecorator(colorHospital, listOfHospital));
            mCalendarView.addDecorator(new EventDecorator(colorVacation, listOfVacation));
        });
    }

}
