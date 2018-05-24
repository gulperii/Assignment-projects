/*two-player board game. Board contains 16 squares and there are 16 different pieces.
*Players take turns in the game. Computer gives the user a random piece to play. User enters coordinates. 
*After that user gives computer a piece and computer randomly places it on the board. 
*The game ends when either all 16 squares are full or there are four pieces that share one property horizontally, 
*vertically, or diagonally. Program should re-display the board after each move. In the end of the game 
*we should print who has won. Also in the beginning we should ask the user if she wants to start a new game or 
*if she wants to continue an ongoing game. If the answer is a new game, program should load the board from “input.txt”.
*/
import java.io.*;
import java.util.*;
public class BoardGame{
	public static void main(String[] args) throws FileNotFoundException{
		Scanner console = new Scanner(System.in);
		String[][] board = new String[4][4];

		System.out.print("Do you want to start a new game or continue to the saved one?(new /saved) ");
		String a= console.next(); // Answer that user gave to the previous question (saved/ new game). 
		while(!(a.equalsIgnoreCase("new")||a.equalsIgnoreCase("saved"))) {
			System.out.print(" We don't understand what you want. Please enter a meaningful answer: ");
			a= console.next();
		}

		if(a.equalsIgnoreCase("new")) {
			newGame(console,board);
		}else if(a.equalsIgnoreCase("saved")) {
			saved(console,board);
		}	}			

//If user wants to start a new game program calls this method.
	public static void newGame (Scanner console,String[][] board) throws FileNotFoundException{

		int score = 1; //Indicates that it's user's turn
		initial(board); 
		writeFile(board,score);
		printIt(board);
		while(!gameOver(board)) {
			score = user(console,board);
			writeFile(board,score);
			printIt(board);
			if(gameOver(board)) {
				break;
			}
			score = comp(console,board);
			writeFile(board,score);
			printIt(board);

		} 
		if(isFull(board)) {
			System.out.println("Nobody wins. Board is full.");
		}
		else {
		if(score == 0) {
			System.out.println("Congratulations! You won.");
		}else if(score==1) {
			System.out.println("Shame on you! Computer won.");
		}
		}
	}

	// If the user choose to continue with the saved game, program calls this method
	public static void saved(Scanner console, String[][] board) throws FileNotFoundException {
		int score= 2; // Indicates whose turn it is (after starting the game). 
		int t= readFile(board); // Indicates whose turn it was before closing the program.
		if(gameOver(board)) {
			System.out.println("Last game has already finished. We are starting a new game.");
			newGame(console,board);
		}else {
		printIt(board);
		if(t ==1) {
				while(!gameOver(board)){
				score = user(console,board);
				writeFile(board,score);
				printIt(board);
				if(gameOver(board)) {
					break;

				}
				score = comp(console,board);
				writeFile(board,score);
				printIt(board);

			} 
			if(isFull(board)) {
				System.out.println("Nobody wins. Board is full.");
			}
			else {
			if(score == 0) {
				System.out.println("Congratulations! You won.");
			}else if(score==1) {
				System.out.println("Shame on you! Computer won.");
			}
			}

		}

		if(t==0) {
			 while(!gameOver(board)){
				score = comp(console,board);
				writeFile(board,score);
				printIt(board);
				if(gameOver(board)) {
					break;

				}
				score = user(console,board);
				writeFile(board,score);
				printIt(board);

			}
			if(isFull(board)) {
				System.out.println("Nobody wins. Board is full.");
			}
			else {
			if(score == 0) {
				System.out.println("Congratulations! You won.");
			}else if(score==1) {
				System.out.println("Shame on you! Computer won.");
			}
			}
		}

	}
	}

// This method is for creating a random piece.
	public static String randomGen() {
		Random rand = new Random();
		String piece =""; // Random piece that computer will give to the user.
		int a = rand.nextInt(2); // first property of the piece 
		if(a==0) {
			piece+='B';
		}
		else {
			piece+='W';
		}
		int b = rand.nextInt(2); // second property of the piece
		if(b==0) {
			piece+='T';
		}
		else {
			piece+='S';
		}int c = rand.nextInt(2); // third property of the piece
		if(c==0) {
			piece+='S';
		}
		else {
			piece+='R';
		}int d = rand.nextInt(2); // forth property of the piece
		if(d==0) {
			piece+='H';
		}
		else {
			piece+='S';
		}

		return piece;
	}
	
	 

	
	// Program calls this method when it's user's turn.
	public static int user(Scanner console,String[][] board) {
		String p = randomGen(); // The piece that computer gave to the user
		while(isUsed(p,board)) {
			p=randomGen();
		}
		System.out.println("Your piece is "+p);
		System.out.print("Where do you want to put it?");
		int x = console.nextInt(); // x coordinate 
		int y = console.nextInt(); // y coordinate
		while(x>3 || y>3 ) {
			System.out.print("Both coordinates must be smaller than four. Try again: ");
			x=console.nextInt();
			y=console.nextInt();
		}
		if(nullSqr(x,y,board)) {
			board[x][y]= p;
		}else {
			while(!(nullSqr(x,y,board))) {
				System.out.print("Square is occupied. Enter new coordinates: ");
				x= console.nextInt();
				y=console.nextInt();
			}
			board[x][y]= p;
		}
		return 0;
	}

