package com.igorkazakov.user.redminepro.screen.Issue_detail;

import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.IssueDetail;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Namable;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueDetailPresenterTest {

    @Mock
    RedmineRepository redmineRepository;

    @Mock
    IssueDetailView issueDetailView;

    IssueDetailPresenter presenter;

    public IssueDetailPresenterTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new IssueDetailPresenter(redmineRepository);
        presenter.attachView(issueDetailView);
    }

    @Test
    public void tryLoadIssueDetailsData() {

        when(redmineRepository.getIssueDetails(1))
                .thenReturn(Observable.just(new IssueDetail()));
        presenter.tryLoadIssueDetailsData(1);

        verify(issueDetailView).showLoading();
        verify(issueDetailView).hideLoading();
        verify(issueDetailView).setupView(Mockito.any());
    }

    @Test
    public void failLoadIssueDetailsData() {

        when(redmineRepository.getIssueDetails(1))
                .thenReturn(Observable.error(new ApiException("", 9)));
        presenter.tryLoadIssueDetailsData(1);

        verify(issueDetailView).showLoading();
        verify(issueDetailView).hideLoading();
        verify(issueDetailView).showError(Mockito.any());
        verify(issueDetailView, never()).setupView(Mockito.any());
    }

    @Test
    public void getSafeName() {

        Namable object = new Status();
        object.setName("name");

        String expectedName = "name";
        String actualName = presenter.getSafeName(object);
        assertEquals(expectedName, actualName);

        object.setName(null);
        expectedName = "";
        actualName = presenter.getSafeName(object);
        assertEquals(expectedName, actualName);
    }
}