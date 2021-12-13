package word_search_plugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result implements Serializable{
	public String word = null;
	String direction = null;
	int startpointRow = 0;
	int startpointCol = 0;
	int endpointCol = 0;
	int endpointRow = 0;
	public Result() {};
	
	public void init() {
		this.word = null;
		this.direction = null;
	}
	public Result makeClone() throws IOException, ClassNotFoundException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(outputStream);
	    out.writeObject(this);

	    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
	    ObjectInputStream in = new ObjectInputStream(inputStream);
	    Result copied = (Result)in.readObject();
	    return copied;
	}
}
