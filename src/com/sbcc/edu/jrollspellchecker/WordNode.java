package com.sbcc.edu.jrollspellchecker;

public class WordNode {
	private String data;
	private WordNode leftChild;
	private WordNode rightChild;
	private WordNode parent;
	
	public WordNode(String word) {
		this.data = word;
	}
	
	public WordNode()
	{
		
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public WordNode getParent() {
		return parent;
	}

	public void setParent(WordNode parent) {
		this.parent = parent;
	}

	public WordNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(WordNode leftChild) {
		this.leftChild = leftChild;
	}

	public WordNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(WordNode rightChild) {
		this.rightChild = rightChild;
	}

	public boolean isLeaf() {
		if (this.getLeftChild() == null || this.getRightChild() == null)
			return true;
		else
			return false;
	}

	public Integer compareTo(WordNode wordNode) {

		return this.data.compareToIgnoreCase(wordNode.data);
		//return tempData.compareToIgnoreCase(tempData2);
	}
}