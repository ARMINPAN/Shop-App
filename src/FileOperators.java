import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperators {




    void writer(String fileName, String Infos){
        try {
            File file = new File("resources\\" +fileName);
            if(!file.exists()) file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Infos);
            fileWriter.close();
        }catch(IOException error){
            if(fileName == "PendingOrders.json")
                System.out.println("ERROR: order not successful! pls try again");
            if(fileName == "OrderHistory.json")
                System.out.println("ERROR: Checkout failed! pls try again");
            if(fileName == "GoodsList.json")
                System.out.println("ERROR: Adding new good failed! pls try again");
            System.out.println("Enter your new request:");
        }

    }
    String reader(String filename, String target){
        File file = new File("resources\\" +filename);
        Scanner reader;
        String readed = "";
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()){
                readed = readed + reader.nextLine();
            }
            return readed;
        } catch (FileNotFoundException e) {
            if(filename == "PendingOrders.json")
                System.out.println("Error deleting order " + target + " !");
            if(filename == "GoodsList.json")
                System.out.println("Error! pls try again");
            System.out.println("Enter your new request:");
        }
        return readed;
    }


}
