import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;

public class Display extends JPanel implements ActionListener {

	static int startX = 10;//initialize variables for the grid size, row, columns
	static int startY = 10;
	static int turn = 2;
	static int rows = 6;
	static int cols = 7;
	static Color[][] grid = new Color[rows][cols];//2d array to store colours for the spaces on the board
	boolean buttons = true;//to help display buttons without duplicates
	static boolean enter = false;//boolean to enter if statement for multiplayer mode
	static boolean ai = false;//to play against ai
	boolean reset = false;
	static int[] counter = new int[7];//counter to keep track of which spaces are taken up
	static String[] reference = { "000000", "000000", "000000", "000000", "000000", "000000", "000000" };//preset values to assist in determining the winner
	static String[] reference1 = { "0000000", "0000000", "0000000", "0000000", "0000000", "0000000" };
	static String[] diagonal = { "0000", "00000", "000000", "000000", "00000", "0000" };
	static String[] diagonal1 = { "0000", "00000", "000000", "000000", "00000", "0000" };


	JButton x1 = new JButton("row 1");//buttons to choose which row the user wants to drop their piece in
	JButton x2 = new JButton("row 2");
	JButton x3 = new JButton("row 3");
	JButton x4 = new JButton("row 4");
	JButton x5 = new JButton("row 5");
	JButton x6 = new JButton("row 6");
	JButton x7 = new JButton("row 7");
	JButton aiMove = new JButton("Click any button to allow ai to move");//additional button that will notify the ai to choose their move

	static JButton play = new JButton("Play with a friend");//main options
	static JButton play1 = new JButton("Play vs ai");
	static JButton main = new JButton("Back To Main Menu");

	JLabel win = new JLabel("Yellow Wins");//output winner message
	JLabel win1 = new JLabel("Red Wins");

	Font myFont = new Font("Courier New", 1, 100);//fonts to help appearance
	Font font2 = new Font("Times News Roman", 1, 40);
	Font font3 = new Font("Times News Roman", 1, 20);

	public Display(Dimension dimension) {//gets dimension from main class
		setSize(dimension);//sets the panel dimensions so that it equals the frame from the main class

		for (int row = 0; row < grid.length; row++) {//double for loop to draw filled in circles on the panel
			for (int col = 0; col < grid[0].length; col++) {
				grid[row][col] = Color.BLACK;//using the colour black
			}
		}

	}

