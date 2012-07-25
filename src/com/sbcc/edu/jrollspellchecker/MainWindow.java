package com.sbcc.edu.jrollspellchecker;

import java.io.*;
import java.util.*;
import java.awt.Toolkit;
import java.awt.datatransfer.*;

import com.cloudgarden.resource.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import org.apache.commons.io.*;
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainWindow extends org.eclipse.swt.widgets.Composite {

	Properties appSettings = new Properties();
	Cursor defaultCursor; // To change the cursor to an arrow at any point after MainWindow() has executed, use setCursor(defaultCursor);
	Cursor waitCursor; // To change the cursor to an hourglass at any point after MainWindow() has executed, use setCursor(waitCursor);
	private Menu menu1;
	private MenuItem aboutMenuItem;
	private Menu helpMenu;
	private MenuItem helpMenuItem;
	private MenuItem exitMenuItem;
	private MenuItem closeFileMenuItem;
	private MenuItem saveFileMenuItem;
	private MenuItem newFileMenuItem;
	private MenuItem openFileMenuItem;
	private MenuItem importDictionaryItem;
	private ToolItem checkToolItem;
	private ToolItem newToolItem;
	private ToolItem saveToolItem;
	private ToolItem openToolItem;
	private ToolBar toolBar;
	@SuppressWarnings("unused")
	private MenuItem fileMenuSep2;
	@SuppressWarnings("unused")
	private MenuItem fileMenuSep1;
	private Composite clientArea;
	private Label statusText;
	private Composite statusArea;
	private Button replaceNextButton;
	private Button replacePrevButton;
	private Button cancelButton;
	private Button addButton;
	private Button skipButton;
	private Button okButton;
	private Text curWord;
	private Text spellCheckText;
	private Menu fileMenu;
	private MenuItem fileMenuItem;
	private SpellChecker spellChecker;
	private String fileName;
	private String dictFileName;
	private ArrayList<String> mainWords;
	private ArrayList<IndexedWord> mainIndexedWords;
	private int index;

	{
		// Register as a resource user - SWTResourceManager will handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	public void spellCheck(ArrayList<String> text)
	{ 
		//edit this to make it highlight the selected word in the arrayList
		//raise a dialogue box with the word to edit
		index = spellChecker.spellCheck(text);
		
		if (index <= 0)
			return;
		else
		{
		  for(int i = 0; i <= index; i++)
			  text.remove(i);
		  spellCheck(text);
		}
		
	}

	public void raiseDialog()
	{
		
	}
	public static void main(String[] args) {
		
		
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		@SuppressWarnings("unused")
		MainWindow inst = new MainWindow(shell, SWT.NULL);
		shell.setLayout(new FillLayout());
		shell.setImage(SWTResourceManager.getImage("images/16x16.png"));
		shell.setText("JRoll Binary Spellchecker");
		shell.setBackgroundImage(SWTResourceManager.getImage("images/ToolbarBackground.gif"));
		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public boolean checkFile(String filename)
	{
		//check if file exists
		File file = new File(filename);
		return file.exists();
	}
	public MainWindow(Composite parent, int style) {

		super(parent, style);
		
		String curDir = System.getProperty("user.dir");
		this.dictFileName = curDir + "\\my_dictionary.txt"; //init with my dictionary file
		
		initGUI();
		
		setPreferences();
		waitCursor = getDisplay().getSystemCursor(SWT.CURSOR_WAIT);
		defaultCursor = getDisplay().getSystemCursor(SWT.CURSOR_ARROW);
		clientArea.setFocus();
		this.setSpellCheckVisibility(false);
	}

	// Load application settings from .ini file
	private void setPreferences() {
		try {
			appSettings.load(new FileInputStream("appsettings.ini"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		// By default, center window
		int width = Integer.parseInt(appSettings.getProperty("width", "640"));
		int height = Integer.parseInt(appSettings.getProperty("height", "480"));
		Rectangle screenBounds = getDisplay().getBounds();
		int defaultTop = (screenBounds.height - height) / 2;
		int defaultLeft = (screenBounds.width - width) / 2;
		int top = Integer.parseInt(appSettings.getProperty("top", String.valueOf(defaultTop)));
		int left = Integer.parseInt(appSettings.getProperty("left", String.valueOf(defaultLeft)));
		getShell().setSize(width, height);
		getShell().setLocation(left, top);
		saveShellBounds();
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		try {
			getShell().addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent evt) {
					shellWidgetDisposed(evt);
				}
			});

			getShell().addControlListener(new ControlAdapter() {
				public void controlResized(ControlEvent evt) {
					shellControlResized(evt);
				}
			});

			getShell().addControlListener(new ControlAdapter() {
				public void controlMoved(ControlEvent evt) {
					shellControlMoved(evt);
				}
			});

			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			{
				clientArea = new Composite(this, SWT.NONE);
				GridData clientAreaLData = new GridData();
				clientAreaLData.grabExcessHorizontalSpace = true;
				clientAreaLData.grabExcessVerticalSpace = true;
				clientAreaLData.horizontalAlignment = GridData.FILL;
				clientAreaLData.verticalAlignment = GridData.FILL;
				clientArea.setLayoutData(clientAreaLData);
				clientArea.setLayout(null);
				{
					spellCheckText = new Text(clientArea,SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
					spellCheckText.setBounds(7, 38, 438, 133);
					//spellCheckText.setTextLimit(100);
					String curDir = System.getProperty("user.dir");
					//spellCheckText.setText(FileUtils.readFileToString(new File(curDir + "\\document.txt")));
				}
				{
					curWord = new Text(clientArea, SWT.NONE);
					curWord.setBounds(16, 177, 200, 21);
				}
				{
					okButton = new Button(clientArea, SWT.PUSH | SWT.CENTER);
					okButton.setText("Change");
					okButton.setBounds(228, 177, 60, 21);
					okButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							okButtonWidgetSelected(evt);
						}
					});
				}
				{
					skipButton = new Button(clientArea, SWT.PUSH | SWT.CENTER);
					skipButton.setText("Skip");
					skipButton.setBounds(16, 204, 60, 21);
					skipButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							skipButtonWidgetSelected(evt);
						}
					});
				}
				{
					addButton = new Button(clientArea, SWT.PUSH | SWT.CENTER);
					addButton.setText("Add Word");
					addButton.setBounds(82, 204, 62, 21);
					addButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							addButtonWidgetSelected(evt);
						}
					});
				}
				{
					cancelButton = new Button(clientArea, SWT.PUSH | SWT.CENTER);
					cancelButton.setText("Cancel");
					cancelButton.setBounds(150, 204, 66, 21);
					cancelButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							cancelButtonWidgetSelected(evt);
						}
					});
				}
				{
					replacePrevButton = new Button(clientArea, SWT.PUSH | SWT.CENTER);
					replacePrevButton.setText("Replace w/ Prev");
					replacePrevButton.setBounds(16, 225, 93, 24);
					replacePrevButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							replacePrevButtonWidgetSelected(evt);
						}
					});
				}
				{
					replaceNextButton = new Button(clientArea, SWT.PUSH | SWT.CENTER);
					replaceNextButton.setText("Replace w/ Next");
					replaceNextButton.setBounds(121, 225, 94, 24);
					replaceNextButton.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							replaceNextButtonWidgetSelected(evt);
						}
					});
				}
				{
					toolBar = new ToolBar(clientArea, SWT.FLAT);
					toolBar.setBackgroundImage(SWTResourceManager.getImage("images/ToolbarBackground.gif"));
					toolBar.setBounds(0, 0, 474, 26);
					{
						newToolItem = new ToolItem(toolBar, SWT.NONE);
						newToolItem.setImage(SWTResourceManager.getImage("images/new.gif"));
						newToolItem.setToolTipText("New");
					}
					{
						openToolItem = new ToolItem(toolBar, SWT.NONE);
						openToolItem.setToolTipText("Open");
						openToolItem.setImage(SWTResourceManager.getImage("images/open.gif"));
						openToolItem.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								try {
									openToolItemWidgetSelected(evt);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					}
					{
						saveToolItem = new ToolItem(toolBar, SWT.NONE);
						saveToolItem.setToolTipText("Save");
						saveToolItem.setImage(SWTResourceManager.getImage("images/save.gif"));
					}
					{
						checkToolItem = new ToolItem(toolBar, SWT.NONE);
						checkToolItem.setToolTipText("SpellCheck");
						checkToolItem.setImage(SWTResourceManager.getImage("images/spellcheck.png"));
						checkToolItem.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								checkToolItemWidgetSelected(evt);
							}
						});
					}
				}
			}
			{
				statusArea = new Composite(this, SWT.NONE);
				GridLayout statusAreaLayout = new GridLayout();
				statusAreaLayout.makeColumnsEqualWidth = true;
				statusAreaLayout.horizontalSpacing = 0;
				statusAreaLayout.marginHeight = 0;
				statusAreaLayout.marginWidth = 0;
				statusAreaLayout.verticalSpacing = 0;
				statusAreaLayout.marginLeft = 3;
				statusAreaLayout.marginRight = 3;
				statusAreaLayout.marginTop = 3;
				statusAreaLayout.marginBottom = 3;
				statusArea.setLayout(statusAreaLayout);
				GridData statusAreaLData = new GridData();
				statusAreaLData.horizontalAlignment = GridData.FILL;
				statusAreaLData.grabExcessHorizontalSpace = true;
				statusArea.setLayoutData(statusAreaLData);
				statusArea.setBackground(SWTResourceManager.getColor(239, 237, 224));
				{
					statusText = new Label(statusArea, SWT.BORDER);
					statusText.setText(" Ready");
					GridData txtStatusLData = new GridData();
					txtStatusLData.horizontalAlignment = GridData.FILL;
					txtStatusLData.grabExcessHorizontalSpace = true;
					txtStatusLData.verticalIndent = 3;
					statusText.setLayoutData(txtStatusLData);
				}
			}
			thisLayout.verticalSpacing = 0;
			thisLayout.marginWidth = 0;
			thisLayout.marginHeight = 0;
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginTop = 3;
			this.setSize(474, 312);
			{
				menu1 = new Menu(getShell(), SWT.BAR);
				getShell().setMenuBar(menu1);
				{
					fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
					fileMenuItem.setText("&File");
					{
						fileMenu = new Menu(fileMenuItem);
						{
							newFileMenuItem = new MenuItem(fileMenu, SWT.PUSH);
							newFileMenuItem.setText("&New");
							newFileMenuItem.setImage(SWTResourceManager.getImage("images/new.gif"));
						}
						{
							openFileMenuItem = new MenuItem(fileMenu, SWT.PUSH);
							openFileMenuItem.setText("&Open");
							openFileMenuItem.setImage(SWTResourceManager.getImage("images/open.gif"));
							openFileMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									try {
										openFileMenuItemWidgetSelected(evt);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
						}
						{
							importDictionaryItem = new MenuItem(fileMenu, SWT.CASCADE);
							importDictionaryItem.setText("Import Dict");
							importDictionaryItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									importDictionaryItemWidgetSelected(evt);
								}
							});
						}
						{
							closeFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							closeFileMenuItem.setText("Close");
						}
						{
							fileMenuSep1 = new MenuItem(fileMenu, SWT.SEPARATOR);
						}
						{
							saveFileMenuItem = new MenuItem(fileMenu, SWT.PUSH);
							saveFileMenuItem.setText("&Save");
							saveFileMenuItem.setImage(SWTResourceManager.getImage("images/save.gif"));
							saveFileMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									try {
										saveFileMenuItemWidgetSelected(evt);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
						}
						{
							fileMenuSep2 = new MenuItem(fileMenu, SWT.SEPARATOR);
						}
						{
							exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							exitMenuItem.setText("E&xit");
							exitMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									exitMenuItemWidgetSelected(evt);
								}
							});
						}
						fileMenuItem.setMenu(fileMenu);
					}
				}
				{
					helpMenuItem = new MenuItem(menu1, SWT.CASCADE);
					helpMenuItem.setText("&Help");
					{
						helpMenu = new Menu(helpMenuItem);
						{
							aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							aboutMenuItem.setText("&About");
							aboutMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									aboutMenuItemWidgetSelected(evt);
								}
							});
						}
						helpMenuItem.setMenu(helpMenu);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void cancelButtonWidgetSelected(SelectionEvent evt) {
		// Go back to top and hide the gui when the cancel button is hit
		this.setSpellCheckVisibility(false);
		spellCheckText.setSelection(0, 0);
	}

	private void exitMenuItemWidgetSelected(SelectionEvent evt) {
		try {
			// Save app settings to file
			appSettings.store(new FileOutputStream("appsettings.ini"), "");
			// if the tree has changed, save to file
			if (spellChecker != null) {
			if ( spellChecker.tree.isChanged()) {
				String curDir = System.getProperty("user.dir");
				String saveFileName = dictFileName;
				spellChecker.saveFile(saveFileName);
			}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		getShell().dispose();
	}


		private void openFileMenuItemWidgetSelected(SelectionEvent evt) throws IOException {
			//open shell, select file
			FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
			String filename = dialog.open();
			if (filename != null) {
				getShell().setText(filename);
			}

			if (filename != null) {
				getShell().setText(filename); // Justin Edit: Maybe I should have created another method for my junk here.
				// Or maybe another class with its own method.

				this.fileName = filename;
				this.spellCheckText.setText(FileUtils.readFileToString(new File(filename)));
			}

		}


	private void openToolItemWidgetSelected(SelectionEvent evt) throws IOException {
		openFileMenuItemWidgetSelected(evt);
	}

	private void aboutMenuItemWidgetSelected(SelectionEvent evt) {
		MessageBox message = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
		message.setText("About Change_This_Title");
		message.setMessage("Change this about message\n\nApplicationName v1.0");
		message.open();
	}

	private void shellWidgetDisposed(DisposeEvent evt) {
		try {
			// Save app settings to file
			appSettings.store(new FileOutputStream("appsettings.ini"), "");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	private void shellControlResized(ControlEvent evt) {
		saveShellBounds();
	}

	// Save window location in appSettings hash table
	private void saveShellBounds() {
		// Save window bounds in app settings
		Rectangle bounds = getShell().getBounds();
		appSettings.setProperty("top", String.valueOf(bounds.y));
		appSettings.setProperty("left", String.valueOf(bounds.x));
		appSettings.setProperty("width", String.valueOf(bounds.width));
		appSettings.setProperty("height", String.valueOf(bounds.height));
	}

	private void shellControlMoved(ControlEvent evt) {
		saveShellBounds();
	}

	@SuppressWarnings("unused")
	private void setStatus(String message) {
		statusText.setText(message);
	}
	
	/*private void checkButtonWidgetSelected(SelectionEvent evt) {
		System.out.println("checkButton.widgetSelected, event="+evt);
		setStatus("Loading dictionary...");
		String curDir = System.getProperty("user.dir");
		fileName = curDir + "\\full_dictionary.txt";
		if (checkFile(fileName)) {
			System.out.println("file exists");
			spellChecker = new SpellChecker(new File(fileName));
		}
		else {
			System.out.println("file does not exist");
			spellChecker = new SpellChecker(new File(fileName));
		}
		String[] windowText = spellCheckText.getText().split(" ");
		
		mainWords = new ArrayList<String>(Arrays.asList(windowText));
		
		
		cycleNew(mainWords);
		
	}*/
	
	public ArrayList<String> cycle (ArrayList<String> words)
	{
		//old cycle method before I refined the code more, no longer in use
		index = spellChecker.spellCheck(words);
		
		if (index == -1)
		{
			this.setStatus("Done checking.");
			return null;
		}
		else {
			this.setSpellCheckVisibility(true);
			this.curWord.setText(words.get(index));
			words.remove(index);
			//use regex
			//create a class with ArrayList
			spellCheckText.setSelection(spellCheckText.getText().indexOf(curWord.getText()), 
					spellCheckText.getText().indexOf(curWord.getText()) + curWord.getText().length());
			//System.out.println(curWord.getText().length());
			//column += words.get(index).length() + 1;
			//spellCheckText.get
			//cycle(words);
			
		}
		return words;
	}
	
	public ArrayList<IndexedWord> cycleNew (ArrayList<IndexedWord> words)
	{
		//cycle through the words and spellcheck them.
		//put in a shorter ArrayList each time.
		index = spellChecker.spellCheckNew(words);
		
		if (index == -1)
		{
			this.setStatus("Done checking.");
			this.setSpellCheckVisibility(false);
			return null;
		}
		else {
			this.setSpellCheckVisibility(true);
			IndexedWord currentWord = words.get(index);
			System.out.println("Index" + index + " word " + words.get(index).data);
			this.curWord.setText(currentWord.getData());
			//removeEntries(words, index);
			//use regex
			//create a class with ArrayList
			spellCheckText.setSelection(currentWord.startIndex, currentWord.endIndex);
			//System.out.println(curWord.getText().length());
			//column += words.get(index).length() + 1;
			//spellCheckText.get
			//cycle(words);
			
		}
		return words;
	}
	
	public void removeEntries(ArrayList<IndexedWord> words, int index)
	{
		int i;
		for (i = 0; i < index; i++)
			words.remove(i);
		this.index = this.index - i;
	}
	
	
	
	private void okButtonWidgetSelected(SelectionEvent evt) {
		//replace the word in the window when the user wants to change it
		System.out.println(index);
		changedReplace(mainIndexedWords.get(index));
		//things to do in this widget:
		//replace current word in the textbox
	}
	
	private void skipButtonWidgetSelected(SelectionEvent evt) {
		//remove the current word from the arraylist and go to the next
		this.mainIndexedWords.remove(index);
		cycleNew(mainIndexedWords);
		//TODO add your code for skipButton.widgetSelected
	}
	
	private void addButtonWidgetSelected(SelectionEvent evt) {
		spellChecker.tree.setChanged(true); //note that binary tree has changed so we will save the dictionary
		spellChecker.insertWord(curWord.getText());
		this.cycleNew(mainIndexedWords);
		//TODO add your code for addButton.widgetSelected
	}
	
	private void replacePrevButtonWidgetSelected(SelectionEvent evt) {
		//search for prev word in dictionary
		curWord.setText(spellChecker.searchPrev(curWord.getText()));
		//TODO add your code for replacePrevButton.widgetSelected
	}
	
	private void replaceNextButtonWidgetSelected(SelectionEvent evt) {
		//search for next word in dictionary
		curWord.setText(spellChecker.searchNext(curWord.getText()));
		//TODO add your code for replaceNextButton.widgetSelected
	}
	
	private void saveFileMenuItemWidgetSelected(SelectionEvent evt) throws IOException {
		System.out.println("saveFileMenuItem.widgetSelected, event="+evt);
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		String filename = dialog.open();
		
		if (filename != null) {
		File newFile = new File(filename);
		FileWriter fWrite = new FileWriter(newFile);
		fWrite.write(spellCheckText.getText());
		fWrite.close();
		}
		//TODO add your code for saveFileMenuItem.widgetSelected
		}
	
	private void checkToolItemWidgetSelected(SelectionEvent evt) {
		System.out.println("checkToolItem.widgetSelected, event="+evt);
		setStatus("Loading dictionary...");
		String curDir = System.getProperty("user.dir");
		if (checkFile(dictFileName)) {
			System.out.println("file exists");
			spellChecker = new SpellChecker(new File(dictFileName));
		}
		else {
			System.out.println("file does not exist");
			this.importDictionaryItemWidgetSelected(null);
			spellChecker = new SpellChecker(new File(dictFileName));
		}
		
		setStatus("dictionary loaded");
		//String[] windowText = spellCheckText.getText().split("(([ .,\"?!\n\r\n])|(--))+");
		
		//when I split up words, I need to associate the words in the 						
		//mainWords = new ArrayList<String>(Arrays.asList(windowText));
		//cycle(mainWords);
		mainIndexedWords = new ArrayList<IndexedWord>();
		indexTest(mainIndexedWords, 0);
		cycleNew(mainIndexedWords);
	}
	
	private void indexTest(ArrayList<IndexedWord> indexedWords, int startIndex) {
	       String regex="\\w*";
	        Pattern p = Pattern.compile(regex);
	        
	            //System.out.println("Text is : " + spellCheckText.getText());
	            Matcher matcher = p.matcher(spellCheckText.getText());
	            while (matcher.find()) {
	                System.out.println("Starting & ending  index of" + matcher.group()+ ":=" +
	             "start=" + matcher.start() + " end = " + matcher.end());
	                if (matcher.start() >= startIndex)
	                	indexedWords.add(new IndexedWord(matcher.start(), matcher.end(), matcher.group()	));
	            }
	        }
	
	public void changedReplace(IndexedWord word)
	{
		int startIndex = word.getStartIndex();
		String tempWindowText = spellCheckText.getText();
		StringBuffer buf = new StringBuffer(spellCheckText.getText());
		buf.delete(word.getStartIndex(), word.getEndIndex());
		buf.insert(word.getStartIndex(), curWord.getText() );
		System.out.println("Trying replace for " + word.data);
		spellCheckText.setText(buf.toString());
		this.mainIndexedWords = new ArrayList<IndexedWord>();
		indexTest(mainIndexedWords,startIndex);
		cycleNew(mainIndexedWords);
	}
	
	private void setSpellCheckVisibility(boolean opt)
	{
		curWord.setVisible(opt);
		okButton.setVisible(opt);
		skipButton.setVisible(opt);
		addButton.setVisible(opt);
		cancelButton.setVisible(opt);
		replacePrevButton.setVisible(opt);
		replaceNextButton.setVisible(opt);
	}
	
	private void importDictionaryItemWidgetSelected(SelectionEvent evt) {
		//System.out.println("importDictionaryItem.widgetSelected, event="+evt);
		FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		String filename = dialog.open();
		if (filename != null) {
			getShell().setText(filename);
		}

		if (filename != null) {
			getShell().setText(filename); // Justin Edit: Maybe I should have created another method for my junk here.
			// Or maybe another class with its own method.

			this.dictFileName = filename;

	}
	}
}

