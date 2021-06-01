import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class ProfitSelleingCalculator {

    protected String HoleOrJustOne = null;

    void holeProfit(){
        //read history file
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonHistory = fOperator.reader("OrderHistory.json", "");
        Ordering[] history = gson.fromJson(jsonHistory, Ordering[].class);

        //read goods file
        String jsonGoods = fOperator.reader("GoodsList.json", "");
        Goods[] good = gson.fromJson(jsonGoods, Goods[].class);
        //calculate
        int totalProfit = 0;
        for(int i = 0; i < history.length; i++){
            for(int j = 0 ; j < good.length; j++){
                if(good[j].Id.equals(history[i].goodId)){
                    totalProfit += (history[i].quantity)*(good[j].sellPrice - good[j].buyPrice);
                }
            }
        }
        System.out.println("total profit until now = " + totalProfit + " IRR");
    }

    void specifiedProfit(String goodId){
        //read history file
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonHistory = fOperator.reader("OrderHistory.json", "");
        Ordering[] history = gson.fromJson(jsonHistory, Ordering[].class);

        //read goods file
        String jsonGoods = fOperator.reader("GoodsList.json", "");
        Goods[] good = gson.fromJson(jsonGoods, Goods[].class);

        //calculate
        int specifiedProfit = 0;
        for(int i = 0; i < history.length; i++){
            for(int j = 0 ; j < good.length; j++){
                if(good[j].Id.equals(goodId) && history[i].goodId.equals(goodId)){
                    specifiedProfit+= (history[i].quantity)*(good[j].sellPrice - good[j].buyPrice);
                }
            }
        }
        System.out.println("profit of selling " + goodId + " until now = " + specifiedProfit + " IRR");
    }

    void Sellings(ProfitSelleingCalculator a){
        //read history file
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        FileOperators fOperator = new FileOperators();
        String jsonHistory = fOperator.reader("OrderHistory.json", "");
        Ordering[] history = gson.fromJson(jsonHistory, Ordering[].class);

        //read goods file
        String jsonGoods = fOperator.reader("GoodsList.json", "");
        Goods[] good = gson.fromJson(jsonGoods, Goods[].class);

        //calculate
        int specifiedProfit = 0;
        int specifiedSell = 0;
        String goodName, goodId;
        CommandLineTable table = new CommandLineTable();
        String[] headers = {"Good Name", "Good Id", "Number Ordered", "Sold", "Profit Made"};
        boolean flag = true;
        for(int i = 0; i < good.length; i++){
            if(a.HoleOrJustOne == (null)) {
                if(flag == true){
                    System.out.println(history.length + " total number of orders: ");
                    flag = false;
                }

                specifiedProfit = 0;
                specifiedSell = 0;
                goodName = good[i].name;
                goodId = good[i].Id;
                int ordered = 0;
                for (int j = 0; j < history.length; j++) {
                    if (good[i].Id.equals(history[j].goodId)) {
                        ordered++;
                        specifiedSell += (history[j].quantity) * (good[i].sellPrice);
                        specifiedProfit += (history[j].quantity) * (good[i].sellPrice - good[i].buyPrice);
                    }
                }
                String[] infos = {goodName, goodId, String.valueOf(ordered), String.valueOf(specifiedSell), String.valueOf(specifiedProfit)};
                table.addRow(infos);
            }
            else{
                    specifiedProfit = 0;
                    specifiedSell = 0;
                    goodName = good[i].name;
                    goodId = good[i].Id;
                    int ordered = 0;
                    for(int j = 0 ; j < history.length; j++){
                        if(good[i].Id.equals(history[j].goodId) && good[i].Id.equals(HoleOrJustOne)){
                            ordered++;
                            specifiedSell += (history[j].quantity)*(good[i].sellPrice);
                            specifiedProfit += (history[j].quantity)*(good[i].sellPrice - good[i].buyPrice);
                        }
                    }
                    if(ordered != 0 || flag == true)   {
                        flag = false;
                        String[] infos = {goodName, goodId, String.valueOf(ordered), String.valueOf(specifiedSell), String.valueOf(specifiedProfit)};
                        table.addRow(infos);
                    }
                }
            }
        table.setShowVerticalLines(true);
        table.setHeaders(headers);
        table.print();

        }

    }


