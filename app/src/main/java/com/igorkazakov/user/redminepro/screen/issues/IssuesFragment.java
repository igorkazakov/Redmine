package com.igorkazakov.user.redminepro.screen.issues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.models.IssueModel;
import com.igorkazakov.user.redminepro.screen.general.LoadingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class IssuesFragment extends Fragment implements IssuesView {


    @BindView(R.id.contentView)
    FrameLayout mContentView;

    @BindView(R.id.issuesList)
    RecyclerView mIssueList;

    @BindView(R.id.issueSwipeRefresh)
    SwipeRefreshLayout mIssueSwipeRefresh;

    private IssuesPresenter mPresenter;
    private LoadingFragment mLoadingView;

    public static IssuesFragment newInstance() {
        IssuesFragment issuesFragment = new IssuesFragment();
        return issuesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_issues, container, false);
        ButterKnife.bind(this, view);

        mIssueSwipeRefresh.setOnRefreshListener(() -> mPresenter.tryLoadIssuesData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mIssueList.setLayoutManager(linearLayoutManager);
        mLoadingView = new LoadingFragment(getActivity(), mContentView);
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(getActivity(), getActivity().getSupportLoaderManager());
        mPresenter = new IssuesPresenter(lifecycleHandler, this);
        mPresenter.tryLoadIssuesData();

        return view;
    }

    @Override
    public void setupView() {

        List<IssueModel> issueModels = DatabaseManager.getDatabaseHelper().getIssueEntityDAO().getMyIssues();
        IssuesAdapter adapter = new IssuesAdapter(issueModels);
        mIssueList.setAdapter(adapter);
        mIssueSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }
}
