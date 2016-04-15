package kr.ac.kaist.se.tardis.json;

import com.google.gwt.core.client.JavaScriptObject;

public class Joke extends JavaScriptObject {

	protected Joke(){}
	
	public final native String getId()/*-{ return this.id }-*/;
	
	public final native String getJoke()/*-{ return this.joke }-*/;
}
