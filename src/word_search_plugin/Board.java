package word_search_plugin;

public class Board {
	char[][] _board = null;
	public Board(char[][] board) {
		this._board = board;
	}
	
	public Board() {
		// TODO Auto-generated constructor stub
	}

	public void display() {
		for(int i =0; i < this._board.length;i++) {
			for(int j=0; j<this._board[i].length; j++) {
				System.out.print(this._board[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
	
	public int getRow() {
		return this._board.length;
	}
	
	public int getCol(int row) {
		return this._board[row].length;
	}
	
	public char getCell(int row, int col) {
		return this._board[row][col];
	}
	
	public void setCell(int row, int col,char t) {
		this._board[row][col] = t;
	}
}
