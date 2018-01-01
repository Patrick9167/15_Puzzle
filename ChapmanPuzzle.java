import java.util.*;
public class ChapmanPuzzle {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Sam Loyd 15-Puzzle");
		System.out.println("The aim of the game is to....");
		System.out.println("Enter 'play' to begin and 'q' at any time to exit.");
		System.out.print("User-> ");
		Scanner scanner = new Scanner(System.in);
		if(scanner.nextLine().equals("play"))
		{
			char[] board = createBoard();		//creates solvable board 
			int bb = 6;							//blank space starting index in array (on board)
			System.out.println();
			printBoard(board);
			boolean solved=false;
			while(!solved)						//IE. keep playing until the board is solved
			{
				System.out.println("\n\nNext Move? (up ‘w’ , down ‘s’ , left ‘a’ , right ‘d’)");
				System.out.print("User-> ");
				switch(scanner.nextLine())
				{
				case "w":
					//move up
					if((bb-8)>=0)							//swap blank space with the tile above it 
					{										
						char temp = board[bb-8];			//swap first '*' with element at (* index) - 8 
						board[bb-8] = board[bb];
						board[bb] = temp;
						temp = board[bb-7];
						board[bb-7] = board[bb+1];
						board[bb+1] = temp;
						bb-=8;
					}
					else
					{
						System.out.println("Unable to move up.");
					}
					break;
				case "s":
					//move down
					if((bb+8)<31)							//swap blank space with the tile below it 
					{
						char temp = board[bb+8];			//swap first '*' with element at (* index) + 8
						board[bb+8] = board[bb];
						board[bb] = temp;
						temp = board[bb+9];
						board[bb+9] = board[bb+1];
						board[bb+1] = temp;
						bb +=8;
					}
					else
					{
						System.out.println("Unable to move down.");
					}
					break;
				case "a":
					//move left
					if(bb%8!=0)								//swap blank space with the tile to the left of it 
					{
						char temp = board[bb-2];			//swap first '*' with element at (* index) - 2
						board[bb-2] = board[bb];
						board[bb] = temp;
						temp = board[bb-1];
						board[bb-1] = board[bb+1];
						board[bb+1] = temp;
						bb-=2;
					}
					else
					{
						System.out.println("Unable to move left.");
					}
					break;
				case "d":
					//move right
					if(((bb+2)%8)!=0)						//swap blank space with the tile to the right of it 
					{
						char temp = board[bb+2];			//swap first '*' with element at (* index) + 2
						board[bb+2] = board[bb];
						board[bb] = temp;
						temp = board[bb+3];
						board[bb+3] = board[bb+1];
						board[bb+1] = temp;
						bb+=2;
					}
					else
					{
						System.out.println("Unable to move right.");
					}
					break;
				case "q":									//exit game if 'q' is entered
					System.out.println("Hasta Luego!\n");
					System.out.println("<<<<<<<<<<<<<<<<<<FIN>>>>>>>>>>>>>>>>>>>>>");
					solved = true;
					break;
				default:
					System.out.println("Oops invalid input, please try again");
				}
				
				System.out.println();
				if(solved!=true) printBoard(board);
				
				if(board[30]=='*') 
					if(isSolved(board))
					{
						solved=true;
						System.out.println("\nYou solved it! Muy Buen!");
					}
				
			}
		}

	}
	
	static void printBoard(char[] board)		//prints the 4x4 board to the console
	{
		for(int i=0; i<31; i++)					
		{
			System.out.print(board[i++]);
			System.out.print(board[i] + ((i==0 || i==7 || i==15 || i==23)? "\n" : " "));	
		}
	}
	
	
	static char[] createBoard()					//creates a 32 character array with two chars per int (ie '1', '5' for 15)
	{
		Random r = new Random();
		boolean valid = false;
		int[] temp = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}; 
		while(!valid)
		{
			shuffle(temp);						//shuffle an integer array containing numbers 1-15 until a solvable combination is achieved
			if(isSolvable(temp)) valid=true;
		}
		
		char[] board = new char[32];
		int j=0;
		for(int i=0; i<31 && j<15; i++)			//transfer the integer array to a 32 character array representing the 15 numbers and the blank space '**'
		{
			board[i] = (i==6)? '*' :(char) ((temp[j]/10)+48);
			i++;
			board[i]= (i==7)? '*' :(char) ((temp[j++]%10)+48);	
		}
		return board;
	}
	
	
	static int[] shuffle(int[] b)				//function to shuffle the elements of an integer array
	{
		Random r = new Random();
		int rand = 0;
		for(int i=0; i<b.length; i++)
		{
			rand = r.nextInt(15);
			int temp = b[i];
			b[i]=b[rand];
			b[rand]=temp;
		}
		return b;
	}
	
	
	static int inversions(int[] p,int low, int high)		//return the number of inversions in the array
	 { // inversions in p[low..high-1]
		 int result = 0;
		 for (int i = low; i < high; i = i+1)
			 for (int j = i+1; j < high; j = j+1)
			 	if ( p[i] > p[j] )
			 			result = result+1;
		 return result;
	 } // inversions
	
	
	static boolean even_perm(int[] p)						//check if there are an even number of inversions in an integer array
	{
		if(p==null) return false;
	   	int n = inversions(p, 0, p.length);
    	return n%2 == 0;
	} // even
	   
	
	static boolean isSolvable(int[] board)					//returns true if there are an even number of inversions in the array
	{
		if(even_perm(board)) return true; else return false;
	}
	
	static boolean isSolved(char[] charBoard)				//returns true if the board is in ascending order (1, 2......., 15)
	{
		if(charBoard==null) return false;
		if(charBoard.length==0) return true;
		int[] board = new int[15];
		int temp=0;
		int j=0;
		for(int i=0; i<29 && j<15; i++)						//copy character array to integer array 
		{
			temp = (int) (charBoard[i]-48);
			temp *= 10;
			i++;
			temp += (int) (charBoard[i]-48);
			board[j++] = temp;
		}
		j=0;
		for (int i = 0; i < board.length&&j<board.length; i++)	//compares element A to the next element B, if A>B then the board is not solved
		{
			j=i+1;
			if ( j<board.length && board[i] > board[j] ) return false;	
		}
				
		return true;
	}

}
