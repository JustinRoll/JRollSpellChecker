package com.sbcc.edu.jrollspellchecker;

import java.io.*;
import java.util.*;

public class MyBinaryTree {
	private WordNode root;

	private WordNode current;
	private int size;

	private boolean changed; // variable to check if tree has been modified after the initial load.

	public MyBinaryTree() {
		root = null;
		changed = false;

	}

	public void save(File file, FileWriter fWrite) throws IOException {
		saveNodePreOrder(root, file, fWrite);
		fWrite.close();
	}

	public void saveNodePreOrder(WordNode curNode, File file, FileWriter fWrite) throws IOException {
		if (curNode == null)
			return;
		saveNode(curNode, file, fWrite);
		saveNodePreOrder(curNode.getLeftChild(), file, fWrite);
		saveNodePreOrder(curNode.getRightChild(), file, fWrite);
	}

	public void printTreePreOrder(WordNode curNode) {
		if (curNode == null)
			return;
		printNode(curNode);
		printTreePreOrder(curNode.getLeftChild());
		printTreePreOrder(curNode.getRightChild());
	}

	public void printNode(WordNode curNode) {
		System.out.println(curNode.getData());
	}

	public void saveNode(WordNode curNode, File file, FileWriter fWrite) throws IOException {
		fWrite.write(curNode.getData() + "\r\n");

	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public static void fillBinaryTree(int[] newArray) {
		// the goal here is to fill the binary tree in order so that it can be searched effectively.
		for (int i = 0; i < newArray.length; i++) {

		}
	}

	public int getLength() {
		return size;
	}

	/*
	 * method is for array-based search, so commenting out public int search(int first, int size, WordNode target) {
	 * 
	 * int middle;
	 * 
	 * 
	 * if (size <= 0) return -1; else { middle = first + size / 2; if (target.compareTo(a[middle]) == 0) {
	 * 
	 * System.out.println("Final number of searches = " + numSearches); numSearches = 0; return middle; } else if
	 * (target.compareTo(a[middle]) == -1) { // the target is less than a[middle], so search before the middle.
	 * numSearches++; System.out.println("number of searches: " + numSearches); return search(first, size / 2, target);
	 * } else if (target.compareTo(a[middle]) == 1) { numSearches++; System.out.println("number of searches: " +
	 * numSearches); return search(middle + 1, (size - 1) / 2, target); } else return -1; //-1 = not found //need
	 * another search for not found } }
	 */
	public void insert(String text) {
		root = insertNode(root, text); // use recursion
	}

	public WordNode search(String target) {
		WordNode cursor = root;
		target.trim(); // make sure there is no white space
		WordNode targetNode = new WordNode(target);
		while (cursor != null) {
			if (targetNode.compareTo(cursor) < 0) {
				if (cursor.getLeftChild() != null) {
					System.out.println("Going from " + cursor.getData() + " to left child "
							+ cursor.getLeftChild().getData());
				} else {
					System.out.println("Going from " + cursor.getData() + " to left null node");
				}
				cursor = cursor.getLeftChild();
			} else if (targetNode.compareTo(cursor) > 0) {
				if (cursor.getRightChild() != null) {
					System.out.println("Going from " + cursor.getData() + " to right child "
							+ cursor.getRightChild().getData());
				} else {
					System.out.println("Going from " + cursor.getData() + " to right null node");
				}
				cursor = cursor.getRightChild();
			} else {
				System.out.println(" returning  node " + cursor.getData());
				return cursor;
			}
		}
		System.out.println("couldn't find node " + target);
		return null;
	}

	public WordNode searchNew(WordNode startNode, WordNode target)
	{
		if (startNode == null)
			return null;
		
		if (startNode.compareTo(target) == 1)
			return startNode;
		
		if (target.compareTo(startNode) <0) {
			if (startNode.getLeftChild() != null)
				System.out.println("Going from " + startNode.getData() + " to left child " + startNode.getLeftChild().getData());
			else
				System.out.println("Going from " +startNode.getData() + " to left null node");
			return searchNew(startNode.getLeftChild(), target);
		}
		else {
			if (startNode.getLeftChild() != null)
				System.out.println("Going from " + startNode.getData() + " to Right child " + startNode.getRightChild().getData());
			else
				System.out.println("Going from " +startNode.getData() + " to Right null node");
			
			return searchNew(startNode.getRightChild(), target);
		}
	}
	public WordNode searchNextOld(String target) {
		WordNode cursor = root;
		Stack<WordNode> wordStack = new Stack<WordNode>();
		target.trim(); // make sure there is no white space
		while (cursor != null) {
			if (target.compareToIgnoreCase(cursor.getData()) < 0) {
				if (cursor.getLeftChild() != null)
					System.out.println("Going from " + cursor.getData() + " to left child "
							+ cursor.getLeftChild().getData());
				else
					System.out.println("Going from " + cursor.getData() + " to left null node");
				wordStack.push(cursor);
				cursor = cursor.getLeftChild();
			} else if (target.compareToIgnoreCase(cursor.getData()) > 0) {
				if (cursor.getRightChild() != null)
					System.out.println("Going from " + cursor.getData() + " to right child "
							+ cursor.getRightChild().getData());
				else
					System.out.println("Going from " + cursor.getData() + " to right null node");
				wordStack.push(cursor);
				cursor = cursor.getRightChild();
			} else {
				System.out.println(" returning  node " + cursor.getData());
				return cursor;
			}
		}
		System.out.println("couldn't find node " + target);
		wordStack.pop(); // pop null node off the stack
		WordNode parentWord = wordStack.pop();
		if (parentWord.getData().compareToIgnoreCase(target) < 0)
			return parentWord;
		else if (parentWord.getRightChild() != null)
			return parentWord.getRightChild();
		else
			return wordStack.pop().getRightChild();
	}

	// go down the tree until you are at a leaf or at the word
	// if the word is in the dictionary, check to see if it has a left child that isn't null
	// if it does, go to the left child.

	// if the word is not in the dictionary, go to the nearest left child

	/*
	 * public WordNode searchPrev(WordNode cursor, String target Stack wordStack) { WordNode cursor = root; if
	 * (cursor.getLeftChild() != null) {
	 * 
	 * } }
	 */

	public WordNode searchPrev(String target) {
		WordNode returnNode = new WordNode("");
		WordNode currentNext = new WordNode("");

		WordNode cursor = root;
		WordNode targetNode = new WordNode(target);

		while (cursor != null) {
			if (cursor.compareTo(targetNode) < 0) {
				if (cursor.getRightChild() == null) {
					returnNode = cursor;
				}
				currentNext = cursor;

				cursor = cursor.getRightChild();
			} else {
				if (cursor.getLeftChild() == null) {
					returnNode = currentNext;
				}
				cursor = cursor.getLeftChild();
			}
		}

		return returnNode;
	}

	// if node exists in dictionary, return nearest node to the left
	// if node does not exist in dictionary, traverse upwards until you see it
	public WordNode searchPrevOld(String target) {
		WordNode cursor = root;
		Stack<WordNode> wordStack = new Stack<WordNode>();
		int level = 1;
		target.trim(); // make sure there is no white space
		WordNode targetNode = new WordNode(target);
		while (cursor != null) {
			if (targetNode.compareTo(cursor) < 0) {
				if (cursor.getLeftChild() != null)
					System.out.println("Going from " + cursor.getData() + " to left child "
							+ cursor.getLeftChild().getData());
				else
					System.out.println("Going from " + cursor.getData() + " to left null node");
				wordStack.push(cursor);
				cursor = cursor.getLeftChild();
			} else if (targetNode.compareTo(cursor) > 0) {
				if (cursor.getRightChild() != null)
					System.out.println("Going from " + cursor.getData() + " to right child "
							+ cursor.getRightChild().getData());
				else
					System.out.println("Going from " + cursor.getData() + " to right null node");
				wordStack.push(cursor);
				cursor = cursor.getRightChild();
			} else {
				// if the word is in the dictionary, check to see if it has a left child that isn't null
				// if it does, go to the left child.
				if (cursor.getLeftChild() != null) {
					cursor = cursor.getLeftChild();
					System.out.println(" returning  node left of target " + cursor.getData());
					return cursor;
				} else {
					while (targetNode.compareTo(cursor) > 0) {
						System.out.println("Going up and left! ");
						cursor = wordStack.pop();
					}
					System.out.println("Returning left child of cursor " + cursor.getLeftChild().getData());
					return cursor.getLeftChild();
				}
			}
		}
		System.out.println("couldn't find node " + target);
		wordStack.pop(); // pop null node off the stack
		WordNode parentWord = wordStack.pop();
		if (parentWord.getData().compareToIgnoreCase(target) == 1)
			return parentWord;
		else if (parentWord.getLeftChild() != null)
			return parentWord.getLeftChild();
		else
			return wordStack.pop().getLeftChild();

	}

	public WordNode searchNext(String target) {
		WordNode returnNode = new WordNode("");
		WordNode currentNext = new WordNode("");

		WordNode cursor = root;
		WordNode targetNode = new WordNode(target);

		while (cursor != null) {
			if (cursor.compareTo(targetNode) > 0) {
				if (cursor.getLeftChild() == null) {
					returnNode = cursor;
				}
				currentNext = cursor;
				;
				cursor = cursor.getLeftChild();
			} else {
				if (cursor.getRightChild() == null) {
					returnNode = currentNext;
				}
				cursor = cursor.getRightChild();
			}
		}

		return returnNode;
	}

	/*
	 * public WordNode moveUpAndLeft (Stack wordStack, WordNode cursor, WordNode target) {
	 * 
	 * }
	 */

	public void print() {
		printTreePreOrder(root);
	}

	public WordNode insertNode(WordNode curNode, String text) {

		if (curNode == null) {

			WordNode newNode = new WordNode();
			newNode.setData(text);
			return newNode;
		} else {
			if (text.compareToIgnoreCase(curNode.getData()) < 0) {
				curNode.setLeftChild(insertNode(curNode.getLeftChild(), text));
				return curNode;
			} else {
				curNode.setRightChild(insertNode(curNode.getRightChild(), text));
				return curNode;
			}
		}
	}

	public WordNode insertNodePostLoad(WordNode curNode, String text) {
		// This method is for inserting to the binary tree after the tree is initially loaded from the dictionary.
		// I don't have the "exists" check when initially loading the tree for the sake of speed.
		if (search(text) != null) // make sure that we aren't inserting duplicate nodes.
			return null;
		return insertNode(curNode, text);
	}

	public void insertPostLoad(String text) {
		root = insertNodePostLoad(root, text); // use recursion
	}

	public WordNode getRoot() {
		return root;
	}

	public void setRoot(WordNode root) {
		this.root = root;
	}

	public void traverse() {
		throw new UnsupportedOperationException();
	}

	public WordNode getCurrent() {
		return current;
	}

	public void setCurrent(WordNode cur) {
		current = cur;
	}
}