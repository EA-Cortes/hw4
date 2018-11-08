import java.util.*;

public class PiratesBeGone{

	public static class Edge implements Comparable<Edge>
	{
		int source, destination, roadCost;
		// Edge Constructor
		Edge(int source, int destination, int roadCost, int weightCap){
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

	public static void main(String args[])
	{
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