package com.igorkazakov.user.redminepro.di.component;

import com.igorkazakov.user.redminepro.api.AuthInterceptor;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.di.module.ApplicationModule;
import com.igorkazakov.user.redminepro.di.module.AuthModule;
import com.igorkazakov.user.redminepro.di.module.RepositoryModule;
import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;
import com.igorkazakov.user.redminepro.screen.Issue_detail.IssueDetailPresenter;
import com.igorkazakov.user.redminepro.screen.auth.LoginPresenter;
import com.igorkazakov.user.redminepro.screen.calendar.CalendarPresenter;
import com.igorkazakov.user.redminepro.screen.dashboard.DashboardPresenter;
import com.igorkazakov.user.redminepro.screen.issues.IssuesPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class,
        ApplicationModule.class,
        AuthModule.class
})

public interface ApplicationComponent {

    void inject(RedmineApplication application);
    void inject(RedmineRepository redmineRepository);
    void inject(OggyRepository oggyRepository);
    void inject(LoginPresenter loginPresenter);
    void inject(AuthInterceptor authInterceptor);
    void inject(CalendarPresenter calendarPresenter);
    void inject(DashboardPresenter dashboardPresenter);
    void inject(IssuesPresenter issuesPresenter);
    void inject(IssueDetailPresenter issueDetailPresenter);





}
