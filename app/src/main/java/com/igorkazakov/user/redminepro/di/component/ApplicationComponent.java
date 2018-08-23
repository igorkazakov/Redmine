package com.igorkazakov.user.redminepro.di.component;

import com.igorkazakov.user.redminepro.api.AuthInterceptor;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.di.module.ApiModule;
import com.igorkazakov.user.redminepro.di.module.ApplicationModule;
import com.igorkazakov.user.redminepro.di.module.AuthModule;
import com.igorkazakov.user.redminepro.di.module.RepositoryModule;
import com.igorkazakov.user.redminepro.repository.Repository;
import com.igorkazakov.user.redminepro.screen.Issue_detail.IssueDetailActivity;
import com.igorkazakov.user.redminepro.screen.auth.LoginActivity;
import com.igorkazakov.user.redminepro.screen.base.BaseActivity;
import com.igorkazakov.user.redminepro.screen.base.BaseFragment;
import com.igorkazakov.user.redminepro.screen.calendar.CalendarFragment;
import com.igorkazakov.user.redminepro.screen.dashboard.DashboardFragment;
import com.igorkazakov.user.redminepro.screen.issues.IssuesFragment;
import com.igorkazakov.user.redminepro.utils.KPIUtils;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class,
        ApplicationModule.class,
        AuthModule.class,
        RepositoryModule.class
})

public interface ApplicationComponent {

    void inject(RedmineApplication application);
    void inject(AuthInterceptor authInterceptor);
    void inject(IssuesFragment issuesFragment);
    void inject(DashboardFragment dashboardFragment);
    void inject(CalendarFragment calendarFragment);
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);
    void inject(LoginActivity loginActivity);
    void inject(IssueDetailActivity issueDetailActivity);
    void inject(Repository repository);
    void inject(KPIUtils kpiUtils);
}
