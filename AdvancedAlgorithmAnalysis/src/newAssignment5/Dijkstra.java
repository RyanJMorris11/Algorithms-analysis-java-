package newAssignment5;

import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 
 * @author ryanmorris
 *
 *
 *         This class will take an adjacency list and perform Dijstra's
 *         algorithm on it.
 */
public class Dijkstra {
	final static String abolsolutePathForInputFile =
			"/Users/ryanmorris/Desktop/140 Algothm design/Assignment 5/input.txt";

	private static ArrayList<int[]> graph = new ArrayList<int[]>();
	private static ArrayList<Vertex> verts = new ArrayList<Vertex>();

	static int numEdges;
	static int numVertices;
	static String name;

	public static void main(String[] args) {
		System.out.println("Started");

		graph = inputGraph(args, graph);

		printListOfArrays(graph);
		initVerts(numVertices);

		try {
			dijkstras_algorithm(graph, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * the source is always 0
	 * 
	 * @param myGraph2
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private static void dijkstras_algorithm(ArrayList<int[]> myGraph2, int source)
			throws FileNotFoundException, UnsupportedEncodingException {

//		File outputFile = new File(System.getProperty("user.dir") +"/3output.txt");
//		PrintWriter writer = new PrintWriter(outputFile , "UTF-8");
		
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		
		verts.get(source).dist = 0;
		verts.get(source).parent = null;

		PriorityQueue<Vertex> myQ = setUpPQ();
		putAllVerticesIn(myQ);

		while (!myQ.isEmpty()) {
			Vertex curVert = myQ.remove();
			for(int i =0; i< curVert.adjVerts.size() ; i++) { // for each neighbor

				/** Adj info:
				 * 0 = this vertex id
				 * 1 = neighbor vertex id
				 * 2 = this edge distance value
				 */
				int[] adjInfo = curVert.adjVerts.get(i); 
				System.out.println("\t *** adj info: " + adjInfo[0] +", "+ adjInfo[1] +", "+ adjInfo[2] +
						" This dist "+ curVert.dist+ " id "+ curVert.id);

				Vertex curNeighbor = verts.get( adjInfo[1]); // get neighbor vertex
				
				if( curVert.dist + adjInfo[2] < curNeighbor.dist ) {
					curNeighbor.dist = curVert.dist + adjInfo[2];
					System.out.println("new dist= "+ curNeighbor.dist);
					curNeighbor.parent = curVert.id;
					myQ.remove(curNeighbor);
					myQ.add(curNeighbor);
				}
			}
		}
		
		try {
			writeSolution(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.close();
	}

	private static void writeSolution(Writer writer) throws IOException {
		writer.write("output:");	
		String str1 = "\nVertex \t\t";
//		String str2 = "\nParent:\t\t";
		String str3 = "\nDistance \t";

		for(int i =0; i<numVertices ; i++) {
			str1 += i+ "\t";
		}
		writer.write(str1);
	
//		for(int i =0; i<numVertices ; i++) {
//			str2 += verts.get(i).parent+"\t";
//		}
//		writer.write(str2);
		
		for(int i =0; i<numVertices ; i++) {
			str3 += verts.get(i).dist+"\t";
		}
		writer.write(str3);

	}

	private static void putAllVerticesIn(PriorityQueue<Vertex> myQ) {
		for (int i = 0; i < verts.size() - 1; i++) {
			myQ.add(verts.get(i));
		}
	}

	private static PriorityQueue<Vertex> setUpPQ() {

		Comparator<Vertex> myComparator = new Comparator<Vertex>() {
			@Override
			public int compare(Vertex v1, Vertex v2) {
				return (v1.dist) - (v2.dist);
			}
		};

		PriorityQueue<Vertex> myQ = new PriorityQueue<Vertex>(numVertices, myComparator);
		return myQ;
	}

	
	private static ArrayList<int[]> inputGraph(String[] args, ArrayList<int[]> locGraph) {
		File loc;
		if (args.length>0) {
			System.out.println("" + args[0]);
			System.out.println("ln 148-args: " + args[0]);
			loc = new File(args[0] );
		}else {
			File outputFile = new File(System.getProperty("user.dir"));
			
			System.out.println("This dir: " +outputFile);
			
//			System.out.println("No command arg path found");
			
			loc = new File(outputFile +"/newAssignment5/input.txt");
		}
		try {
			
			locGraph=inputGraph(loc);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return locGraph;
	}
	
	
	private static ArrayList<int[]> inputGraph( File myFile) throws FileNotFoundException {

		ArrayList<int[]> locGraph = new ArrayList<int[]>();
		// File myFile = new File("../input.txt");
		FileReader fileReader;
		try {
			System.out.println(myFile);
			fileReader = new FileReader(myFile);
			
		}catch(FileNotFoundException e) {
			System.out.println("ln 171 - Defaulting to Absolute path: ");
			myFile = new File(abolsolutePathForInputFile);
			
			fileReader = new FileReader(myFile);
			
		}
		System.out.println("ln 182 -File Location:" + myFile);
		BufferedReader br = new BufferedReader(fileReader);
		
		String next = new String();

		try {
			// get vertices and edges numbers
			String[] tokens;
			boolean ready = false;
			while (!ready) {

				next = br.readLine();
				tokens = next.split("\\s+");
				if (tokens[0].equals("Input")) {
					System.out.println("ln 192-Inputting graph");
					ready = true;
				}
			}

			next = br.readLine();
			tokens = next.split(" ");
			printArray(tokens);

			numVertices = (int) Integer.parseInt(tokens[0]);
			numEdges = (int) Integer.parseInt(tokens[1]);

			// int[] tempInts;

			while ((next = br.readLine()) != null) {

				tokens = next.split(" ");

				if (tokens.length == 0 || tokens.length == 1 || tokens.length == 2) {
					break;
				} else if (tokens.length == 3) {
					int[] tempInts = { Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]),
							Integer.parseInt(tokens[2]) };
					locGraph.add(tempInts);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locGraph;
	}

	private static void printListOfArrays(ArrayList<int[]> myGraph2) {
		System.out.println("Vertices = " + numVertices + " Edges= " + numEdges);
		System.out.println("list Size= " + myGraph2.size());
		
		for (int i = 0; i <= myGraph2.size() - 1; i++) {
			int[] next = myGraph2.get(i);
			for (int j = 0; j <= next.length - 1; j++) {
				System.out.print(next[j] + " ");
			}
			System.out.println(" ");
		}
	}


	static void printVertex(Vertex v) {
		System.out.print("id: " +v.id +" ");
		
		
		for (int j = 0; j <= v.adjVerts.size() - 1; j++) {
			int[] next = v.adjVerts.get(j);
			System.out.print( " ->"+ next[1]);
		}
		System.out.println();
	}

	private static void printArray(String[] next) {
		for (int j = 0; j <= next.length - 1; j++) {
			System.out.print(next[j] + " ");
		}
		System.out.println(" ");
	}

	public static void initVerts(int numVerts) {
		for (int i = 0; i < numVerts; i++) {
			Vertex v = new Vertex(graph, i);
			
			verts.add(v);
		}
		System.out.println("Verts size: "+verts.size());
	}
}
