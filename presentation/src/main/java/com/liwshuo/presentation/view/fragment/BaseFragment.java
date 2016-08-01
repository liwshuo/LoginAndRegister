package com.liwshuo.presentation.view.fragment;

import android.app.Fragment;

import com.liwshuo.presentation.internal.di.HasComponent;

/**
 * Created by lishuo on 16/7/28.
 */

public abstract class BaseFragment extends Fragment {

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
