package edu.pdx.cs410J.chsherpa.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.chsherpa.client.Airline;
import edu.pdx.cs410J.chsherpa.client.Flight;
import edu.pdx.cs410J.chsherpa.client.AirlineService;

/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
  Airline airline;

  @Override
  public Airline getAirline() {
    airline = new Airline();
    airline.addFlight(new Flight());
    return airline;
  }

  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }

  @Override
  public void addAirline( String airlineName ){
    if( this.airline == null )
    {
      this.airline = new Airline();
    }
    this.airline.setname(airlineName);
    return;
  }

  @Override
  public void addFlight( Flight flight ){
    if( this.airline == null )
    {
      this.airline = new Airline();
    }
    this.airline.setname( flight.getFlightName() );
    this.airline.addFlight( flight );
    return;
  }
}