	public void paintComponent(Graphics g) {
		win.setFont(font3);//set fonts for these messages
		win1.setFont(font3);
		win.setBounds(500, 250, 180, 30);//choose where the message will be displayed on the panel
		win1.setBounds(300, 250, 180, 30);
		add(win);//add messages to panel
		add(win1);
		win.setVisible(false);//hide them from the panel to be shown at a later time
		win1.setVisible(false);
		Graphics2D g2 = (Graphics2D) g;//initialize graphic extension

		Dimension d = getSize();//using dimension method to help create a background colour
		g2.setColor(Color.PINK);//set the extensions colour to pink
		g2.fillRect(0, 0, d.width, d.height);//fill the rectangle background to pink
		startX = 20;//initial x and y values where the grid spaces will go
		startY = 350;

		for (int row = 0; row < grid.length; row++) {//drawing the grid here
			for (int col = 0; col < grid[0].length; col++) {
				g2.setColor(grid[row][col]);//changing colour of graphics extention
				g2.fillOval(startX, startY, 100, 100);//size of the oval
				startX = startX + 100;//to help with spacing horizontally
			}
			startX = 20;//to help with spacing verritacally
			startY += 100;

		}

		x1.setBounds(30, 300, 80, 30);//choose where buttons go 
		x2.setBounds(130, 300, 80, 30);
		x3.setBounds(230, 300, 80, 30);
		x4.setBounds(330, 300, 80, 30);
		x5.setBounds(430, 300, 80, 30);
		x6.setBounds(530, 300, 80, 30);
		x7.setBounds(630, 300, 80, 30);
		aiMove.setBounds(300, 200, 320, 30);//additional button placement
		aiMove.addActionListener(this);
		play.setBounds(30, 100, 200, 30);//chosing where main menu buttons go
		play1.setBounds(270, 100, 200, 30);
		main.setBounds(510, 100, 200, 30);

		play1.addActionListener(this);//add action listeners to the buttons
		play.addActionListener(this);
		main.addActionListener(this);

		add(play1);//add main menu buttons 
		add(play);
		add(main);
		main.setVisible(false);//hide to go back to main menu button
		if (buttons == true) {//to help prevent additional buttons being placed on panel
			x1.addActionListener(this);//add action listeners to these buttons
			x2.addActionListener(this);
			x3.addActionListener(this);
			x4.addActionListener(this);
			x5.addActionListener(this);
			x6.addActionListener(this);
			x7.addActionListener(this);
			add(x1);
			add(x2);
			add(x3);
			add(x4);
			add(x5);
			add(x6);
			add(x7);
			x1.setVisible(false);
			x2.setVisible(false);
			x3.setVisible(false);
			x4.setVisible(false);
			x5.setVisible(false);
			x6.setVisible(false);
			x7.setVisible(false);//hide them temporarily 

		}
		buttons = false;//stops buttons from being placed again
		g2.setColor(Color.BLUE);//change colour to blue

		g2.setFont(myFont);//showing the title of the game in red and good font
		g2.drawString("Connect Four", 15, 70);
		g2.setColor(Color.RED);
		g2.setFont(font2);

		if (turn % 2 == 0) {//to determine whos turn it is
			g2.setFont(font2);
			g2.setColor(Color.RED);
			g2.drawString("Red's Turn", 15, 270);//show a message of whos turn it is

		} else {
			g2.setColor(Color.YELLOW);
			g2.setFont(font2);
			g2.drawString("Yellow's Turn", 15, 270);//show a message of whos turn it is
		}

		for (int i = 0; i < 7; i++) {//checks all directions of possible winning combinations
			if (reference[i].contains("rrrr")) {//if there is 4 reds in a row
				win1.setVisible(true);//shows who won on screen
				enter = false;//prevent any more pieces from being dropped
				ai = false;//prevent any more pieces from being dropped
				main.setVisible(true);//give option to go back to main menu
				reset = true;//reset game
				aiMove.setVisible(false);//remove additional button from screen

			} else if (reference[i].contains("yyyy")) {//if there is 4 yellows in a row
				win.setVisible(true);
				enter = false;
				ai = false;
				main.setVisible(true);
				reset = true;
				aiMove.setVisible(false);

			}
		}
		for (int i = 0; i < 6; i++) {
			if (reference1[i].contains("rrrr")) {//if there is 4 reds in a row
				win1.setVisible(true);
				main.setVisible(true);
				ai = false;
				enter = false;
				reset = true;
				aiMove.setVisible(false);

			} else if (diagonal[i].contains("rrrr")) {//if there is 4 reds in a row
				win1.setVisible(true);
				enter = false;
				ai = false;
				main.setVisible(true);
				reset = true;
				aiMove.setVisible(false);

			} else if (diagonal1[i].contains("rrrr")) {//if there is 4 reds in a row
				win1.setVisible(true);
				enter = false;
				ai = false;
				main.setVisible(true);
				reset = true;
				aiMove.setVisible(false);

			} else if (reference1[i].contains("yyyy")) {//if there is 4 yellows in a row
				win.setVisible(true);
				enter = false;
				ai = false;
				main.setVisible(true);
				reset = true;
				aiMove.setVisible(false);

			} else if (diagonal[i].contains("yyyy")) {//if there is 4 yellows in a row
				win.setVisible(true);
				enter = false;
				ai = false;
				main.setVisible(true);
				reset = true;
				aiMove.setVisible(false);

			} else if (diagonal1[i].contains("yyyy")) {//if there is 4 yellows in a row
				win.setVisible(true);
				enter = false;
				ai = false;
				main.setVisible(true);
				reset = true;
				aiMove.setVisible(false);

			}

		}
	}

