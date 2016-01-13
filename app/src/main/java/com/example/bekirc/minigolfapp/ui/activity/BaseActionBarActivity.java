package com.example.bekirc.minigolfapp.ui.activity;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.bekirc.minigolfapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

/**
 * @author bekirc on 28.10.15.
 */
public abstract class BaseActionBarActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        setupToolbar();
    }

    abstract protected void setupToolbar();

    public Toolbar getToolbar() {
        return toolbar;
    }

    protected void showFragment(final int containerId, final Fragment fragment, final String tag, final boolean addToBackStack) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        final Fragment existingFragment = fragmentManager.findFragmentByTag(tag);
        if (existingFragment != null) {
            fragmentManager.popBackStack(tag, 0);
        } else {
            ft.replace(containerId, fragment, tag);
            if (addToBackStack) {
                ft.addToBackStack(tag);
            }
        }

        ft.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    protected void showDialogFragment(DialogFragment fragment, String tag) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && !isFinishing()) {
                try {
                    fragment.show(getSupportFragmentManager(), tag);
                } catch (Exception e) {
                    removeDialogs(tag);
                    Timber.e(e, e.getMessage());
                }
            }
        } else if (!isFinishing()) {
            try {
                fragment.show(getSupportFragmentManager(), tag);
            } catch (Exception e) {
                removeDialogs(tag);
                Timber.e(e, e.getMessage());
            }
        }
    }

    void removeDialogs(String... tags) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = new ArrayList<>();
        for (String tag : Arrays.asList(tags)) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null) {
                fragments.add(fragment);
            }
        }
        for (Fragment fragment : fragments) {
            ft.remove(fragment);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && !isFinishing()) {
                try {
                    ft.commitAllowingStateLoss();
                } catch (Exception e) {
                    Timber.e(e, e.getMessage());
                }
            }
        } else if (!isFinishing()) {
            try {
                ft.commitAllowingStateLoss();
            } catch (Exception e) {
                Timber.e(e, e.getMessage());
            }
        }
    }
}
