package com.adventofcode.flashk.day08;


import module java.base;
import org.jgrapht.Graph;

import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

import org.jgrapht.graph.builder.GraphTypeBuilder;

public class PlaygroundMST {

    private final Graph<JunctionBox, DefaultWeightedEdge> graph;
    private final List<CircuitPair> circuitPairs = new ArrayList<>();

    public PlaygroundMST(List<String> inputs) {

        this.graph = GraphTypeBuilder.<JunctionBox, DefaultWeightedEdge>undirected()
                .weighted(true)
                .edgeSupplier(DefaultWeightedEdge::new)
                .buildGraph();

        long circuitId = 1;
        List<JunctionBox> junctionBoxes = new ArrayList<>();

        // Initialize graphs and boxes
        for(String input : inputs) {
            JunctionBox junctionBox = new JunctionBox(input);

            graph.addVertex(junctionBox);
            junctionBox.setCircuitId(circuitId);
            junctionBoxes.add(junctionBox);
        }

        // Initialize circuit pairs
        for(int i = 0; i < junctionBoxes.size(); i++){
            for(int j = i+1; j < junctionBoxes.size(); j++) {
                CircuitPair circuitPair = new CircuitPair(junctionBoxes.get(i), junctionBoxes.get(j));
                circuitPairs.add(circuitPair);
            }
        }

    }

    public long solveB() {
        List<CircuitPair> sortedCircuits = circuitPairs.stream().sorted().toList();

        for(CircuitPair circuitPair : sortedCircuits) {
            DefaultWeightedEdge edge = graph.addEdge(circuitPair.getFirst(), circuitPair.getSecond());
            graph.setEdgeWeight(edge, circuitPair.getDistance());

            ConnectivityInspector<JunctionBox, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(graph);

            if(inspector.isConnected()) {
                return (long) circuitPair.getFirst().getPosition().getX() * circuitPair.getSecond().getPosition().getX();
            }
        }

        return -1;
    }
}
