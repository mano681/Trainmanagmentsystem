package mwmtrains;


   // Importing required Java libraries
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;




public class Passenger {
   static Scanner inp = new Scanner (System.in) ;
   static int bookingCounter = 100;
   public static void usermenu(){
      while(true) {
         System.out.println("\n ========== PASSENGER MENU ===========\n");
         System.out.println("1: Book Ticket ");
         System.out.println("2: Cancel Ticket ");
         System.out.println("3: Veiw Trains ");
         System.out.println("4: Exit ");

         System.out.println("Enter choice ");
         int choice;

while (true)
{
    System.out.print("Enter choice: ");

    if (inp.hasNextInt())
    {
        choice = inp.nextInt();
        inp.nextLine();
        break;
    }
    else
    {
        System.out.println("Invalid input. Enter a number.");
        inp.next(); // discard wrong input
    }
}
//i actually want to input the logic that if user enters letters instead of numbers so program must
//not crash but instead it should give it space to add a valid input and keep going 


         switch (choice) {
            case 1: bookTicket(); break;
            case 2: cancelTicket(); break;
            case 3: veiwTrains(); break;
            case 4: {return ;} 
               
               
         
            default:System.out.println("Invalid choice ");
               break;
         }
            
         
      }
   }

   public static String[] selectTrains(){
      //This will check for specific train that user has added 

      System.out.println("\n AVAILABLE TRAINS \n\n\n");
      for (String[] train : MWMTrains.trains){
         System.out.println("Train id " + train[MWMTrains.TRAIN_ID] + "|" + "TRAIN NAME " + train[MWMTrains.TRAIN_NAME] +
            "|"+ "AVAILABLE SEATS " + (Integer.parseInt(train[MWMTrains.TOTAL_SEATS])-Integer.parseInt(train[MWMTrains.BOOKED_SEATS])));
         
      }
      System.out.println("Enter train id \n kindly make sure that you add the exacct id ! \n");
      String id = inp.nextLine() ;

      for (String[]train : MWMTrains.trains){
         if (train[MWMTrains.TRAIN_ID].equals(id)){
            return train;
         }
      }
      System.out.println("Train not found ");
      return null ;  //cannot return zero because it is string array 

   }


   public static void veiwTrains(){
      for(String[]train : MWMTrains.trains){
         System.out.println(train[0] +"|" + train[1]);
      }
   }

