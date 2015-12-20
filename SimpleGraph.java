import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
   A simple graph with round nodes and straight edges.
*/
public class SimpleGraph extends Graph
{
   public Node[] getNodePrototypes()
   {
	   Random r = new Random();
	   //Color randomColor = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	 // Color c=new Color((int)(Math.random() * 0x1000000));
      Node[] nodeTypes =
         {
    		  
            new CircleNode(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat())),
           // new CircleNode(Color.WHITE)
         };
      return nodeTypes;
   }

   public Edge[] getEdgePrototypes()
   {
      Edge[] edgeTypes = 
         {
            new LineEdge(),
           
         };
      return edgeTypes;
   }
   private Graph g;
}





