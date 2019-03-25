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

private AudioClip NE_S = null;
private AudioClip NI_S = null;
private AudioClip AE_S = null;
private AudioClip AI_S = null;

// Constructor
public Sound (){
	if (NError_Sound != null){
	    NE_S = Applet.newAudioClip(NError_Sound);
	}
	if (NInfo_Sound != null){
	    NI_S = Applet.newAudioClip(NInfo_Sound);
	}
	if (AError_Sound != null){
	    AE_S = Applet.newAudioClip(AError_Sound);
	}
	if (AInfo_Sound != null){
	    AI_S = Applet.newAudioClip(AInfo_Sound);
    }
}

//functions 
//getSoundNotificationError
public AudioClip getSNE (){ if (NE_S == null){return null;} return NE_S;}

//getSoundNotificationInfo
public AudioClip getSNI(){if (NI_S == null){return null;}return NI_S;}

//getSoundAlertError
public AudioClip getSAE(){if (AE_S == null){return null;}return AE_S;}	 

//getSoundAlertInfo
public AudioClip getSAI() {if (AI_S == null){return null;}return AI_S;} 

}