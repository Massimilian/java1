package org.example;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class MyMojo extends AbstractMojo {
//    @Parameter(property = "vasalekmas@gmail.com", required = true)
    private String mailTo = "vasalekmas@gmail.com";

    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;

    public void execute() throws MojoExecutionException {
        final String to = mailTo;
        Mail mail = new Mail();
        mail.send(to);
    }
}
