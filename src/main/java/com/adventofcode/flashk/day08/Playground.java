package com.adventofcode.flashk.day08;

import module java.base;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Playground {
    
    private final Map<Long,Graph<JunctionBox, DefaultEdge>> graphsPerId = new HashMap<>();
    private final List<CircuitPair> circuitPairs = new ArrayList<>();

    public Playground(List<String> inputs) {

        long circuitId = 1;
        List<JunctionBox> junctionBoxes = new ArrayList<>();

        // Initialize graphs and boxes
        for(String input : inputs) {
            JunctionBox junctionBox = new JunctionBox(input);

            Graph<JunctionBox, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
            graph.addVertex(junctionBox);
            junctionBox.setCircuitId(circuitId);

            graphsPerId.put(circuitId++, graph);
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

    public long solveA(long maxConnections) {

        // Make the shortest N connections
        circuitPairs.stream().sorted().limit(maxConnections).forEach(this::connect);

        // Order the graphs by size
        Comparator<Graph<JunctionBox, DefaultEdge>> comparator = Comparator.comparing(g -> g.vertexSet().size());
        List<Graph<JunctionBox, DefaultEdge>> resultGraphs = graphsPerId.values().stream().sorted(comparator).toList().reversed();

        // Take the biggest three graphs and multiply between them
        return resultGraphs.stream()
                .limit(3)
                .mapToLong(g -> g.vertexSet().size())
                .reduce(1, Math::multiplyExact);
    }

    public long solveB() {

        // Make all the connections
        List<CircuitPair> sortedCircuits = circuitPairs.stream().sorted().toList();

        for(CircuitPair circuitPair : sortedCircuits) {
            connect(circuitPair);

            // Stop as soon as there is a unique graph
            if(graphsPerId.size() == 1) {
                return (long) circuitPair.getFirst().getPosition().getX() * circuitPair.getSecond().getPosition().getX();
            }
        }

        // This shouldn't be a valid solution
        return -1;

    }


    private void connect(CircuitPair circuitPair) {

        JunctionBox firstBox = circuitPair.getFirst();
        JunctionBox secondBox = circuitPair.getSecond();

        if (firstBox.getCircuitId() != secondBox.getCircuitId()) {
            Graph<JunctionBox, DefaultEdge> firstGraph = graphsPerId.get(firstBox.getCircuitId());
            Graph<JunctionBox, DefaultEdge> secondGraph = graphsPerId.get(secondBox.getCircuitId());

            long firstCircuitId = firstBox.getCircuitId();
            long secondCircuitId = secondBox.getCircuitId();

            // Update the circuitId for all vertexes
            secondGraph.vertexSet().forEach(jb -> jb.setCircuitId(firstCircuitId));

            // Merge the graphs
            Graphs.addGraph(firstGraph, secondGraph);
            firstGraph.addEdge(firstBox, secondBox);

            // Remove the other graph
            graphsPerId.remove(secondCircuitId);

        }
    }
}
