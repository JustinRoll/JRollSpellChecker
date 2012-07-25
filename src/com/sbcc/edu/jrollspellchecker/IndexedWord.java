package com.sbcc.edu.jrollspellchecker;

public class IndexedWord {

	
	int startIndex;
	int endIndex;
	String data;
	
	public IndexedWord(int startIndex, int endIndex, String data)
	{
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.data = data;
	}
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	
}
