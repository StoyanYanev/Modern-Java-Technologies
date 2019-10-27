package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.generator.IdGenerator;
import bg.sofia.uni.fmi.mjt.jira.utils.Utils;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Issue {

    private static final int MAX_ACTION_LOG_SIZE = 20;

    private String issueId;
    private String description;
    private IssuePriority priority;
    private IssueResolution resolution;
    private IssueStatus status;
    private Component component;
    private String[] actionLog;
    private final LocalDateTime createdOn;
    private LocalDateTime lastModifiedOn;

    private int actionLogSize;

    public Issue(IssuePriority priority, Component component, String description) {
        Utils.checkIfObjectIsNull(priority);
        Utils.checkIfObjectIsNull(component);
        Utils.checkIfStringIsValid(description);

        this.priority = priority;
        this.component = component;
        this.description = description;
        this.issueId = String.format("%s-%d", component.getShortName(), IdGenerator.generate());
        this.resolution = IssueResolution.UNRESOLVED;
        this.status = IssueStatus.OPEN;
        this.actionLog = new String[MAX_ACTION_LOG_SIZE];
        createdOn = LocalDateTime.now();
        lastModifiedOn = LocalDateTime.now();

        actionLogSize = 0;
    }

    public String getIssueID() {
        return issueId;
    }

    public String getDescription() {
        return description;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public IssueResolution getResolution() {
        return resolution;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public Component getComponent() {
        return component;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getLastModifiedOn() {
        return lastModifiedOn;
    }

    public String[] getActionLog() {
        return actionLog;
    }

    public void setStatus(IssueStatus status) {
        Utils.checkIfObjectIsNull(status);
        this.status = status;
        changeLastModifiedOn();
    }

    public void setResolution(IssueResolution resolution) {
        Utils.checkIfObjectIsNull(resolution);
        this.resolution = resolution;
        changeLastModifiedOn();
    }

    public void addAction(WorkAction action, String description) {
        Utils.checkIfObjectIsNull(action);
        Utils.checkIfStringIsValid(description);
        if (!canActionBeAdded()) {
            throw new RuntimeException("The action can not be added. Not enough memory!");
        }

        String newActionLog = String.format("%s: %s", action.name().toLowerCase(), description);
        actionLog[actionLogSize] = newActionLog;
        actionLogSize++;
        changeLastModifiedOn();
    }

    public abstract void resolve(IssueResolution resolution);

    private void changeLastModifiedOn() {
        lastModifiedOn = LocalDateTime.now();
    }

    private boolean canActionBeAdded() {
        return actionLogSize <= MAX_ACTION_LOG_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Issue)) return false;
        Issue issue = (Issue) o;
        return issueId.equals(issue.issueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueId);
    }
}
