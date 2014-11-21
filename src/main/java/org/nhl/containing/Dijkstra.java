package org.nhl.containing;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
//For more information
//http://en.literateprograms.org/Dijkstra%27s_algorithm_%28Java%29#chunk def:Vertex class

/**
 * We use a simple graph representation where the vertices are represented by a
 * Vertex class.
 *
 * Because we'll need to iterate over the successors of each vertex, we will
 * keep a list of edges exiting each vertex. For use by the algorithm later, we
 * have two other fields: minDistance: The shortest distance from the source to
 * this vertex in the graph. It is initialized to positive infinity (as large as
 * possible). previous: A reference to the previous vertex to get a shortest
 * path from the source vertex to this vertex. In addition, later in the
 * algorithm we will need to order the vertices. We made our class implement the
 * Comparable interface, and we will implement the actual comparison method
 * later.
 *
 * @author Matthijs
 */
class Vertex implements Comparable<Vertex> {

    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(String argName) {
        name = argName;
    }

    public String toString() {
        return name;
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}

/**
 * We also have a class representing an edge that stores its weight and target
 * vertex (the vertex it points to)
 *
 * @author Matthijs
 */
class Edge {

    public final Vertex target;
    public final double weight;

    public Edge(Vertex argTarget, double argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}

/**
 * We're now prepared to define our method. We separate the computation into two
 * stages: Compute the minimum distance from the source to each vertex in the
 * graph. Simultaneously, keep track of the previous reference for each vertex v
 * that gives the previous vertex on the shortest path from the source vertex to
 * v. This is the expensive step.
 *
 * Later, any time we want to find a particular shortest path between the source
 * vertex and a given vertex, we follow the previous references to quickly
 * construct it. For the first part, we write computePaths, which takes as input
 * the source vertex from which all shortest paths are found.
 *
 *
 * The outline of how the function works is shown: we visit each vertex, looping
 * over its out-edges and adjusting minDistance as necessary. The critical
 * operation is relaxing the edges, which is based on the following formula: if
 * (u, v) is an edge and u is on the shortest path to v, d(u) + w(u,v) = d(v).
 *
 * In other words, we can reach v by going from the source to u, then following
 * the edge (u,v). Eventually, we will visit every predecessor of v reachable
 * from the source. The shortest path goes through one of these. We keep track
 * of the shortest distance seen so far by setting minDistance and the vertex it
 * went through by setting previous:
 *
 * @param source
 */
public class Dijkstra {

    /**
     * Simple compute path function.
     *
     * @param source
     */
    public static void computePaths(Vertex source) {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);

                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    /**
     * Get shortest path function.
     *
     * @param target
     * @return
     */
    public static List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }
}
