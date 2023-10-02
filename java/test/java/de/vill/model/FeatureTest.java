package de.vill.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.vill.model.Group;
import de.vill.model.Group.GroupType;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeatureTest {
    Feature feature;
    Group orGroup;
    List<Group> groupList;

    @BeforeEach
    void setUp() throws Exception {
        feature = new Feature("TheFeature");
        orGroup = new Group(GroupType.OR);
        feature.addChildren(orGroup);
        groupList = new LinkedList<>();
        groupList.add(new Group(GroupType.ALTERNATIVE));
        groupList.add(new Group(GroupType.MANDATORY));
        groupList.add(new Group(GroupType.OPTIONAL));
    }

    @Test
    void testAddChildren() {
        assertEquals(feature, orGroup.getParentFeature());
    }

    @Test
    void testRemoveChildren() {
        assertEquals(feature, orGroup.getParentFeature());
        feature.getChildren().remove(orGroup);
        assertNull(orGroup.getParentFeature());
        assertTrue(feature.getChildren().isEmpty());
    }

    @Test
    void testRemoveIf() {
        feature.getChildren().removeIf(e -> true);
        assertNull(orGroup.getParentFeature());
        assertTrue(feature.getChildren().isEmpty());
    }

    @Test
    void testAddAll() {
        feature.getChildren().addAll(groupList);
        assertTrue(feature.getChildren().containsAll(groupList));
        for (Group g : feature.getChildren()) {
            assertEquals(feature, g.getParentFeature());
        }
    }

    @Test
    void testRemoveAll() {
        feature.getChildren().removeAll(groupList);
        assertFalse(feature.getChildren().containsAll(groupList));
        for (Group g : groupList) {
            assertNull(g.getParentFeature());
        }
    }

    @Test
    void testRetainAll() {
        feature.getChildren().retainAll(groupList);
        assertNull(orGroup.getParentFeature());
    }

    @Test
    void testClear() {
        feature.getChildren().clear();
        assertNull(orGroup.getParentFeature());
        for (Group g : groupList) {
            assertNull(g.getParentFeature());
        }
    }

    @Test
    void testSet() {
        Group newGroup = new Group(GroupType.ALTERNATIVE);
        assertEquals(feature, feature.getChildren().set(0, newGroup).getParentFeature());
    }

}
