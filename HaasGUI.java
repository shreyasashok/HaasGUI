import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.event.*;

public class HaasGUI extends JPanel implements FocusListener {

	public static void main(String[] args) {
		System.out.println("Test output");
		JFrame window = new JFrame("Data Validation GUI");
		window.setContentPane(new HaasGUI());
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	JLabel minValueErrorLabel, maxValueErrorLabel, wordErrorLabel; //Labels used for error validation

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

		JTextField minValueField = new JTextField(10); //initialize fields with 10 column width
		minValueField.setName("minValueField");
		JTextField maxValueField = new JTextField(10);
		maxValueField.setName("maxValueField");
		JTextField wordField = new JTextField(10);
		wordField.setName("wordField");
		
		minValueField.addFocusListener(this);
		maxValueField.addFocusListener(this);
		wordField.addFocusListener(this);

		ButtonGroup optionGroup = new ButtonGroup(); //create a button group for the two radio buttons
		JRadioButton trueOption = new JRadioButton("True");
		JRadioButton falseOption = new JRadioButton("False");
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
		c.anchor = GridBagConstraints.CENTER;
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
		c.anchor = GridBagConstraints.CENTER;
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
		c.anchor = GridBagConstraints.CENTER;
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
		System.out.println("Focus lost on "+componentName);
		switch (componentName) {
			case "minValueField":
				minValueErrorLabel.setVisible(true);
				break;
			case "maxValueField":
				maxValueErrorLabel.setVisible(true);
				break;
			case "wordField":
				wordErrorLabel.setVisible(true);
				break;
		}
		JFrame parentWindow = (JFrame) SwingUtilities.getWindowAncestor(this);
		parentWindow.pack(); //repack the window to accomodate any error messages properly

	}
}

