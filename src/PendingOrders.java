import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

abstract class PendingOrdersSpecifications{

}

public class PendingOrders {

    void InsertInPendingOrders(List<Ordering> newList){
        //Gson
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        String text = gson.toJson(newList);
        FileOperators fOperator = new FileOperators();
        fOperator.writer("PendingOrders.json", text);
    }

    boolean checkPendings(String IdToBeChecked){
        FileOperators fOperator = new FileOperators();
        String jsonPendings = fOperator.reader("PendingOrders.json", IdToBeChecked);
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();

        return (jsonPendings.contains(IdToBeChecked));
    }


    void printList(){
        FileOperators fileOperators = new FileOperators();
        String jsonPendings = fileOperators.reader("PendingOrders.json", "");
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        Ordering[] pendings = gson.fromJson(jsonPendings, Ordering[].class);

        CommandLineTable table = new CommandLineTable();
        String goodId = null, OrderId = null, Customer = null, Time = null;
        String[] headers = {"Order Id", "Customer", "Time of Order", "Good Id", "Quantity"};
        ArrayList<String> printed = new ArrayList<String>();
        for(int i = 0; i < pendings.length; i++){
            if(!printed.contains(pendings[i].orderId)){
                for(int j = 0 ; j < pendings.length; j++){
                    if(pendings[i].orderId.equals(pendings[j].orderId)){
                        printed.add(OrderId);
                        goodId = pendings[i].goodId;
                        OrderId = pendings[i].orderId;
                        Customer = pendings[i].buyerId;
                        Time = pendings[i].timeOfOrder;
                    }
                }
            }
            String[] infos = {OrderId, Customer, Time, goodId, String.valueOf(pendings[i].quantity)};
            table.addRow(infos);
        }
        table.setShowVerticalLines(true);
        table.setHeaders(headers);
        table.print();
    }


}
