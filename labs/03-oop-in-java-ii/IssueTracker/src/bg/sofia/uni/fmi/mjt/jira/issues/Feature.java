package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.utils.Utils;

public class Feature extends Issue {
    private boolean isDesigned;
    private boolean isImplemented;
    private boolean isTested;

    public Feature(IssuePriority priority, Component component, String description) {
        super(priority, component, description);
        isDesigned = false;
        isImplemented = false;
        isTested = false;
    }

    @Override
    public void addAction(WorkAction action, String description) {
        Utils.checkIfObjectIsNull(action);
        Utils.checkIfStringIsValid(description);

        if (action == WorkAction.DESIGN) {
            isDesigned = true;
        } else if (action == WorkAction.IMPLEMENTATION) {
            isImplemented = true;
        } else if (action == WorkAction.TESTS) {
            isTested = true;
        }

        super.addAction(action, description);
    }

    @Override
    public void resolve(IssueResolution resolution) {
        Utils.checkIfObjectIsNull(resolution);

        if (!isDesigned || !isImplemented || !isTested) {
            throw new RuntimeException("The feature can not be resolved! It must be designed, implemented and tested");
        }

        super.setResolution(resolution);
        super.setStatus(IssueStatus.RESOLVED);
    }
}
