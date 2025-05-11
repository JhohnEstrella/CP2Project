package Defense;

public class domesticFlight extends Flight {
    private String placeOfArrival;
    private double domesticTax = 810.00;

    public domesticFlight(String placeOfArrival, String flightType, String destination, String departureTime, String arrivalTime, double airlineFare, seatManager[] seats) {
        super(flightType, destination, departureTime, arrivalTime, airlineFare, seats);
        this.placeOfArrival = placeOfArrival;
        generateSeats();
    }
    
    private void generateSeats(){
        seats = new seatManager[40];
        char[] rows = {'A','B','C','D'};
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

    public double calculateDomesticFare() {
        return getAirlineFare() + domesticTax;
    }

    public String getPlaceOfArrival() {
        return placeOfArrival;
    }

    public void setPlaceOfArrival(String placeOfArrival) {
        this.placeOfArrival = placeOfArrival;
    }

    public double getDomesticTax() {
        return domesticTax;
    }

    public void setDomesticTax(double domesticTax) {
        this.domesticTax = domesticTax;
    }   
}