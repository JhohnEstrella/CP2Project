package Defense;

import java.io.*;
import java.time.*;
import java.util.*;

public class Final_Project {
    private static List<reservationSystem> reservations = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {  
        Flight.initializeFlights();
        seatManager.loadBookedSeats(reservations);
        while (true) {
            System.out.println("\n=== Flight Booking System ===");
            System.out.println("1. Book a Flight");
            System.out.println("2. Edit Booking");
            System.out.println("3. View Reservation");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    bookFlight();
                    break;
                case 2:
                    editBooking();
                    break;
                case 3:
                    viewReservation();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    System.out.println("Thank you for using the Flight Booking System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private static reservationSystem findReservationByID(String bookingID) {
        if (bookingID == null || bookingID.trim().isEmpty()) {
            return null;
        }
        for (reservationSystem reservation : reservations) {
            if (reservation.getBookingID().equalsIgnoreCase(bookingID)) {
                return reservation;
            }
        }
        return null;
    }

    public static void bookFlight() throws IOException {
        System.out.println("\n--- Book a Flight ---");

        System.out.print("Enter passenger name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        boolean validAge = false;
        String age = "";
        do {
            System.out.print("Enter passenger age: ");
            String ageInput = scanner.nextLine().trim();
            if (isValidAge(ageInput)) {
                age = ageInput;
                validAge = true;
            }
        } while (!validAge);

        String contactNumber;
        boolean validNumber = false;
        do {
            System.out.print("Enter contact number (e.g., 09XXXXXXXXX or +63XXXXXXXXXX): ");
            contactNumber = scanner.nextLine().trim();
            if (contactNumber.matches("^09\\d{9}$") || contactNumber.matches("^\\+63\\d{10}$")) {
                validNumber = true;
            } else {
                System.out.println("Invalid format. Use '09XXXXXXXXX' (11 digits) or '+63XXXXXXXXXX' (12 digits).");
            }
        } while (!validNumber);

        System.out.println("Is the flight Domestic or International?");
        String choice = scanner.nextLine().trim().toLowerCase();

        Flight selectedFlight = null;
        int flightChoice = -1;

        List<Flight> domesticFlights = new ArrayList<>();
        List<Flight> internationalFlights = new ArrayList<>();

        passenger passengerObj;
        if (choice.equals("international")) {
            System.out.print("Enter Passport No.: ");
            String passportNumber = scanner.nextLine().trim();
            if (passportNumber.isEmpty()) {
                System.out.println("Passport number is required for international flights.");
                return;
            }
            passengerObj = new passenger(name, age, contactNumber, passportNumber);
        } else {
            passengerObj = new passenger(name, age, contactNumber);
        }

        switch (choice) {
            case "domestic":
                System.out.println("\nAvailable Domestic Flights:");
                int index = 1;
                for (Flight flight : Flight.getFlights()) {
                    if (flight.getFlightType().equalsIgnoreCase("Domestic")) {
                        domesticFlights.add(flight);
                        double fare = ((domesticFlight) flight).calculateDomesticFare();
                        System.out.printf("%d. %s -> %s, Dep: %s, Arr: %s, Total Fare: %.2f\n",
                            index, flight.getFlightType(), flight.getDestination(),
                            flight.getDepartureTime(), flight.getArrivalTime(), fare);
                        index++;
                    }
                }
                break;

            case "international":
                System.out.println("\nAvailable International Flights:");
                int index_ = 1;
                for (Flight flight : Flight.getFlights()) {
                    if (flight.getFlightType().equalsIgnoreCase("International")) {
                        internationalFlights.add(flight);
                        double fare = ((internationalFlight) flight).calculateInternationalFare();
                        System.out.printf("%d. %s -> %s, Dep: %s, Arr: %s, Total Fare: %.2f\n",
                            index_, flight.getFlightType(), flight.getDestination(),
                            flight.getDepartureTime(), flight.getArrivalTime(), fare);
                        index_++;
                    }
                }
                break;

            default:
                System.out.println("Invalid selection. Please enter 'Domestic' or 'International'.");
                return;
        }

        if (choice.equals("domestic") && domesticFlights.isEmpty()) {
            System.out.println("No domestic flights available.");
            return;
        } else if (choice.equals("international") && internationalFlights.isEmpty()) {
            System.out.println("No international flights available.");
            return;
        }

        System.out.print("Select a flight number: ");
        try {
            flightChoice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
            return;
        }

        if (flightChoice < 1 || flightChoice > (choice.equals("domestic") ? domesticFlights.size() : internationalFlights.size())) {
            System.out.println("Invalid flight selection.");
            return;
        }

        selectedFlight = choice.equals("domestic") ? domesticFlights.get(flightChoice - 1) : internationalFlights.get(flightChoice - 1);

        seatManager[] seats = selectedFlight.getSeats();
        boolean hasAvailableSeats = false;
        for (seatManager seat : seats) {
            if (!seat.isSeatBooked()) {
                hasAvailableSeats = true;
                break;
            }
        }
        if (!hasAvailableSeats) {
            System.out.println("No seats available for this flight.");
            return;
        }

        System.out.println("\nAvailable Seats:");
        for (seatManager seat : seats) {
            if (!seat.isSeatBooked()) {
                System.out.print(" " + seat.getSeatNumber() + " ");
            }
        }

        System.out.print("\nEnter seat number: ");
        String seatNumber = scanner.nextLine().toUpperCase().trim();
        seatManager selectedSeat = null;

        for (seatManager seat : seats) {
            if (seat.getSeatNumber().equals(seatNumber) && !seat.isSeatBooked()) {
                selectedSeat = seat;
                break;
            }
        }

        if (selectedSeat == null) {
            System.out.println("Seat " + seatNumber + " is unavailable or invalid.");
            return;
        }

        selectedSeat.booked();
        String bookingID = UUID.randomUUID().toString().substring(0, 8);

        double totalFare = selectedFlight instanceof domesticFlight 
            ? ((domesticFlight) selectedFlight).calculateDomesticFare()
            : ((internationalFlight) selectedFlight).calculateInternationalFare();

        LocalDateTime bookingDateTime = LocalDateTime.now();

        reservationSystem reservation = new reservationSystem(
            passengerObj, selectedFlight, selectedSeat, bookingID, totalFare, bookingDateTime
        );

        System.out.println("\nBooking successful! Here is your receipt:\n");
        reservation.printReceipt();
        reservation.savePrintReceipt();
        reservations.add(reservation);
        System.out.println("Reservation added to the system. Your Booking ID is: " + bookingID);
    }

    public static void editBooking() throws IOException {
        if (reservations.isEmpty()) {
            System.out.println("No reservations available to edit.");
            return;
        }

        System.out.println("\n--- Edit Booking ---");
        System.out.print("Enter your Booking ID: ");
        String bookingID = scanner.nextLine().trim();

        reservationSystem reservation = findReservationByID(bookingID);
        if (reservation == null) {
            System.out.println("No booking found with ID: " + bookingID);
            return;
        }

        System.out.println("Current Reservation Details:");
        reservation.printReceipt();

        // Edit contact number
        String contactNumber;
        boolean validNumber = false;
        do {
            System.out.print("Enter new contact number (Current: " + 
                             reservation.getPassenger().getContactNumber() + 
                             ") or press Enter to keep current: ");
            contactNumber = scanner.nextLine().trim();
            if (contactNumber.isEmpty()) {
                validNumber = true; // Keep current
            } else if (contactNumber.matches("^09\\d{9}$") || contactNumber.matches("^\\+63\\d{10}$")) {
                reservation.getPassenger().setContactNumber(contactNumber);
                validNumber = true;
            } else {
                System.out.println("Invalid format. Use '09XXXXXXXXX' (11 digits) or '+63XXXXXXXXXX' (12 digits).");
            }
        } while (!validNumber);

        // Edit age
        boolean validAge = false;
        do {
            System.out.print("Enter new age (Current: " + 
                             reservation.getPassenger().getAge() + 
                             ") or press Enter to keep current: ");
            String newAge = scanner.nextLine().trim();
            if (newAge.isEmpty()) {
                validAge = true; // Keep current
            } else if (isValidAge(newAge)) {
                reservation.getPassenger().setAge(newAge);
                validAge = true;
            }
        } while (!validAge);

        // Edit passport number for international flights
        if (reservation.getFlight().getFlightType().equalsIgnoreCase("International")) {
            System.out.print("Enter new passport number (Current: " +
                             (reservation.getPassenger().getPassportNumber() != null ? 
                              reservation.getPassenger().getPassportNumber() : "None") + 
                             ") or press Enter to keep current: ");
            String newPassportNumber = scanner.nextLine().trim();
            if (!newPassportNumber.isEmpty()) {
                reservation.getPassenger().setPassportNumber(newPassportNumber);
            }
        }

        System.out.print("Confirm Changes? (Y/N): ");
        String confirmation = scanner.nextLine().trim().toUpperCase();
        if (confirmation.equals("Y")) {
            reservation.savePrintReceipt();
            System.out.println("Booking updated successfully. New receipt generated.");
        } else {
            System.out.println("Changes discarded.");
        }
    }

    private static boolean isPositiveInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidAge(String ageStr) {
        if (!isPositiveInteger(ageStr)) {
            System.out.println("Error: Age must be a positive number.");
            return false;
        }
        int age = Integer.parseInt(ageStr);
        if (age < 18) {
            System.out.println("You're too young to book a flight!");
            return false;
        }
        return true;
    }

    public static void viewReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations available.");
            return;
        }

        System.out.print("Enter your Booking ID: ");
        String searchID = scanner.nextLine().trim();

        reservationSystem reservation = findReservationByID(searchID);
        if (reservation == null) {
            System.out.println("No booking found with ID: " + searchID);
            return;
        }

        System.out.println("\n--- Reservation Details ---");
        reservation.printReceipt();
        System.out.println("--- End of Reservation Details ---");
    }

    public static void cancelBooking() throws IOException {
        if (reservations.isEmpty()) {
            System.out.println("No bookings found to cancel.");
            return;
        }

        System.out.print("Enter the Booking ID youcite wish to cancel: ");
        String cancelID = scanner.nextLine().trim();

        reservationSystem toCancel = findReservationByID(cancelID);
        if (toCancel == null) {
            System.out.println("No booking found with ID: " + cancelID);
            return;
        }

        System.out.println("Reservation Details:");
        toCancel.printReceipt();

        while (true) {
            System.out.print("Are you sure you want to cancel the booking? (yes/no): ");
            String confirmation = scanner.nextLine().toLowerCase().trim();

            switch (confirmation) {
                case "yes":
                    seatManager seat = toCancel.getSeats();
                    if (seat != null) {
                        seat.bookingCancelled();
                        String receiptFileName = "receipt_" + toCancel.getBookingID() + ".txt";
                        File receiptFile = new File(receiptFileName);
                        if (receiptFile.exists()) {
                            if (receiptFile.delete()) {
                                System.out.println("Receipt file " + receiptFileName + " deleted successfully.");
                            } else {
                                System.out.println("Failed to delete receipt file " + receiptFileName + ".");
                            }
                        }
                        reservations.remove(toCancel);
                        System.out.println("Booking cancelled successfully.");
                    } else {
                        System.out.println("Seat info not found. Cannot cancel booking properly.");
                    }
                    return;
                case "no":
                    System.out.println("Cancellation aborted.");
                    return;
                default:
                    System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        }
    }
}