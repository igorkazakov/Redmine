package com.igorkazakov.user.redminepro.screen.issues;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.repository.RepositoryInterface;
import com.igorkazakov.user.redminepro.screen.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IssuesFragment extends BaseFragment implements IssuesView {


    @BindView(R.id.contentView)
    FrameLayout mContentView;

    @BindView(R.id.issuesList)
    RecyclerView mIssueList;

    @BindView(R.id.issueSwipeRefresh)
    SwipeRefreshLayout mIssueSwipeRefresh;

    @InjectPresenter
    public IssuesPresenter mPresenter;

    @Inject
    RepositoryInterface mRepository;

    @ProvidePresenter
    IssuesPresenter provideIssuesPresenter() {
        return new IssuesPresenter(mRepository);
    }

    @Override
    public MvpDelegate getMvpDelegate() {
        RedmineApplication.getComponent().inject(this);
        return super.getMvpDelegate();
    }

    public static IssuesFragment newInstance() {
        IssuesFragment issuesFragment = new IssuesFragment();
        return issuesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.content_issues, container, false);
        ButterKnife.bind(this, view);

        mIssueSwipeRefresh.setOnRefreshListener(() -> mPresenter.tryLoadIssuesData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mIssueList.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void setupView(List<Issue> issueModels) {

        IssuesAdapter adapter = new IssuesAdapter(issueModels);
        mIssueList.setAdapter(adapter);
        mIssueSwipeRefresh.setRefreshing(false);
    }
}
