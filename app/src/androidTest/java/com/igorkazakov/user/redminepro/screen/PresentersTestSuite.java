package com.igorkazakov.user.redminepro.screen;

import com.igorkazakov.user.redminepro.screen.Issue_detail.IssueDetailPresenterTest;
import com.igorkazakov.user.redminepro.screen.auth.LoginPresenterTest;
import com.igorkazakov.user.redminepro.screen.calendar.CalendarPresenterTest;
import com.igorkazakov.user.redminepro.screen.dashboard.DashboardPresenterTest;
import com.igorkazakov.user.redminepro.screen.issues.IssuesPresenterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginPresenterTest.class,
        CalendarPresenterTest.class,
        DashboardPresenterTest.class,
        IssueDetailPresenterTest.class,
        IssuesPresenterTest.class})

public class PresentersTestSuite {
}
