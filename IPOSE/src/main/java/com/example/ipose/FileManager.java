package com.example.ipose;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String PATH = "src/main/java/com/example/ipose/savefile.txt";

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

}