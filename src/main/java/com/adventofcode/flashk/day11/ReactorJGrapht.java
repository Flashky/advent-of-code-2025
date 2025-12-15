package com.adventofcode.flashk.day11;

import module java.base;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.nio.dot.DOTExporter;

public class ReactorJGrapht {

    private static final String SERVER = "svr";
    private static final String FFT = "fft";
    private static final String DAC = "dac";
    private static final String END = "out";

    private final Graph<String,DefaultEdge> graph;
    private final AllDirectedPaths<String, DefaultEdge> paths;

    public ReactorJGrapht(List<String> inputs){
        graph = GraphTypeBuilder.<String, DefaultEdge> directed()
                .allowingMultipleEdges(true)
                .allowingSelfLoops(true)
                .edgeClass(DefaultEdge.class)
                .buildGraph();

        for(String input : inputs) {
            String[] splittedInput = input.split(":");

            String device = splittedInput[0];
            if(!graph.containsVertex(device)) {
                graph.addVertex(device);
            }

            String[] splittedOutputs = splittedInput[1].split(StringUtils.SPACE);
            for(String splittedOutput : splittedOutputs) {
                if(!StringUtils.EMPTY.equals(splittedOutput)) {
                    if(!graph.containsVertex(splittedOutput)) {
                        graph.addVertex(splittedOutput);
                    }
                    graph.addEdge(device, splittedOutput);
                }
            }
        }

        paths = new AllDirectedPaths<>(graph);
        exportGraphToDot();
    }

    public long solveB(boolean isSample) {
        if(isSample) {
            return findAllPaths(SERVER, prepareSampleMap());
        } else {
            return findAllPaths(SERVER, prepareInputMap());
        }
    }

    private long findAllPaths(String node, Map<String, Set<String>> originToDestinations) {
        if(END.equals(node)) {
            return 1;
        }

        long result = 0;
        for(String destination : originToDestinations.get(node)) {
            long pathCount = paths.getAllPaths(node, destination, true, null).size();
            long pathChildrenCount = findAllPaths(destination, originToDestinations);
            result += (pathCount * pathChildrenCount);
        }

        return result;

    }

    // Prepare a map of nodes that will be analyzed for the input solution
    private static Map<String, Set<String>> prepareInputMap() {

        Map<String, Set<String>> originToDestinations = new HashMap<>();

        // svr -> fft
        originToDestinations.put(SERVER, Set.of(FFT));

        // fft -> kcx, pzv, ddh
        originToDestinations.put(FFT, Set.of("kcx", "pzv", "ddh"));

        // kcx, pzv, ddh -> jis, gzs, bkh
        Set<String> destinations = new HashSet<>();
        destinations.add("jis");
        destinations.add("gzs");
        destinations.add("bkh");

        originToDestinations.put("kcx", destinations);
        originToDestinations.put("pzv", destinations);
        originToDestinations.put("ddh", destinations);

        // jis, gzs, bkh -> hmi, ctd, dya, pcf, ooe
        destinations = new HashSet<>();
        destinations.add("hmi");
        destinations.add("ctd");
        destinations.add("dya");
        destinations.add("pcf");
        destinations.add("ooe");

        originToDestinations.put("jis", destinations);
        originToDestinations.put("gzs", destinations);
        originToDestinations.put("bkh", destinations);

        // hmi, ctd, dya, pcf, ooe -> dac
        destinations = new HashSet<>();
        destinations.add(DAC);

        originToDestinations.put("hmi", destinations);
        originToDestinations.put("ctd", destinations);
        originToDestinations.put("dya", destinations);
        originToDestinations.put("pcf", destinations);
        originToDestinations.put("ooe", destinations);

        // dac -> out
        destinations = new HashSet<>();
        destinations.add(END);

        originToDestinations.put(DAC, destinations);

        return originToDestinations;
    }

    private Map<String, Set<String>> prepareSampleMap() {
        Map<String, Set<String>> originToDestinations = new HashMap<>();
        originToDestinations.put(SERVER, Set.of(FFT));
        originToDestinations.put(FFT, Set.of(DAC));
        originToDestinations.put(DAC, Set.of(END));

        return originToDestinations;
    }
    public void exportGraphToDot() {

        //Create the exporter (with ID provider)
        DOTExporter<String, DefaultEdge> exporter2 = new DOTExporter<>(v -> v.toString());
        Writer writer = new StringWriter();
        exporter2.exportGraph(graph, writer);
        System.out.println(writer.toString());

    }
}
