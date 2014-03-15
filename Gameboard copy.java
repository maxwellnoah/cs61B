/* Gameboard.java */

/** implements a representation of a gameboard*/

public class Gameboard {
    
    private Chip[][] board;
    private blackCount;
    private whiteCount;
    
    /** creates a new gameboard and sets all postions on the board to empty*/

    public Gameboard() {
        board = new Chip[][];
	blackCount = 0;
	whiteCount = 0;
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                board[i][j] = new Chip(i, j);
            }
        }
	board[0][0] = null;
	board[7][0] = null;
	board[0][7] = null;
	board[7][7] = null;
    }

    /** adds Chip c to postion (x, y) on the board*/

	
    public void addChip(int c, int x, int y) {
        Chip temp = board[x][y];
	temp.setType(c);
	if (c == WHITE) {
		whiteCount++;
	} else {
		blackCount++;
	}
    }
	

    /** moves the Chip in position(x1, y1) to (x2, y2) */


    public void moveChip(int x1, int y2, int x2, int y2) {
        Chip old = board[x1][y1];
	color = old.getType();
	Chip curr = board[x2][y2];
	curr.setType(color);
	old.setType(EMPTY);
    }


    /** returns the Chip at the board index (x, y) */

    public Chip getItem(int x, int y) {
        return board[x][y];
    }



    

