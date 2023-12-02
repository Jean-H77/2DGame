package com.csun.game.modules.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataManager {
    static DataManager instance;

    private DataManager(){

    }

    public SaveData load(String fileName){
        Preferences prefs = Gdx.app.getPreferences(fileName);
        SaveData saveData = new SaveData();
        saveData.playerX = prefs.getInteger("playerX");
        saveData.playerY = prefs.getInteger("playerY");
        return saveData;
    }

    public void save(String fileName,SaveData saveData){
        //This will get your preferences from storage
        Preferences prefs = Gdx.app.getPreferences(fileName);

        prefs.putInteger("playerX", saveData.playerX);
        prefs.putInteger("playerY", saveData.playerY);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("saves",true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(fileName + "\n");
        printWriter.close();


        prefs.flush();
    }

    public List<String> getFiles(){
        File file = new File("saves");
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String> list = new ArrayList<String>();

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }
        return list;
    }

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }


}
