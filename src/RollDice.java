import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class RollDice {

	private static RollDice instance;
	//private JFrame frame;
	private String[] imagePaths = {"images/dice_1.jpg", "images/dice_2.jpg", "images/dice_3.jpg", 
			"images/dice_4.jpg", "images/dice_5.jpg", "images/dice_6.jpg"};
	private Timer timer;
	private int countN = 3;
	
	private JLabel dice1, dice2, dice3, dice4, dice5;
	private JLabel dice1_result, dice2_result, dice3_result, dice4_result, dice5_result;
	
	private JButton rollDiceButton;
	private JButton resetButton;
	private JLabel count;
	private JButton btnRecordScore;
	private JLabel recordScore;

	/**
	 * Create the application.
	 */
	public RollDice() { }
	public void runDice() {
		initialize();		
		moveDice();
	}
	public void setDice(JLabel dice1, JLabel dice2, JLabel dice3, JLabel dice4, JLabel dice5, 
			JLabel dice1_result, JLabel dice2_result, JLabel dice3_result, JLabel dice4_result, JLabel dice5_result) {
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.dice3 = dice3;
		this.dice4 = dice4;
		this.dice5 = dice5;
		
		this.dice1_result = dice1_result;
		this.dice2_result = dice2_result;
		this.dice3_result = dice3_result;
		this.dice4_result = dice4_result;
		this.dice5_result = dice5_result;
	}
	public void setButton(JButton rollDiceButton, JButton resetButton, JButton btnRecordScore) {
		this.rollDiceButton = rollDiceButton;
		this.resetButton = resetButton;
		this.btnRecordScore = btnRecordScore;
	}
	public void setPanel(JLabel recordScore) {
		this.recordScore = recordScore;
	}
	
	public static RollDice getInstance() {
		if (instance == null) instance = new RollDice();
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		rollDiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countN > 0) {
					rollDice();
					countN--;
					rollDiceButton.setEnabled(false);
				}
			}
		});
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetDice();
			}
		});
		btnRecordScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recordScore.setText(String.valueOf(recordScore()));
			}
		});
	}
	
	private void rollDice() {
		timer = new Timer(100, new ActionListener() {
			int delay = 0;
			Random random = new Random();
			
			public void actionPerformed(ActionEvent e) {
				//currentIndex = (currentIndex + 1) % imagePaths.length;
				int currentIndex1 = random.nextInt(imagePaths.length);
				int currentIndex2 = random.nextInt(imagePaths.length);
				int currentIndex3 = random.nextInt(imagePaths.length);
				int currentIndex4 = random.nextInt(imagePaths.length);
				int currentIndex5 = random.nextInt(imagePaths.length);
				
				if (dice1.isEnabled()) {
					dice1.setIcon(new ImageIcon(imagePaths[currentIndex1]));
				}
				if (dice2.isEnabled()) {
					dice2.setIcon(new ImageIcon(imagePaths[currentIndex2]));
				}
				if (dice3.isEnabled()) {
					dice3.setIcon(new ImageIcon(imagePaths[currentIndex3]));
				}
				if (dice4.isEnabled()) {
					dice4.setIcon(new ImageIcon(imagePaths[currentIndex4]));
				}
				if (dice5.isEnabled()) {
					dice5.setIcon(new ImageIcon(imagePaths[currentIndex5]));
				}
				
				delay++;
				if (delay >= 20) {
					resultDice();
				}
			}
		});
		timer.start();
	}
	
	private void resultDice() {
		timer.stop();
		resetButton.setEnabled(true);

		if (countN <= 0) {
			rollDiceButton.setEnabled(false);
		}
		else
			rollDiceButton.setEnabled(true);
	}
	
	private void moveDice() { // 주사위 이동시키는 함수
		SwingUtilities.invokeLater(() -> {
		dice1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dice1_result.setIcon(dice1.getIcon());
				dice1.setIcon(null);
				dice1.setEnabled(false);
				isFullCount();
			}
		});
		dice2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dice2_result.setIcon(dice2.getIcon());
				dice2.setIcon(null);
				dice2.setEnabled(false);
				isFullCount();
			}
		});
		dice3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dice3_result.setIcon(dice3.getIcon());
				dice3.setIcon(null);
				dice3.setEnabled(false);
				isFullCount();
			}
		});
		dice4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dice4_result.setIcon(dice4.getIcon());
				dice4.setIcon(null);
				dice4.setEnabled(false);
				isFullCount();
			}
		});
		dice5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dice5_result.setIcon(dice5.getIcon());
				dice5.setIcon(null);
				dice5.setEnabled(false);
				isFullCount();
			}
		});
	});
	};
	
	private void resetDice() { // 주사위 기록 리셋 함수
		dice1.setIcon(null);
		dice2.setIcon(null);
		dice3.setIcon(null);
		dice4.setIcon(null);
		dice5.setIcon(null);
		
		dice1_result.setIcon(null);
		dice2_result.setIcon(null);
		dice3_result.setIcon(null);
		dice4_result.setIcon(null);
		dice5_result.setIcon(null);
		
		dice1.setEnabled(true);
		dice2.setEnabled(true);
		dice3.setEnabled(true);
		dice4.setEnabled(true);
		dice5.setEnabled(true);
		
		countN =3;
		recordScore.setText("0");
		
		resetButton.setEnabled(false);
		rollDiceButton.setEnabled(true);
		btnRecordScore.setEnabled(false);
	}
	
	private int recordScore() { // 점수 기록하는 함수
		int score = 0;
		String[] res = new String[5];
		
		res[0] = dice1_result.getIcon().toString();
		res[1] = dice2_result.getIcon().toString();
		res[2] = dice3_result.getIcon().toString();
		res[3] = dice4_result.getIcon().toString();
		res[4] = dice5_result.getIcon().toString();
		
		for (int i = 0; i < 5; i++) {
			if (res[i].contains("1"))
				score += 1;
			else if (res[i].contains("2"))
				score += 2;
			else if (res[i].contains("3"))
				score += 3;
			else if (res[i].contains("4"))
				score += 4;
			else if (res[i].contains("5"))
				score += 5;
			else if (res[i].contains("6"))
				score += 6;
		}
		return score;
	}
	
	private void isFullCount() { // 주사위칸에 주사위가 모두 들어가는지 판별
		if (dice1_result.getIcon() != null && dice2_result.getIcon() != null &&
				dice3_result.getIcon() != null && dice4_result.getIcon() != null && dice5_result.getIcon() != null) {
			rollDiceButton.setEnabled(false);
			btnRecordScore.setEnabled(true);
		}
	}
}
