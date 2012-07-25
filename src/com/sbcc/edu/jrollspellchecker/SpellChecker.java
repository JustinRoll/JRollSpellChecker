package com.sbcc.edu.jrollspellchecker;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;

public class SpellChecker {
	private File dictionaryFile;
	private String currentWord;
	public MyBinaryTree tree;

	public SpellChecker(File file) {
		dictionaryFile = file;
		this.tree = new MyBinaryTree();
		try {
			loadFile(dictionaryFile);
			tree.print();
			// method to split and clean the dictionary, then load it into a tree
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// method that checks if the current element is actually a word, then spellchecks it

	public int spellCheck(ArrayList<String> words) {
		// this will return a "Failure Index" on the arrayList
		int index = -1;

		if (words == null)
			System.out.println("Null Obj");
		for (int i = 0; i < words.size(); i++) {
			
			if (words.get(i).contains(" ") || words.get(i).isEmpty() || words.get(i).contains("\\s")) {
				System.out.println("white space character");
				
				continue;
			}
			// add in a filter here for words.get
			// use a regex to ignore non alpha numeric characters

			//if (tree.searchNew(tree.getRoot(), new WordNode(words.get(i))) == null)
			//removeChar(words.get(i), '\r\n');
			if (tree.search(words.get(i)) == null)
				return i;
		}
		return index;
	}
	
	public int spellCheckNew(ArrayList<IndexedWord> words) {
		// this will return a "Failure Index" on the arrayList
		int index = -1;

		if (words == null)
			System.out.println("Null Obj");
		for (int i = 0; i < words.size(); i++) {
			
			if (words.get(i).getData().contains(" ") || words.get(i).getData().isEmpty() || words.get(i).getData().contains("\\s")) {
				System.out.println("white space character");
				
				continue;
			}
			// add in a filter here for words.get
			// use a regex to ignore non alpha numeric characters

			//if (tree.searchNew(tree.getRoot(), new WordNode(words.get(i))) == null)
			//removeChar(words.get(i), '\r\n');
			if (tree.search(words.get(i).getData()) == null)
				return i;
		}
		return index;
	}

	
	public void saveFile(String fileName) throws IOException
	{
		File file = new File(fileName);
		FileWriter fWrite = new FileWriter(file);
		tree.save(file, fWrite);
	}
	
	public File getDictionaryFile() {
		throw new UnsupportedOperationException();
	}

	public void setDictionaryFile(File dictionaryFile) {
		throw new UnsupportedOperationException();
	}

	public void checkWord(Object string) {
		throw new UnsupportedOperationException();
	}

	public void loadFile(File dictionaryfile) throws IOException {
		String text = FileUtils.readFileToString(dictionaryfile);
		String[] dictionaryText = text.split("\n");
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(dictionaryText));
		Collections.shuffle(words);
		for (String word : words) {

			tree.insert(removeChar(word, '\n'));
		}
	}

	public static String removeChar(String s, char c) {
		StringBuffer r = new StringBuffer(s.length());
		r.setLength(s.length());
		int current = 0;
		for (int i = 0; i < s.length(); i++) {
			char cur = s.charAt(i);
			if (cur != c)
				r.setCharAt(current++, cur);
		}
		return r.toString().trim();
	}

	public String searchPrev(String word)
	{
		WordNode node = tree.searchPrev(word);
		return node.getData();
	}
	
	public String searchNext(String word)
	{
		WordNode node = tree.searchNext(word);
		return node.getData();
	}
	
	public void searchWord(String word) {
		//WordNode node = tree.search(word);
		WordNode node = tree.search(word);
	}

	public void insertWord(String word) {
		//insertion method for after the tree is already loaded.
		//this will check if the word exists before inserting to prevent duplicates.
		tree.insertPostLoad(word);
	}
}