	// Program calls this method when it's computer's turn.
	public static int comp(Scanner console,String[][] board) {
		Random rand1 = new Random();
		System.out.print("Pick a piece for computer to play:");
		String comp = console.next().toUpperCase(); // Piece that user gave to computer to play
		while(!isPiece(comp)) {
			System.out.print("There is no piece like that. Please enter a valid input: ");
			comp= console.next().toUpperCase();
		}
		while(isUsed(comp,board)) {
			System.out.print("Already used. Pick another one: ");
			comp = console.next().toUpperCase();
			while(!isPiece(comp)) {
				System.out.print("There is no piece like that. Please enter a valid input: ");
				comp= console.next().toUpperCase();
				
			}
		}
       // Coordinates that computer will put the piece 
		int a = rand1.nextInt(4);
		int b = rand1.nextInt(4);

		if(nullSqr(a,b,board)) {
			board[a][b]= comp;
		}else {
			while(!(nullSqr(a,b,board))) {

				a= rand1.nextInt(4);
				b=rand1.nextInt(4);
			}
			board[a][b]= comp;
		}
		return 1;

	}

    // For printing the board 
	public static void printIt(String[][] board) {
		System.out.println();
		for(int j = 0;j<4;j++) {
			for(int k = 0; k<4; k++) {
				System.out.print(board[j][k]+ " ");
			}
			System.out.println();
		}
		System.out.println();
	}

    // For initializing the matrix with 'E'
	public static void initial(String[][] board) {
		for(int j = 0;j<4;j++) {
			for(int k = 0; k<4; k++) {
				board[j][k]= "E" ;
			}

		}

	}
    // Checks if the game ended. 
	public static boolean gameOver(String[][] board) {
		if(isFull(board)) {
			return true;
		}
		for(int a = 0;a<4;a++) { // Checks all four rows to see if there's a game-ending condition.
			if(equals(board[a][0],board[a][1],board[a][2],board[a][3])) {
				return true;
			}}

		for(int a = 0;a<4;a++) {// Checks all four columns to see if there's a game-ending condition.
			if(equals(board[0][a],board[1][a],board[2][a],board[3][a])) {
				return true;
			}}
		if(equals(board[0][3],board[1][2],board[2][1],board[3][0])) { // Checks diagonal to see if there's a game-ending condition.
			return true;
		}
		if(equals(board[0][0],board[1][1],board[2][2],board[3][3])) { // Checks diagonal to see if there's a game-ending condition.
			return true;
		}else {
			return false;
		}
	}
    // To check if the piece was used before
	public static boolean isUsed(String p, String[][] board){
		for(int j = 0;j<4;j++) {
			for(int k = 0; k<4; k++) {
				if((board[j][k]).equals(p)) {
					return true;
				}
			}
		}
		return false;
	}

	 // To check if all 16 squares are full
	public static boolean isFull(String[][] board){
		for(int j = 0;j<4;j++) {
			for(int k = 0; k<4; k++) {
				if((board[j][k]).equals("E")) {
					return false;
				}
			}
		}
		return true;
	}
    // Checks if the square is empty.
	public static boolean nullSqr(int j, int k,String[][] board) {
		if((board[j][k]).equals("E")) {
			return true;
		}
		return false;
	}
	// Checks if the strings are equal to each other
	public static boolean equals(String s1, String s2,String s3, String s4) {
		if(!isE(s1,s2,s3,s4)) {
			for(int a = 0; a<4 ;a++) {
				if((s1.charAt(a)==s2.charAt(a))&&(s2.charAt(a)==s4.charAt(a))&&(s2.charAt(a)==s3.charAt(a))) {
					return true;
				}

			}

		}
		return false;

	}
	
	// To avoid terminating the program when there are 4 E's in streak 
	public static boolean isE(String s1,String s2,String s3,String s4) {
		return (s1.equals("E")||s2.equals("E")||s3.equals("E")||s4.equals("E"));

	}

	//Writes the current situation to the file.
	public static void writeFile  (String[][] board,int a) throws FileNotFoundException {
		PrintStream stream = new PrintStream("input.txt");
		for(int j = 0;j<4;j++) {
			for(int k = 0; k<4; k++) {
				stream.print(board[j][k]+ " ");
			}
			stream.println();
		}
		stream.println();
		String turn = ""; // Whose turn is it
		if(a==1) {
			turn = "User's";
		}else if(a==0) {
			turn = "Computer's";
		}
		stream.print(turn + " turn" );

		stream.close();

	}
    //Reads the input.txt file
	public static int readFile(String[][] board) throws FileNotFoundException{
		Scanner input = new Scanner(new File("input.txt"));
		int t=2 ; //Whose turn is it
		for(int j = 0;j<4;j++) {
			for(int k = 0; k<4; k++) {
				String s = input.next();
				board[j][k]=s;

			}
		}
		input.nextLine();
		String turn = input.next();
		if(turn.equals("User's")) {
			t=1;
		}else if(turn.equals("Computer's")){
			t = 0;
		}
		return t;

	}
    //Checks if the piece is valid.
	public static boolean isPiece(String p) {
		if(p.length()!=4) {
			return false;
		}
		else {
			if(!(p.charAt(0)=='B'||p.charAt(0)=='W')) {
				return false;
			}
			if(!(p.charAt(1)=='T'||p.charAt(1)=='S')){
				return false;
			}
			if(!(p.charAt(2)=='S'||p.charAt(2)=='R')) {
				return false;
			}
			if(!(p.charAt(3)=='H'||p.charAt(3)=='S')){
				return false;
			}

			return true;

		}
	}

}



