package com.example.ipose;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String PATH = "src/main/java/com/example/ipose/savefile.txt";
    private static final String PATH_TO_SCORE = "src/main/java/com/example/ipose/score.txt";


    public int getMaxLevel(String user) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split("-")[0].trim().equals(user)) {
                    return Integer.parseInt(line.split("-")[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        setMaxLevel(user, 1);
        return 1;
    }

    public void setMaxLevel(String userName, int newLevel) {
        List<String> lines = new ArrayList<>();
        boolean userFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                String user = parts[0].trim();
                if (user.equals(userName)) {
                    lines.add(userName + " - " + newLevel);
                    userFound = true;
                }
                else {
                    lines.add(line);
                }
            }
        }
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        if (!userFound) {
            lines.add(userName + " - " + newLevel);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    public void setNewScore(String name, int level, String score) {
        List<String> lines = new ArrayList<>();
        boolean userFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_SCORE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                String userName = parts[0].trim();
                int score_in_file = Integer.parseInt(parts[2].trim());
                int userLevel = Integer.parseInt(parts[1].trim());
                if (userName.equals(name) && userLevel == level) {
                    userFound=true;
                    if (Integer.parseInt(score) > score_in_file) {
                        lines.add(name + " - " + level + " - " + score);
                    }
                    else {
                        lines.add(line);
                    }
                }
                else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        if (!userFound) {
            lines.add(name + " - " + level + " - " + score);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_SCORE))) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }



    public int getHighScore(int level) {
        int highScore = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_SCORE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                int userLevel = Integer.parseInt(parts[1].trim());
                String userScore = parts[2].trim();

                if (userLevel == level && Integer.parseInt(userScore) > highScore) {
                    highScore = Integer.parseInt(userScore);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return highScore;
    }

}