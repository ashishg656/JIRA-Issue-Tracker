package com.ashish.jiraissuetracker.objects.getIssueTransitions;

/**
 * Created by Ashish on 11/06/16.
 */
public class ChangeIssueStatusPayloadObject {

    Transition transition;

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public class Transition {
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
