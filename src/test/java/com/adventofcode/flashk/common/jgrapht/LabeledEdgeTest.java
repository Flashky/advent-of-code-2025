package com.adventofcode.flashk.common.jgrapht;

import org.jgrapht.nio.AttributeType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabeledEdgeTest {

    @Test
    void getValue() {
        LabeledEdge labeledEdge = new LabeledEdge("expected");
        assertEquals("expected", labeledEdge.getValue());
    }

    @Test
    void getType() {
        LabeledEdge labeledEdge = new LabeledEdge("expected");
        assertEquals(AttributeType.STRING, labeledEdge.getType());
    }
}