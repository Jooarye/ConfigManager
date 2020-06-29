package de.jooarye.configmanager;

import de.jooarye.configmanager.types.ConfigValue;
import de.jooarye.configmanager.types.ConfigValueData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigManager {

    private static List<ConfigValueData> registryList = new ArrayList<>();

    public static void register(Object object) {
        for (final Field field : object.getClass().getDeclaredFields()) {
            if (!isValid(field))
                continue;

            if (!field.isAccessible())
                field.setAccessible(true);

            registryList.add(new ConfigValueData(field.getAnnotation(ConfigValue.class).name(), object, field));
        }
    }

    public static void unregister(Object object) {
        for (final ConfigValueData data : registryList) {
            if (data.getSource().equals(object))
                registryList.remove(data);
        }
    }

    public static void loadConfig(File file) throws IOException {
        Properties props = new Properties();

        props.load(new FileReader(file));

        for (final ConfigValueData data : registryList) {
            String value = props.getProperty(data.getName());

            try {
                if (value != null && !value.isEmpty() && data.getTarget().isAccessible()) {
                    if (int.class.equals(data.getTarget().getType())) {
                        data.getTarget().setInt(data.getSource(), Integer.parseInt(value));
                    } else if (float.class.equals(data.getTarget().getType())) {
                        data.getTarget().setFloat(data.getSource(), Float.parseFloat(value));
                    } else if (double.class.equals(data.getTarget().getType())) {
                        data.getTarget().setDouble(data.getSource(), Double.parseDouble(value));
                    } else if (short.class.equals(data.getTarget().getType())) {
                        data.getTarget().setShort(data.getSource(), Short.parseShort(value));
                    } else if (long.class.equals(data.getTarget().getType())) {
                        data.getTarget().setLong(data.getSource(), Long.parseLong(value));
                    } else if (boolean.class.equals(data.getTarget().getType())) {
                        data.getTarget().setBoolean(data.getSource(), Boolean.parseBoolean(value));
                    } else if (char.class.equals(data.getTarget().getType())) {
                        data.getTarget().setChar(data.getSource(), value.charAt(0));
                    } else if (byte.class.equals(data.getTarget().getType())) {
                        data.getTarget().setByte(data.getSource(), Byte.parseByte(value));
                    } else if (String.class.equals(data.getTarget().getType())) {
                        data.getTarget().set(data.getSource(), value);
                    }
                }
            } catch (IllegalAccessException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    private static boolean isValid(Field field) {
        return field.isAnnotationPresent(ConfigValue.class) && isValidType(field.getType());
    }

    private static boolean isValidType(Class type) {
        if (type.equals(int.class))
            return true;
        else if (type.equals(float.class))
            return true;
        else if (type.equals(double.class))
            return true;
        else if (type.equals(boolean.class))
            return true;
        else if (type.equals(char.class))
            return true;
        else if (type.equals(short.class))
            return true;
        else if (type.equals(byte.class))
            return true;
        else if (type.equals(String.class))
            return true;
        else return type.equals(long.class);
    }

}
