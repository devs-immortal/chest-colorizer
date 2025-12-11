package net.immortaldevs.colorizer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;

public class Config {
    private static final HashMap<String, HashMap<String, HashMap<BlockPos, BlockColor>>> values = new HashMap<>();

    public static BlockColor getColor(String dimension, BlockPos position) {
        return getColor("", dimension, position);
    }

    public static void setColor(String dimension, BlockPos position, BlockColor color) {
        setColor("", dimension, position, color);
    }

    public static void removeColor(String dimension, BlockPos position) {
        removeColor("", dimension, position);
    }

    public static BlockColor getColor(String world, String dimension, BlockPos position) {
        var worldMap = values.computeIfAbsent(world, k -> new HashMap<>());
        var dimensionMap = worldMap.computeIfAbsent(dimension, k -> new HashMap<>());
        return dimensionMap.get(position);
    }

    public static void setColor(String world, String dimension, BlockPos position, BlockColor color) {
        var worldMap = values.computeIfAbsent(world, k -> new HashMap<>());
        var dimensionMap = worldMap.computeIfAbsent(dimension, k -> new HashMap<>());
        dimensionMap.put(position, color);
        save();
    }

    public static void removeColor(String world, String dimension, BlockPos position) {
        var worldMap = values.computeIfAbsent(world, k -> new HashMap<>());
        var dimensionMap = worldMap.computeIfAbsent(dimension, k -> new HashMap<>());
        dimensionMap.remove(position);
        save();
    }

    public static void load() {
        try {
            File file = getConfigFile();
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                save();
                return;
            }
            deserialize(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            File file = getConfigFile();
            file.getParentFile().mkdirs();
            FileUtils.write(file, serialize(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String serialize() {
        StringBuilder builder = new StringBuilder();
        for (String world : values.keySet()) {
            var worldMap = values.get(world);
            for (String dimension : worldMap.keySet()) {
                var dimensionMap = worldMap.get(dimension);
                for (BlockPos position : dimensionMap.keySet()) {
                    builder.append(world).append(";")
                            .append(position.getX()).append(";")
                            .append(position.getY()).append(";")
                            .append(position.getZ()).append(";")
                            .append(dimensionMap.get(position).getName()).append(";")
                            .append(dimension)
                            .append("\n");
                }
            }
        }
        return builder.toString();
    }

    private static void deserialize(String data) {
        values.clear();
        String[] lines = data.split("\n");
        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length < 5) continue;
            String worldName = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);
            String color = parts[4];
            String dimension = parts.length == 6 ? parts[5] : "minecraft:overworld";
            var worldMap = values.computeIfAbsent(worldName, k -> new HashMap<>());
            var dimensionMap = worldMap.computeIfAbsent(dimension, k -> new HashMap<>());
            dimensionMap.put(new BlockPos(x, y, z), BlockColor.fromName(color));
        }
    }

    private static File getConfigFile() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("colorizer.csv");
        return configPath.toFile();
    }
}

