package com.ashish.jiraissuetracker.objects;

import java.util.List;

/**
 * Created by Ashish on 07/06/16.
 */
public class IssuesObjectSingle {

    String expand, id, self, key;
    List<IssuesObjectSingleField> fields;

    public class IssuesObjectSingleField {
        IssueType issuetype;
        Project project;
        FixVersions fixVersions;
        Watches watches;
        Priority priority;
        String timespent;
        String aggregatetimespent, resolution, resolutiondate, lastViewed, created, customfield_10020;

        String customfield_10021, customfield_10022, customfield_10023, customfield_10024;
        String customfield_10016, customfield_10017, customfield_10018, customfield_10019, timeestimate;
        String aggregatetimeoriginalestimate, updated, timeoriginalestimate, description;
        String customfield_10012, customfield_10013, customfield_10014, customfield_10015;
        String customfield_10006, customfield_10007, customfield_10009, aggregatetimeestimate, summary, customfield_10000;
        String customfield_10001, customfield_10002, customfield_10003, customfield_10200;
        String environment;
        String duedate;
        int workratio;

    }

    public class Priority {
        String self, iconUrl, name, id;
    }

    public class IssueType {
        String self, id, description, iconUrl, name;
        boolean subtask;
        long avatarId;
    }

    public class Project {
        String self, id, key, name;
        AvatarUrls avatarUrls;
    }

    public class FixVersions {

    }

    public class AvatarUrls {
        String size48;

        public AvatarUrls() {

        }
    }

    public class Watches {
        String self;
        long watchCount;
        boolean isWatching;
    }
}
