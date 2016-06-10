
package com.ashish.jiraissuetracker.objects.issues;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {

    @SerializedName("issuetype")
    @Expose
    private Issuetype issuetype;
    @SerializedName("timespent")
    @Expose
    private Object timespent;
    @SerializedName("project")
    @Expose
    private Project project;
    @SerializedName("fixVersions")
    @Expose
    private List<Object> fixVersions = new ArrayList<Object>();
    @SerializedName("aggregatetimespent")
    @Expose
    private Object aggregatetimespent;
    @SerializedName("resolution")
    @Expose
    private Object resolution;
    @SerializedName("resolutiondate")
    @Expose
    private Object resolutiondate;
    @SerializedName("workratio")
    @Expose
    private Long workratio;
    @SerializedName("lastViewed")
    @Expose
    private String lastViewed;
    @SerializedName("watches")
    @Expose
    private Watches watches;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("customfield_10020")
    @Expose
    private Object customfield10020;
    @SerializedName("customfield_10021")
    @Expose
    private Object customfield10021;
    @SerializedName("customfield_10022")
    @Expose
    private Object customfield10022;
    @SerializedName("customfield_10023")
    @Expose
    private String customfield10023;
    @SerializedName("priority")
    @Expose
    private Priority priority;
    @SerializedName("customfield_10024")
    @Expose
    private Object customfield10024;
    @SerializedName("labels")
    @Expose
    private List<Object> labels = new ArrayList<Object>();
    @SerializedName("customfield_10016")
    @Expose
    private Object customfield10016;
    @SerializedName("customfield_10017")
    @Expose
    private Object customfield10017;
    @SerializedName("customfield_10018")
    @Expose
    private Object customfield10018;
    @SerializedName("customfield_10019")
    @Expose
    private Object customfield10019;
    @SerializedName("timeestimate")
    @Expose
    private Object timeestimate;
    @SerializedName("aggregatetimeoriginalestimate")
    @Expose
    private Object aggregatetimeoriginalestimate;
    @SerializedName("versions")
    @Expose
    private List<Object> versions = new ArrayList<Object>();
    @SerializedName("issuelinks")
    @Expose
    private List<Issuelink> issuelinks = new ArrayList<Issuelink>();
    @SerializedName("assignee")
    @Expose
    private Assignee assignee;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("components")
    @Expose
    private List<Component> components = new ArrayList<Component>();
    @SerializedName("timeoriginalestimate")
    @Expose
    private Object timeoriginalestimate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("customfield_10012")
    @Expose
    private String customfield10012;
    @SerializedName("customfield_10013")
    @Expose
    private Object customfield10013;
    @SerializedName("customfield_10014")
    @Expose
    private Object customfield10014;
    @SerializedName("customfield_10015")
    @Expose
    private Object customfield10015;
    @SerializedName("customfield_10006")
    @Expose
    private Object customfield10006;
    @SerializedName("customfield_10007")
    @Expose
    private Object customfield10007;
    @SerializedName("customfield_10009")
    @Expose
    private String customfield10009;
    @SerializedName("aggregatetimeestimate")
    @Expose
    private Object aggregatetimeestimate;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("creator")
    @Expose
    private Creator creator;
    @SerializedName("subtasks")
    @Expose
    private List<Object> subtasks = new ArrayList<Object>();
    @SerializedName("reporter")
    @Expose
    private Reporter reporter;
    @SerializedName("customfield_10000")
    @Expose
    private Object customfield10000;
    @SerializedName("aggregateprogress")
    @Expose
    private Aggregateprogress aggregateprogress;
    @SerializedName("customfield_10001")
    @Expose
    private Object customfield10001;
    @SerializedName("customfield_10002")
    @Expose
    private Object customfield10002;
    @SerializedName("customfield_10200")
    @Expose
    private String customfield10200;
    @SerializedName("customfield_10003")
    @Expose
    private Object customfield10003;
    @SerializedName("environment")
    @Expose
    private Object environment;
    @SerializedName("duedate")
    @Expose
    private String duedate;
    @SerializedName("progress")
    @Expose
    private Progress progress;
    @SerializedName("votes")
    @Expose
    private Votes votes;

    /**
     * @return The issuetype
     */
    public Issuetype getIssuetype() {
        return issuetype;
    }

    /**
     * @param issuetype The issuetype
     */
    public void setIssuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
    }

    /**
     * @return The timespent
     */
    public Object getTimespent() {
        return timespent;
    }

    /**
     * @param timespent The timespent
     */
    public void setTimespent(Object timespent) {
        this.timespent = timespent;
    }

    /**
     * @return The project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project The project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return The fixVersions
     */
    public List<Object> getFixVersions() {
        return fixVersions;
    }

    /**
     * @param fixVersions The fixVersions
     */
    public void setFixVersions(List<Object> fixVersions) {
        this.fixVersions = fixVersions;
    }

    /**
     * @return The aggregatetimespent
     */
    public Object getAggregatetimespent() {
        return aggregatetimespent;
    }

    /**
     * @param aggregatetimespent The aggregatetimespent
     */
    public void setAggregatetimespent(Object aggregatetimespent) {
        this.aggregatetimespent = aggregatetimespent;
    }

    /**
     * @return The resolution
     */
    public Object getResolution() {
        return resolution;
    }

    /**
     * @param resolution The resolution
     */
    public void setResolution(Object resolution) {
        this.resolution = resolution;
    }

    /**
     * @return The resolutiondate
     */
    public Object getResolutiondate() {
        return resolutiondate;
    }

    /**
     * @param resolutiondate The resolutiondate
     */
    public void setResolutiondate(Object resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    /**
     * @return The workratio
     */
    public Long getWorkratio() {
        return workratio;
    }

    /**
     * @param workratio The workratio
     */
    public void setWorkratio(Long workratio) {
        this.workratio = workratio;
    }

    /**
     * @return The lastViewed
     */
    public String getLastViewed() {
        return lastViewed;
    }

    /**
     * @param lastViewed The lastViewed
     */
    public void setLastViewed(String lastViewed) {
        this.lastViewed = lastViewed;
    }

    /**
     * @return The watches
     */
    public Watches getWatches() {
        return watches;
    }

    /**
     * @param watches The watches
     */
    public void setWatches(Watches watches) {
        this.watches = watches;
    }

    /**
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The customfield10020
     */
    public Object getCustomfield10020() {
        return customfield10020;
    }

    /**
     * @param customfield10020 The customfield_10020
     */
    public void setCustomfield10020(Object customfield10020) {
        this.customfield10020 = customfield10020;
    }

    /**
     * @return The customfield10021
     */
    public Object getCustomfield10021() {
        return customfield10021;
    }

    /**
     * @param customfield10021 The customfield_10021
     */
    public void setCustomfield10021(Object customfield10021) {
        this.customfield10021 = customfield10021;
    }

    /**
     * @return The customfield10022
     */
    public Object getCustomfield10022() {
        return customfield10022;
    }

    /**
     * @param customfield10022 The customfield_10022
     */
    public void setCustomfield10022(Object customfield10022) {
        this.customfield10022 = customfield10022;
    }

    /**
     * @return The customfield10023
     */
    public String getCustomfield10023() {
        return customfield10023;
    }

    /**
     * @param customfield10023 The customfield_10023
     */
    public void setCustomfield10023(String customfield10023) {
        this.customfield10023 = customfield10023;
    }

    /**
     * @return The priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority The priority
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * @return The customfield10024
     */
    public Object getCustomfield10024() {
        return customfield10024;
    }

    /**
     * @param customfield10024 The customfield_10024
     */
    public void setCustomfield10024(Object customfield10024) {
        this.customfield10024 = customfield10024;
    }

    /**
     * @return The labels
     */
    public List<Object> getLabels() {
        return labels;
    }

    /**
     * @param labels The labels
     */
    public void setLabels(List<Object> labels) {
        this.labels = labels;
    }

    /**
     * @return The customfield10016
     */
    public Object getCustomfield10016() {
        return customfield10016;
    }

    /**
     * @param customfield10016 The customfield_10016
     */
    public void setCustomfield10016(Object customfield10016) {
        this.customfield10016 = customfield10016;
    }

    /**
     * @return The customfield10017
     */
    public Object getCustomfield10017() {
        return customfield10017;
    }

    /**
     * @param customfield10017 The customfield_10017
     */
    public void setCustomfield10017(Object customfield10017) {
        this.customfield10017 = customfield10017;
    }

    /**
     * @return The customfield10018
     */
    public Object getCustomfield10018() {
        return customfield10018;
    }

    /**
     * @param customfield10018 The customfield_10018
     */
    public void setCustomfield10018(Object customfield10018) {
        this.customfield10018 = customfield10018;
    }

    /**
     * @return The customfield10019
     */
    public Object getCustomfield10019() {
        return customfield10019;
    }

    /**
     * @param customfield10019 The customfield_10019
     */
    public void setCustomfield10019(Object customfield10019) {
        this.customfield10019 = customfield10019;
    }

    /**
     * @return The timeestimate
     */
    public Object getTimeestimate() {
        return timeestimate;
    }

    /**
     * @param timeestimate The timeestimate
     */
    public void setTimeestimate(Object timeestimate) {
        this.timeestimate = timeestimate;
    }

    /**
     * @return The aggregatetimeoriginalestimate
     */
    public Object getAggregatetimeoriginalestimate() {
        return aggregatetimeoriginalestimate;
    }

    /**
     * @param aggregatetimeoriginalestimate The aggregatetimeoriginalestimate
     */
    public void setAggregatetimeoriginalestimate(Object aggregatetimeoriginalestimate) {
        this.aggregatetimeoriginalestimate = aggregatetimeoriginalestimate;
    }

    /**
     * @return The versions
     */
    public List<Object> getVersions() {
        return versions;
    }

    /**
     * @param versions The versions
     */
    public void setVersions(List<Object> versions) {
        this.versions = versions;
    }

    /**
     * @return The issuelinks
     */
    public List<Issuelink> getIssuelinks() {
        return issuelinks;
    }

    /**
     * @param issuelinks The issuelinks
     */
    public void setIssuelinks(List<Issuelink> issuelinks) {
        this.issuelinks = issuelinks;
    }

    /**
     * @return The assignee
     */
    public Assignee getAssignee() {
        return assignee;
    }

    /**
     * @param assignee The assignee
     */
    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    /**
     * @return The updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * @param updated The updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * @return The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return The components
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * @param components The components
     */
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /**
     * @return The timeoriginalestimate
     */
    public Object getTimeoriginalestimate() {
        return timeoriginalestimate;
    }

    /**
     * @param timeoriginalestimate The timeoriginalestimate
     */
    public void setTimeoriginalestimate(Object timeoriginalestimate) {
        this.timeoriginalestimate = timeoriginalestimate;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The customfield10012
     */
    public String getCustomfield10012() {
        return customfield10012;
    }

    /**
     * @param customfield10012 The customfield_10012
     */
    public void setCustomfield10012(String customfield10012) {
        this.customfield10012 = customfield10012;
    }

    /**
     * @return The customfield10013
     */
    public Object getCustomfield10013() {
        return customfield10013;
    }

    /**
     * @param customfield10013 The customfield_10013
     */
    public void setCustomfield10013(Object customfield10013) {
        this.customfield10013 = customfield10013;
    }

    /**
     * @return The customfield10014
     */
    public Object getCustomfield10014() {
        return customfield10014;
    }

    /**
     * @param customfield10014 The customfield_10014
     */
    public void setCustomfield10014(Object customfield10014) {
        this.customfield10014 = customfield10014;
    }

    /**
     * @return The customfield10015
     */
    public Object getCustomfield10015() {
        return customfield10015;
    }

    /**
     * @param customfield10015 The customfield_10015
     */
    public void setCustomfield10015(Object customfield10015) {
        this.customfield10015 = customfield10015;
    }

    /**
     * @return The customfield10006
     */
    public Object getCustomfield10006() {
        return customfield10006;
    }

    /**
     * @param customfield10006 The customfield_10006
     */
    public void setCustomfield10006(Object customfield10006) {
        this.customfield10006 = customfield10006;
    }

    /**
     * @return The customfield10007
     */
    public Object getCustomfield10007() {
        return customfield10007;
    }

    /**
     * @param customfield10007 The customfield_10007
     */
    public void setCustomfield10007(Object customfield10007) {
        this.customfield10007 = customfield10007;
    }

    /**
     * @return The customfield10009
     */
    public String getCustomfield10009() {
        return customfield10009;
    }

    /**
     * @param customfield10009 The customfield_10009
     */
    public void setCustomfield10009(String customfield10009) {
        this.customfield10009 = customfield10009;
    }

    /**
     * @return The aggregatetimeestimate
     */
    public Object getAggregatetimeestimate() {
        return aggregatetimeestimate;
    }

    /**
     * @param aggregatetimeestimate The aggregatetimeestimate
     */
    public void setAggregatetimeestimate(Object aggregatetimeestimate) {
        this.aggregatetimeestimate = aggregatetimeestimate;
    }

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The creator
     */
    public Creator getCreator() {
        return creator;
    }

    /**
     * @param creator The creator
     */
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    /**
     * @return The subtasks
     */
    public List<Object> getSubtasks() {
        return subtasks;
    }

    /**
     * @param subtasks The subtasks
     */
    public void setSubtasks(List<Object> subtasks) {
        this.subtasks = subtasks;
    }

    /**
     * @return The reporter
     */
    public Reporter getReporter() {
        return reporter;
    }

    /**
     * @param reporter The reporter
     */
    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    /**
     * @return The customfield10000
     */
    public Object getCustomfield10000() {
        return customfield10000;
    }

    /**
     * @param customfield10000 The customfield_10000
     */
    public void setCustomfield10000(Object customfield10000) {
        this.customfield10000 = customfield10000;
    }

    /**
     * @return The aggregateprogress
     */
    public Aggregateprogress getAggregateprogress() {
        return aggregateprogress;
    }

    /**
     * @param aggregateprogress The aggregateprogress
     */
    public void setAggregateprogress(Aggregateprogress aggregateprogress) {
        this.aggregateprogress = aggregateprogress;
    }

    /**
     * @return The customfield10001
     */
    public Object getCustomfield10001() {
        return customfield10001;
    }

    /**
     * @param customfield10001 The customfield_10001
     */
    public void setCustomfield10001(Object customfield10001) {
        this.customfield10001 = customfield10001;
    }

    /**
     * @return The customfield10002
     */
    public Object getCustomfield10002() {
        return customfield10002;
    }

    /**
     * @param customfield10002 The customfield_10002
     */
    public void setCustomfield10002(Object customfield10002) {
        this.customfield10002 = customfield10002;
    }

    /**
     * @return The customfield10200
     */
    public String getCustomfield10200() {
        return customfield10200;
    }

    /**
     * @param customfield10200 The customfield_10200
     */
    public void setCustomfield10200(String customfield10200) {
        this.customfield10200 = customfield10200;
    }

    /**
     * @return The customfield10003
     */
    public Object getCustomfield10003() {
        return customfield10003;
    }

    /**
     * @param customfield10003 The customfield_10003
     */
    public void setCustomfield10003(Object customfield10003) {
        this.customfield10003 = customfield10003;
    }

    /**
     * @return The environment
     */
    public Object getEnvironment() {
        return environment;
    }

    /**
     * @param environment The environment
     */
    public void setEnvironment(Object environment) {
        this.environment = environment;
    }

    /**
     * @return The duedate
     */
    public String getDuedate() {
        return duedate;
    }

    /**
     * @param duedate The duedate
     */
    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    /**
     * @return The progress
     */
    public Progress getProgress() {
        return progress;
    }

    /**
     * @param progress The progress
     */
    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    /**
     * @return The votes
     */
    public Votes getVotes() {
        return votes;
    }

    /**
     * @param votes The votes
     */
    public void setVotes(Votes votes) {
        this.votes = votes;
    }

}
