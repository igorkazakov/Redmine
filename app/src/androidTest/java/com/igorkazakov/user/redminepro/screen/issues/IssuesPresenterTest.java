package com.igorkazakov.user.redminepro.screen.issues;

import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Observable;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssuesPresenterTest {

    @Mock
    RedmineRepository redmineRepository;

    @Mock
    IssuesView issuesView;

    IssuesPresenter issuesPresenter;

    public IssuesPresenterTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        issuesPresenter = spy(new IssuesPresenter(redmineRepository));
        doNothing().when(issuesPresenter).onFirstViewAttach();

        issuesPresenter.attachView(issuesView);
    }

    @Test
    public void tryLoadIssuesData() {

        when(redmineRepository.getMyIssues()).thenReturn(Observable.fromArray(new ArrayList<>()));
        issuesPresenter.tryLoadIssuesData();

        verify(issuesView).showLoading();
        verify(issuesView).hideLoading();
        verify(issuesView).setupView(Mockito.any());
    }

    @Test
    public void failLoadIssuesData() {

        when(redmineRepository.getMyIssues()).thenReturn(Observable.error(new ApiException("", 9)));
        issuesPresenter.tryLoadIssuesData();

        verify(issuesView).showLoading();
        verify(issuesView).hideLoading();
        verify(issuesView).showError(Mockito.any());
    }
}