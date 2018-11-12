import java.util.*;
import java.util.Scanner;

public class PiratesBeGone{
	public static int n;			// Number of cities
	public static int m;			// Number of roads between cities
	public static int totalCost; 	// Cost of traveling + bribing
	public static int mutexes = 20;	// Traveling homies

	public static class City
	{
		int cityNum, nPirates, bCost;

		City(int cityNum, int nPirates, int bCost){
			this.cityNum = cityNum;
			this.nPirates = nPirates;
			this.bCost = bCost;
		}

			public String toString()
		{
			return "(" + this.nPirates + ", " + this.bCost +  ")";
		}
	}

	public static class Edge implements Comparable<Edge>
	{
		int source, destination, roadCost;
		// Edge Constructor
		Edge(int source, int destination, int roadCost){
			this.source = source;
			this.destination = destination;
			this.roadCost = roadCost;
		}

		public int compareTo(Edge o)
		{
			return Integer.compare(roadCost, o.roadCost);
		}

		public String toString()
		{
			return "(" + this.source + ", " + this.destination + ", " + this.roadCost +  ")";
		}
	}

	public static class Graph
	{
		int cities, edges;
		ArrayList<Edge> allEdges = new ArrayList<>();
		ArrayList<City> allCities = new ArrayList<>();

		Graph(int cities, int edges)
		{	
			this.cities = cities;
			this.edges = edges;
		}

		// Auxilary function to add city to our graph
		public void addCity(int cityNum, int nPirates, int bCost)
		{
			City city = new City(cityNum, nPirates, bCost);
			allCities.add(city);
		}

		// Auxilary function to add edges to our graph
		public void addEdge(int source, int destination, int roadCost)
		{
			Edge edge = new Edge(source, destination, roadCost);
			allEdges.add(edge);
		}

		public ArrayList<Edge> kruskalMST()
		{
			// Step 1: Sort edges by roadCost
			PriorityQueue<Edge> pq = new PriorityQueue<>(allEdges.size(), Comparator.comparingInt(o -> o.roadCost));

			for(int i = 0; i < allEdges.size(); i++)
			{	
				pq.add(allEdges.get(i));
			}			

			int [] parent = new int[edges];
			makeSet(parent);
			ArrayList<Edge> mst = new ArrayList<>();
			int index = 0, tempCost = 0;

			// Step 2: Pick the lowest cost edges to add to our result
			while(index < edges - 1)
			{
				Edge edge = pq.remove();
				int x_set = find(parent, edge.source);
				int y_set = find(parent, edge.destination);

				// If creates a cycle, or a road with n wagons can't support the total weight ignore it
				if(x_set == y_set /* ||edge.weightCap * wagons < shipWeight */ ){
		
				}else{
					// These are valid edges for our MST
					mst.add(edge);
					tempCost += edge.roadCost;
					index++;
					union(parent, x_set, y_set);
				}
				// If there are no edges left, break out of the while loop
				if(pq.size() < 1)
					break;
			}

			// ----------- Output working MSTs ----------- 
			printGraph(allEdges);
			printCities(allCities);
			printGraph(mst);

			return mst;
		}

		// Auxilary function to create subset 
		public void makeSet(int [] parent)
		{
			for(int i = 0; i < edges; i++)
				parent[i] = i;
		}

		// Auxliary function that finds if node already exists in tree
		public int find(int [] parent, int vertex)
		{
			if(parent[vertex] != vertex)
				return find(parent, parent[vertex]);
			return vertex;
		}

		// Function that that does the union of two sets
		void union(int [] parent, int x, int y)
		{
			int x_set_parent = find(parent, x);
			int y_set_parent = find(parent, y);
			parent[y_set_parent] = x_set_parent;
		}
	
		// Output functions
		public void printCities(ArrayList<City> cityList)
		{
			System.out.println("\nCities");
			for(int i = 0; i < cityList.size(); i++)
			{
				City city = cityList.get(i);
				System.out.println(city);
			}
		}

