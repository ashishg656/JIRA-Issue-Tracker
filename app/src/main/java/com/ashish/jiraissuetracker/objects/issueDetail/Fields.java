
package com.ashish.jiraissuetracker.objects.issueDetail;

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
    private List<FixVersion> fixVersions = new ArrayList<FixVersion>();
    @SerializedName("aggregatetimespent")
    @Expose
    private Object aggregatetimespent;
    @SerializedName("resolution")
    @Expose
    private Resolution resolution;
    @SerializedName("resolutiondate")
    @Expose
    private String resolutiondate;
    @SerializedName("workratio")
    @Expose
    private Integer workratio;
    @SerializedName("lastViewed")
    @Expose
    private String lastViewed;
    @SerializedName("watches")
    @Expose
    private Watches watches;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("priority")
    @Expose
    private Priority priority;
    @SerializedName("labels")
    @Expose
    private List<String> labels = new ArrayList<>();
    @SerializedName("timeestimate")
    @Expose
    private Object timeestimate;
    @SerializedName("aggregatetimeoriginalestimate")
    @Expose
    private Object aggregatetimeoriginalestimate;
    @SerializedName("versions")
    @Expose
    private List<FixVersion> versions = new ArrayList<>();
    @SerializedName("issuelinks")
    @Expose
    private List<Object> issuelinks = new ArrayList<Object>();
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
    private List<Object> components = new ArrayList<Object>();
    @SerializedName("timeoriginalestimate")
    @Expose
    private Object timeoriginalestimate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("timetracking")
    @Expose
    private Timetracking timetracking;
    @SerializedName("attachment")
    @Expose
    private List<Attachment> attachment = new ArrayList<>();
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
    @SerializedName("aggregateprogress")
    @Expose
    private Aggregateprogress aggregateprogress;
    @SerializedName("environment")
    @Expose
    private Object environment;
    @SerializedName("duedate")
    @Expose
    private Object duedate;
    @SerializedName("progress")
    @Expose
    private Progress progress;
    @SerializedName("votes")
    @Expose
    private Votes votes;
    @SerializedName("worklog")
    @Expose
    private Worklog worklog;

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
    public List<FixVersion> getFixVersions() {
        return fixVersions;
    }

    /**
     * @param fixVersions The fixVersions
     */
    public void setFixVersions(List<FixVersion> fixVersions) {
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
    public Resolution getResolution() {
        return resolution;
    }

    /**
     * @param resolution The resolution
     */
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    /**
     * @return The resolutiondate
     */
    public String getResolutiondate() {
        return resolutiondate;
    }

    /**
     * @param resolutiondate The resolutiondate
     */
    public void setResolutiondate(String resolutiondate) {
        this.resolutiondate = resolutiondate;
    }

    /**
     * @return The workratio
     */
    public Integer getWorkratio() {
        return workratio;
    }

    /**
     * @param workratio The workratio
     */
    public void setWorkratio(Integer workratio) {
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
     * @return The labels
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * @param labels The labels
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
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
    public List<FixVersion> getVersions() {
        return versions;
    }

    /**
     * @param versions The versions
     */
    public void setVersions(List<FixVersion> versions) {
        this.versions = versions;
    }

    /**
     * @return The issuelinks
     */
    public List<Object> getIssuelinks() {
        return issuelinks;
    }

    /**
     * @param issuelinks The issuelinks
     */
    public void setIssuelinks(List<Object> issuelinks) {
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
    public List<Object> getComponents() {
        return components;
    }

    /**
     * @param components The components
     */
    public void setComponents(List<Object> components) {
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
     * @return The timetracking
     */
    public Timetracking getTimetracking() {
        return timetracking;
    }

    /**
     * @param timetracking The timetracking
     */
    public void setTimetracking(Timetracking timetracking) {
        this.timetracking = timetracking;
    }


    /**
     * @return The attachment
     */
    public List<Attachment> getAttachment() {
        return attachment;
    }

    /**
     * @param attachment The attachment
     */
    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
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
    public Object getDuedate() {
        return duedate;
    }

    /**
     * @param duedate The duedate
     */
    public void setDuedate(Object duedate) {
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

    /**
     * @return The worklog
     */
    public Worklog getWorklog() {
        return worklog;
    }

    /**
     * @param worklog The worklog
     */
    public void setWorklog(Worklog worklog) {
        this.worklog = worklog;
    }

}
