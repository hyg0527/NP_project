import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.*;
public class BGM{
	public void playBGM(int situation) {
		File musicPath = null;
		try {
			System.out.println(situation);
			switch(situation) {
			case 1: musicPath = new File("music/BGM-Credit.wav");
			break;
			case 2: musicPath = new File("music/BGM-Gam036-Dice.wav");
			break;
			case 3: musicPath = new File("music/Jingle-Gam036-Dice-Open.wav");
			break;
			case 4: musicPath = new File("music/BGM-Gam036-Res.wav");
			break;
		}
            if(musicPath.exists()){ 
                     AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                     Clip clip = AudioSystem.getClip();
                     clip.open(audioInput);
                     clip.start();
                     }
            else{
                      System.out.println("Couldn't find Music file");
                   }
   }
   catch (Exception ex){
              ex.printStackTrace();
        }
}}
