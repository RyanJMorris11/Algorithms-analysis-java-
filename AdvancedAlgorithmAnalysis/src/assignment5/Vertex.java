package assignment5;

import java.util.ArrayList;

public class Vertex {
	Integer parent;
	Integer dist;
	Integer id;
	ArrayList<int[]> adjVerts = new ArrayList<int[]>();
	
	Vertex(ArrayList<int[]> graph, int newId) {
		id = newId;
		parent = null;
		dist = Integer.MAX_VALUE;
		

		for( int i=0 ; i<graph.size() ; i++) {
			int[] temp = graph.get(i);
			
			if( temp[0] == id ) {
				
				adjVerts.add( temp );
			}
//			else if( temp[1] == id) {
//				adjVerts.add(temp[0]);
//			}	
		}
		Dijkstra.printVertex(this);
		
	}


}
