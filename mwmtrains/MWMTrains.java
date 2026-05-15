package mwmtrains;

// Importing required Java libraries
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;

public class MWMTrains {

    // Scanner object to take input from user
    static Scanner sc = new Scanner(System.in);

    // List to store all active trains
    static ArrayList<String[]> trains = new ArrayList<>();

    // List to store deleted trains
    static ArrayList<String[]> deletedTrains = new ArrayList<>();

    // Booking counter for passenger bookings
    static int bookingCounter = 100;

    // Adding static indexes and variables
    static final int TRAIN_NAME      = 0;
    static final int TRAIN_ID        = 1;
    static final int ROUTE           = 2;
    static final int DEPARTURE_CITY  = 3;
    static final int ARRIVAL_CITY    = 4;
    static final int STATIONS        = 5;
    static final int DEPARTURE_TIME  = 6;
    static final int ARRIVAL_TIME    = 7;
    static final int TOTAL_SEATS     = 8;
    static final int BOOKED_SEATS    = 9;
    static final int REMAINING_SEATS = 10;
    static final int SEAT_TYPES      = 11;


    // ================= MAIN =================
    public static void main(String[] args) {
        {
            loadTrainsFromFile();           // Load saved trains from file

            loadDeletedTrainsFromFile();    // Load deleted trains from file

            if (trains.isEmpty())          // If no train data exists, fill default data
            {
                fillTrainData();
                saveTrainsToFile();
            }

            mainMenu();                    // Show main menu
        }
    }

