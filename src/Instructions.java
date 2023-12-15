import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Instructions {

	private JFrame frameInstructions;

	/**
	 * Create the application.
	 */
	public Instructions() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameInstructions = new JFrame();
		frameInstructions.setResizable(false);
		frameInstructions.setBounds(100, 100, 450, 636);
		frameInstructions.setVisible(true);
		frameInstructions.getContentPane().setLayout(null);
		
		JLabel instruction = new JLabel("New label");
		instruction.setIcon(new ImageIcon("images/게임설명.jpg"));
		
		instruction.setBounds(0, 0, 434, 597);
		frameInstructions.getContentPane().add(instruction);
	}
}
