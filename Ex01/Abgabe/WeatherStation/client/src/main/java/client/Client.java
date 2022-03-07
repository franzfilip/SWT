package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weatherstation.WeatherStation;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
    public static void main(String[] args){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String availCmds = "commands: last, avg, quit";
        WeatherStation weatherStation = new WeatherStation();
        Logger logger = LoggerFactory.getLogger(Client.class);

        System.out.println(availCmds);
        String userCmd = promptFor(in, "");

        while(!userCmd.equals("quit")){
            switch (userCmd){
                case "last":{
                    logger.info("User requested latest measurement");
                    System.out.println(weatherStation.getLatest());
                    break;
                }
                case "avg": {
                    logger.info("User requested avg measurements");
                    System.out.println(weatherStation.getAverage(getNumberOfMeasurements(in)));
                    break;
                }
            }

            System.out.println(availCmds);
            userCmd = promptFor(in, "");
        }

        weatherStation.shutDown();
    }

    public static int getNumberOfMeasurements(BufferedReader in){
        String amount = promptFor(in, "Number of measurements: ");
        try{
            return Integer.parseInt(amount);
        }
        catch (NumberFormatException ex){
            System.out.println("Enter a valid number");
            return getNumberOfMeasurements(in);
        }
    }

    static String promptFor(BufferedReader in, String p) {
        System.out.print(p + "> ");
        System.out.flush();
        try {
            return in.readLine();
        } catch (Exception e) {
            return promptFor(in, p);
        }
    }
}
