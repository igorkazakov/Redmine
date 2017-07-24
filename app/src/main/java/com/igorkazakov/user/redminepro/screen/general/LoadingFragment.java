package com.igorkazakov.user.redminepro.screen.general;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorkazakov.user.redminepro.R;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by user on 21.07.17.
 */

public class LoadingFragment extends Fragment {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static LoadingView view(@NonNull FragmentManager fm) {
        return new LoadingFragmentView(fm);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        return view;
    }

    private static class LoadingFragmentView implements LoadingView {

        private final FragmentManager mFm;
        private final AtomicBoolean mWaitForHide;

        private LoadingFragmentView(@NonNull FragmentManager fm) {

            mFm = fm;
            boolean shown = fm.findFragmentByTag(LoadingFragment.class.getName()) != null;
            mWaitForHide = new AtomicBoolean(shown);
        }

        @Override
        public void showLoading() {
            if (mWaitForHide.compareAndSet(false, true)) {
                if (mFm.findFragmentByTag(LoadingFragment.class.getName()) == null) {
                    LoadingFragment fragment = new LoadingFragment();
                    FragmentTransaction ft = mFm.beginTransaction();
                    ft.add(R.id.dashboardContainer, fragment, LoadingFragment.class.getName()).commit();
                }
            }
        }

        @Override
        public void hideLoading() {
            if (mWaitForHide.compareAndSet(true, false)) {
                HANDLER.post(new LoadingFragmentView.HideTask(mFm));
            }
        }

        private static class HideTask implements Runnable {

            private final Reference<FragmentManager> mFmRef;
            private int mAttempts = 10;

            public HideTask(@NonNull FragmentManager fm) {
                mFmRef = new WeakReference<>(fm);
            }

            @Override
            public void run() {
                HANDLER.removeCallbacks(this);
                final FragmentManager fm = mFmRef.get();
                if (fm != null) {
                    final LoadingFragment fragment = (LoadingFragment) fm.findFragmentByTag(LoadingFragment.class.getName());
                    if (fragment != null) {
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment).commit();

                    } else if (--mAttempts >= 0) {
                        HANDLER.postDelayed(this, 300);
                    }
                }
            }
        }
    }
}
