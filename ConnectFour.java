import javax.swing.*;

public class ConnectFour {
	public static void main(String[] arg) {

		JFrame frame = new JFrame("Connect Four");//title of frame
		frame.setSize(750,1000);//size of game
		frame.setResizable(false);//cannot be resized
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit game when closed
		frame.add(new Display(frame.getSize()));//call Display class which has the game aspects of the frame
		frame.setVisible(true);//allow the frame to be visible
	}
}
