package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.utils.Utils;

public class Bug extends Issue {
    private boolean isFixed;
    private boolean isTested;

    public Bug(IssuePriority priority, Component component, String description) {
        super(priority, component, description);
        isFixed = false;
        isTested = false;
    }

    @Override
    public void addAction(WorkAction action, String description) {
        Utils.checkIfObjectIsNull(action);
        Utils.checkIfStringIsValid(description);

        if (action == WorkAction.FIX) {
            isFixed = true;
        } else if (action == WorkAction.TESTS) {
            isTested = true;
        }

        super.addAction(action, description);
    }

    @Override
    public void resolve(IssueResolution resolution) {
        Utils.checkIfObjectIsNull(resolution);

        if (!isFixed || !isTested) {
            throw new RuntimeException("The bug can not be resolved! It must be fixed and tested");
        }

        super.setResolution(resolution);
        super.setStatus(IssueStatus.RESOLVED);
    }
}
