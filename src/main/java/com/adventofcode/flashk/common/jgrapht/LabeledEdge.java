package com.adventofcode.flashk.common.jgrapht;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.AttributeType;

/// Labeled edge class based on [JGraphT LabeledEdges](https://jgrapht.org/guide/LabeledEdges)
///
/// Allows creating an edge with label.
///
/// It also implements the JGraphT Attribute interface to allow exporting the labels in DOT representation
public class LabeledEdge extends DefaultEdge implements Attribute {

    private final String label;

    public LabeledEdge(String label) {
        this.label = label;
    }

    @Override
    public String getValue() {
        return label;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.STRING;
    }

    @Override
    public String toString() {
        return "(" + getSource() + " : " + getTarget() + " : " + label + ")";
    }
}