    // ================= VALIDATION =================
    public static boolean isValidTrainID(String id) {
        if (id == null) return false;

        try {
            if (!id.matches("\\d+UP\\/\\d+DN"))    // Check format
                return false;

            // Split UP and DN parts
            String upString = id.substring(0, id.indexOf("UP"));
            String dnString = id.substring(id.indexOf("/") + 1, id.indexOf("DN"));

            int up = Integer.parseInt(upString);
            int dn = Integer.parseInt(dnString);

            // DN must be UP + 1
            return dn == up + 1;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    // ---------------- TRAIN DATA ----------------
    // Each block = one train record
    public static void fillTrainData() {

        trains.add(new String[]{
            "Subak Raftar Express",                                   // train name
            "101UP/102DN",                                            // train id
            "Rawalpindi to Lahore",                                   // route
            "Rawalpindi",                                             // departure city
            "Lahore",                                                 // arrival city
            "Gujar Khan, Jhelum, Gujrat",                            // stations
            "07:00 am",                                               // departure time
            "11:55 am",                                               // arrival time
            "70",                                                     // total seats
            "35",                                                     // booked seats
            "35",                                                     // remaining seats
            "18 Economy seats, 9 AC seats, 8 Luxury seats"           // seat types
        });

        trains.add(new String[]{
            "Subak Kharam Express",
            "103UP/104DN",
            "Rawalpindi to Lahore",
            "Rawalpindi",
            "Lahore",
            "Gujar Khan, Jhelum, Gujranwala",
            "04:30 pm",
            "09:35 pm",
            "70",
            "35",
            "35",
            "18 Economy seats, 9 AC seats, 8 Luxury seats"
        });

        trains.add(new String[]{
            "Rawal Express",
            "105UP/106DN",
            "Rawalpindi to Lahore",
            "Rawalpindi",
            "Lahore",
            "Lala Musa, Jhelum, Chak Lala",
            "12:30 am",
            "04:45 am",
            "70",
            "35",
            "35",
            "18 Economy seats, 9 AC seats, 8 Luxury seats"
        });

        trains.add(new String[]{
            "Islamabad Express",
            "107UP/108DN",
            "Rawalpindi to Lahore",
            "Rawalpindi",
            "Lahore",
            "Gujranwala, Gujrat",
            "05:15 pm",
            "10:35 pm",
            "70",
            "0",
            "70",
            "35 Economy seats, 18 AC seats, 17 Luxury seats"
        });

        trains.add(new String[]{
            "Karachi Express",
            "15UP/16DN",
            "Karachi to Lahore",
            "Karachi",
            "Lahore",
            "Hyderabad, Rohri, Khanewal",
            "06:30 pm",
            "01:00 pm (Next Day)",
            "70",
            "35",
            "35",
            "18 Economy seats,  9 AC seats, 8 Luxury seats"
        });

        trains.add(new String[]{
            "Shalimar Express",
            "27UP/28DN",
            "Karachi to Lahore",
            "Karachi",
            "Lahore",
            "Khanewal, Rohri, Hyderabad",
            "06:00 am",
            "01:45 am (Next Day)",
            "70",
            "0",
            "70",
            "35 Economy seats, 18 AC seats, 17 Luxury seats"
        });

        trains.add(new String[]{
            "Pak Business Express",
            "33UP/34DN",
            "Karachi to Lahore",
            "Karachi",
            "Lahore",
            "Sahiwal, Multan, Hyderabad",
            "04:00 pm",
            "10:20 am (Next Day)",
            "70",
            "35",
            "35",
            "18 Economy seats, 9 AC seats, 8 Luxury seats"
        });

        trains.add(new String[]{
            "Karakoram Express",
            "41UP/42DN",
            "Karachi to Lahore",
            "Karachi",
            "Lahore",
            "Hyderabad, Rohri, Multan",
            "03:00 pm",
            "09:20 am (Next Day)",
            "70",
            "0",
            "70",
            "35 Economy seats, 18 AC seats, 17 Luxury seats"
        });

        trains.add(new String[]{
            "Awam Express",
            "13UP/14DN",
            "Karachi to Peshawar",
            "Karachi",
            "Peshawar",
            "Rohri, Multan, Lahore",
            "07:00 am",
            "04:45 pm",
            "70",
            "35",
            "35",
            "18 Economy seats, 9 AC seats, 8 Luxury seats"
        });

        trains.add(new String[]{
            "Hazara Express",
            "11UP/12DN",
            "Karachi to Havelian",
            "Karachi",
            "Havelian",
            "Multan, Lahore, Rawalpindi",
            "06:35 am",
            "04:40 pm (Next Day)",
            "70",
            "0",
            "70",
            "35 Economy seats, 18 AC seats, 17 Luxury seats"
        });

        trains.add(new String[]{
            "Khyber Mail",
            "01UP/02DN",
            "Karachi to Peshawar",
            "Karachi",
            "Peshawar",
            "Hyderabad, Multan, Lahore",
            "10:15 pm",
            "05:20 am (The 3rd Day)",
            "70",
            "35",
            "35",
            "18 Economy seats, 9 AC seats, 8 Luxury seats "
        });

        trains.add(new String[]{
            "Shah Hussain Express",
            "43UP/44DN",
            "Lahore to Multan",
            "Lahore",
            "Multan",
            "Khanewal, Sahiwal, Okara",
            "01:15 am",
            "09:00 am",
            "70",
            "0",
            "70",
            "35 Economy seats, 18 AC seats, 17 Luxury seats"
        });

        trains.add(new String[]{
            "Tezgam Express",
            "07UP/08DN",
            "Lahore to Multan",
            "Lahore",
            "Multan",
            "Khanewal, Sahiwal",
            "01:05 pm",
            "06:45 pm",
            "70",
            "35",
            "35",
            "18 Economy seats, 9 AC seats, 8 Luxury seats "
        });

        trains.add(new String[]{
            "Bahuddin Zakaria Express",
            "25UP/26DN",
            "Karachi to Multan",
            "Karachi",
            "Multan",
            "Hyderabad, Rohri, Rawalpindi",
            "06:30 pm",
            "11:30 am",
            "70",
            "35",
            "35",
            "18 Economy seats, 9 AC seats, 8 Luxury seats "
        });

        trains.add(new String[]{
            "Fareed Express",
            "37UP/38DN",
            "Karachi to Lahore",
            "Karachi",
            "Lahore",
            "Hyderabad, Khanewal, Sahiwal",
            "07:30 pm",
            "08:30 pm (The Next Day)",
            "70",
            "0",
            "70",
            "35 Economy seats, 18 AC seats, 17 Luxury seats"
        });
    }

    // ================= SAVE TRAIN DATA TO FILE =================
    public static void saveTrainsToFile() {
        try {
            // Create file writer
            PrintWriter writer = new PrintWriter("C:\\MWM\\trains.txt");

            // Write each train in file
            for (String[] train : trains) {
                for (int i = 0; i < train.length; i++) {
                    writer.print(train[i]);

                    // Add separator between fields
                    if (i < train.length - 1)
                        writer.print("|");
                }
                writer.println();
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Error Saving File!");
        }
    }

    // ================= LOAD TRAIN DATA FROM FILE =================
    public static void loadTrainsFromFile() {
        try {
            File file = new File("C:\\MWM\\trains.txt");

            if (!file.exists())
                return;

            Scanner fileScanner = new Scanner(file);

            trains.clear();

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                String[] train = line.split("\\|");

                if (train.length == 12)
                    trains.add(train);
            }

            fileScanner.close(); // IMPORTANT
        }
        catch (Exception e) {
            System.out.println("Error Loading File!");
        }
    }

    // ================= SAVE DELETED TRAINS =================
    public static void saveDeletedTrainsToFile() {
        try {
            PrintWriter writer = new PrintWriter("C:\\MWM\\deletedTrains.txt");

            for (String[] train : deletedTrains) {
                for (int i = 0; i < train.length; i++) {
                    writer.print(train[i]);

                    if (i < train.length - 1)
                        writer.print("|");
                }
                writer.println();
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Error Saving Deleted Trains!");
        }
    }

    // ================= LOAD DELETED TRAINS =================
    public static void loadDeletedTrainsFromFile() {
        try {
            File file = new File("C:\\MWM\\deletedTrains.txt");

            if (!file.exists())
                return;

            Scanner fileScanner = new Scanner(file);

            deletedTrains.clear();

            while (fileScanner.hasNextLine()) {
                String[] train = fileScanner.nextLine().split("\\|");

                if (train.length >= 2)
                    deletedTrains.add(train);
            }
            fileScanner.close();
        }
        catch (Exception e) {
            System.out.println("Error Loading Deleted Trains!");
        }
    }

    // ================= MAIN MENU =================
    public static void mainMenu() {
        while (true) {
            System.out.println("\n---------------- WELCOME TO THE MWM TRAINS! ----------------");
            System.out.println("Choose your area of interest:");
            System.out.println("1. Admin");
            System.out.println("2. Passenger");
            System.out.println("3. Exit");

            System.out.print("\nEnter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid Input!");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> adminLogin();

                case 2 -> passengerMenu();

                case 3 -> {
                    System.out.println("Program Terminated.");
                    System.exit(0);
                }

                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    // ================= ADMIN LOGIN =================
    public static void adminLogin() {
        System.out.println("\n----------- ADMIN LOGIN -----------");
        System.out.println("Hint: Founder birth year style key");

        System.out.print("Enter Admin Key: ");

        // input validation
        if (!sc.hasNextInt()) {
            System.out.println("Invalid Input! Numbers only.");
            sc.nextLine();
            return;
        }

        int key = sc.nextInt();
        sc.nextLine();

        // hardcoded secure key
        if (key == 181819) {
            System.out.println("Access Granted.");
            adminMenu();
        }
        else {
            System.out.println("Wrong Admin Key!");
        }
    }

    // ================= ADMIN MENU =================
    public static void adminMenu() {
        while (true) {
            System.out.println("\n---------------- ADMIN MENU ----------------");

            System.out.println("1. View all Trains");
            System.out.println("2. Add a Train");
            System.out.println("3. Delete a Train");
            System.out.println("4. Edit Train Information");
            System.out.println("5. View Deleted Trains");
            System.out.println("6. Return to Main Menu");

            System.out.print("\nEnter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid Input!");
                sc.nextLine();
                continue;
            }

            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> viewTrainData();

                case 2 -> addTrain();

                case 3 -> deleteTrain();

                case 4 -> editTrain();

                case 5 -> viewDeletedTrains();

                case 6 -> {
                    return;
                }

                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    // ================= VIEW =================
    public static void viewTrainData() {
        System.out.println("\n---------------- TRAIN DATA ----------------");

        if (trains.isEmpty()) {
            System.out.println("No trains available.");
            return;
        }

        for (String[] train : trains) {
            if (train == null || train.length < 12) continue;

            System.out.println("Train Name: "        + train[TRAIN_NAME]);
            System.out.println("Train ID: "          + train[TRAIN_ID]);
            System.out.println("Route: "             + train[ROUTE]);
            System.out.println("Departure City: "    + train[DEPARTURE_CITY]);
            System.out.println("Arrival City: "      + train[ARRIVAL_CITY]);
            System.out.println("Stations: "          + train[STATIONS]);
            System.out.println("Departure Time: "    + train[DEPARTURE_TIME]);
            System.out.println("Arrival Time: "      + train[ARRIVAL_TIME]);
            System.out.println("Total Seats: "       + train[TOTAL_SEATS]);
            System.out.println("Booked Seats: "      + train[BOOKED_SEATS]);
            System.out.println("Remaining Seats: "   + train[REMAINING_SEATS]);
            System.out.println("Types of seats available: " + train[SEAT_TYPES]);

            System.out.println("----------------------");
        }
    }

    // ================= VIEW DELETED TRAIN =================
    public static void viewDeletedTrains() {
        System.out.println("Total Deleted Trains: " + deletedTrains.size());
        System.out.println("\n----------- DELETED TRAINS -----------");

        if (deletedTrains.isEmpty()) {
            System.out.println("No Deleted Trains Found.");
            return;
        }

        for (String[] train : deletedTrains) {
            if (train == null || train.length < 8) continue;

            System.out.println("Train Name: "      + train[0]);
            System.out.println("Train ID: "        + train[1]);
            System.out.println("Route: "           + train[2]);
            System.out.println("Departure City: "  + train[3]);
            System.out.println("Arrival City: "    + train[4]);
            System.out.println("Stations: "        + train[5]);
            System.out.println("Departure Time: "  + train[6]);
            System.out.println("Arrival Time: "    + train[7]);

            System.out.println("----------------------");
        }
    }

    // ================= ADD TRAIN =================
    public static void addTrain() {
        String[] train = new String[12];

        System.out.print("Train Name: ");
        train[0] = sc.nextLine();

        while (true) {
            System.out.print("Enter Train ID (xxUP/xxDN where DN=UP+1): ");
            train[1] = sc.nextLine();

            boolean exists = false;

            for (String[] t : trains) {
                if (t[1].equals(train[1])) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Train ID already exists!");
            }
            else if (isValidTrainID(train[1])) {
                break;
            }
            else {
                System.out.println("Invalid ID!");
            }
        }

        System.out.print("Route: ");
        train[2] = sc.nextLine();

        System.out.print("Departure City: ");
        train[3] = sc.nextLine();

        System.out.print("Arrival City: ");
        train[4] = sc.nextLine();

        System.out.print("Stations: ");
        train[5] = sc.nextLine();

        System.out.print("Departure Time: ");
        train[6] = sc.nextLine();

        System.out.print("Arrival Time: ");
        train[7] = sc.nextLine();

        train[8]  = "70";
        train[9]  = "0";
        train[10] = "70";
        train[11] = "35 Economy seats, 18 AC seats, 17 Luxury seats";

        trains.add(train);
        saveTrainsToFile();

        System.out.println("Train Added Successfully!");
    }

    // ================= DELETE =================
    public static void deleteTrain() {
        System.out.print("Enter Train ID to delete: ");
        String id = sc.nextLine();

        for (int i = 0; i < trains.size(); i++) {
            String[] train = trains.get(i);

            if (train != null && train.length > 1 && train[1].equals(id)) {
                // add to deleted list
                deletedTrains.add(train);

                // remove from active list
                trains.remove(i);

                // save both files
                saveTrainsToFile();
                saveDeletedTrainsToFile();

                System.out.println("Train Deleted Successfully!");
                return;
            }
        }

        System.out.println("Train Not Found!");
    }

    // ================= EDIT =================
    public static void editTrain() {
        System.out.print("Enter Train ID to edit: ");
        String id = sc.nextLine();

        for (String[] train : trains) {
            if (train[1].equals(id)) {
                System.out.print("Enter New Train Name: ");
                train[0] = sc.nextLine();

                System.out.print("Enter New Departure Time: ");
                train[6] = sc.nextLine();

                System.out.print("Enter New Arrival Time: ");
                train[7] = sc.nextLine();

                saveTrainsToFile();

                System.out.println("Train Updated Successfully!");
                return;
            }
        }

        System.out.println("Train Not Found!");
    }


    // ======================================================
    // =================== PASSENGER SECTION ===============
    // ======================================================

    public static void passengerMenu() {
        while (true) {
            System.out.println("\n ========== PASSENGER MENU ===========\n");
            System.out.println("1: Book Ticket ");
            System.out.println("2: Cancel Ticket ");
            System.out.println("3: Veiw Trains ");
            System.out.println("4: Search & Book by City ");
            System.out.println("5: Exit ");

            int choice;

            while (true) {
                System.out.print("Enter choice: ");

                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                }
                else {
                    System.out.println("Invalid input. Enter a number.");
                    sc.next(); // discard wrong input
                }
            }

            switch (choice) {
                case 1: bookTicket();      break;
                case 2: cancelTicket();    break;
                case 3: veiwTrains();      break;
                case 4: searchAndBook();   break;
                case 5: { return; }

                default: System.out.println("Invalid choice ");
                    break;
            }
        }
    }

    public static String[] selectTrains() {
        // This will check for specific train that user has added

        System.out.println("\n AVAILABLE TRAINS \n\n\n");
        for (String[] train : trains) {
            System.out.println("Train id " + train[TRAIN_ID] + "|" + "TRAIN NAME " + train[TRAIN_NAME] +
                "|" + "AVAILABLE SEATS " + (Integer.parseInt(train[TOTAL_SEATS]) - Integer.parseInt(train[BOOKED_SEATS])));
        }
        System.out.println("Enter train id \n kindly make sure that you add the exacct id ! \n");
        String id = sc.nextLine();

        for (String[] train : trains) {
            if (train[TRAIN_ID].equals(id)) {
                return train;
            }
        }
        System.out.println("Train not found ");
        return null; // cannot return zero because it is string array
    }

    public static void veiwTrains() {
        for (String[] train : trains) {
            System.out.println(train[0] + "|" + train[1]);
        }
    }

    public static void bookTicket() {
        String[] train = selectTrains();

        if (train == null) return;

        int totalseats = Integer.parseInt(train[TOTAL_SEATS]);
        int bookedseats = Integer.parseInt(train[BOOKED_SEATS]);

        if (bookedseats >= totalseats) {
            System.out.println("No seats available");
            return;
        }

        int people = 0;

        // exception handling that if user enters anything else than a digit for age so program should return instead of crashing
        try {
            System.out.print("How many tickets do you want to book? ");
            people = sc.nextInt();
            sc.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Kindly enter a valid age in digits ");
        }

        int totalBill = 0;

        for (int i = 1; i <= people; i++) {
            System.out.println("\n--- Passenger " + i + " ---");

            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Are you a student? (yes/no): ");
            String student = sc.nextLine();

            System.out.println("Select seat type:");
            System.out.println("1. Economy - 1000");
            System.out.println("2. AC - 1500");
            System.out.println("3. Luxury - 2000");

            int seatChoice = sc.nextInt();
            sc.nextLine();

            int price = switch (seatChoice) {
                case 1 -> 1000;   // lambda arrow operator use kia hai that means like ke take val from left and perform something on right for example if main pe 1000 likh rai hun that means case 1 pe 1000 return kar dain , so yeahhh khudd likha hai sara code <..>
                case 2 -> 1500;
                case 3 -> 2000;
                default -> 0;
            };

            if (price == 0) {
                System.out.println("Invalid seat type, skipping passenger");
                continue;
            }

            int studentDiscount = student.equalsIgnoreCase("yes") ? 50 : 0; // ternary operator usage

            int ageDiscount = 0;
            if (age < 10) ageDiscount = 10;
            else if (age > 50) ageDiscount = 20;

            int discount = Math.max(studentDiscount, ageDiscount);

            int finalBill = price - (price * discount / 100);

            totalBill += finalBill;

            String bookingID = "B" + bookingCounter;
            bookingCounter++;

            String seatType = "";

            switch (seatChoice) {
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
                train[TRAIN_ID],
                train[TRAIN_NAME],
                seatType,
                finalBill,
                discount
            );

            System.out.println("\n--- BILL ---");
            System.out.println("Name: "      + name);
            System.out.println("Seat Price: " + price);
            System.out.println("Discount: "  + discount + "%");
            System.out.println("Booking ID: " + bookingID);
            System.out.println("Final Bill: " + finalBill);
        }

        // Update seats AFTER all bookings
        bookedseats += people;
        train[BOOKED_SEATS] = String.valueOf(bookedseats);

        saveTrainsToFile();

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
                                   int discount) {
        try {
            File file = new File("C:\\MWM\\bookings.txt");

            // create file if missing
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file, true);

            PrintWriter writer = new PrintWriter(fos);

            writer.println(
                bookingID + "|" +
                name      + "|" +
                age       + "|" +
                trainID   + "|" +
                trainName + "|" +
                seatType  + "|" +
                finalBill + "|" +
                discount
            );

            writer.close();

            System.out.println("BOOKING SAVED SUCCESSFULLY");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelTicket() {
        System.out.println("Enter your booking id ");
        String bookingID = sc.nextLine();
        ArrayList<String> updateBookings = new ArrayList<>();
        boolean found = false;

        try {
            File file = new File("C:\\MWM\\bookings.txt");
            Scanner filScanner = new Scanner(file);
            // check if inp is a string
            while (filScanner.hasNextLine()) {
                String line = filScanner.nextLine();
                String[] booking = line.split("\\|");

                if (booking[0].equals(bookingID)) {
                    found = true;
                    String trainID = booking[3];
                    for (String[] train : trains) {
                        if (train[TRAIN_ID].equals(trainID)) {
                            int bookedseats = Integer.parseInt(train[BOOKED_SEATS]);

                            bookedseats--;
                            train[BOOKED_SEATS] = String.valueOf(bookedseats);
                            break;
                        }
                    }
                    continue;
                }
                updateBookings.add(line);
            }
            filScanner.close();
            PrintWriter writer = new PrintWriter(file);
            for (String booking : updateBookings) {
                writer.println(booking);
            }
            writer.close();
            saveTrainsToFile();

            if (found) {
                System.out.println("Trained cancelled successfully ");
            }
            else {
                System.out.println("System ID not found ");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // abhi aik cheez implement karni hai or wo ye hai ke pehle make sure krainke bookings hoi hain ke nai
    // us ke baad ji bookings update hunn

    // ================= SEARCH & BOOK BY CITY =================
    public static void searchAndBook() {

        // Step 1: take city input from user
        System.out.print("Enter Departure City: ");
        String depCity = sc.nextLine();

        System.out.print("Enter Arrival City: ");
        String arrCity = sc.nextLine();

        // Step 2: collect all trains that match both cities
        ArrayList<String[]> matchingTrains = new ArrayList<>();

        for (String[] train : trains) {
            if (train[DEPARTURE_CITY].equalsIgnoreCase(depCity) &&
                train[ARRIVAL_CITY].equalsIgnoreCase(arrCity)) {
                matchingTrains.add(train);
            }
        }

        // Step 3: if nothing found, inform and return
        if (matchingTrains.isEmpty()) {
            System.out.println("No trains found for this route.");
            return;
        }

        // Step 4: display matched trains
        System.out.println("\n ===== TRAINS AVAILABLE FOR YOUR ROUTE =====\n");

        for (String[] train : matchingTrains) {
            int available = Integer.parseInt(train[TOTAL_SEATS]) - Integer.parseInt(train[BOOKED_SEATS]);

            System.out.println("Train Name    : " + train[TRAIN_NAME]);
            System.out.println("Train ID      : " + train[TRAIN_ID]);
            System.out.println("Route         : " + train[ROUTE]);
            System.out.println("Departure Time: " + train[DEPARTURE_TIME]);
            System.out.println("Arrival Time  : " + train[ARRIVAL_TIME]);
            System.out.println("Stations      : " + train[STATIONS]);
            System.out.println("Seat Types    : " + train[SEAT_TYPES]);

            // clearly mark if train is fully booked
            if (available <= 0) {
                System.out.println("Availability  : FULLY BOOKED");
            } else {
                System.out.println("Available Seats: " + available);
            }

            System.out.println("----------------------");
        }

        // Step 5: ask if user wants to book
        System.out.print("Do you want to book a ticket? (yes/no): ");
        String answer = sc.nextLine();

        if (!answer.equalsIgnoreCase("yes")) {
            return;
        }

        // Step 6: let user pick a train from the matched list only
        System.out.print("Enter Train ID from the list above: ");
        String chosenID = sc.nextLine();

        String[] selectedTrain = null;

        for (String[] train : matchingTrains) {
            if (train[TRAIN_ID].equals(chosenID)) {
                selectedTrain = train;
                break;
            }
        }

        // Step 7: validate selection
        if (selectedTrain == null) {
            System.out.println("Invalid Train ID. Returning to menu.");
            return;
        }

        int totalseats  = Integer.parseInt(selectedTrain[TOTAL_SEATS]);
        int bookedseats = Integer.parseInt(selectedTrain[BOOKED_SEATS]);

        if (bookedseats >= totalseats) {
            System.out.println("Sorry, this train is fully booked.");
            return;
        }

        // Step 8: proceed with booking — same logic as bookTicket()
        int people = 0;

        try {
            System.out.print("How many tickets do you want to book? ");
            people = sc.nextInt();
            sc.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Kindly enter a valid number in digits.");
            sc.nextLine();
            return;
        }

        int totalBill = 0;

        for (int i = 1; i <= people; i++) {
            System.out.println("\n--- Passenger " + i + " ---");

            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Are you a student? (yes/no): ");
            String student = sc.nextLine();

            System.out.println("Select seat type:");
            System.out.println("1. Economy - 1000");
            System.out.println("2. AC - 1500");
            System.out.println("3. Luxury - 2000");

            int seatChoice = sc.nextInt();
            sc.nextLine();

            int price = switch (seatChoice) {
                case 1 -> 1000;
                case 2 -> 1500;
                case 3 -> 2000;
                default -> 0;
            };

            if (price == 0) {
                System.out.println("Invalid seat type, skipping passenger");
                continue;
            }

            int studentDiscount = student.equalsIgnoreCase("yes") ? 50 : 0;

            int ageDiscount = 0;
            if (age < 10) ageDiscount = 10;
            else if (age > 50) ageDiscount = 20;

            int discount = Math.max(studentDiscount, ageDiscount);

            int finalBill = price - (price * discount / 100);

            totalBill += finalBill;

            String bookingID = "B" + bookingCounter;
            bookingCounter++;

            String seatType = "";

            switch (seatChoice) {
                case 1: seatType = "Economy"; break;
                case 2: seatType = "AC";      break;
                case 3: seatType = "Luxury";  break;
            }

            saveBooking(
                bookingID,
                name,
                age,
                selectedTrain[TRAIN_ID],
                selectedTrain[TRAIN_NAME],
                seatType,
                finalBill,
                discount
            );

            System.out.println("\n--- BILL ---");
            System.out.println("Name: "       + name);
            System.out.println("Seat Price: " + price);
            System.out.println("Discount: "   + discount + "%");
            System.out.println("Booking ID: " + bookingID);
            System.out.println("Final Bill: " + finalBill);
        }

        // Update booked seats AFTER all passengers done
        bookedseats += people;
        selectedTrain[BOOKED_SEATS] = String.valueOf(bookedseats);

        saveTrainsToFile();

        System.out.println("\n========================");
        System.out.println("TOTAL GROUP BILL: " + totalBill);
        System.out.println("========================");
    }
}
