package com.ashish.jiraissuetracker.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.extras.ZAnimatorListener;
import com.ashish.jiraissuetracker.fragments.FilterIssuesFragment;
import com.ashish.jiraissuetracker.fragments.IssuesJqlUrlFragment;
import com.ashish.jiraissuetracker.interfaces.FilterIssueinterface;
import com.ashish.jiraissuetracker.utils.UIUtils;

import java.util.List;

/**
 * Created by Ashish on 24/06/16.
 */
public class FilterIssuesActivity extends BaseActivity implements FilterIssueinterface, View.OnClickListener {

    public int selectedSortOrderPosition = 0;
    List<String> selectedAssignee, selectedReporter, selectedprojects, selectedPriorities, selectedResolution, selectedStatus, selectedType;
    String text, issueKey, labels;

    int toolbarAnimationDuration = 500, floatingActionButtonHideDuration = 200, floatingActionButtonshowDuration = 400;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_issues_activity_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.FloatingActionButton);
        floatingActionButton.setOnClickListener(this);

        if (getIntent().hasExtra("openfilter") && getIntent().getBooleanExtra("openfilter", false)) {
            setFilterIssueFragment();
        }
    }

    @Override
    public void hideFloatingActionButton() {
        try {
            floatingActionButton.animate()
                    .setDuration(floatingActionButtonHideDuration)
                    .scaleX(0)
                    .scaleY(0)
                    .setInterpolator(new AccelerateInterpolator())
                    .setListener(new ZAnimatorListener() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            floatingActionButton.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            floatingActionButton.setVisibility(View.GONE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showFloatingActionButton() {
        try {
            floatingActionButton.animate()
                    .setDuration(floatingActionButtonshowDuration)
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new ZAnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            floatingActionButton.setVisibility(View.VISIBLE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void hideToolbar() {
        try {
            toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    UIUtils.removeViewTreeObserver(toolbar, this);
                    int height = toolbar.getHeight();
                    toolbar.animate().translationY(-height).setDuration(toolbarAnimationDuration).start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (floatingActionButton.getVisibility() == View.GONE) {
            showFloatingActionButton();
        }

        try {
            String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentByTag(fragmentTag);
            if (currentFragment instanceof FilterIssuesFragment) {
                if (((FilterIssuesFragment) currentFragment).checkIfIgnoreBackPress()) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* handle fab icon on back press */
        boolean foundFilterFragment = false, foundIssueFragment = false;
        if (getSupportFragmentManager() != null && getSupportFragmentManager().getFragments() != null) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof FilterIssuesFragment) {
                    foundFilterFragment = true;
                } else if (fragment instanceof IssuesJqlUrlFragment) {
                    foundIssueFragment = true;
                }
            }
        }
        if (foundFilterFragment && foundIssueFragment) {
            changeFabIcon(R.drawable.ic_tick, R.drawable.ic_filter_filled);
        }
        /* end handle fab icon on back press */

        super.onBackPressed();
    }

    void showToolbar() {
        try {
            toolbar.animate().translationY(0).setDuration(toolbarAnimationDuration).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFilterIssueFragment() {
        changeFabIcon(R.drawable.ic_tick, true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FilterIssuesFragment.newInstance(new Bundle()))
                .commitAllowingStateLoss();
    }

    private void addFilterIssuesFragment() {
        changeFabIcon(R.drawable.ic_filter_filled, R.drawable.ic_tick);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, FilterIssuesFragment.newInstance(new Bundle()))
                .addToBackStack("filter").commitAllowingStateLoss();
    }

    private void changeFabIcon(int icon, boolean changeColor) {
        if (this != null && this.getResources() != null) {
            floatingActionButton.setImageResource(icon);
        }
    }

    private void changeFabIcon(final int iconOld, final int newIcon) {
        try {
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator anim1 = ObjectAnimator.ofFloat(floatingActionButton, "rotation", 0f, 180f);
            anim1.setDuration(100);
            anim1.addListener(new ZAnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    floatingActionButton.setImageResource(iconOld);
                }
            });

            ObjectAnimator anim2 = ObjectAnimator.ofFloat(floatingActionButton, "rotation", 180f, 360f);
            anim2.setDuration(100);
            anim2.addListener(new ZAnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    floatingActionButton.setImageResource(newIcon);
                }
            });

            animatorSet.play(anim1);
            animatorSet.play(anim2).after(anim1);
            animatorSet.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIssuesFragmentWithJqlPostRequest(Bundle bundle) {
        while (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }

        UIUtils.hideSoftKeyboard(this);

        changeFabIcon(R.drawable.ic_tick, R.drawable.ic_filter_filled);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, IssuesJqlUrlFragment.newInstance(bundle))
                .commitAllowingStateLoss();
    }

    @Override
    public int getSelectedSortOrderPosition() {
        return selectedSortOrderPosition;
    }

    @Override
    public void setSelectedSortOrderPosition(int selectedSortOrderPosition) {
        this.selectedSortOrderPosition = selectedSortOrderPosition;
    }

    @Override
    public List<String> getSelectedAssignee() {
        return selectedAssignee;
    }

    @Override
    public void setSelectedAssignee(List<String> selectedAssignee) {
        this.selectedAssignee = selectedAssignee;
    }

    @Override
    public List<String> getSelectedReporter() {
        return selectedReporter;
    }

    @Override
    public void setSelectedReporter(List<String> selectedReporter) {
        this.selectedReporter = selectedReporter;
    }

    @Override
    public List<String> getSelectedprojects() {
        return selectedprojects;
    }

    @Override
    public void setSelectedprojects(List<String> selectedprojects) {
        this.selectedprojects = selectedprojects;
    }

    @Override
    public List<String> getSelectedPriorities() {
        return selectedPriorities;
    }

    @Override
    public void setSelectedPriorities(List<String> selectedPriorities) {
        this.selectedPriorities = selectedPriorities;
    }

    @Override
    public List<String> getSelectedResolution() {
        return selectedResolution;
    }

    @Override
    public void setSelectedResolution(List<String> selectedResolution) {
        this.selectedResolution = selectedResolution;
    }

    @Override
    public List<String> getSelectedStatus() {
        return selectedStatus;
    }

    @Override
    public void setSelectedStatus(List<String> selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    @Override
    public List<String> getSelectedType() {
        return selectedType;
    }

    @Override
    public void setSelectedType(List<String> selectedType) {
        this.selectedType = selectedType;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getIssueKey() {
        return issueKey;
    }

    @Override
    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    @Override
    public String getLabels() {
        return labels;
    }

    @Override
    public void setLabels(String labels) {
        this.labels = labels;
    }

    @Override
    public void setFilterDataAgain() {
        try {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof FilterIssuesFragment) {
                    ((FilterIssuesFragment) fragment).setData();
                    return;
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.FloatingActionButton:
                try {
                    if (getSupportFragmentManager() != null && getSupportFragmentManager().getFragments() != null) {
                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            if (fragment instanceof FilterIssuesFragment) {
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("from_jql", true);
                                ((FilterIssuesFragment) fragment).saveDataForEdittexts();
                                setIssuesFragmentWithJqlPostRequest(bundle);
                                return;
                            }
                        }
                    }

                    addFilterIssuesFragment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
