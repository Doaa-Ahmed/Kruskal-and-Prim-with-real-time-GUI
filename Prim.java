import java.util.ArrayList;
import java.util.Random;

/*
 Pseudo-code
ReachSet = {0};                    // You can use any node...
UnReachSet = {1, 2, ..., N-1};
SpanningTree = {};
while ( UnReachSet != empty )
   {
      Find edge e = (x, y) such that:
     1. x belongs to ReachSet
	 2. y belongs to UnReachSet
	 3. e has smallest cost

      SpanningTree = SpanningTree union {e};

      ReachSet   = ReachSet union {y};
      UnReachSet = UnReachSet - {y};
   }*/

public class Prim {
	private Graph graph;
	public double total = 0.0;
	public ArrayList<Integer> reachset;
	public String bad=null;
	ArrayList<Integer> mst = new ArrayList<Integer>(); // minimum spanning tree

	public Prim(Graph graph) {
		this.graph = graph;
	}

	public void alg(double[] w,int[] starts, int[] ends) {
		 reachset= new ArrayList<Integer>();
		ArrayList<Integer> unreachset= new ArrayList<Integer>();
		//ArrayList<Double> w= new ArrayList<Double>();
		
		
		//int[] reachset = {};
		//int[] unreachset= new int[graph.nodes.size()];
		
		for(int i=0; i<graph.nodes.size();i++)
		unreachset.add(i);
		for(int i=0; i<graph.nodes.size(); i++){
			System.out.print(unreachset.get(i));
		}
		double min= 99999.9;
		int index=0;
		int worst= unreachset.size()+2;
		int randomNum =  0 + (int)(Math.random() * (((w.length-1) - 0) + 1));
		
		reachset.add((Integer)starts[randomNum]);
		unreachset.remove((Integer)starts[randomNum]);
		System.out.println("  random: "+ randomNum+ " ,, weights size: "+ w.length+ " , size of unreched: "+ unreachset.size());
		for(int i=0; i<unreachset.size(); i++){
			System.out.print(unreachset.get(i));
		}
		System.out.println(" ");
		System.out.println("starts: ");
		for(int i=0; i<starts.length; i++)
			System.out.print(starts[i]);
		System.out.println("ends: ");
		for(int i=0; i<ends.length; i++)
			System.out.print(ends[i]);
		while (unreachset.size()>0 && worst>0){
			int j;
			for( j=0; j< w.length; j++){
				if(w[j]<min)
                  if( reachset.contains(starts[j])) 
                	  if(unreachset.contains(ends[j]))
					{min=w[j];	
					index=j;
					}
			}
			System.out.println("index: "+ index);
			System.out.println(unreachset.size());
			mst.add((Integer)starts[index]);
			mst.add((Integer)ends[index]);
			total+=min;
			reachset.add((Integer)ends[index]);
			unreachset.remove((Integer)ends[index]);
			worst=worst-1;
			System.out.print("unreachset: ");
			for(int i=0; i<unreachset.size();i++)
				System.out.print(unreachset.get(i));
			min=99999;
		}

		if(worst==0&& unreachset.size()>0)
			{bad="The rest of Graph is unreachable";
			}
	}
}
