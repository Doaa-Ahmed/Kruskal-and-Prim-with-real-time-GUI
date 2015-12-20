import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;

/**
   A tool bar that contains node and edge prototype icons.
   Exactly one icon is selected at any time.
*/
public class ToolBar extends JPanel
{
	private Graph graph;
   /**
      Constructs a tool bar with no icons.
   */
   public ToolBar(Graph graph)
   {
	   this.graph=graph;
      group = new ButtonGroup();
      tools = new ArrayList();
      Kruskal.setEnabled(false);
      Prim.setEnabled(false);
      

      JToggleButton grabberButton = new JToggleButton(new 
         Icon()
         {
            public int getIconHeight() { return BUTTON_SIZE; }
            public int getIconWidth() { return BUTTON_SIZE; }
            public void paintIcon(Component c, Graphics g,
               int x, int y)
            {
               Graphics2D g2 = (Graphics2D) g;
               GraphPanel.drawGrabber(g2, x + OFFSET, y + OFFSET);
               GraphPanel.drawGrabber(g2, x + OFFSET, y + BUTTON_SIZE - OFFSET);
               GraphPanel.drawGrabber(g2, x + BUTTON_SIZE - OFFSET, y + OFFSET);
               GraphPanel.drawGrabber(g2, x + BUTTON_SIZE - OFFSET, y + BUTTON_SIZE - OFFSET);
            }
         });
      
      group.add(grabberButton);      
      add(grabberButton);
      grabberButton.setSelected(true);
      tools.add(null);

      Node[] nodeTypes = graph.getNodePrototypes();
      for (int i = 0; i < nodeTypes.length; i++)
         add(nodeTypes[i]);
      Edge[] edgeTypes = graph.getEdgePrototypes();
      for (int i = 0; i < edgeTypes.length; i++)
         add(edgeTypes[i]);
   }

   /**
      Gets the node or edge prototype that is associated with
      the currently selected button
      @return a Node or Edge prototype
   */
   public Object getSelectedTool()
   {
      for (int i = 0; i < tools.size(); i++)
      {
         JToggleButton button = (JToggleButton) getComponent(i);
         if (button.isSelected()) return tools.get(i);
      }
      return null;
   }

   /**
      Adds a node to the tool bar.
      @param n the node to add
   */
   public void add(final Node n)
   {
      JToggleButton button = new JToggleButton(new
         Icon()
         {
            public int getIconHeight() { return BUTTON_SIZE; }
            
            public int getIconWidth() { return BUTTON_SIZE; }
            public void paintIcon(Component c, Graphics g,
               int x, int y)
            {
               double width = n.getBounds().getWidth();
               double height = n.getBounds().getHeight();
               Graphics2D g2 = (Graphics2D) g;
               double scaleX = (BUTTON_SIZE - OFFSET)/ width;
               double scaleY = (BUTTON_SIZE - OFFSET)/ height;
               double scale = Math.min(scaleX, scaleY);

               AffineTransform oldTransform = g2.getTransform();
               g2.translate(x, y);
               g2.scale(scale, scale);
               g2.translate(Math.max((height - width) / 2, 0), Math.max((width - height) / 2, 0));
               g2.setColor(Color.black);
               n.draw(g2,2000);
               g2.setTransform(oldTransform);
            }
         });
      group.add(button);      
      add(button);
      tools.add(n);
      
   }

