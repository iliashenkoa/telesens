package com.yahoo.ailiashenko.mod2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransportNetwork<T> {
	List<NetworkSegment<T>> network;

	public TransportNetwork() {
	}

	public TransportNetwork(List<NetworkSegment<T>> network) {
		super();
		this.network = network;
	}

	public void setNetwork(List<NetworkSegment<T>> network) {
		this.network = network;
	}

	public List<NetworkSegment<T>> getNetwork() {
		return network;
	}

	public void addNetworkSegment(NetworkSegment<T> segment) {
		this.network.add(segment);
	}

	private Map<T, List<T>> getAdjList() {
		Map<T, List<T>> adjList = new HashMap<>();
		for (NetworkSegment<T> seg : this.network) {
			if (adjList.get(seg.getBegin()) == null) {
				adjList.put(seg.getBegin(), Stream.of(seg.getEnd()).collect(Collectors.toList()));
			} else {
				adjList.get(seg.getBegin()).add(seg.getEnd());
			}
			if (adjList.get(seg.getEnd()) == null) {
				adjList.put(seg.getEnd(), Stream.of(seg.getBegin()).collect(Collectors.toList()));
			} else {
				adjList.get(seg.getEnd()).add(seg.getBegin());
			}
		}
		return adjList;
	}

	public Set<T> searchMax1Hop(T node) {
		Set<T> nodes = new HashSet<>();
		Map<T, List<T>> adjList = this.getAdjList();
		nodes.addAll(adjList.get(node));
		Iterator<T> i = nodes.iterator();
		while (i.hasNext()) {
			nodes.addAll(adjList.get(i.next()));
		}
		nodes.remove(node);
		return nodes;
	}

	public List<T> sortByLinks() {
		List<T> temp = new ArrayList<>();
		temp.addAll(this.getAdjList().keySet());
		temp.sort((T node1, T node2) -> this.getAdjList().get(node1).size() - this.getAdjList().get(node2).size());
		return temp;
	}

	public NetworkSegment<T> maxSpeedSegment() {
		this.network.sort(
				(NetworkSegment<T> seg1, NetworkSegment<T> seg2) -> Double.compare(seg2.getSpeed(), seg1.getSpeed()));
		return network.get(0);
	}

	public Set<T> fastestWay(T node1, T node2) {
		int[][] adjMatrix = this.getAdjMatrix();
		int start = listOfAllNodes().indexOf(node1);
		int end = listOfAllNodes().indexOf(node2);
		Dijkstra<T> d = new Dijkstra<>(listOfAllNodes().size(), adjMatrix);
		return d.dijkstraGetMinDistances(start, end, listOfAllNodes());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t\t\t TRANSPORT NETWORK\n");
		for (NetworkSegment<T> seg : this.network) {
			sb.append(seg.toString() + System.lineSeparator());
		}
		return sb.toString();
	}

	private List<T> listOfAllNodes() {
		List<T> t = new ArrayList<>();
		t.addAll(this.getAdjList().keySet());
		return t;
	}

	public int[][] getAdjMatrix() {
		List<T> listOfNodes = listOfAllNodes();
		int count = listOfNodes.size();

		int[][] adjMatrix = new int[count][count];
		for (int[] row : adjMatrix) {
			Arrays.fill(row, 0);
		}
		for (int c = 0; c < count; c++) {
			for (NetworkSegment<T> seg : network) {
				if (seg.getBegin() == listOfNodes.get(c)) {
					adjMatrix[c][listOfNodes.indexOf(seg.getEnd())] = seg.getTime();
				} else if (seg.getEnd() == listOfNodes.get(c)) {
					adjMatrix[c][listOfNodes.indexOf(seg.getBegin())] = seg.getTime();
				}
			}
		}
		return adjMatrix;
	}

}

class Dijkstra<T> {
	int vertices;
	int[][] matrix;

	public Dijkstra(int count, int[][] adjMatrix) {
		this.vertices = count;
		this.matrix = adjMatrix;
	}

	int getMinimumVertex(boolean[] mst, int[] key) {
		int minKey = Integer.MAX_VALUE;
		int vertex = -1;
		for (int i = 0; i < vertices; i++) {
			if (!mst[i] && minKey > key[i]) {
				minKey = key[i];
				vertex = i;
			}
		}
		return vertex;
	}

	public Set<T> dijkstraGetMinDistances(int sourceVertex, int endVertex, List<T> list) {
		boolean[] spt = new boolean[vertices];
		int[] distance = new int[vertices];
		int inf = Integer.MAX_VALUE;
		Set<T> re = new LinkedHashSet<>();
		for (int i = 0; i < vertices; i++) {
			distance[i] = inf;
		}
		distance[sourceVertex] = 0;
		for (int i = 0; i < vertices; i++) {
			int vertexU = getMinimumVertex(spt, distance);
			spt[vertexU] = true;
			for (int vertexV = 0; vertexV < vertices; vertexV++) {
				if (matrix[vertexU][vertexV] > 0) {
					if (!spt[vertexV] && (matrix[vertexU][vertexV] != inf)) {
						int newKey = matrix[vertexU][vertexV] + distance[vertexU];
						if (newKey < distance[vertexV]) {
							distance[vertexV] = newKey;
							re.add(list.get(vertexU));
						}
					}
				}
			}
		}
		re.add(list.get(endVertex));
		System.out.println("MIN TIME " + distance[endVertex]);
		return re;
	}

}
