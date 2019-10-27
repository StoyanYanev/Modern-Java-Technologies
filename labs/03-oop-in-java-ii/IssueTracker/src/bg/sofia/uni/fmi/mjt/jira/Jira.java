package bg.sofia.uni.fmi.mjt.jira;

import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Filter;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Repository;
import bg.sofia.uni.fmi.mjt.jira.issues.Issue;
import bg.sofia.uni.fmi.mjt.jira.utils.Utils;

public class Jira implements Filter, Repository {
    private static final int MAX_NUMBER_OF_ISSUES = 100;

    private Issue[] issues;
    private int issuesSize;

    public Jira() {
        issues = new Issue[MAX_NUMBER_OF_ISSUES];
        issuesSize = 0;
    }

    public void addActionToIssue(Issue issue, WorkAction action, String actionDescription) {
        Utils.checkIfObjectIsNull(issue);
        Utils.checkIfObjectIsNull(action);
        Utils.checkIfStringIsValid(actionDescription);

        Issue currentIssue = find(issue.getIssueID());
        Utils.checkIfObjectIsNull(currentIssue); // throw an exception if the current issue is not found
        currentIssue.addAction(action, actionDescription);
    }

    public void resolveIssue(Issue issue, IssueResolution resolution) {
        Utils.checkIfObjectIsNull(issue);
        Utils.checkIfObjectIsNull(resolution);

        Issue currentIssue = find(issue.getIssueID());
        Utils.checkIfObjectIsNull(currentIssue); // throw an exception if the current issue is not found
        currentIssue.resolve(resolution);
    }

    @Override
    public Issue find(String issueID) {
        Utils.checkIfStringIsValid(issueID);

        for (int i = 0; i < issuesSize; i++) {
            if (issues[i].getIssueID().equals(issueID)) {
                return issues[i];
            }
        }

        return null;
    }

    @Override
    public void addIssue(Issue issue) {
        Utils.checkIfObjectIsNull(issue);
        if (!canIssueBeAdded()) {
            throw new RuntimeException("The issue can not be added. Not enough memory!");
        }
        if (isIssueExisting(issue)) {
            throw new RuntimeException("The same issue already exists!");
        }

        issues[issuesSize] = issue;
        issuesSize++;
    }

    private boolean isIssueExisting(Issue issue) {
        for (int i = 0; i < issuesSize; i++) {
            if (issues[i].equals(issue)) {
                return true;
            }
        }

        return false;
    }

    private boolean canIssueBeAdded() {
        return issuesSize <= MAX_NUMBER_OF_ISSUES;
    }
}