		public void printGraph(ArrayList<Edge> edgeList)
		{
			System.out.println("\nRoads");
			for(int i = 0; i < edgeList.size(); i++)
			{
				Edge edge = edgeList.get(i);
				System.out.println(edge);
			}
			System.out.println();
		}
	}

	public static void Xorviar(Graph graph){
		ArrayList<Edge> mst = new ArrayList<>();
		City thisCity = new City(0, 0, 0);
		
		mst = graph.kruskalMST();
		
		int currentCity = 0, roadPrice = 0, nextCity = 0, p = 0, penUlt = 0;


		// while(currentCity != n -1){
			while(currentCity != n -1){
			System.out.println(mutexes + " Mutexes arrived at city: " + currentCity);

			// go from current city to next cheapest
			for(int i = 0; i < mst.size(); i++)
			{
				Edge edge = mst.get(i);
				Edge edge2;
				if(edge.source == currentCity){
					
					if(i < mst.size()-1){
						for(int k = i+1; k < mst.size()-1; k++){
							edge2 = mst.get(k);	
							if(edge2.source == edge.source){
								edge = edge2;
									break;
							}else if(edge2.source == n - 1 || edge2.destination == n -1){
								System.out.println("Almost there!\n");
									break;
							}						
						}
					}
					
					nextCity = edge.destination;
					roadPrice = edge.roadCost;
					thisCity = graph.allCities.get(nextCity);
					System.out.println("Next city: " + nextCity + " Costs: " + edge.roadCost + "\n");
					
						break;
				}else if(edge.destination == currentCity){
					// thisCity = graph.allCities.get(currentCity);
					nextCity = edge.source;
					roadPrice = edge.roadCost;
					thisCity = graph.allCities.get(nextCity);
					System.out.println("Next city: " + nextCity + " Costs: " + edge.roadCost + "\n");
						break;
				}


			}
			// pay for transportation
			totalCost += mutexes *roadPrice;
			roadPrice = 0;

			// decide whether to bribe or not
			
			if(thisCity.nPirates > mutexes){
				mutexes += thisCity.nPirates;
				totalCost += thisCity.bCost * thisCity.nPirates;
			}else{
				mutexes -= thisCity.nPirates;
			}
			// look for next city


			
			currentCity = nextCity;
			// currentCity++;
			p++;
		}

		System.out.println("\n" + totalCost);

	}

	public static void main(String args[])
	{	
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		m = sc.nextInt();

		Graph graph = new Graph(n, m);

		for(int i = 0; i < n; i++)
		{
			int nPirates = sc.nextInt();
			int bribeCost = sc.nextInt();
			graph.addCity(i, nPirates, bribeCost);
		}

		for (int k = 0; k < m; k++)
		{
			int origin = sc.nextInt() - 1;
			int destination = sc.nextInt() - 1;
			int roadCost = sc.nextInt();
		
			graph.addEdge(origin, destination, roadCost);
		}

		Xorviar(graph);
		// Assume there's always a path from Southchester to every other city
		// Homie starts journey with 19 other homies
		// Homie has hella cash
		// Homie has a whip that fits 20 people max
		// Homie never arrests a pirate himself unless he's at Tarasoga
		// We only leave homies behind if they arrest a pirate
		// Each path the whip can take can be taken in reverse

		// Inputs
		// First line of input takes n and m
		// n - the next n lines contain 2 integers
			// The number of pirates
			// The cost of bribing each pirate
		// m - the next m lines contain 3 integers
		// describing the possible routes the whip can take
			// ID of starting city
			// ID of destination city
			// cost of using the path per passenger

		// Output
		// Integer representing the min amount of cheddar that homie will need to arrest Ruffus
	} 
}


/* --------------------- Sample Input ---------------------


Case 1:
4 5
0 1
2 15
10 1
6 100
1 2 30
1 3 15
2 3 10
2 4 15
3 4 45

520

Case 2:
5 5
0 1
12 15
10 1
15 100
6 100
1 2 30
2 3 25
2 4 25
4 3 10
5 4 33

1289

Case 3: 
5 5
0 1
4 1
10 1
3 100
6 100
1 2 30
2 3 100
2 4 10
4 3 10
5 4 33

1178
*/