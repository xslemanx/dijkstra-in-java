import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {
	static ArrayList<Country> Countrys = new ArrayList<Country>();//array list of all countries
	 static HashMap<String, Country> allNodes = new HashMap<>();//to get the country object by it's name
	private Country source;//source country
	private Country destination;//Destination country
	private PriorityQueue<Country> heap;//heap used to find the shortest path

	public Dijkstra() {
		// TODO Auto-generated constructor stub
	}

	public Dijkstra(ArrayList<Country> Countrys, Country source, Country destination) {// for each path between tow
																						// countries
		heap = new PriorityQueue<>(Countrys.size());// make an heap with countries number size
		this.destination = destination;
		this.Countrys = Countrys;
		for (Country country : Countrys) {// reset all countries cost
			country.resetTemps();// make all with max integer cost
			if (country == source) {
				country.setTempCost(0);// make the source country cost to zero
			}
			heap.add(country);// add all countries to the heap
		}
	}

	public void generateDijkstra() {
		while (!heap.isEmpty() && heap.contains(destination)) {//while the heap is not empty and it contains the destination country
			Country minUnknown = heap.poll();//get the root and delete it from the heap
			LinkedList<Edge> adjacentsList = minUnknown.getAdjacentsList();//get all edges
			for (Edge edge : adjacentsList) {//for every edge
				Country adjacentCountry = edge.getAdjacentCity();//get the adjacent country by the edge
				if (heap.contains(adjacentCountry)) {//check if the heap contains the  
					if ((minUnknown.getTempCost() + edge.getDistance()) < adjacentCountry.getTempCost()) {//if the new path cost is less then the old path cost 
						heap.remove(adjacentCountry);//remove the country node from the heap
						adjacentCountry.setTempCost(minUnknown.getTempCost() + edge.getDistance());//set the new distance
						adjacentCountry.setTempPrev(minUnknown);//set the previous country point in the adjacent
						heap.add(adjacentCountry);//reAdd the country to the heap
					}
				}
			}
		}
	}

	private String pathString;
	 String distanceString;

	public Country[] pathTo(Country destination) {//to generate a string that contains path
		LinkedList<Country> countries = new LinkedList<>();//array list of counties
		Country iterator = destination;
		distanceString = String.format("%.2f", destination.getTempCost());//get the whole path distance
		while (iterator != source) {//for each country in the path
			countries.addFirst(iterator);//add it at first of the array list ,used to draw  in the map
			pathString = iterator.getFullName() + ": " + String.format("%.2f", iterator.getTempCost()) + "  KM" + "\n"//add the country name and distance to the string
					+ "-->  " + pathString;//add the old string after adding the new country string,(because the counties is started from destination to source
			iterator = iterator.getTempPrev();//go to the previous country
			
		}
		

		return countries.toArray(new Country[0]);//return an array of countries,by convert the arraylist to simple array
	}

	public String getPathString() {
		if (countOccurrences(pathString, '\n') <= 1) {//if the lines is equal or less than one line that means there is no path,(at least must be 2 lines one for source and one for destination)
			pathString = "No Path";
			distanceString = "\t\t\t------------------";
			return pathString;
		}

		pathString = "" + pathString;
		int truncateIndex;
		{//this tow lines because the last country points in null value(cut the last line  only)
		 truncateIndex = pathString.lastIndexOf('\n');
		pathString = pathString.substring(0, truncateIndex);
		}
		
		return pathString;
	}

	private static int countOccurrences(String haystack, char needle) {//to count how many lines in the path string
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}

	public static ArrayList<Country> readFile() throws FileNotFoundException {// method whose read the file
		String line = null;// to read each line
		int numberOfCountryes, numberOfEdges;
		File file = new File("data.txt");// the data file
		Scanner scan = new Scanner(file);
		line = scan.nextLine();// read the first line only
		String[] str = line.split(" ");
		numberOfCountryes = Integer.parseInt(str[0]);// first integer is number of countries
		numberOfEdges = Integer.parseInt(str[1]);// second integer is number of edges
		for (int i = 0; i < numberOfCountryes; i++) {// to read all countries data
			line = scan.nextLine();// each line is country
			String[] strN = line.split(" ");
			Country newCountry = new Country(strN[0], (float) Double.parseDouble(strN[1]), (float) Double.parseDouble(strN[2]));// make an new country-> name x y
			Countrys.add(newCountry);// add it to the array list
			allNodes.putIfAbsent(strN[0], newCountry);// add it to the hash map

		}
		for (int i = 0; i < numberOfEdges; i++) {// to read all edges
			line = scan.nextLine();// each line is country
			String[] strN = line.split(" ");
			Country fromCity = allNodes.get(strN[0]);
			Country	toCity = allNodes.get(strN[1]);// get the countries as objects by there names
			fromCity.addAdjacentCity(toCity, distance(fromCity.x,fromCity.y,toCity.x,toCity.y)); // add an path between them
		}

		return Countrys;

	}
	//haversine
	 public static double distance(double x1, double y1, double x2, double y2) {//calculate the distance between tow countries by there Latitude and longitude
		 //Distance, d = 6378.8 * arccos[(sin(Latitude1) * sin(Latitude2)) + cos(Latitude1) * cos(Latitude2) * cos(longitude1 – longitude2)]
		 return 6378.8
					* Math.acos((Math.sin(Math.toRadians(y1)) * Math.sin(Math.toRadians(y2)))
							+ Math.cos(Math.toRadians(y1)) * Math.cos(Math.toRadians(y2))
									* Math.cos(Math.toRadians(x1) - Math.toRadians(x2)));
		  }

}
