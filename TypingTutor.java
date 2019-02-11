package game;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TypingTutor extends JFrame implements ActionListener, KeyListener {

	private JLabel lblTimer;
	private JLabel lblScore;
	private JLabel lblWord;
	private JTextField txtWord;
	private JButton btnstart;
	private JButton btnstop;

	private Timer Clocktimer = null;
	private Timer Wordtimer = null;
	private boolean running = false;
	private int timeRemaining = 0;
	private int score = 0;

	private String[] words = null;

	public TypingTutor(String[] args) {
		words = args;
		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);

		Font font = new Font("Comic Sans MS", 1, 150);

		lblTimer = new JLabel("TIMER");
		lblTimer.setFont(font);
		super.add(lblTimer);

		lblScore = new JLabel("SCORE");
		lblScore.setFont(font);
		super.add(lblScore);

		lblWord = new JLabel("");
		lblWord.setFont(font);
		super.add(lblWord);

		txtWord = new JTextField("");
		txtWord.setFont(font);
		txtWord.addKeyListener(this);
		super.add(txtWord);

		btnstart = new JButton("START");
		btnstart.addActionListener(this);
		btnstart.setFont(font);
		super.add(btnstart);

		btnstop = new JButton("STOP");
		btnstop.addActionListener(this);
		btnstop.setFont(font);
		super.add(btnstop);

		super.setExtendedState(MAXIMIZED_BOTH);
		super.setTitle("TYPING TUTOR");
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setVisible(true);

		setupthegame();

	}

	private void setupthegame() {
		Clocktimer = new Timer(1000, this);
		Clocktimer.setInitialDelay(0);

		Wordtimer = new Timer(3000, this);
		Wordtimer.setInitialDelay(0);

		running = false;
		score = 0;
		timeRemaining = 50;

		lblTimer.setText("Time:" + timeRemaining);
		lblScore.setText("Score:" + score);
		lblWord.setText("");
		txtWord.setText("");
		btnstop.setText("STOP");
		btnstart.setText("START");

		txtWord.setEnabled(false);
		btnstop.setEnabled(false);

	}

	@Override
	public  synchronized void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnstart)
			handlestart();

		else if (e.getSource() == btnstop)
			handlestop();

		else if (e.getSource() == Clocktimer)
			handleClocktimer();

		else if (e.getSource() == Wordtimer)
			handleWordtimer();

	}

	private void handlestart() {
		if (running == false) {
			Clocktimer.start();
			Wordtimer.start();
			running = true;
			btnstart.setText("PAUSE");
			txtWord.setEnabled(true);
			btnstop.setEnabled(true);
		} else {
			Clocktimer.stop();
			Wordtimer.stop();
			running = false;
			btnstart.setText("START");
			txtWord.setEnabled(false);
			btnstop.setEnabled(false);
		}

	}

	private void handlestop() {
		Clocktimer.stop();
		Wordtimer.stop();

		int choice = JOptionPane.showConfirmDialog(this, "replay?");
		if (choice == JOptionPane.YES_OPTION)
			setupthegame();
		else if (choice == JOptionPane.NO_OPTION)
			JOptionPane.showMessageDialog(this, "FINAL SCORE :" + score);

		else if (choice == JOptionPane.CANCEL_OPTION) {

			if (timeRemaining == 0)
				setupthegame();
			else
				Clocktimer.start();
			Wordtimer.start();

		}

	}

	private void handleClocktimer() {
		if (timeRemaining > 0) {
			timeRemaining--;
			lblTimer.setText("Time:" + timeRemaining);

		}
		

		else
			handlestop();

	}

	private void handleWordtimer() {

		String actual = lblWord.getText();
		String type = txtWord.getText();

		if (type.length() > 0 && type.equals(actual))
			score++;

		int ridx = (int) (Math.random() * words.length);
		lblScore.setText("Score:" + score);
		lblWord.setText(words[ridx]);
		txtWord.setText("");

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

		String actual = lblWord.getText();
		String type = txtWord.getText();

		if (type.length() > 0 && type.equals(actual)) {
			score++;

			int ridx = (int) (Math.random() * words.length);
			
			lblScore.setText("Score:" + score);
			lblWord.setText(words[ridx]);
			txtWord.setText("");

			Wordtimer.restart();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