   /**
      Adds an edge to the tool bar.
      @param n the edge to add
   */
   public void add(final Edge e)
   {
      JToggleButton button = new JToggleButton(new
         Icon()
         {
            public int getIconHeight() { return BUTTON_SIZE; }
            public int getIconWidth() { return BUTTON_SIZE; }
            public void paintIcon(Component c, Graphics g,
               int x, int y)
            {
               Graphics2D g2 = (Graphics2D) g;
               PointNode p = new PointNode();
               p.translate(OFFSET, OFFSET);
               PointNode q = new PointNode();
               q.translate(BUTTON_SIZE - OFFSET, BUTTON_SIZE - OFFSET);
               e.connect(p, q);
               g2.translate(x, y);
               g2.setColor(Color.black);
               e.draw(g2);
               g2.translate(-x, -y);
            }
         });
      group.add(button);
      add(button);      
      tools.add(e);
      
      group.add(addw);
      add(addw);
      group.add(Kruskal);
      add(Kruskal);
      group.add(Prim);
      add(Prim);
      
      addw.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
         //display table to add weights
      	   frame=new JFrame();
      	  
      	frame.setTitle( "Add Weights" );
    	frame.setSize( 200, 200 );
    	frame.setBackground( Color.gray);
    	// Create a panel to hold all other components
    	JPanel topPanel = new JPanel();
    	topPanel.setLayout( new BorderLayout() );
    	frame.getContentPane().add( topPanel );
    	String columnNames[] = { "Start", "End", "Weight" };
    	Object[][] dataValues= new Object[graph.edges.size()][3]; 
        
    		for (int i=0; i<graph.edges.size();i++){
    		dataValues[i][0]=	new Integer (graph.nodes.indexOf(((Edge) graph.edges.get(i)).getStart()));
    		dataValues[i][1]=	new Integer (graph.nodes.indexOf(((Edge) graph.edges.get(i)).getEnd()));
    		}
 
    		 
    		 model = new DefaultTableModel(dataValues, columnNames)
    		{
    		    @Override
				public boolean isCellEditable(int row, int column)
    		    {
    		       return column==2;
    		    }
    		};

    			 table = new JTable(model /*dataValues, columnNames*/ );

    			// Add the table to a scrolling pane
    			JScrollPane scrollPane = new JScrollPane( table );
    			topPanel.add( scrollPane, BorderLayout.CENTER );
    			JPanel j= new JPanel();
    			j.add(ok);
    			topPanel.add(j,BorderLayout.SOUTH);
    			frame.setVisible(true);
    			
        }
        
      });
      ok.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
        	if (table.isEditing())
        		table.getCellEditor().stopCellEditing();
        	frame.setVisible(false);
        	Kruskal.setEnabled(true);
	        Prim.setEnabled(true);
	        weights= new double[graph.edges.size()];
	        starts=new int[graph.edges.size()];
	        ends= new int[graph.edges.size()];
	        
	        for(int i=0; i<graph.edges.size();i++)
	        { 
	        starts[i]= (Integer) table.getModel().getValueAt(i, 0); 
	        ends[i]= (Integer) table.getModel().getValueAt(i, 1);
	        weights[i]= Double.parseDouble ((String) table.getModel().getValueAt(i, 2)); 
	        }
        }
        
      });
      Kruskal.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
        	Kruskal k= new Kruskal(graph);
        	k.alg(weights, starts, ends);
        	kruskalframe=new JFrame();
        	kruskalframe.setSize(200,100);
        	JTextArea mst= new JTextArea();
        	ArrayList<String> sb = new ArrayList<String>();
        	for (int i=0; i<k.mst.size(); i++) { 
        	    sb.add(k.mst.get(i).toString());
        	    sb.add("->");
        	}
        	mst.append(sb.toString());
        	mst.append(" The total cost is: ");
        	mst.append(Double.toString(k.total));
        	kruskalframe.add(mst);
        	kruskalframe.setVisible(true);
        }
        
      });
      Prim.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
        	Prim p= new Prim(graph);
        	p.alg(weights, starts, ends);
        	primsframe= new JFrame();
        	primsframe.setSize(200,100);
        	JTextArea mst= new JTextArea();
        	ArrayList<String> sb = new ArrayList<String>();
        	for (int i=0; i<p.reachset.size(); i++) { 
        	    sb.add(p.reachset.get(i).toString());
        	    sb.add("->");
        	}
        	mst.append(sb.toString());
        	mst.append(" The total cost is: ");
        	mst.append(Double.toString(p.total));
        	if (p.bad!=null){
        		mst.append(p.bad);
        	}
        	primsframe.add(mst);
        	primsframe.setVisible(true);
        	
        	//p.mst;
        	       
        }
        
      });
      
   }
 
   private ButtonGroup group;
   private ArrayList tools;
  public JButton Kruskal= new JButton("Kruskal");
  public JButton Prim= new JButton("Prim");
  public JButton ok= new JButton("OK");
  public JButton addw= new JButton("Add weights");
  public JTable table;
  public JFrame frame;
  public JFrame primsframe;
  public JFrame kruskalframe;
  public double[] weights;
  public int[] starts;
  public int[] ends;
 private DefaultTableModel model;
   private static final int BUTTON_SIZE = 25;
   private static final int OFFSET = 4;
   
}
