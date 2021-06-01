import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditStock {

    static List<Goods> goodsListObjs = new ArrayList<Goods>();

    boolean checkGoods(String IdToBeChecked){
        FileOperators fOperator = new FileOperators();
        String jsonPendings = fOperator.reader("GoodsList.json", IdToBeChecked);
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        return (jsonPendings.contains(IdToBeChecked));
    }




    void editName(String goodId, String newN, boolean flag){
        if(checkGoods(goodId)){
            //read
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            Gson gson = builder.create();
            FileOperators fOperator = new FileOperators();
            String jsonGoods = fOperator.reader("GoodsList.json", "");
            Goods[] good = gson.fromJson(jsonGoods, Goods[].class);
            good[Integer.parseInt(goodId.split("-",3)[2])-1].name = newN; //update name
            //update the stock/json file
            goodsListObjs.clear();
            Collections.addAll(goodsListObjs,good);
            String text = gson.toJson(goodsListObjs);
            fOperator.writer("GoodsList.json", text);
            System.out.println(goodId + "`s name changed to " + newN + " successfully!");
            if(flag == true)
                System.out.println("what`s your new request:");
        }
        else{
            System.out.println("good with this id not found! pls try again:");
        }
    }

    void editNameCount(String goodId, String newN, int newC){
        editName(goodId, newN, false);
        if(checkGoods(goodId)){
            //read
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            Gson gson = builder.create();
            FileOperators fOperator = new FileOperators();
            String jsonGoods = fOperator.reader("GoodsList.json", "");
            Goods[] good = gson.fromJson(jsonGoods, Goods[].class);
            good[Integer.parseInt(goodId.split("-",3)[2])-1].stock = newC; //update count
            //update the stock/json file
            goodsListObjs.clear();
            Collections.addAll(goodsListObjs,good);
            String text = gson.toJson(goodsListObjs);
            fOperator.writer("GoodsList.json", text);
            System.out.println(goodId + "`s count changed to " + newC + " successfully!");
            System.out.println("what`s your new request:");
        }
        else{
            System.out.println("good with this id not found! pls try again:");
        }
    }

    void editPriceCount(String goodId, int newSP, int newBP, int newC){

        if(checkGoods(goodId)){
            //read
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            Gson gson = builder.create();
            FileOperators fOperator = new FileOperators();
            String jsonGoods = fOperator.reader("GoodsList.json", "");
            Goods[] good = gson.fromJson(jsonGoods, Goods[].class);
            good[Integer.parseInt(goodId.split("-",3)[2])-1].stock = newC; //update count
            good[Integer.parseInt(goodId.split("-",3)[2])-1].sellPrice = newSP; //update Sp
            good[Integer.parseInt(goodId.split("-",3)[2])-1].buyPrice = newBP; //update Bp
            //update the stock/json file
            goodsListObjs.clear();
            Collections.addAll(goodsListObjs,good);
            String text = gson.toJson(goodsListObjs);
            fOperator.writer("GoodsList.json", text);
            System.out.println(goodId + "`s sp/bp/count changed successfully!");
            System.out.println("what`s your new request:");
        }
        else{
            System.out.println("good with this id not found! pls try again:");
        }
    }

    double StockChecker(String goodId){

        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonGoods = fOperator.reader("GoodsList.json", "");
        Goods[] good = gson.fromJson(jsonGoods, Goods[].class);
        double available = 0;
        //System.out.println(Integer.parseInt(goodId.split("-",3)[2]));
        try {
            available = good[Integer.parseInt(goodId.split("-",3)[2])-1].stock;
        }catch (Exception e){
            //System.out.println(e);
            System.out.println("Sth wrong! please try again: (good id must be in this form: PanjehShop-Good-i)");
        }
        return available;
    }

}
