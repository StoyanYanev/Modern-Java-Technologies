package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.utils.Utils;

import java.util.Objects;

public class Component {
    private String name;
    private String shortName;

    public Component(String name, String shortName) {
        Utils.checkIfStringIsValid(name);
        Utils.checkIfStringIsValid(shortName);

        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Component)) return false;
        Component component = (Component) o;
        return name.equals(component.name) &&
                shortName.equals(component.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shortName);
    }
}
