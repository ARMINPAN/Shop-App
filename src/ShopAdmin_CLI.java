import java.util.Scanner;
//orders ids format = PanjehShop-Order-i
//Goods ids format = PanjeShop-Goods-i

public class ShopAdmin_CLI {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String action = "";

        //admin actions
        System.out.println("Hello Admin! what do you wanna do :?");
        do{
            action = scan.nextLine();

            // get goods` lists
            Goods printLists = new Goods();
            if(action.equals("ls -r"))
                printLists.getAllGoodsList();
            else if(action.equals("ls -i"))
                printLists.getAvailableGoodsList();
            else if(action.equals("ls -n"))
                printLists.getUnavailableGoodsList();

            else if(action.equals("ls -o")){
                PendingOrders List = new PendingOrders();
                List.printList();
            }
            else if(action.equals("ls -ho")){
                OrderingHistory List =  new OrderingHistory();
                List.printList();
            }


            // checkout
            else if((action.split(" ",2)[0]).equals("checkout")){
                if((action.split(" ",2)[1]).split("-",3)[1].equals("Order")){
                    OrderingHistory history =  new OrderingHistory();
                    history.insert((action.split(" ",2)[1]));
                }
                else{
                    System.out.println("Input order Id incorrect pls try again!");
                }
            }

            //add new good
            else if((action.split(" ",9)[0]).equals("add") && (action.split(" ",9)[1]).equals("-n") && (action.split(" ",9)[3]).equals("-c") && (action.split(" ",9)[5]).equals("-sp") && (action.split(" ",9)[7]).equals("-bp")) {
                ///TODO shart gozashte shavad ke aya ghablan mojod hast ya na
                Goods newGood = new Goods();
                if (!((action.split(" ", 9)[2]).equals(null) || ((action.split(" ", 9)[2]).equals(" "))) && !((action.split(" ", 9)[4]).equals(null) || ((action.split(" ", 9)[4]).equals(" "))) && !((action.split(" ", 9)[6]).equals(null) || ((action.split(" ", 9)[6]).equals(" "))) && !((action.split(" ", 9)[8]).equals(null) || ((action.split(" ", 9)[8]).equals(" ")))){
                    if (!newGood.checkGoods(action.split(" ", 9)[2])) {
                        newGood.name = (action.split(" ", 9)[2]);
                        newGood.stock = (Integer.valueOf(action.split(" ", 9)[4]));
                        newGood.sellPrice = (Integer.valueOf(action.split(" ", 9)[6]));
                        newGood.buyPrice = (Integer.valueOf(action.split(" ", 9)[8]));
                        newGood.Id = newGood.goodIdGenerator();
                        newGood.addToGoods(newGood);
                        System.out.println("add was successful -> good_id = " + newGood.Id);
                    } else {
                        System.out.println("adding " + action.split(" ", 9)[2] + " failed, already in stock!");
                    }
                }
                else{
                    System.out.println("adding " + action.split(" ", 9)[2] + " failed, already in stock!");
                }
            }

            //remove
            else if((action.split(" ",3)[0]).equals("remove") && (action.split(" ",3)[1]).equals("-c")){
                if(!(action.split(" ",3)[0]).equals("") && !(action.split(" ",3)[0]).equals(" ")){
                    Goods remove = new Goods();
                    remove.Id = (action.split(" ",3)[2]);
                    remove.removingGoods(remove);
                }
                else{
                    System.out.println("removing failed, please try again");
                }

            }

            //edit
            else if(action.split(" ",6).length<5 && (action.split(" ",4)[0]).equals("edit") && (action.split(" ",4)[2]).equals("-n")){
                if(!((action.split(" ",6)[3]).equals(null)||(action.split(" ",6)[3]).equals(" "))){
                    EditStock editN = new EditStock();
                    editN.editName((action.split(" ",6)[1]),(action.split(" ",6)[3]), true);
                }
                else{
                    System.out.println("new name is invalid, please try again");
                }

            }
            else if((action.split(" ",6)[0]).equals("edit") && (action.split(" ",6)[2]).equals("-n") && (action.split(" ",6)[4]).equals("-c")){
                if(!((action.split(" ",6)[3]).equals(null)||(action.split(" ",6)[3]).equals(" ")) && !((action.split(" ",6)[5]).equals(null)||(action.split(" ",6)[5]).equals(" "))){
                    EditStock editN = new EditStock();
                    editN.editNameCount((action.split(" ",6)[1]),(action.split(" ",6)[3]),Integer.parseInt(action.split(" ",6)[5]));
                }
                else{
                    System.out.println("new name is invalid, please try again");
                }
            }
            else if((action.split(" ",8)[0]).equals("edit") && (action.split(" ",8)[2]).equals("-sp") && (action.split(" ",8)[4]).equals("-bp") && (action.split(" ",8)[6]).equals("-c")){
                if(Integer.parseInt(action.split(" ",8)[3]) >= Integer.parseInt(action.split(" ",8)[5])){
                    EditStock editN = new EditStock();
                    editN.editPriceCount((action.split(" ",8)[1]),Integer.parseInt(action.split(" ",8)[3]),Integer.parseInt(action.split(" ",8)[5]),Integer.parseInt(action.split(" ",8)[7]));
                }
                else{
                    System.out.println("Buy price entered is more than sell price entered, please try again:");
                }

            }

            // profit
            else if((action.split(" ",2)[0]).equals("calc") && (action.split(" ",2)[1]).equals("-p")){
                ProfitSelleingCalculator profCal = new ProfitSelleingCalculator();
                profCal.holeProfit();
            }
            else if((action.split(" ",4)[0]).equals("calc") && (action.split(" ",4)[1]).equals("-p") && (action.split(" ",4)[2]).equals("-c")){
                EditStock checker = new EditStock();
                if(checker.checkGoods(action.split(" ",4)[3])){
                    ProfitSelleingCalculator profCal = new ProfitSelleingCalculator();
                    profCal.specifiedProfit(action.split(" ",4)[3]);
                }
                else
                    System.out.println("This good isn`t in the history! pls try again:");
            }
            // sell infos
            else if((action.split(" ",2)[0]).equals("calc") && (action.split(" ",2)[1]).equals("-s")){
                ProfitSelleingCalculator profSell = new ProfitSelleingCalculator();
                profSell.Sellings(profSell);
            }
            else if((action.split(" ",4)[0]).equals("calc") && (action.split(" ",4)[1]).equals("-s") && (action.split(" ",4)[2]).equals("-c")){
                EditStock checker = new EditStock();
                if(checker.checkGoods(action.split(" ",4)[3])){
                    ProfitSelleingCalculator profSell = new ProfitSelleingCalculator();
                    profSell.HoleOrJustOne = action.split(" ",4)[3];
                    profSell.Sellings(profSell);
                }
                else
                    System.out.println("This good isn`t in the history! pls try again:");
            }
            else{
                System.out.println("Wrong input, please try again");
            }

        }while(true);


    }


}
