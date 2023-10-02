package de.vill.model.constraint;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ParenthesisConstraint extends Constraint {
    private Constraint content;

    public ParenthesisConstraint(Constraint content) {
        this.content = content;
    }

    public Constraint getContent() {
        return content;
    }

    @Override
    public String toString(boolean withSubmodels, String currentAlias) {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(content.toString(withSubmodels, currentAlias));
        result.append(")");
        return result.toString();
    }

    @Override
    public List<Constraint> getConstraintSubParts() {
        return Arrays.asList(content);
    }

    @Override
    public void replaceConstraintSubPart(Constraint oldSubConstraint, Constraint newSubConstraint) {
        if (content == oldSubConstraint) {
            content = newSubConstraint;
        }
    }

    @Override
    public Constraint clone() {
        return new ParenthesisConstraint(content.clone());
    }

    @Override
    public int hashCode(int level) {
        return 31 * level + (content == null ? 0 : content.hashCode(1 + level));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ParenthesisConstraint other = (ParenthesisConstraint) obj;
        return Objects.equals(content, other.content);
    }
}
