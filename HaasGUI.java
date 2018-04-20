import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.*;

public class HaasGUI extends JPanel implements FocusListener, ActionListener {

	public static void main(String[] args) { //main subroutine, runs when the program is executed
		JFrame window = new JFrame("Data Validation GUI");
		window.setContentPane(new HaasGUI());
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocation(200,200); //move window closer to middle of screen
		window.setMinimumSize(window.getSize()); //make sure the user can't resize the window too small to be usable
		window.setVisible(true);
	}

	JLabel minValueErrorLabel, maxValueErrorLabel, wordErrorLabel; //Labels used for error validation
	JTextField minValueField, maxValueField, wordField; //entry fields
	boolean minValueFieldTouched = false, maxValueFieldTouched = false, wordFieldTouched = false; //keep track of whether the user has interacted with the fields for error handling
	JRadioButton trueOption, falseOption; //radio buttons for the two options

	public HaasGUI() {
		JLabel minValueLabel = new JLabel("Min Value");
		JLabel maxValueLabel = new JLabel("Max Value");
		JLabel wordLabel = new JLabel("Word");	
		JLabel optionLabel = new JLabel("Option");

		minValueErrorLabel = new JLabel(" "); //these are set to " " because it allows them to preserve their space in the layout, avoiding annoying repositioning of UI elements
		minValueErrorLabel.setForeground(Color.RED);

		maxValueErrorLabel = new JLabel(" ");
		maxValueErrorLabel.setForeground(Color.RED);

		wordErrorLabel = new JLabel(" ");
		wordErrorLabel.setForeground(Color.RED);

		minValueField = new JTextField(24); //initialize fields with 24 column width
		minValueField.setName("minValueField");
		maxValueField = new JTextField(24);
		maxValueField.setName("maxValueField");
		wordField = new JTextField(24);
		wordField.setName("wordField");
		
		minValueField.addFocusListener(this);
		maxValueField.addFocusListener(this);
		wordField.addFocusListener(this);

		ButtonGroup optionGroup = new ButtonGroup(); //create a button group for the two radio buttons
		trueOption = new JRadioButton("True");
		falseOption = new JRadioButton("False");
		trueOption.setSelected(true); //default selection
		optionGroup.add(trueOption); //add the radio buttons to the group
		optionGroup.add(falseOption);

		JButton okButton = new JButton("OK");
		okButton.setName("ok");
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setName("cancel");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		GridBagLayout layout = new GridBagLayout(); //Generate a GridBagLayout to arrange the GUI elements
		GridBagConstraints c = new GridBagConstraints(); //a GridBagConstraints object for placing the GUI elements

		setBorder(BorderFactory.createEmptyBorder(20,20,20,20)); //create a border of padding around the GUI
		setLayout(layout); //set this JPanel's layout to the GridBagLayout we just created

		
		//Set our constant constraint properties here that will apply to all GUI elements
		c.ipadx = 10; //create some padding so that there is space between components
		c.ipady = 10;

		//Place the min value label and button
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_END; //align labels right
		c.insets = new Insets(5,0,5,5);
		add(minValueLabel, c);

		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5,0,5,0);
		add(minValueField, c);

		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0,0,0,0);
		add(minValueErrorLabel, c);

		//Place the max value label and button
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(5,0,5,5);
		add(maxValueLabel, c);

		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5,0,5,0);
		add(maxValueField, c);

		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0,0,0,0); 
		add(maxValueErrorLabel, c);

		//Place the word label and button
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(5,0,5,5);
		add(wordLabel, c);

		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(wordField, c);

		c.gridx = 1;
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0,0,0,0);
		add(wordErrorLabel, c);

		//Place the options label and radio buttons
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2; //this component spans two columns
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.LINE_START; //left align
		add(optionLabel, c);

		JPanel optionsPanel = new JPanel(); //create a separate panel for the two options so that they align nicely
		optionsPanel.setLayout(new GridLayout(1,2,0,0));
		optionsPanel.add(trueOption);
		optionsPanel.add(falseOption);
		
		c.gridy = 7;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.CENTER;
		add(optionsPanel, c);

		//Place the OK, Cancel buttons

		JPanel buttonPanel = new JPanel(); //create a separate panel for the two buttons so that they align nicely
		buttonPanel.setLayout(new GridLayout(1,2,3,10));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		add(buttonPanel, c);

	}

	public void focusGained(FocusEvent e) { //no need to implement anything here

	}

	public void focusLost(FocusEvent e) { //focusLost events on the entry fields trigger error validation
		String componentName = e.getComponent().getName();
		String componentText = ((JTextField) e.getComponent()).getText();

		switch (componentName) {
			case "minValueField":
				minValueFieldTouched = true;
				break;
			case "maxValueField":
				maxValueFieldTouched = true;
				break;
			case "wordField":
				wordFieldTouched = true;
				break;
		}
		validateFields();


	}

	public void actionPerformed(ActionEvent e) { //triggered when user presses OK or Cancel
		String buttonName = ((JButton) e.getSource()).getName(); //find out which button was pressed
		switch (buttonName) {
			case "ok":
				saveData(); //if they pressed ok, proceed with saveData subroutine
				break;
			case "cancel":
				cancel(); //if they pressed cancel, proceed with the cancel subroutine
				break;
		}
	}

	private boolean validateFields() {

		boolean minValueIsNumber = false; //Criteria for data validation
		boolean maxValueIsNumber = false; 
		boolean maxValueGreaterThanOrEqualToZero = false;
		boolean maxGreaterThanMin = false;
		boolean wordIsNotBlank = false; //I assume we want a required input into the word field. If it is not required, this criteria should be removed
		boolean wordHasNoDigits = false;

		double minValue = 0.0, maxValue = 0.0;
		try {
			minValue = Double.parseDouble(minValueField.getText());
			minValueIsNumber = true; //if the parse fails, then this line will not occur
		}
		catch (NumberFormatException e) {
			minValueIsNumber = false;
		}

		try {
			maxValue = Double.parseDouble(maxValueField.getText());
			maxValueIsNumber = true;
		}
		catch (NumberFormatException e) {
			maxValueIsNumber = false;
		}

		maxValueGreaterThanOrEqualToZero = maxValue >= 0.0;
		maxGreaterThanMin = maxValue > minValue;

		String wordText = wordField.getText();
		wordIsNotBlank = !wordText.isEmpty(); 
		wordHasNoDigits = !wordText.matches(".*\\d+.*"); //This regex returns true if there are any numbers in the string

		boolean minValueValid = true, maxValueValid = true, wordValid = true;
		String minValueErrorMessage = "", maxValueErrorMessage = "", wordErrorMessage = "";
		if (!minValueIsNumber) {
			minValueValid = false;
			minValueErrorMessage = "Please enter a number.";
		}
		if (!maxValueIsNumber) {
			maxValueValid = false;
			maxValueErrorMessage = "Please enter a number.";
		}
		if (maxValueIsNumber && minValueIsNumber && !maxGreaterThanMin) { //only check this if both fields are valid numbers
			maxValueValid = false;
			maxValueErrorMessage = "Max value must be greater than Min value.";
		}
		if (maxValueIsNumber && !maxValueGreaterThanOrEqualToZero) { //This condition overrides previous condition, this is intentional. This error should have precedence
			maxValueValid = false;
			maxValueErrorMessage = "Max value must be greater than or equal to zero.";	
		}
		if (!wordHasNoDigits) {
			wordValid = false;
			wordErrorMessage = "Word cannot have any numbers.";
		}
		if (!wordIsNotBlank) { //This condition overrides previous condition, this is intentional. This error should have precedence
			wordValid = false;
			wordErrorMessage = "Please enter a word.";
		}

		minValueValid = minValueValid || !minValueFieldTouched; //these check if the user has interacted with the field yet
		maxValueValid = maxValueValid || !maxValueFieldTouched; //we don't want to generate errors on untouched fields, that would be annoying to the user
		wordValid = wordValid || !wordFieldTouched;

		
		minValueErrorLabel.setText(minValueValid ? " " : minValueErrorMessage); //if the field is valid, set the text to " "; otherwise, set it to error message
		maxValueErrorLabel.setText(maxValueValid ? " " : maxValueErrorMessage); //the " " is a bit of a hack, but it solves the problem of the whole UI moving around
		wordErrorLabel.setText(wordValid ? " " : wordErrorMessage);             //if you simply set the field visible property to false

		JFrame parentWindow = (JFrame) SwingUtilities.getWindowAncestor(this);
		Dimension oldMinimumSize = parentWindow.getMinimumSize();
		parentWindow.setMinimumSize(parentWindow.getSize()); //make sure we don't resize the window if the user manually expanded it
		parentWindow.pack(); //repack the window to accomodate any error messages properly
		parentWindow.setMinimumSize(oldMinimumSize); //reset the old minimum size again

		return minValueValid && maxValueValid && wordValid; //return true if all fields are valid or untouched
	}	

	private void saveData() {
		minValueFieldTouched = true; //set all fields to dirty, since the user wants to save
		maxValueFieldTouched = true; //by doing that, we can properly validate all fields
		wordFieldTouched = true;
		boolean dataValidated = validateFields(); //validate fields before saving

		if (dataValidated) { //only save if the data passes validation checks
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter fileTypeFilter = new FileNameExtensionFilter("CSV Files", "csv"); //File chooser set to save to csv
			fc.setFileFilter(fileTypeFilter);

			int returnValue = fc.showSaveDialog(this);
			File selectedFile = null;
			if (returnValue == JFileChooser.APPROVE_OPTION) { //if the user approved the file save operation

				selectedFile = fc.getSelectedFile();
				if (!selectedFile.getAbsolutePath().endsWith(".csv")) {
					selectedFile = new File(selectedFile.getAbsolutePath()+".csv"); //JFileChooser doesn't automatically add the file extension, so I do it here if the user didn't
				}

				if (selectedFile != null) { //check if the file object actually points to a file
					try {
						FileWriter fileWriter = new FileWriter(selectedFile); //initialize a FileWriter to write to the CSV File

						fileWriter.append("\"Word\",");
						String wordString = wordField.getText();
						wordString = wordString.replace("\"", "\"\""); //If there are quotes in the word, replace them with double quotes as per CSV Standards
						wordString = "\""+wordString+"\""; //surround the word in quotes
						fileWriter.append(wordString);
						fileWriter.append("\n"); //new line at the end of the first data row

						fileWriter.append("\"Min\",");
						String minValueString = ""+Double.parseDouble(minValueField.getText()); //this concatenation allows the string to look like a double, even if the user enters an integer
						fileWriter.append(minValueString); //no need to quote this since it should be a number
						fileWriter.append("\n"); //new line at the end of the second data row

						fileWriter.append("\"Max\",");
						String maxValueString = ""+Double.parseDouble(maxValueField.getText()); //this concatenation allows the string to look like a double, even if the user enters an integer
						fileWriter.append(maxValueString); 
						fileWriter.append("\n"); //new line at the end of the third data row

						String optionValueString = trueOption.isSelected() ? "TRUE" : "FALSE"; //if the True radio button is clicked, this equals TRUE, otherwise FALSE
						fileWriter.append("\"Option\",");
						fileWriter.append(optionValueString); 
						fileWriter.append("\n"); //new line at the end of the fourth data row

						fileWriter.flush(); //properly flush and close the stream to prevent data corruption
						fileWriter.close();

						JOptionPane.showMessageDialog(this, "Successfully saved file.", "Success", JOptionPane.INFORMATION_MESSAGE); //dialog to indicate success
					}
					catch (IOException ioe) {
						JOptionPane.showMessageDialog(this, "Error saving file. Error: "+ioe.toString(), "File error", JOptionPane.ERROR_MESSAGE); //if there is an unexpected error, tell the user
					}
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Please correct mistakes before saving.", "Validation error", JOptionPane.WARNING_MESSAGE); //pop up a dialog if fields fail validation
		}
	}

	private void cancel() {
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm exit", JOptionPane.YES_NO_OPTION); //display a confirmation dialog
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0); //if user picks yes, exit
		}
	}
}

