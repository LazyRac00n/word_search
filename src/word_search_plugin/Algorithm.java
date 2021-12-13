package word_search_plugin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Algorithm {
	//ArrayList<String> _result = new ArrayList<String>();
	ArrayList<Result> _results = new ArrayList<Result>();
	Board _board = new Board();

	public List<Result> findWords(Board board, List<String> words) throws ClassNotFoundException, IOException{
		TrieNode root = new TrieNode();
		for(int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			TrieNode node = root;
			
			for(Character letter : word.toCharArray()) {
				if(node.child.containsKey(letter)) {
					node = node.child.get(letter);
				}else {
					TrieNode newNode = new TrieNode();
					node.child.put(letter, newNode);
					node = newNode;
					
				}
			}
			node.word = word;
		}
		
		this._board = board;
		
		for(int row = 0; row < board.getRow(); ++row) {
			for(int col = 0; col < board.getCol(row); col++) {
				if(root.child.containsKey(board.getCell(row, col))) {
					Result result = new Result();
					backtracking(row, col, root, result,"Starting", row, col);
				}
			}
		}
		return this._results;
		
	}
	
	private void backtracking(int row, int col, TrieNode root, Result result, String direction,int startRow, int startCol) throws ClassNotFoundException, IOException {
		Character letter = this._board.getCell(row, col);
		TrieNode currNode = root.child.get(letter);
		
		if(currNode.word != null ) {
			//this._result.add(currNode.word);
			Result temp = result.makeClone();
			temp.word = currNode.word;
			temp.startpointCol = startCol;
			temp.startpointRow = startRow;
			temp.endpointRow =row;
			temp.endpointCol =col;
			temp.direction = direction;
			this._results.add(temp);
			currNode.word = null;
			
		}
		
		this._board.setCell(row, col, '#');
		int[] rowOffset = {-1, 0, 1, 0, -1, -1, 1, 1};
		int[] colOffset = {0, 1, 0, -1, -1, 1, -1, 1};
		/*for(int i = 0; i < 8; ++i) {
			int newRow = row + rowOffset[i];
			int newCol = col + colOffset[i];
			if(newRow<0 || newRow >= this._board.getRow() || newCol < 0 || newCol >= this._board.getCol(newRow)) {
				continue;
			}
			if(currNode.child.containsKey(this._board.getCell(newRow, newCol))){
					backtracking(newRow, newCol, currNode, result,direction)
				}
			}
		}*/
		switch(direction) {
			case "Starting":
				for(int i = 0; i < 8; i++) {
					int newRow = row + rowOffset[i];
					int newCol = col + colOffset[i];
					if(newRow<0 || newRow >= this._board.getRow() || newCol < 0 || newCol >= this._board.getCol(newRow)) {
						continue;
					}
					if(currNode.child.containsKey(this._board.getCell(newRow, newCol))){
						if(i==0) {
							backtracking(newRow, newCol, currNode, result,"Top",startRow, startCol);
						}
						else if(i==1) {
							backtracking(newRow, newCol, currNode, result,"Right", startRow, startCol);
						}
						else if(i==2) {
							backtracking(newRow, newCol, currNode, result,"Down", startRow, startCol);
						}
						else if(i==3) {
							backtracking(newRow, newCol, currNode, result,"Left", startRow, startCol);
						}
						else if(i==4) {
							backtracking(newRow, newCol, currNode, result,"LeftTop", startRow, startCol);
						}
						else if(i==5) {
							backtracking(newRow, newCol, currNode, result,"RightTop", startRow, startCol);
						}
						else if(i==6) {
							backtracking(newRow, newCol, currNode, result,"LeftDown", startRow, startCol);
						}
						else if(i==7) {
							backtracking(newRow, newCol, currNode, result,"RightDown", startRow, startCol);
						}
					}
				}
			case "Top":
				int newRowT = row - 1;
				int newColT = col;
				if(newRowT<0 || newRowT >= this._board.getRow() || newColT < 0 || newColT >= this._board.getCol(newRowT)) {
					break;
				}
				if(currNode.child.containsKey(this._board.getCell(newRowT, newColT))){
					backtracking(newRowT, newColT, currNode, result,"Top", startRow, startCol);
				}
				break;
			case "Down":
				int newRowD = row + 1;
				int newColD = col;
				if(newRowD<0 || newRowD >= this._board.getRow() || newColD < 0 || newColD >= this._board.getCol(newRowD)) {
					break;
				}
				if(currNode.child.containsKey(this._board.getCell(newRowD, newColD))){
					backtracking(newRowD, newColD, currNode, result,"Down", startRow, startCol);
				}
				break;
			case "Left":
				int newRowL = row;
				int newColL = col - 1;
				if(newRowL<0 || newRowL >= this._board.getRow() || newColL < 0 || newColL >= this._board.getCol(newRowL)) {
					break;
				}
				if(currNode.child.containsKey(this._board.getCell(newRowL, newColL))){
					backtracking(newRowL, newColL, currNode, result,"Left", startRow, startCol);
				}
				break;
			case "Right":
				int newRowR = row;
				int newColR = col + 1;
				if(newRowR<0 || newRowR >= this._board.getRow() || newColR < 0 || newColR >= this._board.getCol(newRowR)) {
					break;
				}
				if(currNode.child.containsKey(this._board.getCell(newRowR, newColR))){
					backtracking(newRowR, newColR, currNode, result,"Right", startRow, startCol);
				}
				break;
			case "LeftTop":
				int newRowLT = row - 1;
				int newColLT = col - 1;
				if(newRowLT<0 || newRowLT >= this._board.getRow() || newColLT < 0 || newColLT >= this._board.getCol(newRowLT)) {
					break;
				}
				if(currNode.child.containsKey(this._board.getCell(newRowLT, newColLT))){
					backtracking(newRowLT, newColLT, currNode, result,"LeftTop", startRow, startCol);
				}
				break;
			case "RightTop":
				int newRowRT = row - 1;
				int newColRT = col + 1;
				if(newRowRT<0 || newRowRT >= this._board.getRow() || newColRT < 0 || newColRT >= this._board.getCol(newRowRT)) {
					break;
				}
				if(currNode.child.containsKey(this._board.getCell(newRowRT, newColRT))){
					backtracking(newRowRT, newColRT, currNode, result,"RightTop", startRow, startCol);
				}
				break;
			case "LeftDown":
				int newRowLD= row + 1;
				int newColLD = col - 1;
				if(newRowLD<0 || newRowLD >= this._board.getRow() || newColLD < 0 || newColLD >= this._board.getCol(newRowLD)) {
					break;
				}
				if(currNode.child.containsKey(this._board.getCell(newRowLD, newColLD))){
					backtracking(newRowLD, newColLD, currNode, result,"LeftDown", startRow, startCol);
				}
				break;
			case "RightDown":
				int newRowRD = row + 1;
				int newColRD = col + 1;
				if(newRowRD<0 || newRowRD >= this._board.getRow() || newColRD < 0 || newColRD >= this._board.getCol(newRowRD)) {
					break;
				}
				if(currNode.child.containsKey(this._board.getCell(newRowRD, newColRD))){
					backtracking(newRowRD, newColRD, currNode, result,"RightDown", startRow, startCol);
				}
				break;
			
		}
			
		this._board.setCell(row, col, letter);
		if(currNode.child.isEmpty()) {
			root.child.remove(letter);
		}
		
	}

	
}
