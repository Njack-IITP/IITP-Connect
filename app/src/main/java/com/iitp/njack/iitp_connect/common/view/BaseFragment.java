package com.iitp.njack.iitp_connect.common.view;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.iitp.njack.iitp_connect.IITPConnectApplication;
import com.iitp.njack.iitp_connect.common.di.Injectable;
import com.iitp.njack.iitp_connect.common.presenter.BasePresenter;
import com.iitp.njack.iitp_connect.ui.ViewUtils;

import dagger.Lazy;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements Injectable {

    protected abstract @StringRes int getTitle();

    @Override
    public void onResume() {
        super.onResume();
        setTitle(getString(getTitle()));
    }

    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    protected Lazy<P> getPresenterProvider() {
        return null;
    }

    @SuppressWarnings("PMD.NullAssignment")
    protected P getPresenter() {
        Lazy<P> provider = getPresenterProvider();
        return (provider == null) ? null : provider.get();
    }

    protected void setTitle(String title) {
        ViewUtils.setTitle(this, title);
    }

    @Override
    public void onStop() {
        super.onStop();
        P presenter = getPresenter();
        if (presenter != null)
            presenter.detach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        IITPConnectApplication.getRefWatcher(getActivity()).watch(this);
    }

}

