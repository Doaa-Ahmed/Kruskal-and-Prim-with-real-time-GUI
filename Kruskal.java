import java.util.ArrayList;

public class Kruskal {
	private Graph graph;
	public double total = 0.0;
	public ArrayList<Integer> reachset;
	ArrayList<Integer> mst = new ArrayList<Integer>(); // minimum spanning tree

	public Kruskal(Graph graph) {
		this.graph = graph;
	}

	public void alg(double[] w, int[] starts, int[] ends) {
		reachset = new ArrayList<Integer>();
		ArrayList<Integer> unreachset = new ArrayList<Integer>();
		ArrayList<Integer> once = new ArrayList<Integer>();

		int index = 0;
		for (int i = 0; i < graph.nodes.size(); i++)
			unreachset.add(i);
		for (int i = 0; i < graph.nodes.size(); i++) {
			System.out.print(unreachset.get(i));
		}

		double min = 99999.9;
		while (unreachset.size() > 0) {
			int j;
			for (j = 0; j < w.length; j++) {
				if (once.contains(w[j]) == false)
					if (w[j] < min)
						if (unreachset.contains(ends[j])) {
							min = w[j];
							index = j;
						}
			}

			mst.add((Integer)starts[index]);
			mst.add((Integer)ends[index]);
			total+=min;
			reachset.add((Integer)ends[index]);
			if(unreachset.contains((Integer)starts[index]))
				unreachset.remove((Integer)starts[index]);
			unreachset.remove((Integer)ends[index]);
			System.out.print("unreachset: ");
			for(int i=0; i<unreachset.size();i++)
				System.out.print(unreachset.get(i));
			min=99999;
		}
	}
}