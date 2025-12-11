package com.adventofcode.flashk.day11;

import static java.lang.IO.println;

import module java.base;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.nio.dot.DOTExporter;

public class ReactorPart2OnlyInput {

    private static final String START = "you";
    private static final String END = "out";
    private static final String SERVER = "svr";
    private static final String DAC = "dac";
    private static final String FFT = "fft";

    private Graph<String,DefaultEdge> graph;

    public ReactorPart2OnlyInput(List<String> inputs){
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

        //exportGraphToDot();
    }

    public long solveB() {
        long result = 0;

        // THIS ALGORITHM IS ONLY FOR THE INPUT

        AllDirectedPaths<String, DefaultEdge> paths = new AllDirectedPaths<>(graph);

        //  Si terminan:
        List<GraphPath<String, DefaultEdge>> svrToFftPaths = paths.getAllPaths(SERVER, FFT, true, null);

        Set<String> origins = new HashSet<>();
        origins.add(FFT);

        Set<String> destinations = new HashSet<>();
        destinations.add("kcx");
        destinations.add("pzv");
        destinations.add("ddh");

        List<GraphPath<String, DefaultEdge>> fftToKcxPzvDdh = paths.getAllPaths(origins, destinations, true, null);

        origins = destinations;
        destinations = new HashSet<>();
        destinations.add("jis");
        destinations.add("gzs");
        destinations.add("bkh");

        List<GraphPath<String, DefaultEdge>> kcxPzvDdhToJisGzsBkh = paths.getAllPaths(origins, destinations, true, null);

        origins = destinations;
        destinations = new HashSet<>();
        destinations.add("hmi");
        destinations.add("ctd");
        destinations.add("dya");
        destinations.add("pcf");
        destinations.add("ooe");

        List<GraphPath<String, DefaultEdge>> jisGzsBkhToCentralNodes = paths.getAllPaths(origins, destinations, true, null);

        origins = destinations;
        destinations = new HashSet<>();
        destinations.add(DAC);

        List<GraphPath<String, DefaultEdge>> centralNodesToDac = paths.getAllPaths(origins, destinations, true, null);

        List<GraphPath<String, DefaultEdge>> dacToEndPaths = paths.getAllPaths(DAC, END, true, null);

        result = (long) svrToFftPaths.size() *
                    (long) fftToKcxPzvDdh.size() *
                    (long) kcxPzvDdhToJisGzsBkh.size() *
                    (long) jisGzsBkhToCentralNodes.size() *
                    (long) centralNodesToDac.size() *
                    (long) dacToEndPaths.size();

        //List<GraphPath<String, DefaultEdge>> dacToEndPaths = paths.getAllPaths(DAC, END, false, Integer.MAX_VALUE);
        //List<GraphPath<String, DefaultEdge>> dacToFftPaths = paths.getAllPaths(DAC, FFT, false, Integer.MAX_VALUE);
        // Nota del sample: no hay caminos de DAC a FFT.

        // No terminan con el fichero de input:
        //List<GraphPath<String, DefaultEdge>> svrToDacPaths = paths.getAllPaths(SERVER, DAC, true, Integer.MAX_VALUE);
        //List<GraphPath<String, DefaultEdge>> fftToDacPaths = paths.getAllPaths(FFT, DAC, true, Integer.MAX_VALUE);
        //List<GraphPath<String, DefaultEdge>> fftToEndPaths = paths.getAllPaths(FFT, END, true, Integer.MAX_VALUE);

        //List<GraphPath<String, DefaultEdge>> svrToDacPaths = paths.getAllPaths(SERVER, DAC, true, Integer.MAX_VALUE);
        //List<GraphPath<String, DefaultEdge>> svrToFftPaths = paths.getAllPaths(SERVER, FFT, true, Integer.MAX_VALUE);
        //List<GraphPath<String, DefaultEdge>> svrToOutPaths = paths.getAllPaths(SERVER, END, true, Integer.MAX_VALUE);

        //long totalPathsSvrFftDacEnd = svrToFftPaths.size() * fftToDacPaths.size() * dacToEndPaths.size();
        //long totalPathsSvrDacFftEnd = svrToDacPaths.size() * dacToFftPaths.size() * fftToEndPaths.size();


        return result;
    }

    public long solveBSample() {
        AllDirectedPaths<String, DefaultEdge> paths = new AllDirectedPaths<>(graph);

        //  Si terminan:
        List<GraphPath<String, DefaultEdge>> svrToFftPaths = paths.getAllPaths(SERVER, FFT, false, Integer.MAX_VALUE);
        List<GraphPath<String, DefaultEdge>> fftToDacPaths = paths.getAllPaths(FFT, DAC, false, Integer.MAX_VALUE);
        List<GraphPath<String, DefaultEdge>> dacToEndPaths = paths.getAllPaths(DAC, END, false, Integer.MAX_VALUE);

        return (long) svrToFftPaths.size() * fftToDacPaths.size() * dacToEndPaths.size();
    }

    /*
         //  Si terminan:
        List<GraphPath<String, DefaultEdge>> svrToFftPaths = paths.getAllPaths(SERVER, FFT, false, Integer.MAX_VALUE);
        List<GraphPath<String, DefaultEdge>> dacToEndPaths = paths.getAllPaths(DAC, END, false, Integer.MAX_VALUE);
        List<GraphPath<String, DefaultEdge>> dacToFftPaths = paths.getAllPaths(DAC, FFT, false, Integer.MAX_VALUE);
        // Nota del sample: no hay caminos de DAC a FFT.

        // No terminan con el fichero de input:
        List<GraphPath<String, DefaultEdge>> svrToDacPaths = paths.getAllPaths(SERVER, DAC, true, Integer.MAX_VALUE);
        List<GraphPath<String, DefaultEdge>> fftToDacPaths = paths.getAllPaths(FFT, DAC, true, Integer.MAX_VALUE);
        List<GraphPath<String, DefaultEdge>> fftToEndPaths = paths.getAllPaths(FFT, END, true, Integer.MAX_VALUE);
     */
    public void exportGraphToDot() {

        //Create the exporter (with ID provider)
        DOTExporter<String, DefaultEdge> exporter2 = new DOTExporter<>(v -> v.toString());
        Writer writer = new StringWriter();
        exporter2.exportGraph(graph, writer);
        System.out.println(writer.toString());

    }
}
