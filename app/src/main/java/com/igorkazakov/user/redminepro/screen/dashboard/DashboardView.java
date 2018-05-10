package com.igorkazakov.user.redminepro.screen.dashboard;

import com.igorkazakov.user.redminepro.models.StatisticModel;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.screen.base.BaseViewInterface;

import java.util.List;

/**
 * Created by user on 12.07.17.
 */

public interface DashboardView extends BaseViewInterface {

    void setupCurrentWeekStatistic(float remainHours, float remainDays, float weekHours);
    void setupStatisticRecyclerView(List<StatisticModel> timeModelList);
    void setupChart(TimeModel model, float kpi);
}
