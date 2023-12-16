import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class Game {

	private JFrame frmYachtDice;
	private JTextField txtInput;
	private String UserName, ip, port;
	private boolean isRunning = false;
	
	private JButton sendButton;
	private JTextArea textArea;
	private JLabel player1_wait;
	private JLabel player2_wait;
	private JButton gameStart;
	private JLabel txtGameStatus;
    private JButton showInstructions;
	
	public JLabel dice1, dice2, dice3, dice4, dice5;
	public JLabel dice1_result, dice2_result, dice3_result, dice4_result, dice5_result;
	public JLabel recordScore;
	public JButton btnRollDice, resetButton, btnRecordScore;
	
	private RollDice rollDice;
	
    private Socket socket; // 연결소켓
    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private DataOutputStream dos;

	/**
	 * Create the application.
	 */
	public Game(String name, String ip, String port) {
		initialize();

		AppendText(name + "님이 입장하였습니다.\nip: " + ip + " , port: " + port + "\n");
		UserName = name;
		this.ip = ip;
		this.port = port;
		 
		rollDice = RollDice.getInstance();
       	rollDice.setDice(dice1, dice2, dice3, dice4, dice5, dice1_result, dice2_result, dice3_result, dice4_result, dice5_result);
       	rollDice.setButton(btnRollDice, resetButton, btnRecordScore);
       	rollDice.setPanel(recordScore);
       	
		 try {
	            socket = new Socket(ip, Integer.parseInt(port));
	            is = socket.getInputStream();
	            dis = new DataInputStream(is);
	            os = socket.getOutputStream();
	            dos = new DataOutputStream(os);

	            SendMessage("/login " + UserName);
	            ListenNetwork net = new ListenNetwork();
	            net.start();
	            MyactionListener action = new MyactionListener();
	            sendButton.addActionListener(action); // 내부클래스로 액션 리스너를 상속받은 클래스로
	            txtInput.addActionListener(action);
	            txtInput.requestFocus();
	        } catch (NumberFormatException | IOException e) {
	            e.printStackTrace();
	            AppendText("connect error");
	        }

	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmYachtDice = new JFrame();
		frmYachtDice.setTitle("Yacht Dice");
		frmYachtDice.setResizable(false);
		frmYachtDice.getContentPane().setBackground(new Color(106, 187, 68));
		frmYachtDice.getContentPane().setForeground(new Color(0, 0, 0));
		frmYachtDice.setBounds(100, 100, 900, 600);
		frmYachtDice.setVisible(true);
		frmYachtDice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmYachtDice.getContentPane().setLayout(null);
		
		JPanel scoreboard = new JPanel();
		scoreboard.setBounds(12, 10, 256, 540);
		frmYachtDice.getContentPane().add(scoreboard);
		scoreboard.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("images/점수판2_.jpg"));
		lblNewLabel.setBounds(0, 0, 252, 541);
		scoreboard.add(lblNewLabel);
		
		recordScore = new JLabel("0");
		recordScore.setHorizontalAlignment(SwingConstants.CENTER);
		recordScore.setFont(new Font("굴림", Font.PLAIN, 13));
		recordScore.setBounds(135, 504, 51, 26);
		scoreboard.add(recordScore);
		lblNewLabel.setComponentZOrder(recordScore, 0);
		
		JPanel info = new JPanel();
		info.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info.setBackground(new Color(192, 192, 192));
		info.setBounds(621, 10, 251, 541);
		frmYachtDice.getContentPane().add(info);
		info.setLayout(null);
		
		JPanel chatting = new JPanel();
		chatting.setBounds(12, 86, 227, 325);
		info.add(chatting);
		chatting.setLayout(null);
		
		txtInput = new JTextField();
		txtInput.setBounds(9, 293, 153, 23);
		chatting.add(txtInput);
		txtInput.setColumns(10);
		
		sendButton = new JButton("전송");
		sendButton.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		sendButton.setBounds(163, 292, 59, 25);
		chatting.add(sendButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(9, 10, 213, 273);
		chatting.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JPanel user = new JPanel();
		user.setBounds(12, 10, 227, 66);
		info.add(user);
		user.setLayout(null);
		
		JLabel player1 = new JLabel("PLAYER 1");
		player1.setBounds(36, 10, 57, 15);
		user.add(player1);
		
		JLabel player2 = new JLabel("PLAYER 2");
		player2.setBounds(134, 10, 57, 15);
		user.add(player2);
		
		player1_wait = new JLabel("대기 중...");
		player1_wait.setBounds(36, 41, 57, 15);
		user.add(player1_wait);
		
		player2_wait = new JLabel("대기 중...");
		player2_wait.setBounds(134, 41, 57, 15);
		user.add(player2_wait);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(12, 421, 227, 110);
		info.add(panel_2);
		panel_2.setLayout(null);
		
		gameStart = new JButton("게임 시작");
		gameStart.setEnabled(false);
		gameStart.setBounds(12, 77, 97, 23);
		panel_2.add(gameStart);
		
		showInstructions = new JButton("게임 설명");
		showInstructions.setBounds(121, 77, 97, 23);
		panel_2.add(showInstructions);
		
		txtGameStatus = new JLabel("게임 시작 대기중입니다...");
		txtGameStatus.setFont(new Font("굴림", Font.PLAIN, 15));
		txtGameStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtGameStatus.setBounds(15, 10, 203, 57);
		panel_2.add(txtGameStatus);
		
		JPanel dice_panel = new JPanel();
		dice_panel.setBackground(new Color(48, 48, 48));
		dice_panel.setBounds(284, 10, 325, 72);
		frmYachtDice.getContentPane().add(dice_panel);
		dice_panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 10, 50, 50);
		dice_panel.add(panel_1);
		panel_1.setLayout(null);
		
		dice1_result = new JLabel("");
		dice1_result.setBounds(0, 0, 50, 50);
		panel_1.add(dice1_result);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(74, 10, 50, 50);
		dice_panel.add(panel_1_1);
		panel_1_1.setLayout(null);
		
		dice2_result = new JLabel("");
		dice2_result.setBounds(0, 0, 50, 50);
		panel_1_1.add(dice2_result);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setBounds(136, 10, 50, 50);
		dice_panel.add(panel_1_2);
		panel_1_2.setLayout(null);
		
		dice3_result = new JLabel("");
		dice3_result.setBounds(0, 0, 50, 50);
		panel_1_2.add(dice3_result);
		
		JPanel panel_1_3 = new JPanel();
		panel_1_3.setBounds(198, 10, 50, 50);
		dice_panel.add(panel_1_3);
		panel_1_3.setLayout(null);
		
		dice4_result = new JLabel("");
		dice4_result.setBounds(0, 0, 50, 50);
		panel_1_3.add(dice4_result);
		
		JPanel panel_1_4 = new JPanel();
		panel_1_4.setBounds(260, 10, 50, 50);
		dice_panel.add(panel_1_4);
		panel_1_4.setLayout(null);
		
		dice5_result = new JLabel("");
		dice5_result.setBounds(0, 0, 50, 50);
		panel_1_4.add(dice5_result);
		
		btnRollDice = new JButton("주사위 굴리기!!");
		btnRollDice.setEnabled(false);
		btnRollDice.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		btnRollDice.setBounds(370, 497, 139, 39);
		frmYachtDice.getContentPane().add(btnRollDice);
		
		JPanel roll = new JPanel();
		roll.setBackground(new Color(48, 48, 48));
		roll.setBounds(284, 92, 325, 336);
		frmYachtDice.getContentPane().add(roll);
		roll.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(170, 34, 34));
		panel.setBounds(28, 30, 268, 277);
		roll.add(panel);
		panel.setLayout(null);
		
		dice1 = new JLabel("");
		dice1.setBounds(25, 89, 50, 50);
		panel.add(dice1);
		
		dice2 = new JLabel("");
		dice2.setBounds(106, 89, 50, 50);
		panel.add(dice2);
		
		dice3 = new JLabel("");
		dice3.setBounds(190, 89, 50, 50);
		panel.add(dice3);
		
		dice4 = new JLabel("");
		dice4.setBounds(62, 149, 50, 50);
		panel.add(dice4);
		
		dice5 = new JLabel("");
		dice5.setBounds(150, 149, 50, 50);
		panel.add(dice5);
		
		resetButton = new JButton("완료");
		resetButton.setEnabled(false);
		resetButton.setBounds(280, 503, 78, 30);
		frmYachtDice.getContentPane().add(resetButton);
		
		btnRecordScore = new JButton("점수기록");
		btnRecordScore.setEnabled(false);
		btnRecordScore.setFont(new Font("굴림", Font.PLAIN, 11));
		btnRecordScore.setBounds(531, 504, 78, 30);
		frmYachtDice.getContentPane().add(btnRecordScore);
		
		findActionListener();
	}
	
	public void findActionListener() {
		gameStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendMessage("/startGame"); // 게임을 시작한다고 서버에 알리기
				
				resetButton.setEnabled(true);
			}
		});
		
		showInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Instructions(); // 게임 방법 설명창 띄우기
			}
		});
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendMessage("/done");
				btnRollDice.setEnabled(false);
				resetButton.setEnabled(false);
			}
		});
		btnRollDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendMessage("/rollDice");
				if (!isRunning) {
		            isRunning = true;
					rollDice.runDice();
					rollDice = null;
		            isRunning = false;
		        }
			}
		});
	}
	
	// Server Message를 수신해서 화면에 표시
    class ListenNetwork extends Thread {
        public void run() {
        	String[] u;
        	int num = 0;
            while (true) {
                try {
                    // Use readUTF to read messages
                    String msg = dis.readUTF();
                    if (msg.contains(":Num\n")) {
                    	u = msg.split(":");
                    	num = Integer.parseInt(u[0]);
                        checkFull(num);
                    }          
                    else if (msg.contains(":loadUser\n")) {
                    	u = msg.split(":");
                    	if (num > 1) { // player1이 들어와 있다면
                        	player2_wait.setText(u[0].trim()); // 들어온 플레이어를 player2로 지정
                    	}
                    	else { // 들어온 사용자가 없다면 player1로 지정
                    		player1_wait.setText(u[0].trim());
                    	}
                    }
                    else if (msg.equals("//startGame")) {
                    	gameStart.setEnabled(false);
                    }
                    else if (msg.equals("//player")) {
                    	btnRollDice.setEnabled(true);
                    	resetButton.setEnabled(true);
                    }
                    else if (msg.equals("//rollDice")) {
                    	//rollDice.runDice();
                    	//AppendText("roroll\n");
                    }
                    else if (msg.contains("차례입니다.\n")) {
                    	txtGameStatus.setText(msg);
                    	AppendText(msg);
                    }
                    else
                    	AppendText(msg);
                    
                } catch (IOException e) {
                    AppendText("dis.read() error");
                    frmYachtDice.setVisible(false); // 추가 사용자 접근 방지를 위해 게임창 닫기
                    try {
                        dos.close();
                        dis.close();
                        socket.close();
                        break;
                    } catch (Exception ee) {
                        break;
                    }
                }
            }
        }
        public void checkFull(int num) { // 방안에 두명이 모두 들어오면 게임시작 버튼 활성화
        	if (num >= 2) {
        		gameStart.setEnabled(true);
        		txtGameStatus.setText("게임 시작 준비 완료!!");
        	}
        	else {
        		gameStart.setEnabled(false);
        		btnRollDice.setEnabled(false);
        		if (num == 1) {
        			if (player1_wait.getText() != "대기 중..." && player2_wait.getText() != "대기 중...") {
            			player1_wait.setText(player2_wait.getText());
            		}
            		player2_wait.setText("대기 중...");
        		}
        		txtGameStatus.setText("게임 시작 대기중입니다...");
        	}
        }
    }
    
    // 화면에 출력
    public void AppendText(String msg) {
        textArea.append(msg);
        textArea.setCaretPosition(textArea.getText().length());
    }
    
 // Server에게 network으로 전송
    public void SendMessage(String msg) {
        try {
            // Use writeUTF to send messages
            dos.writeUTF(msg);
        } catch (IOException e) {
            AppendText("dos.write() error");
            try {
                dos.close();
                dis.close();
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.exit(0);
            }
        }
    }
    
	
	// keyboard enter key 치면 서버로 전송
	class MyactionListener implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == sendButton || e.getSource() == txtInput) {
				String msg = null;
				msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				SendMessage(msg);
				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}
}
