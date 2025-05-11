package Defense;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    private static List<Flight> flights = new ArrayList<>();
    private String flightType;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double airlineFare;
    public seatManager[] seats;

    public Flight(String flightType, String destination, String departureTime, String arrivalTime, double airlineFare, seatManager[] seats) {
        this.flightType = flightType;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airlineFare = airlineFare;
        this.seats = seats;
    }

    public static void initializeFlights() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // Domestic Flights
        flights.add(new domesticFlight("Manila", "Domestic", "MNL", 
                LocalDateTime.of(2025, 5, 19, 9, 0).format(formatter), 
                LocalDateTime.of(2025, 5, 19, 10, 10).format(formatter), 3000.00, null));
        flights.add(new domesticFlight("Cebu", "Domestic", "CEB", 
                LocalDateTime.of(2025, 6, 2, 8, 30).format(formatter), 
                LocalDateTime.of(2025, 6, 2, 9, 40).format(formatter), 2800.00, null));
        flights.add(new domesticFlight("Iloilo", "Domestic", "ILO", 
                LocalDateTime.of(2025, 6, 5, 10, 15).format(formatter), 
                LocalDateTime.of(2025, 6, 5, 11, 25).format(formatter), 3400.00, null)); 
        // International Flights
        flights.add(new internationalFlight(true, "Japan", "International", "Tokyo (Narita)", 
                LocalDateTime.of(2025, 6, 10, 7, 15).format(formatter), 
                LocalDateTime.of(2025, 6, 10, 15, 30).format(formatter), 18000.00, null));
        flights.add(new internationalFlight(true, "Italy", "International", "Milan (Linate)", 
                LocalDateTime.of(2025, 6, 12, 9, 45).format(formatter), 
                LocalDateTime.of(2025, 6, 13, 10, 30).format(formatter), 26000.00, null));
        flights.add(new internationalFlight(true, "United States", "International", "Seattle (SEA)", 
                LocalDateTime.of(2025, 6, 15, 11, 30).format(formatter), 
                LocalDateTime.of(2025, 6, 16, 13, 45).format(formatter), 32000.00, null));
    }

    public static List<Flight> getFlights() {
        return flights;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getAirlineFare() {
        return airlineFare;
    }

    public void setAirlineFare(double airlineFare) {
        this.airlineFare = airlineFare;
    }

    public seatManager[] getSeats() {
        return seats;
    }

    public void setSeats(seatManager[] seats) {
        this.seats = seats;
    }
}