
/**
 * Implements the Nearest Neighbor algorithm for the TSP, and
 * an iterative improvement method that uses 2-OPT greedily.
 * Results are returned as an array of indices into the table argument, 
 * e.g. if the table has length four, a valid result would be {2,0,1,3}. 
 */
import java.util.*;

/*
 * Author Theoridho Andily(22764884)
 * completed in 16/08/2020
 */
public class NearestNeighbour {
	private NearestNeighbour() {
	}

	/**
	 * Returns the shortest tour found by exercising the NN algorithm from each
	 * possible starting city in table. table[i][j] == table[j][i] gives the cost of
	 * travel between City i and City j.
	 */
	public static int[] tspnn(double[][] table) {

		// 1. Start with city 0 then find the nearest neighbor
		// 2. to find the nearest neighbor we take the first row of the array then find
		// the smallest value
		// 3. then save the index of the city and make it as the search city
		// 4. repeat step 3 until we find all the city and save them onto shortestPath
		// array.

		int[] toursArray = new int[table.length];
		double toursLength = 1000000000000000000000.000;
		for(int city1 = 0; city1 < table.length; city1++) {
			int city = city1;
			ArrayList<Integer> shortestPath = new ArrayList<Integer>();
			shortestPath.add(city);
			int totalCity = table.length-1;
			int cityToCompare;
			int compare = 1;
			double length = 0;
			int c = 0;
			
			//This loop will add the new city to the paths
			while(shortestPath.size() <= totalCity) {
				
				//This will find a distance to compare to
				double shortest = table[city][compare];
				if(shortest == 0.0) {
					compare = compare + 1;
					shortest = table[city][compare];
				}
				while(shortestPath.contains(compare) == true) {
					compare = compare + 1;
					if(compare >= table.length) {
						compare = 0;
					}
				}
				shortest = table[city][compare];
				boolean shortTest = true;
				int row = table[city].length;
				
				//This will compare the initial value to other length 
				for(cityToCompare = 0; cityToCompare <= row-1; cityToCompare++) {
					if(cityToCompare != city && shortestPath.contains(cityToCompare) == false && table[city][cityToCompare] < shortest) {
						shortest = table[city][cityToCompare];
						shortTest = false;
						c = cityToCompare;
					}
				}
				
				//Check whether the first value was the shortest or the compare value
				if(shortTest == true) {
					shortestPath.add(compare);
				}
				else {
					shortestPath.add(c);
				}
				length = length + shortest;
				city = shortestPath.get(shortestPath.size() - 1);
			} 
			int[] arr = shortestPath.stream().mapToInt(i -> i).toArray();
			length = length + table[city1][shortestPath.get(shortestPath.size() - 1)];
			
			//Check if the length of this tour is lower than other tours
			if(length < toursLength) {
				toursLength = length;
				toursArray = arr;
			}
		}
		return toursArray;
	}
	
	/*
	 * This method finds the distance of the tour for the 2-opt methods
	 * 
	 * @param int[] tour
	 * @param double[][] table
	 * @return int length of tour
	 */
	public static double distance(int[] tour, double[][] table) {
        double distance = 0;
        int i;
        for (i = 0; i < tour.length - 1; i++) {
            distance = distance + table[tour[i]][tour[i+1]];
        }
        return distance;
    }

	/*
	 * This method swaps the tour to find all the possible variation to find better tour distance
	 * @param int i from 2-opt
	 * @param int j from 2-opt
	 * @param int[] tour given
	 * @return int[] new tour
	 */
	 public static int[] swap(int c, int d, int[] tour) {
	        int size = tour.length;
	        int[] newTour = new int[tour.length];
	        for (int a = 0; a <= c - 1; ++a) {
	            newTour[a] = tour[a];
	        }
	        int change = 0;
	        for (int b = c; b <= d; ++b) {
	            newTour[b] = tour[d - change];
	            change++;
	        }
	        for (int e = d + 1; e < size; ++e) {
	            newTour[e] = tour[e];
	        }
	        return newTour;

	    }
	/** 
	 * Uses 2-OPT repeatedly to improve cs, choosing the shortest option in each
	 * iteration. You can assume that cs is a valid tour initially. table[i][j] ==
	 * table[j][i] gives the cost of travel between City i and City j.
	 */
	public static int[] tsp2opt(int[] cs, double[][] table) {
		//This method will run by a given variable value to change the amount of time to run the while loop 
		//and I set it as 10 as it provides the best value
		int tourLen = cs.length;
		int counter = 10;
		//double newDistance = distance(cs, table);
		int i, k;
		while(counter > 0){
			double newDistance = distance(cs, table);
		    for (i = 1; i < tourLen - 1; i++) {
		        for (k = i + 1; k < tourLen; k++) {
		            int[] new_route = swap(i, k, cs);
		            double new_distance = distance(new_route, table);
		            if (new_distance < newDistance) {
		            	cs = new_route;
		                newDistance = new_distance;
		                break;
		            }
		        }
		    }
		    counter--;
		}
        return cs;
    }

}
