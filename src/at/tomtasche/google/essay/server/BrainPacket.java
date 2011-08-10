package at.tomtasche.google.essay.server;

import java.util.Arrays;

public class BrainPacket {

    Thought result;


    public BrainPacket() {}


    @Override
    public String toString() {
	return "BrainPacket [result=" + result + "]";
    }


    public String getFirstIdea() {
	return result.suggestions[0].completion;
    }


    public static class Thought {

	String query;
	String lang;
	Idea[] suggestions;
	boolean queryAtWordBoundary;


	public Thought() {}


	@Override
	public String toString() {
	    return "Thought [query=" + query + ", lang=" + lang + ", suggestions="
		    + Arrays.toString(suggestions) + ", queryAtWordBoundary=" + queryAtWordBoundary
		    + "]";
	}
    }

    public static class Idea {

	String completion;
	String renderingCompletion;


	public Idea() {}


	@Override
	public String toString() {
	    return "Idea [completion=" + completion + ", renderingCompletion="
		    + renderingCompletion + "]";
	}
    }
}