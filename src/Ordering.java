import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;

abstract class ordersSpecifications{
    protected String orderId;
    protected String buyerId;
    protected String timeOfOrder;
    protected String goodId;
    protected double quantity;
    protected int lastOrderId;
    static Ordering goToHistory;
}


public class Ordering extends ordersSpecifications{

    static List<Ordering> pendingListObjs = new ArrayList<Ordering>();

    void load(){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonPending = fOperator.reader("PendingOrders.json", "");
        Ordering[] pendings = gson.fromJson(jsonPending, Ordering[].class);
        pendingListObjs.clear();
        Collections.addAll(pendingListObjs,pendings);
    }

    String orderIdGenerator(){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonPending = fOperator.reader("PendingOrders.json", "");
        Ordering[] pendings = gson.fromJson(jsonPending, Ordering[].class);
        pendingListObjs.clear();
        Collections.addAll(pendingListObjs,pendings);
        if(pendings.length == 0)
            lastOrderId = 1;
        else
            lastOrderId = pendings[pendings.length-1].lastOrderId + 1;
        return ("PanjehShop-Order-" + String.valueOf(lastOrderId));
    }

    static void addToPending(Ordering newOrder){
        // add to pending file
        pendingListObjs.add(newOrder);
      //  System.out.println(pendingListObjs);
        List<Ordering> pendingList = pendingListObjs;
        PendingOrders pending = new PendingOrders();
        pending.InsertInPendingOrders(pendingList);
    }

    String setDateTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }


    void canclingOrder(Ordering cancle){
        PendingOrders cancling = new PendingOrders();
        if(cancling.checkPendings(orderId)){
            load();
            Iterator<Ordering> itr = pendingListObjs.iterator();
            //System.out.println(itr.hasNext());
            while(itr.hasNext()){
                Ordering nxt = itr.next();
                if(nxt.orderId.equals(cancle.orderId)){
                    goToHistory = nxt;
                    itr.remove();
                    System.out.println("order " + orderId + " was deleted successfully!");
                }
            }

            List<Ordering> pendingList = pendingListObjs;
            PendingOrders pending = new PendingOrders();
            pending.InsertInPendingOrders(pendingList);
            //System.out.println(cancle.lastOrderId);
            UpdateOrdering(cancle.lastOrderId);
        }
        else{
            System.out.println("Error deleting order " + orderId + " !, Wrong order Id entered or It isn`t in pending list anymore");
            System.out.println("Enter your new request:");
        }
    }

    void UpdateStock(int thresh){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonGoods = fOperator.reader("PendingOrders.json", "");
        Ordering[] order = gson.fromJson(jsonGoods, Ordering[].class);
        //System.out.println(thresh);
        for(int i = thresh; i < order.length; i++){
            order[i].lastOrderId--;
            order[i].orderId = "PanjehShop-Order-" + order[i].lastOrderId;
        }
        //update the stock/json file
        pendingListObjs.clear();
        Collections.addAll(pendingListObjs,order);
        String text = gson.toJson(pendingListObjs);
        fOperator.writer("PendingOrders.json", text);
    }

    void UpdateOrdering(int thresh){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonGoods = fOperator.reader("PendingOrders.json", "");
        Ordering[] order = gson.fromJson(jsonGoods, Ordering[].class);

        for(int i = thresh-1; i < order.length; i++){
            order[i].lastOrderId--;
            order[i].orderId = "PanjehShop-Order-" + order[i].lastOrderId;
        }
        //update the stock/json file
        pendingListObjs.clear();
        Collections.addAll(pendingListObjs,order);
        String text = gson.toJson(pendingListObjs);
        fOperator.writer("PendingOrders.json", text);

    }

}


