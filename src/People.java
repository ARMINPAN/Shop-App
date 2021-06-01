import java.util.Scanner;

abstract class peopleSpecifications{
    protected String Id = "";
}



public class People extends peopleSpecifications{

    // login system
    protected void login(Scanner scan){
        System.out.println("HI! Enter your username to continue:");
        do{
            Id = scan.nextLine();
            if(!(Id.split(" ",2)[0]).equals("login")){
                System.out.println("Username is needed to continue:");
            }
            else{
                System.out.println("Welcome " + Id.split(" ",2)[1] + "!");
            }
        }while(!(Id.split(" ",2)[0]).equals("login"));
        // set the id
        Id = Id.split(" ",2)[1];
    }


}
