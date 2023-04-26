package net.immortaldevs.colorizer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Config {
    private static final HashMap<String, HashMap<BlockPos, ChestColor>> values = new HashMap<>();

    public static ChestColor getColor(String worldName, BlockPos position) {
        if (!values.containsKey(worldName)) return null;
        return values.get(worldName).get(position);
    }

    public static void setColor(String worldName, BlockPos position, ChestColor color) {
        if (!values.containsKey(worldName)) values.put(worldName, new HashMap<>());
        values.get(worldName).put(position, color);
        save();
    }

    public static void removeColor(String worldName, BlockPos position) {
        if (!values.containsKey(worldName)) return;
        values.get(worldName).remove(position);
        save();
    }

    public static void load() {
        try {
            File file = MinecraftClient.getInstance().runDirectory.toPath().resolve("config/colorizer.csv").toFile();
            if (!file.exists()) {
                file.createNewFile();
                save();
                return;
            }
            deserialize(FileUtils.readFileToString(file, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            File file = MinecraftClient.getInstance().runDirectory.toPath().resolve("config/colorizer.csv").toFile();
            FileUtils.write(file, serialize(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String serialize() {
        StringBuilder builder = new StringBuilder();
        for (String worldName : values.keySet()) {
            for (BlockPos position : values.get(worldName).keySet()) {
                builder.append(worldName).append(";")
                        .append(position.getX()).append(";")
                        .append(position.getY()).append(";")
                        .append(position.getZ()).append(";")
                        .append(values.get(worldName).get(position).getName())
                        .append("\n");
            }
        }
        return builder.toString();
    }

    private static void deserialize(String data) {
        values.clear();
        String[] lines = data.split("\n");
        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length != 5) continue;
            String worldName = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);
            String color = parts[4];
            if (!values.containsKey(worldName)) values.put(worldName, new HashMap<>());
            values.get(worldName).put(new BlockPos(x, y, z), ChestColor.fromName(color));
        }
    }
}

