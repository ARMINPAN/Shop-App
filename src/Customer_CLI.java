import java.util.Scanner;

//orders ids format = PanjehShop-Order-i
//Goods ids format = PanjeShop-Goods-i

public class Customer_CLI {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String action = "";

        People currentUser = new People();
        currentUser.login(scan);

        // user actions
        System.out.println("What do you need today :?");
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

            // ordering
            else if((action.split(" ",4)[0]).equals("order") && (action.split(" ",4)[2]).equals("-c")){

                Goods goodOrdered = new Goods();
                goodOrdered.Id = action.split(" ",4)[1];
                EditStock checker = new EditStock();
                //System.out.println(available);
                // check the stock
                if(checker.checkGoods(goodOrdered.Id)){
                    double available = checker.StockChecker(goodOrdered.Id);
                    if(Double.parseDouble(action.split(" ",4)[3]) > available){
                        System.out.println("ERROR: order not successful");
                    }
                    else{
                        Ordering newOrder = new Ordering();
                        newOrder.orderId = newOrder.orderIdGenerator();
                        newOrder.buyerId = currentUser.Id;
                        newOrder.goodId = action.split(" ",4)[1];
                        newOrder.quantity = Double.parseDouble(action.split(" ",4)[3]);
                        newOrder.timeOfOrder = newOrder.setDateTime();
                        newOrder.addToPending(newOrder);
                        System.out.println("Your order id is = " + newOrder.orderId);
                        System.out.println("You can cancle your order before accepting by the shop, after that you can`t do anything.");
                        System.out.println("Enter your new request:");
                    }
                }
                else{
                    System.out.println("This good is not in the stock, try sth else or call the ShopAdmin for adding it!");
                    System.out.println("Enter your new request:");
                }

            }
            //cancle a pending order
            else if((action.split(" ",3)[0]).equals("order") && (action.split(" ",3)[1]).equals("-d")){
                Ordering newCancling = new Ordering();
                newCancling.orderId = (action.split(" ",3)[2]);
                newCancling.lastOrderId = Integer.valueOf(action.split(" ",3)[2].split("-",3)[2]);
                newCancling.canclingOrder(newCancling);
            }
            //wrong inputs
            else if(!action.equals("logout")){
                System.out.println("We didn`t understand your query! Please try again:");
            }


        }while(!action.equals("logout"));

    }





}