  public static void bookTicket()
{
    String[] train = selectTrains();

    if (train == null) return;

    int totalseats = Integer.parseInt(train[MWMTrains.TOTAL_SEATS]);
    int bookedseats = Integer.parseInt(train[MWMTrains.BOOKED_SEATS]);

    if (bookedseats >= totalseats)
    {
        System.out.println("No seats available");
        return;
    }
    int people = 0 ;

    //exception handling that if user enters anything else than a digit for age so program should return instead of crashing 

    try{
    System.out.print("How many tickets do you want to book? ");
     people = inp.nextInt();
    inp.nextLine();}
    catch(InputMismatchException e ){
      System.out.println("Kindly enter a valid age in digits ");
    }

    int totalBill = 0;

    for (int i = 1; i <= people; i++)
    {
        System.out.println("\n--- Passenger " + i + " ---");

        System.out.print("Enter name: ");
        String name = inp.nextLine();

        System.out.print("Enter age: ");
        int age = inp.nextInt();
        inp.nextLine();

        System.out.print("Are you a student? (yes/no): ");
        String student = inp.nextLine();

        System.out.println("Select seat type:");
        System.out.println("1. Economy - 1000");
        System.out.println("2. AC - 1500");
        System.out.println("3. Luxury - 2000");

        int seatChoice = inp.nextInt();
        inp.nextLine();

        int price = switch (seatChoice)
        {
            case 1 -> 1000;   //lambda arrow operator use kia hai that means like ke take val from left and perform something on right for example if main pe 1000 likh rai hun that means case 1 pe 1000 return kar dain , so yeahhh khudd likha hai sara code <..>
            case 2 -> 1500;
            case 3 -> 2000;
            default -> 0;
        };

        if (price == 0)
        {
            System.out.println("Invalid seat type, skipping passenger");
            continue;
        }

        int studentDiscount = student.equalsIgnoreCase("yes") ? 50 : 0; //ternary operator usage 

        int ageDiscount = 0;
        if (age < 10) ageDiscount = 10;
        else if (age > 50) ageDiscount = 20;

        int discount = Math.max(studentDiscount, ageDiscount);

        int finalBill = price - (price * discount / 100);

        totalBill += finalBill;
        String bookingID = "B" + bookingCounter;
bookingCounter++;

String seatType = "";

switch (seatChoice)
{
    case 1:
        seatType = "Economy";
        break;

    case 2:
        seatType = "AC";
        break;

    case 3:
        seatType = "Luxury";
        break;
}

saveBooking(
bookingID,
name,
age,
train[MWMTrains.TRAIN_ID],
train[MWMTrains.TRAIN_NAME],
seatType,
finalBill,
discount
);
        

        System.out.println("\n--- BILL ---");
        System.out.println("Name: " + name);
        System.out.println("Seat Price: " + price);
        System.out.println("Discount: " + discount + "%");
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Final Bill: " + finalBill);
    }

    // Update seats AFTER all bookings
    bookedseats += people;
    train[MWMTrains.BOOKED_SEATS] = String.valueOf(bookedseats);

    MWMTrains.saveTrainsToFile();

    System.out.println("\n========================");
    System.out.println("TOTAL GROUP BILL: " + totalBill);
    System.out.println("========================");
}


public static void saveBooking(String bookingID,
                               String name,
                               int age,
                               String trainID,
                               String trainName,
                               String seatType,
                               int finalBill,
                               int discount)
{
    try
    {
        File file = new File("C:\\MWM\\bookings.txt");

        // create file if missing
        if (!file.exists())
        {
            file.createNewFile();
        }

        FileOutputStream fos =
        new FileOutputStream(file, true);

        PrintWriter writer = new PrintWriter(fos);

        writer.println(
        bookingID + "|" +
        name + "|" +
        age + "|" +
        trainID + "|" +
        trainName + "|" +
        seatType + "|" +
        finalBill + "|" +
        discount
        );

        writer.close();

        System.out.println("BOOKING SAVED SUCCESSFULLY");
    }

    catch (Exception e)
    {
        e.printStackTrace();
    }
}
   

   public static void cancelTicket(){
      System.out.println("Enter your booking id ");
      String bookingID = inp.nextLine(); 
      ArrayList<String> updateBookings = new ArrayList<>() ;
      boolean found = false ; 

      try {
         File file = new File ("C:\\MWM\\bookings.txt") ;
         Scanner filScanner = new Scanner(file) ;
         //check if inp is a string 
         while(filScanner.hasNextLine()){
            String line =filScanner.nextLine();
            String[]booking = line.split("\\|") ;

            if (booking[0] . equals(bookingID)){
               found = true ;
               String trainID = booking[3];
               for (String[]train : MWMTrains.trains ){
                  if (train[MWMTrains.TRAIN_ID].equals(trainID)){
                     int bookedseats = Integer.parseInt(train[MWMTrains.BOOKED_SEATS]);

                     bookedseats--;
                     train[MWMTrains.BOOKED_SEATS] = String.valueOf(bookedseats) ;
                     break;

                  }
               }
               continue;
            }
            updateBookings.add(line);
         }
         filScanner.close();
         PrintWriter writer = new PrintWriter(file) ;
         for (String booking :updateBookings){
            writer.println(booking); 
         }
         writer.close();
         MWMTrains.saveTrainsToFile();

         if(found)
         {
            System.out.println("Trained cancelled successfully ");
         }
         else {
            System.out.println("System ID not found ");
         }
      }
      catch(Exception e){
         e.printStackTrace();
      }
   }



    
}



//abhi aik cheez implement karni hai or wo ye hai ke pehle make sure krainke bookings hoi hain ke nai 
// us ke baad ji bookings update hunn 