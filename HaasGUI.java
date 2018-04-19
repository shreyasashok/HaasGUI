import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;

public class HaasGUI extends JPanel implements FocusListener {

	public static void main(String[] args) {
		System.out.println("Test output");
		JFrame window = new JFrame("Data Validation GUI");
		window.setContentPane(new HaasGUI());
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocation(200,200); //move window closer to middle of screen
		window.setResizable(false);
		window.setVisible(true);
	}

	JLabel minValueErrorLabel, maxValueErrorLabel, wordErrorLabel; //Labels used for error validation
	JTextField minValueField, maxValueField, wordField;
	boolean minValueFieldTouched = false, maxValueFieldTouched = false, wordFieldTouched = false;
	JRadioButton trueOption, falseOption;

	public HaasGUI() {
		JLabel minValueLabel = new JLabel("Min Value");
		JLabel maxValueLabel = new JLabel("Max Value");
		JLabel wordLabel = new JLabel("Word");	
		JLabel optionLabel = new JLabel("Option");

		minValueErrorLabel = new JLabel("Error!!!");
		minValueErrorLabel.setForeground(Color.RED);

		maxValueErrorLabel = new JLabel("Error!!!");
		maxValueErrorLabel.setForeground(Color.RED);

		wordErrorLabel = new JLabel("Error!!!");
		wordErrorLabel.setForeground(Color.RED);

		minValueField = new JTextField(10); //initialize fields with 10 column width
		minValueField.setName("minValueField");
		maxValueField = new JTextField(10);
		maxValueField.setName("maxValueField");
		wordField = new JTextField(10);
		wordField.setName("wordField");
		
		minValueField.addFocusListener(this);
		maxValueField.addFocusListener(this);
		wordField.addFocusListener(this);

		ButtonGroup optionGroup = new ButtonGroup(); //create a button group for the two radio buttons
		trueOption = new JRadioButton("True");
		falseOption = new JRadioButton("False");
		optionGroup.add(trueOption); //add the radio buttons to the group
		optionGroup.add(falseOption);


		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");

		GridBagLayout layout = new GridBagLayout(); //Generate a GridBagLayout to arrange the GUI elements
		GridBagConstraints c = new GridBagConstraints(); //a GridBagConstraints object for placing the GUI elements

		setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); //create a border of padding around the GUI
		setLayout(layout); //set this JPanel's layout to the GridBagLayout we just created

		//Set our constant constraint properties here that will apply to all GUI elements
		c.ipadx = 10; //create some padding so that there is space between components
		c.ipady = 10;

		//Place the min value label and button
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_END; //align labels right
		add(minValueLabel, c);

		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(minValueField, c);

		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(minValueErrorLabel, c);
		minValueErrorLabel.setVisible(false);

		//Place the max value label and button
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_END;
		add(maxValueLabel, c);

		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(maxValueField, c);

		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_START;
		add(maxValueErrorLabel, c);
		maxValueErrorLabel.setVisible(false);

		//Place the word label and button
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.LINE_END;
		add(wordLabel, c);

		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(wordField, c);

		c.gridx = 1;
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_START;
		add(wordErrorLabel, c);
		wordErrorLabel.setVisible(false);

		//Place the options label and radio buttons
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2; //this component spans two columns
		c.anchor = GridBagConstraints.LINE_START; //left align
		add(optionLabel, c);

		c.gridy = 7;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(trueOption, c);

		c.gridx = 1;
		add(falseOption, c);

		//Place the OK, Cancel buttons
		c.gridx = 0;
		c.gridy = 8;
		c.anchor = GridBagConstraints.CENTER;
		add(okButton, c);

		c.gridx = 1;
		add(cancelButton, c);

	}

	private class MinMaxInputVerifier extends InputVerifier {
		public boolean verify(JComponent input) {
			JTextField inputField = (JTextField) input;
			return "test".equals(inputField.getText());
		}
	}


	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {
		String componentName = e.getComponent().getName();
		String componentText = ((JTextField) e.getComponent()).getText();

		boolean valid = true;
		double value = 0.0;
		String errorMessage = "";
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

		JFrame parentWindow = (JFrame) SwingUtilities.getWindowAncestor(this);
		parentWindow.pack(); //repack the window to accomodate any error messages properly

	}

	private boolean validateFields() {

		boolean minValueIsNumber = false; //Criteria for data validation
		boolean maxValueIsNumber = false; 
		boolean maxValueGreaterThanOrEqualToZero = false;
		boolean maxGreaterThanMin = false;
		boolean wordIsNotBlank = false;
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
		if (maxValueIsNumber && minValueIsNumber && !maxGreaterThanMin) {
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

		minValueErrorLabel.setText(minValueErrorMessage); //set our error messages and visibility
		minValueErrorLabel.setVisible(!minValueValid);
		maxValueErrorLabel.setText(maxValueErrorMessage);
		maxValueErrorLabel.setVisible(!maxValueValid);
		wordErrorLabel.setText(wordErrorMessage);
		wordErrorLabel.setVisible(!wordValid);

		return minValueValid && maxValueValid && wordValid; //return true if all fields are valid or untouched
	}	
}

