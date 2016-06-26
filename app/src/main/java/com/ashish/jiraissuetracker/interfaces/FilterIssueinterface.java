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

    void setFilterDataAgain();
}
