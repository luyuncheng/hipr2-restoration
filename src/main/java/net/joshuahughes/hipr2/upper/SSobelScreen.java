package net.joshuahughes.hipr2.upper;
//package code.iface.sobel;

import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
//import code.iface.utils.*;
//import code.operator.sobel.*;

/**
 *
 *SSobelScreen is the user interface to the Sobel
 *algorithm.  It is run as an applet
 *embedded in the file sobel.htm 
 *@author Timothy Sharman
 */


public class SSobelScreen extends VisionApplet1 {

  //The operator class for performing the roberts cross operator. It's a thread
  Sobel sobel;

  //The scale value to be applied after the sobel
  double scaleval;

  //The offset value to be added
  float offsetval = 0;

  //The listeners for the GUI
  private ButtonListener startbl = new ButtonListener();
  private ButtonListener abortbl = new ButtonListener();
  
  //the components for the interface
  private JButton startbutton = new JButton("Sobel");
  private JButton abortbutton = new JButton("Stop");
  private JTextField time_taken = new JTextField(20);
  private JLabel time = new JLabel("Time");
  private JTextField sobscale = new JTextField(5);
  private JLabel scalelabel = new JLabel("Enter scale factor: ");
  private JLabel offsetLabel = new JLabel("Enter offset value: ");
  private JTextField offsetBox = new JTextField(5);

  public void add_extra() {

 //initialise the operator constructors
    sobel = new Sobel();
    sobel. run();  

    midc.weighty = 0.5;

    midc. gridx = 0;
    midc. gridy = 0;
    midlayout. setConstraints(scalelabel, midc);
    mid. add(scalelabel);

    midc. gridx = 1;
    midc. gridy = 0;
    midlayout. setConstraints(sobscale, midc);
    mid. add(sobscale);
    sobscale. setText("1.0");

    midc. gridx = 0;
    midc. gridy = 2;
    midlayout. setConstraints(offsetLabel, midc);
    mid. add(offsetLabel);

    midc. gridx = 1;
    midc. gridy = 2;
    midlayout. setConstraints(offsetBox, midc);
    mid. add(offsetBox);
    offsetBox. setText("0");

    midc. gridx = 0;
    midc. gridy = 3;
    midlayout. setConstraints(startbutton, midc);
    startbutton .setBackground(Color.green);
    mid. add(startbutton);
    startbutton. addActionListener(startbl);
    
    midc. gridx = GridBagConstraints.RELATIVE;
    abortbutton. setBackground(Color.red);
    midlayout. setConstraints(abortbutton, midc);
    mid. add(abortbutton);
    abortbutton. addActionListener(abortbl);

    midc. gridx = 0;
    midc. gridy = 4;
    midlayout. setConstraints(time, midc);   
    mid. add(time);
    
    midc. gridx = 1;
    midlayout. setConstraints(time_taken, midc);
    time_taken. setEditable(false);
    mid. add(time_taken);

    mid. repaint();
    
  }


 /**
   *
   *Handles the actions performed when buttons on the interface are pressed.
   *
   *@param evt The event which caused this object to be called
   *
   */
  
  
  class ButtonListener implements ActionListener {
    
    public void actionPerformed(ActionEvent evt) {
      JButton b = (JButton)evt.getSource();
      if( b == startbutton ) {
	try{
	  scaleval = Double. valueOf(sobscale.getText()).doubleValue();
	  if(scaleval < 0){
	    JOptionPane.showMessageDialog(null,("Scale value must be positive"),
	    ("Error!"), JOptionPane.WARNING_MESSAGE);     
	  }
	  else{
	    offsetval = Float. valueOf(offsetBox. getText()). floatValue();
	    time_msec = System.currentTimeMillis();
	    dest_1d = sobel. apply_sobel(src_1d, i_w, i_h, scaleval, offsetval);
	    time_msec = System.currentTimeMillis() - time_msec;
	    time_taken.setText(new Long(time_msec).toString()+" msecs");
	    dest = createImage(new MemoryImageSource(d_w,d_h,dest_1d,0,d_w));
	    
	    dest_canvas. updateImage(dest);
	    //Add a pixel listener to the canvas
	    PixelListener brpl = new PixelListener();
	    dest_canvas. addMouseMotionListener(brpl);
	  }
	}
	catch(NumberFormatException e){
	  JOptionPane.showMessageDialog(null,("Invalid parameters specified"),
					("Error!"), JOptionPane.WARNING_MESSAGE);     
	}
	
      }
      
      else if ( b == abortbutton ) {
	
	time_taken. setText("");
	
      }
      
    }
  }


  public void set_src_image(int [] input_img , int w, int h, String name) {
    
    //create a new sobel operator
    sobel = new Sobel(); 
    
    set_src_image2(input_img, w, h, name);
  }
  
  /**
   *Used by the browser to find out what the applet is for
   *
   *@return the function of the applet
   */
  public String getAppletInfo() {
    return "An applet for applying a Sobel operator to an image. Written by Timothy Sharman";
  }
}
