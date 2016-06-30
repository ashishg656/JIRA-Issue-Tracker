package com.ashish.jiraissuetracker.interfaces;

import java.util.List;

/**
 * Created by Ashish on 26/06/16.
 */
public interface FilterIssueinterface {

    public int getSelectedSortOrderPosition();

    public void setSelectedSortOrderPosition(int selectedSortOrderPosition);

    public List<String> getSelectedAssignee();

    public void setSelectedAssignee(List<String> selectedAssignee);

    public List<String> getSelectedReporter();

    public void setSelectedReporter(List<String> selectedReporter);

    public List<String> getSelectedprojects();

    public void setSelectedprojects(List<String> selectedprojects);

    public List<String> getSelectedPriorities();

    public void setSelectedPriorities(List<String> selectedPriorities);

    public List<String> getSelectedResolution();

    public void setSelectedResolution(List<String> selectedResolution);

    public List<String> getSelectedStatus();

    public void setSelectedStatus(List<String> selectedStatus);

    public List<String> getSelectedType();

    public void setSelectedType(List<String> selectedType);

    public String getText();

    public void setText(String text);

    public String getIssueKey();

    public void setIssueKey(String issueKey);

    public String getLabels();

    public void setLabels(String labels);

    public void hideFloatingActionButton();

    public void showFloatingActionButton();

    void setFilterDataAgain(int type, List<String> data);
}
