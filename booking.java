package Defense;

import java.io.FileWriter;
import java.io.IOException;

public abstract class booking {
    protected passenger passenger;
    protected Flight flight;
    protected seatManager seats;

    // Default constructor
    public booking() {
    }

    // Getters and setters
    public passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public seatManager getSeats() {
        return seats;
    }

    public void setSeats(seatManager seats) {
        this.seats = seats;
    }

    public void printReceipt() {
        System.out.println("==============================\n");
        System.out.println("     FLIGHT RECEIPT\n");
        System.out.println("==============================\n");

        System.out.println("Passenger Information\n");
        System.out.println("==============================\n");
        System.out.println("Name: " + passenger.getName() + "\n");
        System.out.println("Age: " + passenger.getAge() + "\n");
        System.out.println("Contact-Number: " + passenger.getContactNumber() + "\n");

        System.out.println("\nFlight Details:\n");
        System.out.println("=============================\n");
        System.out.println("Flight Type: " + flight.getFlightType() + "\n");
        System.out.println("Destination: " + flight.getDestination() + "\n");
        System.out.println("Departure Time: " + flight.getDepartureTime() + "\n");
        System.out.println("Arrival Time: " + flight.getArrivalTime() + "\n");
        System.out.println("Airline Fee: " + flight.getAirlineFare() + "\n");

        if (flight.getFlightType().equalsIgnoreCase("Domestic")) {
            domesticFlight domestic = (domesticFlight) flight;
            System.out.println("\nDomestic Flight Details:\n");
            System.out.println("=========================\n");
            System.out.println("Place of Arrival: " + domestic.getPlaceOfArrival() + "\n");
            System.out.println("Domestic Tax: " + domestic.getDomesticTax() + "\n");
        } else if (flight.getFlightType().equalsIgnoreCase("International")) {
            internationalFlight international = (internationalFlight) flight;
            System.out.println("\nInternational Flight Details:\n");
            System.out.println("========================\n");
            System.out.println("Passport Number: " + passenger.getPassportNumber() + "\n");
            System.out.println("Country of Arrival: " + international.getCountryOfArrival() + "\n");
            System.out.println("International Tax: " + international.getInternationalTax() + "\n");
        }

        System.out.println("\nSeat Details\n");
        System.out.println("===========================\n");
        System.out.println("Seat Number: " + seats.getSeatNumber() + "\n");
        System.out.println("Seat Booked: " + (seats.isSeatBooked() ? "Taken" : "Find another seat...") + "\n");

        System.out.println("=============================\n");
        System.out.println("   Thank you for Flying with us!\n");
        System.out.println("=============================\n");
    }

    public void savePrintReceipt() throws IOException {
        // Default implementation (optional, can be overridden)
        FileWriter writer = new FileWriter("receipt.txt");
        writer.write("Default receipt content\n");
        writer.close();
    }
}