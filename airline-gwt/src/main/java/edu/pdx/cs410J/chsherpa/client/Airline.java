package edu.pdx.cs410J.chsherpa.client;

import edu.pdx.cs410J.AbstractAirline;

/*
import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline<Flight>
{
 public Airline() {

  }

  private Collection<Flight> flights = new ArrayList<>();

  @Override
  public String getName() {
    return "Air CS410J";
  }

  @Override
  public void addFlight(Flight flight) {
    this.flights.add(flight);
  }

  @Override
  public Collection<Flight> getFlights() {
    return this.flights;
  }
}
*/

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;

/**
 * Created by chsherpa on 7/11/17.
 */
public class Airline extends AbstractAirline<Flight>{
    private List<Flight> flights = new ArrayList<Flight>();
    private String airlineName;

    /**
    * In order for GWT to serialize this class (so that it can be sent between
    * the client and the server), it must have a zero-argument constructor.
    */
    public Airline(){ }

    public Airline( String airlineName )
    {
        this.airlineName = new String(airlineName);
    }
    /**
     * Constructor with flights object passed in
     * Adds to the List only if the name of the flights passed in matches
     * @param flights
     */
    public Airline( Flight flights){
        addFlightFrom(flights);
    }

    @Override
    public void addFlight(Flight flight ) {
       addFlightFrom( flight );
    }

    @Override
    public Collection<Flight> getFlights() {
        return this.flights;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Return the name of the Airlines
     * @return Name of the first flight in the list
     */
    public String getName() {
        return (this.flights != null )?this.flights.get(0).getFlightName():null;
    }

    /**
     * Method to add flights object
     * @param flight Flight object containing info for flight
     */
    public void addFlightFrom( Flight flight ){
        if( this.flights.isEmpty() )
            this.flights.add(flight);
        else{
          if (flight.getFlightName().equals(this.flights.get(0).getFlightName()))
          {
            if( this.flights.size() > 1 )
            {
              this.flights.add(flight);
    //          Arrays.sort(new List[]{flights});
            }
            else
            {
              this.flights.add(flight);
            }
          }
          else
              throw new IllegalArgumentException("Flight Name does not match Airline's Name");
        }
        return;
    }
}
