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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.FilterIssuesActivity;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.interfaces.FilterIssueinterface;
import com.ashish.jiraissuetracker.objects.issueComments.Author;

/**
 * Created by Ashish on 24/06/16.
 */
public class FilterIssuesFragment extends BaseFragment implements View.OnClickListener {

    EditText filterText, filterIssueKey, filterLabels;

    TextView filterOrder, filterAssignee, filterReporter,
            filterProject, filterPriority, filterResolution, filterStatus, filterType, filterComponents;
    LinearLayout filterOrderLayout, filterAssigneeLayout, filterReporterLayout,
            filterProjectLayout, filterPriorityLayout, filterResolutionLayout, filterStatusLayout, filterTypeLayout, filterComponentsLayout;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    FilterIssueinterface issueinterface;

    int selectedItem;

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
        filterComponents = (TextView) rootView.findViewById(R.id.filter_components);

        filterOrderLayout = (LinearLayout) rootView.findViewById(R.id.filter_order_text_container);
        filterAssigneeLayout = (LinearLayout) rootView.findViewById(R.id.filter_assignee_container);
        filterReporterLayout = (LinearLayout) rootView.findViewById(R.id.filter_reporter_c);
        filterProjectLayout = (LinearLayout) rootView.findViewById(R.id.filter_project_c);
        filterPriorityLayout = (LinearLayout) rootView.findViewById(R.id.filter_priority_c);
        filterResolutionLayout = (LinearLayout) rootView.findViewById(R.id.filter_resolution_c);
        filterStatusLayout = (LinearLayout) rootView.findViewById(R.id.filter_status_c);
        filterTypeLayout = (LinearLayout) rootView.findViewById(R.id.filter_type_c);
        filterComponentsLayout = (LinearLayout) rootView.findViewById(R.id.filter_components_c);

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
        filterComponentsLayout.setOnClickListener(this);

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

                break;
            case R.id.filter_priority_c:

                break;
            case R.id.filter_resolution_c:

                break;
            case R.id.filter_status_c:

                break;
            case R.id.filter_type_c:

                break;
            case R.id.filter_components_c:

                break;
        }
    }

    private void openSelectAssigneeFragment() {
        Bundle b = new Bundle();
        b.putInt("type", AppConstants.FILTER_USER_SELECT_ASSIGNEE);

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                SelectUserFragment.newInstance(b)).addToBackStack("user").commitAllowingStateLoss();
    }

    private void openSelectReporterFragment() {
        Bundle b = new Bundle();
        b.putInt("type", AppConstants.FILTER_USER_SELECT_REPORTER);

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                SelectUserFragment.newInstance(b)).addToBackStack("user").commitAllowingStateLoss();
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

            filterOrder.setText(items[issueinterface.getSelectedSortOrderPosition()]);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
