package de.jooarye.test;

import de.jooarye.configmanager.ConfigManager;
import de.jooarye.configmanager.types.ConfigValue;

import java.io.File;
import java.io.IOException;

public class Main {

    @ConfigValue(name = "firstName")
    private String firstName;

    @ConfigValue(name = "lastName")
    private String lastName;

    @ConfigValue(name = "age")
    private int age;

    @ConfigValue(name = "height")
    private double height;

    public static void main(String[] args) throws IOException {
        Main main = new Main();

        ConfigManager.register(main);
        ConfigManager.loadConfig(new File("test.properties"));

        main.test();
    }

    private void test() {
        System.out.println(String.format("%s %s was %d years old and %.2fm big!", firstName, lastName, age, height));
    }


}
