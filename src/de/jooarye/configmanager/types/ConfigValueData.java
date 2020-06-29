package de.jooarye.configmanager.types;

import java.lang.reflect.Field;

public class ConfigValueData {
    private final String name;
    private final Object source;
    private final Field target;

    public ConfigValueData(String name, Object source, Field target) {
        this.name = name;
        this.source = source;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public Object getSource() {
        return source;
    }

    public Field getTarget() {
        return target;
    }
}
