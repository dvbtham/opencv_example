package openCV;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WorkingWithImage image = new WorkingWithImage();
		
		if(!image.goToNext) return;		
		
		image.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		image.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		image.setVisible(true);
		
	}

}