	public static void reset() {//reset method
		for (int i = 0; i < 7; i++) {//resetting many static variables that were declared at the beginning of the code
			reference[i] = "000000";
		}
		for (int i = 0; i < 6; i++) {
			reference1[i] = "0000000";
		}

		for (int i = 0; i < 7; i++) {
			counter[i] = 0;
		}
		diagonal[0] = "0000";
		diagonal[1] = "00000";
		diagonal[2] = "000000";
		diagonal[3] = "000000";
		diagonal[4] = "00000";
		diagonal[5] = "0000";
		diagonal1[0] = "0000";
		diagonal1[1] = "00000";
		diagonal1[2] = "000000";
		diagonal1[3] = "000000";
		diagonal1[4] = "00000";
		diagonal1[5] = "0000";
		play.setVisible(true);//show main menu buttons again
		play1.setVisible(true);
		enter = false;//allowing the ability to enter these game modes again
		ai = false;
		turn = 2;

	}

	public void actionPerformed(ActionEvent event) {//method to see what the user has chosen
		String command = event.getActionCommand();
		if (command.contentEquals("Play with a friend")) {//user has chosen to play with a friend
			enter = true;//allows access to play with a friend if statement
			play.setVisible(false);//hide main menu buttons
			play1.setVisible(false);
			x1.setVisible(true);//show buttons for choosing rows
			x2.setVisible(true);
			x3.setVisible(true);
			x4.setVisible(true);
			x5.setVisible(true);
			x6.setVisible(true);
			x7.setVisible(true);
		} else if (command.contentEquals("Play vs ai")) {
			ai = true;//allows access to play with ai if statement
			x1.setVisible(true);//show buttons for choosing rows
			x2.setVisible(true);
			x3.setVisible(true);
			x4.setVisible(true);
			x5.setVisible(true);
			x6.setVisible(true);
			x7.setVisible(true);
			play.setVisible(false);//hide main menu buttons
			play1.setVisible(false);
			aiMove.setVisible(true);//show additional button
		} else if (command.contentEquals("Back To Main Menu")) {//if the user has chosen to go back to the main menu 
			for (int row = 0; row < grid.length; row++) {//reset the grid back to all black
				for (int col = 0; col < grid[0].length; col++) {
					grid[row][col] = Color.BLACK;
				}
			}
			aiMove.setVisible(false);//hide buttons that should not be in main menu
			x1.setVisible(false);
			x2.setVisible(false);
			x3.setVisible(false);
			x4.setVisible(false);
			x5.setVisible(false);
			x6.setVisible(false);
			x7.setVisible(false);
			repaint();//update the graphics
			reset();//call reset class in case user is looking to play another game

		}
		if (enter == true) {//if chosen to play the a friend
			if (command.equals("row 1")) {//if they chose the first button

				if (counter[0] < 6) {//and there is room in the row
					if (turn % 2 == 0) {//if reds turn
						reference[0] = reference[0].substring(0, counter[0]) + 'r'//update arrays to help determine a winner
								+ reference[0].substring(counter[0] + 1);
						;
						grid[5 - counter[0]][0] = new Color(255, 0, 0);//paint the empty space red
						repaint();//update graphics
					} else {
						reference[0] = reference[0].substring(0, counter[0]) + 'y'//update arrays to help determine a winner
								+ reference[0].substring(counter[0] + 1);
						;
						grid[5 - counter[0]][0] = new Color(255, 255, 0);//paint the empty space red
						repaint();//update graphics
					}
					turn++;//increase counter of turns to allow proper rotation of turns
				} else {
					System.out.println("invalid, no more space");//if the user trys to place their piece in a full row
				}
				counter[0]++;//update amount of room in the row

			} else if (command.equals("row 2")) {//exact same as first button but different row
				if (counter[1] < 6) {
					if (turn % 2 == 0) {
						reference[1] = reference[1].substring(0, counter[1]) + 'r'
								+ reference[1].substring(counter[1] + 1);
						;
						grid[5 - counter[1]][1] = new Color(255, 0, 0);
						repaint();
					} else {
						reference[1] = reference[1].substring(0, counter[1]) + 'y'
								+ reference[1].substring(counter[1] + 1);
						;
						grid[5 - counter[1]][1] = new Color(255, 255, 0);
						repaint();
					}
					turn++;
				} else {
					System.out.println("invalid, no more space");
				}
				counter[1]++;

			} else if (command.equals("row 3")) {
				if (counter[2] < 6) {
					if (turn % 2 == 0) {
						reference[2] = reference[2].substring(0, counter[2]) + 'r'
								+ reference[2].substring(counter[2] + 1);
						;
						grid[5 - counter[2]][2] = new Color(255, 0, 0);
						repaint();
					} else {
						reference[2] = reference[2].substring(0, counter[2]) + 'y'
								+ reference[2].substring(counter[2] + 1);
						;
						grid[5 - counter[2]][2] = new Color(255, 255, 0);
						repaint();
					}
					turn++;
				} else {
					System.out.println("invalid, no more space");
				}
				counter[2]++;

			} else if (command.equals("row 4")) {
				if (counter[3] < 6) {
					if (turn % 2 == 0) {
						reference[3] = reference[3].substring(0, counter[3]) + 'r'
								+ reference[3].substring(counter[3] + 1);
						;
						grid[5 - counter[3]][3] = new Color(255, 0, 0);
						repaint();
					} else {
						reference[3] = reference[3].substring(0, counter[3]) + 'y'
								+ reference[3].substring(counter[3] + 1);
						;
						grid[5 - counter[3]][3] = new Color(255, 255, 0);
						repaint();
					}
					turn++;
				} else {
					System.out.println("invalid, no more space");
				}
				counter[3]++;

			} else if (command.equals("row 5")) {
				if (counter[4] < 6) {
					if (turn % 2 == 0) {
						reference[4] = reference[4].substring(0, counter[4]) + 'r'
								+ reference[4].substring(counter[4] + 1);
						;
						grid[5 - counter[4]][4] = new Color(255, 0, 0);
						repaint();
					} else {
						reference[4] = reference[4].substring(0, counter[4]) + 'y'
								+ reference[4].substring(counter[4] + 1);
						;
						grid[5 - counter[4]][4] = new Color(255, 255, 0);
						repaint();
					}
					turn++;
				} else {
					System.out.println("invalid, no more space");
				}
				counter[4]++;

			} else if (command.equals("row 6")) {
				if (counter[5] < 6) {
					if (turn % 2 == 0) {
						reference[5] = reference[5].substring(0, counter[5]) + 'r'
								+ reference[5].substring(counter[5] + 1);
						;
						grid[5 - counter[5]][5] = new Color(255, 0, 0);
						repaint();
					} else {
						reference[5] = reference[5].substring(0, counter[5]) + 'y'
								+ reference[5].substring(counter[5] + 1);
						;
						grid[5 - counter[5]][5] = new Color(255, 255, 0);
						repaint();
					}
					turn++;
				} else {
					System.out.println("invalid, no more space");
				}
				counter[5]++;

			} else if (command.equals("row 7")) {
				if (counter[6] < 6) {
					if (turn % 2 == 0) {
						reference[6] = reference[6].substring(0, counter[6]) + 'r'
								+ reference[6].substring(counter[6] + 1);
						;
						grid[5 - counter[6]][6] = new Color(255, 0, 0);
						repaint();
					} else {
						reference[6] = reference[6].substring(0, counter[6]) + 'y'
								+ reference[6].substring(counter[6] + 1);
						;
						grid[5 - counter[6]][6] = new Color(255, 255, 0);
						repaint();
					}
					turn++;
				} else {
					System.out.println("invalid, no more space");
				}
				counter[6]++;
			}
			
			
			
			
			//build these arrays to help determining 4 in a row horizontally, vertically, or diagonally
			for (int j = 0; j < 6; j++) {
				for (int i = 0; i < 7; i++) {
					reference1[j] = reference1[j].substring(0, i) + reference[i].charAt(j)
							+ reference1[j].substring(i + 1);
				}
			}

			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < 4 + j; i++) {
					diagonal[j] = diagonal[j].substring(0, i) + reference1[2 - j + i].charAt(i)
							+ diagonal[j].substring(1 + i);
				}
			}

