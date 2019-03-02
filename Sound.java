import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import java.applet.*;

public class Sound{
// Fields
private	 URL NError_Sound = this.getClass().getResource("sound//NError.wav");
private  URL NInfo_Sound = this.getClass().getResource("sound//NInfo.wav");
private  URL  AError_Sound = this.getClass().getResource("sound//AError.wav");
private  URL  AInfo_Sound = this.getClass().getResource("sound//AInfo.wav");

// private	URL Bullet_Sound = this.getClass().getClassLoader().getResource("Sound/Bullet_Sound.wav");
// private URL Explosion_Sound = this.getClass().getClassLoader().getResource("Sound/Explosion_Sound.wav");
// private  URL  GameOver_Sound = this.getClass().getClassLoader().getResource("Sound/GameOver_Sound.wav");
// private  URL  Power_Sound = this.getClass().getClassLoader().getResource("Sound/Power_Sound.wav");

private AudioClip NE_S;
private AudioClip NI_S;
private AudioClip AE_S;
private AudioClip AI_S;

// Constructor
public Sound (){
	NE_S = Applet.newAudioClip(NError_Sound);
	NI_S = Applet.newAudioClip(NInfo_Sound);
	AE_S = Applet.newAudioClip(AError_Sound);
	AI_S = Applet.newAudioClip(AInfo_Sound);
}

//functions 
//getSoundNotificationError
public AudioClip getSNE (){return NE_S;}

//getSoundNotificationInfo
public AudioClip getSNI(){return NI_S;}

//getSoundAlertError
public AudioClip getSAE(){return AE_S;}	 

//getSoundAlertInfo
public AudioClip getSAI() {return AI_S;} 

}