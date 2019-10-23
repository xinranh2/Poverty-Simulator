package com.tutorial.main;
 
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
//import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
//this class handles player movement
public class PlayerMovement extends KeyAdapter {
   
 
    private Handler handler;
 
    private boolean moveUp = false; //we have to use booleans for this to avoid
    private boolean moveDown = false; //weird delay on holding down a key
    private boolean moveLeft = false;
    private boolean moveRight = false;
   
    private File filePathSound = new File("").getAbsoluteFile();
    private AudioInputStream stream;
    private Clip clip;
    private BackGroundSound BGS;
    
    private final int SPEED = 2;
    
    public PlayerMovement(Handler handler) throws UnsupportedAudioFileException, IOException, LineUnavailableException { //this is a constructor
        this.handler = handler;
        stream = AudioSystem.getAudioInputStream(PlayerMovement.class.getResource("FOOTSTEPS.wav"));
        //play all the sound exactly like this and don't change it 
        clip = AudioSystem.getClip();
        
        BGS = new BackGroundSound((long) 0.0, clip, "?", stream, filePathSound);

        //what we pass to this function
        //will be set to "handler" defined above
    }
   

    public void tick() {
    	if (moveUp || moveDown || moveLeft || moveRight) {
            try {
				BGS.play();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	} else {
    		 try {
					BGS.stop();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	} 
    }
    // open audioInputStream to the clip

    
     //, throws UnsupportedAudioFileException, IOException, LineUnavailableException;
                // create AudioInputStream object
       
   
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println("codeKey = " + key);
       
        for (int i = 0; i < handler.object.size(); i++) { //loop through all game objects
            GameObject tempObjectKeyIn = handler.object.get(i);
            if (tempObjectKeyIn.getId() == ID.Player) {
                 //key events that control Player1
                 //THIS CODE GETS RUN WHEN A BUTTON IS PRESSED
                if(key == KeyEvent.VK_W) { //if W pressed
                    moveUp = true; //set moveUp to true
                    tempObjectKeyIn.setVelY(-SPEED);
                    System.out.println("w press");
                } else if (key == KeyEvent.VK_S) {//else if S pressed
                    moveDown = true; //set moveDown to true

                    tempObjectKeyIn.setVelY(SPEED);
                    System.out.println("S press");
                } else if (key == KeyEvent.VK_A) {
                    moveLeft = true;
                    System.out.println("1.0 a presses");
                    tempObjectKeyIn.setVelX(-SPEED);
                } else if (key == KeyEvent.VK_D) {
                    moveRight = true;
                    tempObjectKeyIn.setVelX(SPEED);
                    System.out.println("d press");
                }
            }
        }
        //System.out.println(key);
    }
 
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        /**
         * NOTE: As Y increases, things move down the screen. So Y = 0 is the top of the screen, I think.
         */
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObjectKeyIn = handler.object.get(i);
            if (tempObjectKeyIn.getId() == ID.Player) {
                if (key == KeyEvent.VK_W) { //when W key released
                    System.out.println("w release");
                    moveUp = false; //set moveUp to false
                    if (moveDown == true) { //but if you're moving down
                        tempObjectKeyIn.setVelY(SPEED); //keep doing it
                    } else { //if not
                        tempObjectKeyIn.setVelY(0); //then you're not moving vertically
                    }
                } else if (key == KeyEvent.VK_S) { //S key released
                    System.out.println("s release");
                    moveDown = false;
                   
                    if (moveUp == true) {
                        tempObjectKeyIn.setVelY(-SPEED);
                    } else {
                        tempObjectKeyIn.setVelY(0);
                    }
                } else if (key == KeyEvent.VK_A) {
                    System.out.println("a release");
                    moveLeft = false;
                    if (moveRight == true) {
                        tempObjectKeyIn.setVelX(SPEED);
                    } else {
                        tempObjectKeyIn.setVelX(0);
                    }
                } else if (key == KeyEvent.VK_D) {
                    System.out.println("d release");
                    moveRight = false;
                    if (moveLeft == true) {
                        tempObjectKeyIn.setVelX(-SPEED);
                    } else {
                        tempObjectKeyIn.setVelX(0);
                    }
                }
                if (key == KeyEvent.VK_ESCAPE) System.exit(1); //press escape to close game, probably replace with a pause menu later
                }
            }
        }
        //System.out.println("o" + e);
    }

