/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.voice;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 *
 * @author DUC TOAN
 */
public class Speech{
   VoiceManager voiceManager;
   Voice syntheticVoice;
   String voice = "mbrola_us1";
   
   public Speech() {
       
   }
   
   public void setVoice(String voice) {
       this.voice = voice;
   }
   
    /**
     *
     * @param text
     */
   public void speechText(String text){
       System.setProperty("mbrola.base", "lib/mbrola");
       voiceManager = VoiceManager.getInstance();
       syntheticVoice = voiceManager.getVoice(voice);
       syntheticVoice.allocate();
       syntheticVoice.speak(text);
       syntheticVoice.deallocate();
   }
}
