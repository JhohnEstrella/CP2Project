package Defense;

public class internationalFlight extends Flight {
    private boolean requiredPassport;
    private double internationalTax = 1620.00;
    private String countryOfArrival;

    public internationalFlight(boolean requiredPassport, String countryOfArrival, String flightType, String destination, String departureTime, String arrivalTime, double airlineFare, seatManager[] seats) {
        super(flightType, destination, departureTime, arrivalTime, airlineFare, seats);
        this.requiredPassport = requiredPassport;
        this.countryOfArrival = countryOfArrival;
        generateSeats();
    }
    
    private void generateSeats(){
        seats = new seatManager[90];
        char[] rows = {'A','B','C','D','E','F','G','H','I'};
        int seatPerRow = 10;
        int index = 0;
        
        for (int i = 0; i < rows.length; i++) {
            char row = rows[i];
            for (int seatNumber = 1; seatNumber <= seatPerRow; seatNumber++) {
                if (index < seats.length) {
                    String seatId = row + "" + seatNumber; 
                    seats[index++] = new seatManager(seatId, false);
                }
            }
        }
    }

    public double calculateInternationalFare() {
        return getAirlineFare() + internationalTax;
    }

    public boolean isRequiredPassport() {
        return requiredPassport;
    }

    public void setRequiredPassport(boolean requiredPassport) {
        this.requiredPassport = requiredPassport;
    }

    public double getInternationalTax() {
        return internationalTax;
    }

    public void setInternationalTax(double internationalTax) {
        this.internationalTax = internationalTax;
    }

    public String getCountryOfArrival() {
        return countryOfArrival;
    }

    public void setCountryOfArrival(String countryOfArrival) {
        this.countryOfArrival = countryOfArrival;
    }   
}