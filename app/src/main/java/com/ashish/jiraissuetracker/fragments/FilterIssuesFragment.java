package com.ashish.jiraissuetracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.FilterIssuesActivity;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.interfaces.FilterIssueinterface;
import com.ashish.jiraissuetracker.objects.issueComments.Author;
import com.ashish.jiraissuetracker.utils.UIUtils;

import java.util.List;

/**
 * Created by Ashish on 24/06/16.
 */
public class FilterIssuesFragment extends BaseFragment implements View.OnClickListener {

    EditText filterText, filterIssueKey, filterLabels;

    TextView filterOrder, filterAssignee, filterReporter,
            filterProject, filterPriority, filterResolution, filterStatus, filterType;
    LinearLayout filterOrderLayout, filterAssigneeLayout, filterReporterLayout,
            filterProjectLayout, filterPriorityLayout, filterResolutionLayout, filterStatusLayout, filterTypeLayout;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    FilterIssueinterface issueinterface;
    TextView removeAllFilters;

    int selectedItem;
    boolean showedEditFilterDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FilterIssuesActivity) {
            issueinterface = (FilterIssueinterface) context;
        }
    }

    public static FilterIssuesFragment newInstance(Bundle b) {
        FilterIssuesFragment frg = new FilterIssuesFragment();
        frg.setArguments(b);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.filter_issues_fragment_layout, container, false);

        filterText = (EditText) rootView.findViewById(R.id.filter_text);
        filterIssueKey = (EditText) rootView.findViewById(R.id.filter_issuekey);
        filterOrder = (TextView) rootView.findViewById(R.id.filter_order_text);
        filterAssignee = (TextView) rootView.findViewById(R.id.filter_assignee);
        filterReporter = (TextView) rootView.findViewById(R.id.filter_reporter);
        filterLabels = (EditText) rootView.findViewById(R.id.filter_labels);
        filterProject = (TextView) rootView.findViewById(R.id.filter_project);
        filterPriority = (TextView) rootView.findViewById(R.id.filter_priority);
        filterResolution = (TextView) rootView.findViewById(R.id.filter_resolution);
        filterStatus = (TextView) rootView.findViewById(R.id.filter_status);
        filterType = (TextView) rootView.findViewById(R.id.filter_type);

        removeAllFilters = (TextView) rootView.findViewById(R.id.remove_all_filters);

        filterOrderLayout = (LinearLayout) rootView.findViewById(R.id.filter_order_text_container);
        filterAssigneeLayout = (LinearLayout) rootView.findViewById(R.id.filter_assignee_container);
        filterReporterLayout = (LinearLayout) rootView.findViewById(R.id.filter_reporter_c);
        filterProjectLayout = (LinearLayout) rootView.findViewById(R.id.filter_project_c);
        filterPriorityLayout = (LinearLayout) rootView.findViewById(R.id.filter_priority_c);
        filterResolutionLayout = (LinearLayout) rootView.findViewById(R.id.filter_resolution_c);
        filterStatusLayout = (LinearLayout) rootView.findViewById(R.id.filter_status_c);
        filterTypeLayout = (LinearLayout) rootView.findViewById(R.id.filter_type_c);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        filterOrderLayout.setOnClickListener(this);
        filterAssigneeLayout.setOnClickListener(this);
        filterReporterLayout.setOnClickListener(this);
        filterProjectLayout.setOnClickListener(this);
        filterPriorityLayout.setOnClickListener(this);
        filterResolutionLayout.setOnClickListener(this);
        filterStatusLayout.setOnClickListener(this);
        filterTypeLayout.setOnClickListener(this);
        removeAllFilters.setOnClickListener(this);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setDataForEdittexts();

        setData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_order_text_container:
                openSortOrderDialog();
                break;
            case R.id.filter_assignee_container:
                openSelectAssigneeFragment();
                break;
            case R.id.filter_reporter_c:
                openSelectReporterFragment();
                break;
            case R.id.filter_project_c:
                openSelectProjectFragment();
                break;
            case R.id.filter_priority_c:
                openSelectPriorityFragment();
                break;
            case R.id.filter_resolution_c:
                openSelectResolutionsFragment();
                break;
            case R.id.filter_status_c:
                openSelectStatusesFragment();
                break;
            case R.id.filter_type_c:
                openSelectTypesFragment();
                break;
            case R.id.remove_all_filters:
                showRemoveAllFiltersDialog();
                break;
        }
    }

    private void showRemoveAllFiltersDialog() {
        builder = new AlertDialog.Builder(getActivity()).setTitle("Clear Filters")
                .setMessage("All your filters will go away. Are you sure you want to reset filters?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeAllFilters();

                        setData();
                        setDataForEdittexts();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        dialog = builder.create();
        dialog.show();
    }

    private void showConfirmExitDiaog() {
        builder = new AlertDialog.Builder(getActivity()).setTitle("Exit Filter")
                .setMessage("You have not applied your changes yet. Are you sure you want to discard filter changes?")
                .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showedEditFilterDialog = true;
                        getActivity().onBackPressed();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showedEditFilterDialog = false;
                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        showedEditFilterDialog = false;
                    }
                });

        dialog = builder.create();
        dialog.show();
    }

    public boolean checkIfIgnoreBackPress() {
        if (showedEditFilterDialog) {
            return false;
        }

        if (!checkIfListEmpty(issueinterface.getSelectedAssignee(), issueinterface.getSelectedReporter()
                , issueinterface.getSelectedprojects(), issueinterface.getSelectedPriorities(), issueinterface.getSelectedResolution(),
                issueinterface.getSelectedStatus(), issueinterface.getSelectedType())
                || !checkIfEdittextEmpty(issueinterface.getIssueKey(), issueinterface.getText(), issueinterface.getLabels())
                || issueinterface.getSelectedSortOrderPosition() != 0) {
            showConfirmExitDiaog();

            return true;
        }
        return false;
    }

    boolean checkIfEdittextEmpty(String... args) {
        for (String string : args) {
            if (string != null && string.trim().length() > 0) {
                return false;
            }
        }
        return true;
    }

    boolean checkIfListEmpty(List<String>... args) {
        for (List<String> list : args) {
            if (list != null && list.size() > 0) {
                return false;
            }
        }
        return true;
    }

    private void removeAllFilters() {
        issueinterface.setSelectedSortOrderPosition(0);
        issueinterface.setText(null);
        issueinterface.setLabels(null);
        issueinterface.setIssueKey(null);
        issueinterface.setSelectedAssignee(null);
        issueinterface.setSelectedPriorities(null);
        issueinterface.setSelectedprojects(null);
        issueinterface.setSelectedReporter(null);
        issueinterface.setSelectedResolution(null);
        issueinterface.setSelectedStatus(null);
        issueinterface.setSelectedType(null);
    }

    private void openSelectAssigneeFragment() {
        issueinterface.hideFloatingActionButton();

        Bundle b = new Bundle();
        b.putInt("type", AppConstants.FILTER_USER_SELECT_ASSIGNEE);

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_fullscreen,
                SelectUserFragment.newInstance(b)).addToBackStack("user").commitAllowingStateLoss();
    }

    private void openSelectReporterFragment() {
        issueinterface.hideFloatingActionButton();

        Bundle b = new Bundle();
        b.putInt("type", AppConstants.FILTER_USER_SELECT_REPORTER);

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_fullscreen,
                SelectUserFragment.newInstance(b)).addToBackStack("user").commitAllowingStateLoss();
    }

    private void openSelectProjectFragment() {
        issueinterface.hideFloatingActionButton();

        Bundle b = new Bundle();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_fullscreen,
                SelectProjectFragment.newInstance(b)).addToBackStack("project").commitAllowingStateLoss();
    }

    private void openSelectResolutionsFragment() {
        issueinterface.hideFloatingActionButton();

        Bundle b = new Bundle();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_fullscreen,
                SelectResolutionsFragment.newInstance(b)).addToBackStack("resolution").commitAllowingStateLoss();
    }

    private void openSelectStatusesFragment() {
        issueinterface.hideFloatingActionButton();

        Bundle b = new Bundle();

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_fullscreen,
                SelectStatusesFragment.newInstance(b)).addToBackStack("status").commitAllowingStateLoss();
    }

    private void openSelectTypesFragment() {
        issueinterface.hideFloatingActionButton();

        Bundle b = new Bundle();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_fullscreen,
                SelectTypesFragment.newInstance(b)).addToBackStack("type").commitAllowingStateLoss();
    }

    private void openSelectPriorityFragment() {
        issueinterface.hideFloatingActionButton();

        Bundle b = new Bundle();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_fullscreen,
                SelectPriorityFragment.newInstance(b)).addToBackStack("priority").commitAllowingStateLoss();
    }

    private void openSortOrderDialog() {
        String[] listItems = getActivity().getResources().getStringArray(R.array.sort_order_options_values);
        selectedItem = issueinterface.getSelectedSortOrderPosition();

        builder = new AlertDialog.Builder(getActivity()).setTitle("Select Sort Order")
                .setMessage(null)
                .setSingleChoiceItems(listItems, issueinterface.getSelectedSortOrderPosition(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedItem = i;
                    }
                }).setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        issueinterface.setSelectedSortOrderPosition(selectedItem);

                        setData();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        dialog = builder.create();
        dialog.show();
    }

    public void setData() {
        try {
            String[] items = getActivity().getResources().getStringArray(R.array.sort_order_options);

            try {
                filterOrder.setText(items[issueinterface.getSelectedSortOrderPosition()]);
            } catch (Exception e) {
                e.printStackTrace();
                filterOrder.setText("Default");
            }

            if (issueinterface.getSelectedAssignee() == null || issueinterface.getSelectedAssignee().size() == 0) {
                filterAssignee.setText("All");
            } else {
                String assignee = TextUtils.join(", ", issueinterface.getSelectedAssignee());
                filterAssignee.setText(assignee);
            }

            if (issueinterface.getSelectedReporter() == null || issueinterface.getSelectedReporter().size() == 0) {
                filterReporter.setText("All");
            } else {
                String assignee = TextUtils.join(", ", issueinterface.getSelectedReporter());
                filterReporter.setText(assignee);
            }

            if (issueinterface.getSelectedprojects() == null || issueinterface.getSelectedprojects().size() == 0) {
                filterProject.setText("All");
            } else {
                String projects = TextUtils.join(", ", issueinterface.getSelectedprojects());
                filterProject.setText(projects);
            }

            if (issueinterface.getSelectedPriorities() == null || issueinterface.getSelectedPriorities().size() == 0) {
                filterPriority.setText("All");
            } else {
                String priorities = TextUtils.join(", ", issueinterface.getSelectedPriorities());
                filterPriority.setText(priorities);
            }

            if (issueinterface.getSelectedResolution() == null || issueinterface.getSelectedResolution().size() == 0) {
                filterResolution.setText("All");
            } else {
                String priorities = TextUtils.join(", ", issueinterface.getSelectedResolution());
                filterResolution.setText(priorities);
            }

            if (issueinterface.getSelectedStatus() == null || issueinterface.getSelectedStatus().size() == 0) {
                filterStatus.setText("All");
            } else {
                String priorities = TextUtils.join(", ", issueinterface.getSelectedStatus());
                filterStatus.setText(priorities);
            }

            if (issueinterface.getSelectedType() == null || issueinterface.getSelectedType().size() == 0) {
                filterType.setText("All");
            } else {
                String priorities = TextUtils.join(", ", issueinterface.getSelectedType());
                filterType.setText(priorities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setDataForEdittexts() {
        filterLabels.setText(issueinterface.getLabels());
        filterText.setText(issueinterface.getText());
        filterIssueKey.setText(issueinterface.getIssueKey());
    }

    public void saveDataForEdittexts() {
        issueinterface.setIssueKey(filterIssueKey.getText().toString().trim());
        issueinterface.setLabels(filterLabels.getText().toString().trim());
        issueinterface.setText(filterText.getText().toString().trim());
    }
}
