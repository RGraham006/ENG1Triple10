package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.Gdx;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;

public class soundFrame {
    LinkedList<Sound> Sounds = new LinkedList<>();
    LinkedList<Long> soundIDs = new LinkedList<>();
    HashMap<soundsEnum, LinkedList<Long>> soundIDsMap = new HashMap<>();
    enum soundsEnum{

    }
    long playSound(soundsEnum rinh){
        Sound toPlay = Sounds.get(rinh.ordinal());
        long soundID = toPlay.play();
        soundIDs.push(soundID);
        return soundID;
    }

    void pauseSound(soundsEnum rinh, long ID){
        Sounds.get(rinh.ordinal()).pause(ID);
    }

    void addSound(String filepath){
        Sound effect = Gdx.audio.newSound(Gdx.files.internal(filepath));
        Sounds.push(effect);
    }

    void muteSound(){
        for(int i = 0; i <soundIDs.size(); i++){
            soundIDs.get(i);

        }
        
    }
}
