import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderingHistory {

    private static List<Ordering> orderHistoryObjs = new ArrayList<Ordering>();
    static List<Goods> goodsListObjs = new ArrayList<Goods>();

    void insert(String orderId){
        PendingOrders pending = new PendingOrders();
        if(pending.checkPendings(orderId)){
            Ordering remove = new Ordering();
            remove.orderId = orderId;
            remove.lastOrderId = Integer.valueOf(orderId.split("-",3)[2]);
            uptadeStock(orderId);
            remove.load();
            remove.canclingOrder(remove); // remove the order from pending orders
            remove.UpdateStock(Integer.parseInt(orderId.split("-",3)[2]));
            // add to historyFile
            orderHistoryObjs.add(remove.goToHistory);
            List<Ordering> History = orderHistoryObjs;
            //Gson
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            Gson gson = builder.create();
            String text = gson.toJson(History);
            FileOperators fOperator = new FileOperators();
            fOperator.writer("OrderHistory.json", text);
            System.out.println("checking out " + orderId + " done!");
            System.out.println("Enter your new request:");
        }
        else{
            System.out.println("Checking out failed, pls try again!");
        }

    }


    void printList(){
        FileOperators fileOperators = new FileOperators();
        String jsonHistory = fileOperators.reader("OrderHistory.json", "");
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        Ordering[] History = gson.fromJson(jsonHistory, Ordering[].class);

        CommandLineTable table = new CommandLineTable();
        String goodId = null, OrderId = null, Customer = null, Time = null;
        String[] headers = {"Order Id", "Customer", "Time of Order", "Good Id", "Quantity"};
        ArrayList<String> printed = new ArrayList<String>();
        for(int i = 0; i < History.length; i++){
            if(!printed.contains(History[i].orderId)){
                for(int j = 0 ; j < History.length; j++){
                    if(History[i].orderId.equals(History[j].orderId)){
                        printed.add(OrderId);
                        goodId = History[i].goodId;
                        OrderId = History[i].orderId;
                        Customer = History[i].buyerId;
                        Time = History[i].timeOfOrder;
                    }
                }
            }
            String[] infos = {OrderId, Customer, Time, goodId, String.valueOf(History[i].quantity)};
            table.addRow(infos);
        }
        table.setShowVerticalLines(true);
        table.setHeaders(headers);
        table.print();
    }

    void uptadeStock(String orderId){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonGoods = fOperator.reader("GoodsList.json", "");
        Goods[] goods = gson.fromJson(jsonGoods, Goods[].class);

        String jsonPending = fOperator.reader("PendingOrders.json", "");
        Ordering[] pendings = gson.fromJson(jsonPending, Ordering[].class);

        for(int k = 0; k < pendings.length; k++){
            if(pendings[k].orderId.equals(orderId)){
                for(int i = 0; i < goods.length; i++){
                    if(goods[i].Id.equals(pendings[k].goodId)){
                        //System.out.println(pendings[k].quantity);
                        goods[i].stock -= pendings[k].quantity;
                    }
                }
            }
        }
        //update the stock/json file
        goodsListObjs.clear();
        Collections.addAll(goodsListObjs,goods);
        String text = gson.toJson(goodsListObjs);
        fOperator.writer("GoodsList.json", text);

    }


}
