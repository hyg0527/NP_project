import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ClientMain {

	private JFrame frmYachtDiceServer;
	private JTextField textFieldName;
	private JTextField textFieldAddress;
	private JTextField textFieldPort;

	private JButton btnEnter;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientMain window = new ClientMain();
					window.frmYachtDiceServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public ClientMain() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmYachtDiceServer = new JFrame();
		frmYachtDiceServer.setTitle("YACHT DICE LAUNCHER");
		frmYachtDiceServer.setBounds(100, 100, 300, 350);
		frmYachtDiceServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmYachtDiceServer.getContentPane().setLayout(null);
		
		textFieldName = new JTextField();
		textFieldName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldName.setBounds(77, 136, 168, 21);
		frmYachtDiceServer.getContentPane().add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAddress.setText("127.0.0.1");
		textFieldAddress.setColumns(10);
		textFieldAddress.setBounds(77, 179, 168, 21);
		frmYachtDiceServer.getContentPane().add(textFieldAddress);
		
		textFieldPort = new JTextField();
		textFieldPort.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPort.setText("25000");
		textFieldPort.setColumns(10);
		textFieldPort.setBounds(77, 223, 168, 21);
		frmYachtDiceServer.getContentPane().add(textFieldPort);
		
		JLabel lblName = new JLabel("이름");
		lblName.setBounds(32, 139, 33, 15);
		frmYachtDiceServer.getContentPane().add(lblName);
		
		JLabel lbladdress = new JLabel("주소");
		lbladdress.setBounds(32, 182, 33, 15);
		frmYachtDiceServer.getContentPane().add(lbladdress);
		
		JLabel lblPort = new JLabel("포트 번호");
		lblPort.setBounds(12, 226, 57, 15);
		frmYachtDiceServer.getContentPane().add(lblPort);
		
		btnEnter = new JButton("입장하기");
		btnEnter.setBounds(88, 266, 97, 23);
		frmYachtDiceServer.getContentPane().add(btnEnter);
		

		MyActionListener actionListener = new MyActionListener();
		btnEnter.addActionListener(actionListener);


		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("images/title.png"));
		lblNewLabel.setBounds(12, 10, 260, 105);
		frmYachtDiceServer.getContentPane().add(lblNewLabel);
		
	}
	class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String userName = textFieldName.getText().trim();
			String ipAddr = textFieldAddress.getText().trim();
			String portNum = textFieldPort.getText().trim();
			
			new Game(userName, ipAddr, portNum);
			frmYachtDiceServer.setVisible(false);
		}
	}

}
