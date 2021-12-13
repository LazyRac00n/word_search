package word_search_plugin.handlers;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;		

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleListener;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IPatternMatchListener;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.framework.Bundle;

import word_search_plugin.Algorithm;
import word_search_plugin.Board;
import word_search_plugin.Result;

import org.eclipse.jface.text.IDocument;
/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler implements IConsoleListener, IPatternMatchListener {
	/**
	 * The constructor.
	 */
	
	private String pattern;
	private String text = null;
	List<IConsole> textConsoles = new ArrayList<>();
	List<Character> t = new ArrayList<>();
	IConsoleManager manager;
	private List<Result> _result = new ArrayList<Result>();
	private List<String> _words = new ArrayList<String>();
	private Algorithm alogrithm = new Algorithm();
	private List<String> wordList = new ArrayList<String>();
	private Board board;
	public SampleHandler() {
		pattern = "\\w+";
		
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		manager = ConsolePlugin.getDefault().getConsoleManager();
		for(IConsole console : manager.getConsoles()) {
			if(console instanceof TextConsole) {
				((TextConsole) console).addPatternMatchListener(this);
				textConsoles.add(console);
			}
		}
		manager.addConsoleListener(this);
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		createBoard();
		readLine();
		try {
			_result = this.alogrithm.findWords(this.board, _words);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessageDialog.openInformation(
				window.getShell(),
				"WordSearch",
				"Words found:" + this.writeLine());
		return null;
		
	}


	@Override
	public void connect(TextConsole arg0) {
		
	}

	@Override
	public void disconnect() {
		
	}

	@Override
	public void matchFound(PatternMatchEvent arg0) {
		for(IConsole console : textConsoles) {
			IDocument document = ((TextConsole) console).getDocument();
			text = document.get();
		}
	}

	@Override
	public int getCompilerFlags() {
		return 0;
	}

	@Override
	public String getLineQualifier() {
		return null;
	}

	@Override
	public String getPattern() {
		return pattern;
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void consolesAdded(IConsole[] arg0) {
		// TODO Auto-generated method stub
		for(IConsole console : manager.getConsoles()) {
			if(console instanceof TextConsole) {
				((TextConsole) console).addPatternMatchListener(this);
				textConsoles.add(console);
			}
		}
		_result.clear();
		wordList.clear();
		board = null;
		
	}
	
	@Override
	public void consolesRemoved(IConsole[] arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void createBoard() {
		String word = "";
		for(Character c: text.toCharArray()) {
			if(c != '\n') {
				word += c;
			}else {
				wordList.add(word);
				word = "";
			}
		}
		char[][] _board = new char[wordList.size()][];
		for(int i=0;i < wordList.size();i++) {
			char[] row = new char[wordList.get(i).length()];
			int j =0;
			for(Character c : wordList.get(i).toCharArray()) {
				row[j] = c;
				j++;
			}
			_board[i] = row;
		}
		this.board = new Board(_board);
	}
	private void readLine() {
		BufferedReader reader;
		try {
			Bundle bundle = Platform.getBundle("word_search_plugin");
			InputStream is = FileLocator.openStream(bundle, new Path("resources/words.txt"), false);
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = reader.readLine();
			while(line != null) {
				this._words.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	private String writeLine() {
		String results = null;
  		BufferedWriter writer;
  		try {
  			String str = "";
  			String homepath = System.getProperty("user.home");
		    String filePath = homepath+"/Downloads/results.txt";
  			writer = new BufferedWriter(new FileWriter(filePath));
  			for(Result r : this._result) {
  				str = str + r.word +"\n";
  			}
  			writer.write(str);
  			writer.close();
  			results = str;
  		}catch(IOException e) {
  			e.printStackTrace();
  		}
		return results;
  		
  		
  	}
}