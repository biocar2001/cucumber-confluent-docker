package com.arq.san.kafka.streams.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources")
public class RunCucumberTest {

    public static void executeCommand(Process process, String operacion, boolean exit){
        try{
            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            System.out.println(output.toString());
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println(operacion + " Exito Success !!!!");
                System.out.println(output);
                if(exit) {
                    System.exit(0);
                }
            } else {
                System.out.println(operacion + " ERROR EN SCRIPT !!!!");
                System.out.println(output);
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @BeforeClass
    public static void startEnvironment(){
        try{
            String scriptStartPath = ClassLoader.getSystemResource("docker/start.sh").getPath();
            System.out.println(scriptStartPath);
            Process process = Runtime.getRuntime().exec("chmod +x " + scriptStartPath);
            executeCommand(process,"START chmod ENVIRONMENT",false);
            Process processtart = Runtime.getRuntime().exec("bash " + scriptStartPath);
            executeCommand(processtart,"START ENVIRONMENT",false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void stopEnvironment(){

        try{
            String scriptStopPath = ClassLoader.getSystemResource("docker/stop.sh").getPath();
            Process process = Runtime.getRuntime().exec("chmod +x " + scriptStopPath);
            executeCommand(process, "STOP chmod ENVIRONMENT",false);
            Process processtop = Runtime.getRuntime().exec("bash " + scriptStopPath);
            executeCommand(processtop,"STOP ENVIRONMENT",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
