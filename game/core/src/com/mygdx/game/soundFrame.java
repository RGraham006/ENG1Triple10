package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.Gdx;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;

public class soundFrame {

    /**
     * Uses an enum containing all the names of the sound effects
     * Uses an array of all the sounds, must have the same index as the enum
     * Uses a hashmap to store the IDs for each sound
     */
    enum soundsEnum{
    end;
    }
    Sound[] Sounds = new Sound[soundsEnum.end.ordinal()-1];

    HashMap<soundsEnum, LinkedList<Long>> soundIDsMap = new HashMap<>();
    float currentSystemVolume = 1.0f;

    /**
     * @param rinh - Enum value as the name of the sound
     * @return Returns the ID given when a sound is played
     * Retrieves the sound from the array based on the enum
     * Plays the sound getting the ID
     * If the sound already has previous IDs, add the ID to the list
     * If the sound doesnt already have IDs then create a new list and add that to the hashmap under an enum key
     */
    public long playSound(soundsEnum rinh){
        Sound toPlay = Sounds[rinh.ordinal()];
        long soundID = toPlay.play();
        LinkedList<Long> soundIDs = new LinkedList<>();

        if(soundIDsMap.get(rinh) == null){
            soundIDs.push(soundID);
            soundIDsMap.put(rinh, soundIDs);
        }else{
            soundIDs = soundIDsMap.get(rinh);
            soundIDs.push(soundID);
            soundIDsMap.put(rinh, soundIDs);
        }
        return soundID;
    }


    /**
     *
     * @param rinh - Enum value of the name of the sound
     * @param ID - The ID required to interact with the sound instance
     *
     * Find the sound from the enum, and pause it using the sound instance ID
     */
    public void pauseSound(soundsEnum rinh, long ID){
        Sounds[rinh.ordinal()].pause(ID);
    }

    /**
     *
     * @param rinh - Enum value of the name of the sound
     * @param ID - The ID required to interact with the sound instance
     *
     * Find the sound from the enum, and resume it using the sound instance ID
     */

    public void resumeSound(soundsEnum rinh, long ID){
        Sounds[rinh.ordinal()].resume(ID);
    }


    /**
     *
     * @param rinh - Enum value of the name of the sound
     * @param ID - The ID required to interact with the sound instance
     *
     * Find the sound from the enum, and set it to loop using the sound instance ID
     */
    public void setLooping(soundsEnum rinh, long ID){
        Sounds[rinh.ordinal()].setLooping(ID, true);
    }


    /**
     *
     * @param filepath - The filepath of the sound wanted to be played
     * @param rinh - Enum value of the name of the sound
     *
     * Checks if the sound doesnt exist in the Sound array
     * If it doesnt, create a sound and add it to the array
     */
    public void addSound(String filepath, soundsEnum rinh){
        if(Sounds[rinh.ordinal()] == null){
            Sound effect = Gdx.audio.newSound(Gdx.files.internal(filepath));
            Sounds[rinh.ordinal()] = effect;
        }
    }

    /**
     *
     * @param rinh - Enum value of the name of the sound
     *
     * Removes the sound from the Sound array in the position of the enum value
     */
    public void removeSound(soundsEnum rinh){
        if(Sounds[rinh.ordinal()] != null){
            Sounds[rinh.ordinal()] = null;
        }
    }

    public void setSystemVolume(float volume){
        /**
         * @param volume - The volume wanted to set all the sounds to
         *
         * Sets the current system volume variable to the volume set
         * Iterate through all the sounds, and get the sound
         * Iterate through all the IDs of each sound, and set each instace of each sound to the required volume
         */
        if(volume != 0.0f){
            currentSystemVolume = volume;
        }
        for (soundsEnum key : soundIDsMap.keySet()) {
            LinkedList<Long> value = soundIDsMap.get(key);
            Sound currentSound = Sounds[key.ordinal()];
            for(int i = 0; i<value.size(); i++){
                currentSound.setVolume(value.get(i), volume);
            }
        }
    }

    /**
     * Runs setSystemVolume with a sound of 0
     */
    public void muteSound(){
        setSystemVolume(0.0f);
    }

    /**
     * Sets the volume back to the volume previous to mute, allows return to previous volume after a mute
     */
    public void unmuteSound(){
        setSystemVolume(currentSystemVolume);
    }
}
