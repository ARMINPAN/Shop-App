import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.*;

abstract class goodsSpecifications{
    protected String name;
    protected String Id;
    protected int buyPrice;
    protected int sellPrice;
    protected double stock;
    protected int lastGoodId;
}

public class Goods extends goodsSpecifications {

    static List<Goods> goodsListObjs = new ArrayList<Goods>();




    String goodIdGenerator(){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonGoods = fOperator.reader("GoodsList.json", "");
        Goods[] good = gson.fromJson(jsonGoods, Goods[].class);
        goodsListObjs.clear();
        Collections.addAll(goodsListObjs,good);
        if(good.length == 0)
            lastGoodId = 1;
        else
            lastGoodId = good[good.length-1].lastGoodId + 1;
        return ("PanjehShop-Good-" + String.valueOf(lastGoodId));
    }

    //add ti file
    static void addToGoods(Goods newGood){
        // add to pending file
        goodsListObjs.add(newGood);
        List<Goods> goodsList = goodsListObjs;
        //Gson
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        String text = gson.toJson(goodsList);
        FileOperators fOperator = new FileOperators();
        fOperator.writer("GoodsList.json", text);
    }

    boolean checkGoods(String NameToBeChecked){
        FileOperators fOperator = new FileOperators();
        String jsonGoods = fOperator.reader("GoodsList.json", NameToBeChecked);
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        return (jsonGoods.contains(NameToBeChecked));
    }

    void removingGoods(Goods remove){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonGoods = fOperator.reader("GoodsList.json", "");
        Goods[] goods = gson.fromJson(jsonGoods, Goods[].class);
        goodsListObjs.clear();
        Collections.addAll(goodsListObjs,goods);

        Goods removing = new Goods();
        if(removing.checkGoods(Id)){
            Iterator<Goods> itr = goodsListObjs.iterator();
            while(itr.hasNext()){
                Goods nxt = itr.next();
                if(nxt.Id.equals(remove.Id)){
                    itr.remove();
                }
            }
            List<Goods> goodList = goodsListObjs;
            String text = gson.toJson(goodList);
           // System.out.println(text);
            fOperator.writer("GoodsList.json", text);
            System.out.println("Good: " + Id + " was deleted successfully!");
            System.out.println("Enter your new request:");
            UpdateStock(remove.lastGoodId);
        }
        else{
            System.out.println("Error deleting good: " + Id + " !, Wrong good Id entered or It isn`t available in stock");
            System.out.println("Enter your new request:");
        }
    }


    static void getAllGoodsList(){
        FileOperators fileOperators = new FileOperators();
        String jsonGoods = fileOperators.reader("GoodsList.json", "");
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        Goods[] goods = gson.fromJson(jsonGoods, Goods[].class);

        CommandLineTable table = new CommandLineTable();
        String goodName = null, Count = null, Price = null;
        String[] headers = {"Good Name" , "Inventory", "Price"};

        for(int i = 0; i < goods.length; i++){
            goodName = goods[i].name;
            Count = String.valueOf(goods[i].stock);
            Price = String.valueOf(goods[i].sellPrice);
            String[] infos = {goodName, Count, Price};
            table.addRow(infos);
        }

        table.setShowVerticalLines(true);
        table.setHeaders(headers);
        table.print();
    }

    static void getAvailableGoodsList(){
        FileOperators fileOperators = new FileOperators();
        String jsonGoods = fileOperators.reader("GoodsList.json", "");
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        Goods[] goods = gson.fromJson(jsonGoods, Goods[].class);

        CommandLineTable table = new CommandLineTable();
        String goodName = null, Count = null, Price = null;
        String[] headers = {"Good Name" , "Inventory", "Price"};

        for(int i = 0; i < goods.length; i++){
            if(goods[i].stock != 0){
                goodName = goods[i].name;
                Count = String.valueOf(goods[i].stock);
                Price = String.valueOf(goods[i].sellPrice);
                String[] infos = {goodName, Count, Price};
                table.addRow(infos);
            }
        }

        table.setShowVerticalLines(true);
        table.setHeaders(headers);
        table.print();
    }

    static void getUnavailableGoodsList(){
        FileOperators fileOperators = new FileOperators();
        String jsonGoods = fileOperators.reader("GoodsList.json", "");
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        Goods[] goods = gson.fromJson(jsonGoods, Goods[].class);

        CommandLineTable table = new CommandLineTable();
        String goodName = null, Count = null, Price = null;
        String[] headers = {"Good Name" , "Inventory", "Price"};

        for(int i = 0; i < goods.length; i++){
            if(goods[i].stock == 0){
                goodName = goods[i].name;
                Count = String.valueOf(goods[i].stock);
                Price = String.valueOf(goods[i].sellPrice);
                String[] infos = {goodName, Count, Price};
                table.addRow(infos);
            }
        }

        table.setShowVerticalLines(true);
        table.setHeaders(headers);
        table.print();
    }


    void UpdateStock(int thresh){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonGoods = fOperator.reader("GoodsList.json", "");
        Goods[] goods = gson.fromJson(jsonGoods, Goods[].class);

        for(int i = thresh+1; i < goods.length; i++){
                goods[i].lastGoodId--;
                goods[i].Id = "PanjeShop-Good-" + goods[i].lastGoodId;
        }
        //update the stock/json file
        goodsListObjs.clear();
        Collections.addAll(goodsListObjs,goods);
        String text = gson.toJson(goodsListObjs);
        fOperator.writer("GoodsList.json", text);
    }
}