			for (int j = 0; j < 3; j++) {

				for (int i = 0; i < 6 - j; i++) {
					diagonal[3 + j] = diagonal[3 + j].substring(0, i) + reference1[i].charAt(1 + j + i)
							+ diagonal[3 + j].substring(1 + i);
				}

			}
			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < 4 + j; i++) {
					diagonal1[j] = diagonal1[j].substring(0, i) + reference1[3 + j - i].charAt(i)
							+ diagonal1[j].substring(1 + i);
				}

			}
			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < 6 - j; i++) {
					diagonal1[3 + j] = diagonal1[3 + j].substring(0, i) + reference1[5 - i].charAt(1 + j + i)
							+ diagonal1[3 + j].substring(1 + i);
				}

			}
		}
		
		//if chosen to play with the ai
		if (ai == true) {
			String temp2 = "";
			add(aiMove);//add addtional button
			if (turn % 2 != 0) {//if it is the ai's turn

				int temp;
				Random num = new Random();//create a random number from 0 to 6
				temp = num.nextInt(7);
				if (temp == 0) {//depending on the number the ai will drop a piece in that row
					temp2 = "row 1";
				} else if (temp == 1) {
					temp2 = "row 2";

				} else if (temp == 2) {
					temp2 = "row 3";

				} else if (temp == 3) {
					temp2 = "row 4";

				} else if (temp == 4) {
					temp2 = "row 5";

				} else if (temp == 5) {
					temp2 = "row 6";

				} else if (temp == 6) {
					temp2 = "row 7";

				}

				if (temp2.equals("row 1")) {//if ai chooses row 1

					if (counter[0] < 6) {//and there is space in the row
						reference[0] = reference[0].substring(0, counter[0]) + 'y'//update arrays to help determine a winner
								+ reference[0].substring(counter[0] + 1);
						;
						grid[5 - counter[0]][0] = new Color(255, 255, 0);//paint the empty space yellow
						repaint();//update the graphics

						turn++;//add 1 to turn to ensure proper rotation of turns
					} else {
						System.out.println("invalid, no more space");//if ai attempted to place piece in a full row
					}
					counter[0]++;//keeps track of empty space in a row

				} else if (temp2.equals("row 2")) {//same as row 1 but in a different row
					if (counter[1] < 6) {
						reference[1] = reference[1].substring(0, counter[1]) + 'y'//update arrays to help determine a winner
								+ reference[1].substring(counter[1] + 1);
						;
						grid[5 - counter[1]][1] = new Color(255, 255, 0);
						repaint();

						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[1]++;

				} else if (temp2.equals("row 3")) {
					if (counter[2] < 6) {
						reference[2] = reference[2].substring(0, counter[2]) + 'y'
								+ reference[2].substring(counter[2] + 1);
						;
						grid[5 - counter[2]][2] = new Color(255, 255, 0);
						repaint();

						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[2]++;

				} else if (temp2.equals("row 4")) {
					if (counter[3] < 6) {
						reference[3] = reference[3].substring(0, counter[3]) + 'y'
								+ reference[3].substring(counter[3] + 1);
						;
						grid[5 - counter[3]][3] = new Color(255, 255, 0);
						repaint();

						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[3]++;

				} else if (temp2.equals("row 5")) {
					if (counter[4] < 6) {
						reference[4] = reference[4].substring(0, counter[4]) + 'y'
								+ reference[4].substring(counter[4] + 1);
						;
						grid[5 - counter[4]][4] = new Color(255, 255, 0);
						repaint();

						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[4]++;

				} else if (temp2.equals("row 6")) {
					if (counter[5] < 6) {
						reference[5] = reference[5].substring(0, counter[5]) + 'y'
								+ reference[5].substring(counter[5] + 1);
						;
						grid[5 - counter[5]][5] = new Color(255, 255, 0);
						repaint();

						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[5]++;

				} else if (temp2.equals("row 7")) {
					if (counter[6] < 6) {
						reference[6] = reference[6].substring(0, counter[6]) + 'y'
								+ reference[6].substring(counter[6] + 1);
						;
						grid[5 - counter[6]][6] = new Color(255, 255, 0);
						repaint();

						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[6]++;
				}

			} else if (turn % 2 != 1) {//if it is the players turn

				if (command.equals("row 1")) {//and the player has chosen row 1

					if (counter[0] < 6) {//and there is space available
						if (turn % 2 == 0) {
							reference[0] = reference[0].substring(0, counter[0]) + 'r'//update arrays to help determine a winner
									+ reference[0].substring(counter[0] + 1);
							;
							grid[5 - counter[0]][0] = new Color(255, 0, 0);//paint the empty space red
							repaint();//update graphics
						}
						turn++;//to ensure proper rotation in turns
					} else {
						System.out.println("invalid, no more space");//if user tries to place piece in full row
					}
					counter[0]++;//to keep track of spacing in the row

				} else if (command.equals("row 2")) {//very similar to row 1 (comments above)
					if (counter[1] < 6) {
						if (turn % 2 == 0) {
							reference[1] = reference[1].substring(0, counter[1]) + 'r'
									+ reference[1].substring(counter[1] + 1);
							;
							grid[5 - counter[1]][1] = new Color(255, 0, 0);
							repaint();
						}
						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[1]++;

				} else if (command.equals("row 3")) {
					if (counter[2] < 6) {
						if (turn % 2 == 0) {
							reference[2] = reference[2].substring(0, counter[2]) + 'r'
									+ reference[2].substring(counter[2] + 1);
							;
							grid[5 - counter[2]][2] = new Color(255, 0, 0);
							repaint();
						}
						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[2]++;

				} else if (command.equals("row 4")) {
					if (counter[3] < 6) {
						if (turn % 2 == 0) {
							reference[3] = reference[3].substring(0, counter[3]) + 'r'
									+ reference[3].substring(counter[3] + 1);
							;
							grid[5 - counter[3]][3] = new Color(255, 0, 0);
							repaint();
						}
						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[3]++;

				} else if (command.equals("row 5")) {
					if (counter[4] < 6) {
						if (turn % 2 == 0) {
							reference[4] = reference[4].substring(0, counter[4]) + 'r'
									+ reference[4].substring(counter[4] + 1);
							;
							grid[5 - counter[4]][4] = new Color(255, 0, 0);
							repaint();
						}
						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[4]++;

				} else if (command.equals("row 6")) {
					if (counter[5] < 6) {
						if (turn % 2 == 0) {
							reference[5] = reference[5].substring(0, counter[5]) + 'r'
									+ reference[5].substring(counter[5] + 1);
							;
							grid[5 - counter[5]][5] = new Color(255, 0, 0);
							repaint();
						}
						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[5]++;

				} else if (command.equals("row 7")) {
					if (counter[6] < 6) {
						if (turn % 2 == 0) {
							reference[6] = reference[6].substring(0, counter[6]) + 'r'
									+ reference[6].substring(counter[6] + 1);
							;
							grid[5 - counter[6]][6] = new Color(255, 0, 0);
							repaint();
						}
						turn++;
					} else {
						System.out.println("invalid, no more space");
					}
					counter[6]++;
				}
			}
			
			//creating and updating arrays to have up to date information to determine a winner
			for (int j = 0; j < 6; j++) {
				for (int i = 0; i < 7; i++) {
					reference1[j] = reference1[j].substring(0, i) + reference[i].charAt(j)
							+ reference1[j].substring(i + 1);
				}
			}

			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < 4 + j; i++) {
					diagonal[j] = diagonal[j].substring(0, i) + reference1[2 - j + i].charAt(i)
							+ diagonal[j].substring(1 + i);
				}
			}

			for (int j = 0; j < 3; j++) {

				for (int i = 0; i < 6 - j; i++) {
					diagonal[3 + j] = diagonal[3 + j].substring(0, i) + reference1[i].charAt(1 + j + i)
							+ diagonal[3 + j].substring(1 + i);
				}

			}
			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < 4 + j; i++) {
					diagonal1[j] = diagonal1[j].substring(0, i) + reference1[3 + j - i].charAt(i)
							+ diagonal1[j].substring(1 + i);
				}

			}
			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < 6 - j; i++) {
					diagonal1[3 + j] = diagonal1[3 + j].substring(0, i) + reference1[5 - i].charAt(1 + j + i)
							+ diagonal1[3 + j].substring(1 + i);
				}

			}
		}

	}

}
