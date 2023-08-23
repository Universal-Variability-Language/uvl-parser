package de.vill.model;

import de.vill.config.Configuration;
import de.vill.util.Util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

/**
 * This class represents all kinds of groups (or, alternative, mandatory,
 * optional, cardinality)
 */
public class Group {
    /**
     * An enum with all possible group types.
     */
    public enum GroupType {
        OR, ALTERNATIVE, MANDATORY, OPTIONAL, GROUP_CARDINALITY
    }

    /// The type of the group (if type is GROUP_CARDINALITY or FEATURE_CARDINALITY
    /// lower and upper bound must be set!)
    public GroupType GROUPTYPE;
    private final List<Feature> features;
    private String lowerBound;
    private String upperBound;
    private Feature parent;

    /**
     * The constructor of the group class.
     *
     * @param groupType The type of the group.
     */
    public Group(GroupType groupType) {
        this.GROUPTYPE = groupType;
        features = new LinkedList<Feature>() {
            /**
             *
             */
            private static final long serialVersionUID = 3856024708694486586L;

            @Override
            public boolean add(Feature e) {
                if (super.add(e)) {
                    e.setParentGroup(Group.this);
                    return true;
                }
                return false;
            }

            @Override
            public void add(int index, Feature element) {
                super.set(index, element);
                element.setParentGroup(Group.this);
            }

            @Override
            public Feature remove(int index) {
                Feature f = super.remove(index);
                f.setParentGroup(null);
                return f;
            }

            @Override
            public boolean remove(Object o) {
                if (super.remove(o)) {
                    ((Feature) o).setParentGroup(null);
                    return true;
                }
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Feature> c) {
                if (super.addAll(index, c)) {
                    c.forEach(e -> e.setParentGroup(Group.this));
                    return true;
                }
                return false;
            }

            @Override
            public void clear() {
                ListIterator<Feature> it = this.listIterator();
                while (it.hasNext()) {
                    it.next().setParentGroup(null);
                }
                super.clear();
            }

            @Override
            public Feature set(int index, Feature element) {
                Feature f;
                if ((f = super.set(index, element)) != null) {
                    f.setParentGroup(Group.this);
                    return f;
                }
                return null;
            }

            class FeatureIterator implements ListIterator<Feature> {
                private ListIterator<Feature> itr;
                Feature lastReturned;

                public FeatureIterator(ListIterator<Feature> itr) {
                    this.itr = itr;
                }

                @Override
                public boolean hasNext() {
                    return itr.hasNext();
                }

                @Override
                public Feature next() {
                    lastReturned = itr.next();
                    return lastReturned;
                }

                @Override
                public boolean hasPrevious() {
                    return itr.hasPrevious();
                }

                @Override
                public Feature previous() {
                    lastReturned = itr.previous();
                    return lastReturned;
                }

                @Override
                public int nextIndex() {
                    return itr.nextIndex();
                }

                @Override
                public int previousIndex() {
                    return itr.previousIndex();
                }

                @Override
                public void remove() {
                    itr.remove();
                    lastReturned.setParentGroup(null);
                }

                @Override
                public void set(Feature e) {
                    itr.set(e);
                    lastReturned.setParentGroup(null);
                    e.setParentGroup(Group.this);
                }

                @Override
                public void add(Feature e) {
                    itr.add(e);
                    e.setParentGroup(Group.this);
                }

            }

            @Override
            public ListIterator<Feature> listIterator(int index) {
                return new FeatureIterator(super.listIterator(index));
            }

            ;
        };
    }

    /**
     * This method only returns a value if the group is a cardinality group. If not,
     * this method returns null.
     *
     * @return null or the lower bound of the group cardinality as string
     */
    public String getLowerBound() {
        return lowerBound;
    }

    /**
     * Set the lower bound (only if the group is a cardinality group).
     *
     * @param lowerBound the lower bound of the group
     */
    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * This method only returns a value if the group is a cardinality group. If not,
     * this method returns null. If there is no upper bound in the cardinality, this
     * method returns the lower bound. The returned value might also be the *
     * symbol, if the upper bound is unlimited.
     *
     * @return the upper bound as string
     */
    public String getUpperBound() {
        return upperBound;
    }

    /**
     * Set the upper bound of the group (only if the group is a cardinality group).
     *
     * @param upperBound the upper bound of the group (may be * symbol)
     */
    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    /**
     * Returns all children of the group (Features). If there are non an empty list
     * is returned, not null. The returned list is not a copy, which means editing
     * this list will actually change the group.
     *
     * @return A list with all child features.
     */
    public List<Feature> getFeatures() {
        return features;
    }

    /**
     * Returns the group and all its children as uvl valid string.
     *
     * @param withSubmodels true if the featuremodel is printed as composed
     *                      featuremodel with all its submodels as one model, false
     *                      if the model is printed with separated sub models
     * @param currentAlias  the namspace from the one referencing the group (or the
     *                      features in the group) to the group (or the features in
     *                      the group)
     * @return uvl representaiton of the group
     */
    public String toString(boolean withSubmodels, String currentAlias) {
        StringBuilder result = new StringBuilder();

        switch (GROUPTYPE) {
            case OR:
                result.append("or");
                break;
            case ALTERNATIVE:
                result.append("alternative");
                break;
            case OPTIONAL:
                result.append("optional");
                break;
            case MANDATORY:
                result.append("mandatory");
                break;
            case GROUP_CARDINALITY:
                result.append(getCardinalityAsSting());
                break;
        }

        result.append(Configuration.getNewlineSymbol());

        for (Feature feature : features) {
            result.append(Util.indentEachLine(feature.toString(withSubmodels, currentAlias)));
        }

        return result.toString();
    }

    private String getCardinalityAsSting() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        if (getLowerBound().equals(getUpperBound())) {
            result.append(getLowerBound());
        } else {
            result.append(getLowerBound());
            result.append("..");
            result.append(getUpperBound());
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public Group clone() {
        Group group = new Group(GROUPTYPE);
        group.setUpperBound(getUpperBound());
        group.setLowerBound(getLowerBound());
        for (Feature feature : getFeatures()) {
            group.getFeatures().add(feature.clone());
        }
        for (Feature feature : group.getFeatures()) {
            feature.setParentGroup(group);
        }
        return group;
    }

    /**
     * Returns the parent feature of the group.
     *
     * @return Parent Feature of the group.
     */
    public Feature getParentFeature() {
        return parent;
    }

    /**
     * Sets the parent feature of the group.
     *
     * @param parent The parent feature of the group.
     */
    public void setParentFeature(Feature parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Group)) {
            return false;
        }

        if (this.GROUPTYPE != ((Group) obj).GROUPTYPE) {
            return false;
        }

        if (this.getUpperBound() != null && !(this.getUpperBound().equals(((Group) obj).getUpperBound()))) {
            return false;
        }

        if (this.getLowerBound() != null && !(this.getLowerBound().equals(((Group) obj).getLowerBound()))) {
            return false;
        }

        if (this.getFeatures().size() != ((Group) obj).getFeatures().size()) {
            return false;
        }

        final List<Feature> objFeatures = ((Group) obj).getFeatures();
        for (final Feature currentFeature : this.getFeatures()) {
            if (!objFeatures.contains(currentFeature)) {
                return false;
            }
        }

        return true;
    }
}
