package Defense;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class seatManager {
    private String seatNumber;
    private boolean seatBooked;

    public seatManager(String seatNumber, boolean seatBooked) {
        this.seatNumber = seatNumber;
        this.seatBooked = seatBooked;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isSeatBooked() {
        return seatBooked;
    }

    public void setSeatBooked(boolean seatBooked) {
        this.seatBooked = seatBooked;
    }
    
    public void booked() {
        this.seatBooked = true; // means that the seat is taken       
    }
    
    public void bookingCancelled() {
        this.seatBooked = false; // cancelled booking 
    }

    public static void loadBookedSeats(List<reservationSystem> reservations) throws IOException {
        File directory = new File(".");
        File[] receiptFiles = directory.listFiles((dir, name) -> name.startsWith("receipt_") && name.endsWith(".txt"));
        if (receiptFiles == null || receiptFiles.length == 0) {
            System.out.println("No receipt files found.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (File file : receiptFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String bookingID = null;
                String name = null;
                String age = null;
                String contactNumber = null;
                String passportNumber = null;
                String destination = null;
                String seatNumber = null;
                String departureTimeStr = null;
                String flightType = null;
                double totalFare = 0.0;
                LocalDateTime bookingDateTime = null;
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Booking ID: ")) {
                        bookingID = line.substring("Booking ID: ".length()).trim();
                    } else if (line.startsWith("Name: ")) {
                        name = line.substring("Name: ".length()).trim();
                    } else if (line.startsWith("Age: ")) {
                        age = line.substring("Age: ".length()).trim();
                    } else if (line.startsWith("Contact-Number: ")) {
                        contactNumber = line.substring("Contact-Number: ".length()).trim();
                    } else if (line.startsWith("Passport Number: ")) {
                        passportNumber = line.substring("Passport Number: ".length()).trim();
                    } else if (line.startsWith("Destination: ")) {
                        destination = line.substring("Destination: ".length()).trim();
                    } else if (line.startsWith("Seat Number: ")) {
                        seatNumber = line.substring("Seat Number: ".length()).trim();
                    } else if (line.startsWith("Departure: ")) {
                        departureTimeStr = line.substring("Departure: ".length()).trim();
                    } else if (line.startsWith("Flight Type: ")) {
                        flightType = line.substring("Flight Type: ".length()).trim();
                    } else if (line.startsWith("Total Fare: ")) {
                        try {
                            totalFare = Double.parseDouble(line.substring("Total Fare: ".length()).trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid total fare in receipt: " + file.getName());
                        }
                    } else if (line.startsWith("Booking Date: ")) {
                        try {
                            bookingDateTime = LocalDateTime.parse(line.substring("Booking Date: ".length()).trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid booking date in receipt: " + file.getName());
                        }
                    }
                }

                // Validate required fields
                if (bookingID == null || destination == null || seatNumber == null || departureTimeStr == null || flightType == null) {
                    System.out.println("Skipping invalid receipt file: " + file.getName());
                    continue;
                }

                // Parse departure time
                LocalDateTime departureTime;
                try {
                    departureTime = LocalDateTime.parse(departureTimeStr, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid departure time in receipt: " + file.getName());
                    continue;
                }

                // Find the flight
                Flight flight = null;
                for (Flight f : Flight.getFlights()) {
                    LocalDateTime flightDepartureTime = LocalDateTime.parse(f.getDepartureTime(), formatter);
                    if (f.getDestination().equals(destination) && flightDepartureTime.equals(departureTime) && f.getFlightType().equalsIgnoreCase(flightType)) {
                        flight = f;
                        break;
                    }
                }

                if (flight == null) {
                    System.out.println("Flight to " + destination + " with departure " + departureTimeStr + " not found for receipt " + file.getName());
                    continue;
                }

                // Mark the seat as booked
                seatManager[] seats = flight.getSeats();
                seatManager bookedSeat = null;
                for (seatManager seat : seats) {
                    if (seat.getSeatNumber().equals(seatNumber)) {
                        seat.booked();
                        bookedSeat = seat;
                        break;
                    }
                }

                if (bookedSeat == null) {
                    System.out.println("Seat " + seatNumber + " not found for flight to " + destination + " in receipt " + file.getName());
                    continue;
                }

                // Create passenger
                passenger passengerObj = flightType.equalsIgnoreCase("International") 
                    ? new passenger(name, age, contactNumber, passportNumber)
                    : new passenger(name, age, contactNumber);

                // Create and add reservation
                reservationSystem reservation = new reservationSystem(passengerObj, flight, bookedSeat, bookingID, totalFare, bookingDateTime);
                reservations.add(reservation);
            } catch (IOException e) {
                System.out.println("Error reading receipt file: " + file.getName());
            }
        }
        System.out.println("Loaded " + receiptFiles.length + " receipt(s).");
    }
}