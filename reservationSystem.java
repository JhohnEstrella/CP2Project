package Defense;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.io.IOException;

public class reservationSystem extends booking {
    private String bookingID;
    private double totalFare;
    private LocalDateTime bookingDateTime;

    public reservationSystem(passenger passenger, Flight flight, seatManager seats, String bookingID, double totalFare, LocalDateTime bookingDateTime) {
        super(); // Call parent constructor (if needed)
        setPassenger(passenger); // Use inherited setters from booking
        setFlight(flight);
        setSeats(seats);
        this.bookingID = bookingID;
        this.totalFare = totalFare;
        this.bookingDateTime = bookingDateTime;
    }

    // Getters and setters
    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    @Override
    public void printReceipt() {
        super.printReceipt(); // Call parent implementation
        // Add booking-specific details
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Total Fare: " + totalFare);
        System.out.println("Booking Date: " + bookingDateTime);
    }

    @Override
    public void savePrintReceipt() throws IOException {
        FileWriter writer = new FileWriter("receipt_" + bookingID + ".txt"); // Unique file per booking
        writer.write("==============================\n");
        writer.write("     FLIGHT RECEIPT\n");
        writer.write("==============================\n\n");

        writer.write("Booking ID: " + bookingID + "\n");
        writer.write("Total Fare: " + totalFare + "\n");
        writer.write("Booking Date: " + bookingDateTime + "\n\n");

        writer.write("Passenger Information\n");
        writer.write("==============================\n");
        writer.write("Name: " + getPassenger().getName() + "\n");
        writer.write("Age: " + getPassenger().getAge() + "\n");
        writer.write("Contact-Number: " + getPassenger().getContactNumber() + "\n");

        if (getFlight().getFlightType().equalsIgnoreCase("International")) {
            writer.write("Passport Number: " + getPassenger().getPassportNumber() + "\n");
        }

        writer.write("\nFlight Details:\n");
        writer.write("=============================\n");
        writer.write("Flight Type: " + getFlight().getFlightType() + "\n");
        writer.write("Destination: " + getFlight().getDestination() + "\n");
        writer.write("Departure: " + getFlight().getDepartureTime() + "\n");
        writer.write("Arrival: " + getFlight().getArrivalTime() + "\n");
        writer.write("Airline Fare: " + getFlight().getAirlineFare() + "\n");

        if (getFlight().getFlightType().equalsIgnoreCase("Domestic")) {
            domesticFlight domestic = (domesticFlight) getFlight();
            writer.write("\nDomestic Flight Details:\n");
            writer.write("Place of Arrival: " + domestic.getPlaceOfArrival() + "\n");
            writer.write("Domestic Tax: " + domestic.getDomesticTax() + "\n");
        } else if (getFlight().getFlightType().equalsIgnoreCase("International")) {
            internationalFlight international = (internationalFlight) getFlight();
            writer.write("\nInternational Flight Details:\n");
            writer.write("Country of Arrival: " + international.getCountryOfArrival() + "\n");
            writer.write("International Tax: " + international.getInternationalTax() + "\n");
        }

        writer.write("\nSeat Details\n");
        writer.write("===========================\n");
        writer.write("Seat Number: " + getSeats().getSeatNumber() + "\n");
        writer.write("Seat Status: " + (getSeats().isSeatBooked() ? "Booked" : "Available") + "\n");

        writer.write("=============================\n");
        writer.write("   Thank you for Flying with us!\n");
        writer.write("=============================\n");

        writer.close();
    }
}