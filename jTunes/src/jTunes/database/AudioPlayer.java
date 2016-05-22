/**
 * @author: codejava.net's writers
 */

package jTunes.database;

import jTunes.Resources;
 
import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 
/**
 * This code handles audio file playback and manages user control
 * implementing LineListener to listen to events and act upon them.
 */

public class AudioPlayer implements LineListener {
    

    private boolean playCompleted; // Boolean to tell when playback has been completely finished (upon file close)
    private boolean loop; // loop toggle
    private final long FIVESECONDS = 5000000;
    private final int START = 0;
    private Clip audioClip; // our clip object, plays the sound file
    // \/ This is temporary. This SHOULD be removed when gui and event tracking is implemented.
    private Scanner reader = new Scanner(System.in); 
     
    
    public boolean load(String songName){ // Loads the sound file into Clip.
        String audioFilePath = Resources.audioRoot + songName + ".wav";
        URL audioFile = getClass().getClassLoader().getResource(audioFilePath);
//        File audioFile = new File(audioFilePath);
        playCompleted = false;
        loop = false;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            System.out.println("Song Loaded");
            return true;
        }
        catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            return false;
            //ex.printStackTrace();
        }
        catch(NullPointerException ex){
        	System.out.println("File not Found or Song Name Incorrect");
        	return false;
        }
        catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            return false;
            //ex.printStackTrace();
        }
        catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            return false;
            //ex.printStackTrace();
        }
    }
        


    public void play(){ // Function plays the sound file
        if(audioClip.isOpen()  && !(audioClip.isActive())) {
            playCompleted = false;
            audioClip.start();
            System.out.println("Song Playing");
        } 
    }
   
    public boolean isCompleted(){ // Returns true if play is completed and not looping
        return playCompleted;
    }
    
    public boolean isPlaying(){
        // no audio loaded
        if (audioClip == null) return false;
        
        // active state === "playing"
    	if (audioClip.isActive()) return true;
    	
    	// not in active state
    	return false;
    }
    
    public void end() { // This closes the file and triggers playCompleted boolean
        if (audioClip != null) { 
            if(audioClip.isActive())
                audioClip.stop();
            audioClip.close();
        }
        playCompleted = true;
        System.out.println("Song Ended");
    }
    
    public void pause(){ // This pauses the playback of the file. 
        if(audioClip.isActive()){
            System.out.println("Paused");
            audioClip.stop();
        }
    }
   
    public void beginning(){ // |< goes to the beginning of the file to play.
        audioClip.setFramePosition(START);
        System.out.println("Song back to beginning");
    }
   
    public void toggleLoop(){ // toggles loop function
        loop = !loop;
    }
   
    public void back5(){ // Jumps back 5 seconds in playback
        if(audioClip.getMicrosecondPosition() > FIVESECONDS)
            audioClip.setMicrosecondPosition(audioClip.getMicrosecondPosition() - FIVESECONDS);
        else 
            audioClip.setMicrosecondPosition(START);
    }
   
    public void fwd5(){ // Jumps forward 5 seconds in playback
        if(audioClip.getMicrosecondPosition() < (audioClip.getMicrosecondLength()-FIVESECONDS))
            audioClip.setMicrosecondPosition(audioClip.getMicrosecondPosition() + FIVESECONDS);
        else 
            audioClip.setMicrosecondPosition(audioClip.getMicrosecondLength());
    }
    
    public double getSongProgress(){ // Returns the current song progress as a double between 0.0 and 100.0.
        return ((double)audioClip.getMicrosecondPosition()/(double)audioClip.getMicrosecondLength())*100.0;
    }
   
   
    /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
         
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");   
        } 
        else if (type == LineEvent.Type.STOP) {
            System.out.println("Playback stopped");
            if (audioClip.getMicrosecondLength() == audioClip.getMicrosecondPosition()) {
                playCompleted = true;
                System.out.println("Playback completed.");
                if(loop) {
                    playCompleted = false;
                    beginning();
                    play();
                }
            }  
        else if (type == LineEvent.Type.CLOSE) {
                System.out.println("Playback completed.");
            }
        }
    }
    
    
    
    public void menu() {
        // TEMPORARY FUNCTION TO TEST PLAYBACK FUNCTIONS. 
        
        int n = 0;

        System.out.print("1 - Play\n2 - Pause\n3 - Exit\n4 - Go back to the beginning\n5 - Toggle Loop\n6 - Back 5 seconds\n7 - Forward 5 seconds\n");
        if (loop) System.out.println("Looping is enabled");
        
        try {
            n = reader.nextInt();
        } catch (InputMismatchException ex) {
            if(playCompleted) return;
            System.out.println("This is an invalid choice.");
            reader.next();
            return;
        }
        
        if(playCompleted) return;
        
        switch (n){
            case 1: play();
                    break;
            case 2: pause();
                    break;
            case 3: end();
                    break;
            case 4: beginning();
                    break;
            case 5: toggleLoop();
                    break;
            case 6: back5();
                    break;
            case 7: fwd5();
                    break;
            default: break;
        }
    }
}
    
    
    