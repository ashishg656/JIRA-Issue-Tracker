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

    void setFilterDataAgain();
}
