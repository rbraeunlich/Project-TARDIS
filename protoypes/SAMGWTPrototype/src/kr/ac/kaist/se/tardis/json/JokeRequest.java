package kr.ac.kaist.se.tardis.json;

import com.google.gwt.core.client.JavaScriptObject;

public class JokeRequest extends JavaScriptObject {

	protected JokeRequest(){}
	
	protected final native String getType()/*-{ return this.type }-*/;
	
	public final native Joke getValue()/*-{ return this.value }-*/;

}
