package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.utils.Utils;

public class Task extends Issue {

    public Task(IssuePriority priority, Component component, String description) {
        super(priority, component, description);
    }

    @Override
    public void addAction(WorkAction action, String description) {
        Utils.checkIfObjectIsNull(action);
        Utils.checkIfStringIsValid(description);

        if (action != WorkAction.DESIGN && action != WorkAction.RESEARCH) {
            throw new RuntimeException("Invalid work action! The task work action must be design or research!");
        }
        super.addAction(action, description);
    }

    @Override
    public void resolve(IssueResolution resolution) {
        Utils.checkIfObjectIsNull(resolution);

        super.setResolution(resolution);
        super.setStatus(IssueStatus.RESOLVED);
    }
}
