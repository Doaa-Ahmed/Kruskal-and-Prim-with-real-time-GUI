import javax.swing.*;

/**
   A program for editing UML diagrams.
*/
public class SimpleGraphEditor
{  
   public static void main(String[] args)
   {  SimpleGraph s= new SimpleGraph();
      JFrame frame = new GraphFrame(s);
   //   Actions a= new Actions(s);
      frame.setVisible(true);
   }
}

