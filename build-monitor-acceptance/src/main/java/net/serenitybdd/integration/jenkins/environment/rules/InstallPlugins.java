package net.serenitybdd.integration.jenkins.environment.rules;

import java.nio.file.Path;

public class InstallPlugins {
    public static InstallPluginsFromDisk fromDisk(Path... locations) {
        return new InstallPluginsFromDisk(locations);
    }
}
