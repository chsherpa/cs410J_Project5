package edu.pdx.cs410J.chsherpa.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {

  private final Alerter alerter;
  private final AirlineServiceAsync airlineService;
  private final Logger logger;

  @VisibleForTesting
  Button showAirlineButton;

  @VisibleForTesting
  Button showUndeclaredExceptionButton;

  @VisibleForTesting
  Button showDeclaredExceptionButton;

  @VisibleForTesting
  Button showClientSideExceptionButton;

  public AirlineGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  AirlineGwt(Alerter alerter) {
    this.alerter = alerter;
    this.airlineService = GWT.create(AirlineService.class);
    this.logger = Logger.getLogger("airline");
    Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
  }

  private void alertOnException(Throwable throwable) {
    Throwable unwrapped = unwrapUmbrellaException(throwable);
    StringBuilder sb = new StringBuilder();
    sb.append(unwrapped.toString());
    sb.append('\n');

    for (StackTraceElement element : unwrapped.getStackTrace()) {
      sb.append("  at ");
      sb.append(element.toString());
      sb.append('\n');
    }

    this.alerter.alert(sb.toString());
  }

  private Throwable unwrapUmbrellaException(Throwable throwable) {
    if (throwable instanceof UmbrellaException) {
      UmbrellaException umbrella = (UmbrellaException) throwable;
      if (umbrella.getCauses().size() == 1) {
        return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
      }
    }
    return throwable;
  }

  private class InputTextBox extends FlowPanel
  {
    public InputTextBox(final String labelText, String placeHolder, VerticalPanel panel)
    {
      HTML label = new HTML(new SafeHtmlBuilder().appendEscapedLines("\n"+labelText +"\n").toSafeHtml());
      //Label label = new Label(labelText);
      TextBox input = new TextBox();
      panel.add(label);
      input.getElement().setAttribute("placeholder", labelText);
      panel.add(input);
    }
  }

  private class DateSelect
  {
    public DateSelect( final String labelText, VerticalPanel panel )
    {
      HTML label = new HTML(new SafeHtmlBuilder().appendEscapedLines("\n"+labelText +"\n").toSafeHtml());
      ListBox Month = new ListBox();
      Month.addItem( "--select--" );
      Month.addItem( "January" );
      Month.addItem( "February" );
      Month.addItem( "March" );
      Month.addItem( "April" );
      Month.addItem( "May" );
      Month.addItem( "June" );
      Month.addItem( "July" );
      Month.addItem( "August" );
      Month.addItem( "September" );
      Month.addItem( "October" );
      Month.addItem( "November" );
      Month.addItem( "December" );

      panel.add(label);
      panel.add(Month);
    }

  }

  private void addTextFields( VerticalPanel panel )
  {
    Date today = new Date();
    DateTimeFormat dt = DateTimeFormat.getFormat("MM/dd/yyyy");
    DateTimeFormat tf = DateTimeFormat.getFormat("hh:mm");
    String dd = dt.format(today).toString();
    String tt = tf.format(today, TimeZone.createTimeZone(0)).toString();

    //Window.alert(dtf.format(today, TimeZone.createTimeZone(0)));

    final String placeHolder = new String("Enter in values here");
    Label airlineNameLabel = new Label("\nAirline Name:");
    TextBox airlineName = new TextBox();
    airlineName.getElement().setAttribute("placeholder", placeHolder);

    Label airlineNumberLabel = new Label("\nFlight Number:");
    TextBox airlineNumber = new TextBox();
    airlineNumber.getElement().setAttribute("placeholder", placeHolder);

    Label airlineSourceLabel = new Label("\nFlight Source:");
    TextBox airlineSource = new TextBox();
    airlineSource.getElement().setAttribute("placeholder", placeHolder);

    //Departure Date Stuff
    Label airlineDepartDateLabel = new Label("\nFlight Date: ");
    DateBox departDatePick = new DateBox();
    departDatePick.getElement().setAttribute("placeholder", dd );
    departDatePick.setFormat(new DateBox.DefaultFormat(dt));

    /*
    departDatePick.addValueChangeHandler(new ValueChangeHandler<Date>() {
      @Override
      public void onValueChange(ValueChangeEvent<Date> valueChangeEvent)
      {
        Date date = valueChangeEvent.getValue();
        String dateString = DateTimeFormat.getFormat("MM/dd/yyyy").format(date);
      }
    });
    */

    final Label departTimePickLabel = new Label("\nTimeBox");
    final DateBox.Format timeFormat = new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.TIME_SHORT));
    DateBox departTimePick = new DateBox();
    departTimePick.setFormat(timeFormat);
    departTimePick.getDatePicker().setVisible(false);
    departTimePick.getElement().setAttribute("placeholder", tt );
    departTimePick.setFormat(new DateBox.DefaultFormat(tf));

    //Arrival Date Stuff
    Label airlineDestinationLabel = new Label("\nFlight Destination:");
    TextBox airlineDestination = new TextBox();
    airlineDestination.getElement().setAttribute("placeholder", placeHolder );

    Label airlineArriveDateLabel = new Label("\nFlight Date: ");
    DateBox arriveDatePick = new DateBox();
    arriveDatePick.getElement().setAttribute("placeholder", dd );
    arriveDatePick.setFormat(new DateBox.DefaultFormat(dt));

    final Label arriveTimePickLabel = new Label("\nTimeBox");
    DateBox arriveTimePick = new DateBox();
    arriveTimePick.setFormat(timeFormat);
    arriveTimePick.getDatePicker().setVisible(false);
    arriveTimePick.getElement().setAttribute("placeholder", tt );
    arriveTimePick.setFormat(new DateBox.DefaultFormat(tf));

    ListBox departHour = new ListBox();
    for( int i= 1; i < 13; i++ )
    {
      String a = new Integer(i).toString();
      if( a.length() < 2 )
      {
        departHour.addItem( "0"+a);
      }
      else
      {
        departHour.addItem(a);
      }
    }

    ListBox departMin = new ListBox();
    for( int i = 0; i < 60; i++ )
    {
      String a = new Integer(i).toString();
      if( a.length() < 2 )
      {
        departMin.addItem( "0"+a);
      }
      else
      {
        departMin.addItem( a );
      }
    }

    ListBox departAmpm = new ListBox();
    departAmpm.addItem("AM");
    departAmpm.addItem("PM");

    ListBox arriveHour = new ListBox();
    for( int i= 1; i < 13; i++ )
    {
      String a = new Integer(i).toString();
      if( a.length() < 2 )
      {
        arriveHour.addItem( "0"+a);
      }
      else
      {
        arriveHour.addItem(a);
      }
    }

    ListBox arriveMin = new ListBox();
    for( int i = 0; i < 60; i++ )
    {
      String a = new Integer(i).toString();
      if( a.length() < 2 )
      {
        arriveMin.addItem( "0"+a);
      }
      else
      {
        arriveMin.addItem( a );
      }
    }

    ListBox arriveAmpm = new ListBox();
    arriveAmpm.addItem("AM");
    arriveAmpm.addItem("PM");

    Label hourLabel = new Label("Hour");
    Label minLabel = new Label("Min");
    Label meridianLabel = new Label("AM/PM");

    //Buttons for Submittal vs Search
    Button submitButton = new Button("Submit");
    Button searchButton = new Button("Search");


    String departure = new String();
    Grid departTime = new Grid(2,3);
    departTime.setWidget(0,0,hourLabel);
    departTime.setWidget(0,1,minLabel);
    departTime.setWidget(0,2,meridianLabel);
    departTime.setWidget(1,0,departHour);
    departTime.setWidget(1,1,departMin);
    departTime.setWidget(1,2,departAmpm);
//    hour.getSelectedValue().toString();
//    min.getSelectedValue().toString();

    //SUBGRID for ARRIVE TIME
    Grid arriveTime = new Grid(2,3);
    arriveTime.setWidget(0,0,hourLabel);
    arriveTime.setWidget(0,1,minLabel);
    arriveTime.setWidget(0,2,meridianLabel);
    arriveTime.setWidget(1,0,arriveHour);
    arriveTime.setWidget(1,1,arriveMin);
    arriveTime.setWidget(1,2,arriveAmpm);

    //MAIN GRID
    Grid grid = new Grid(13,3);

    //Layout Pattern
    grid.setWidget(0,0,airlineNameLabel);
    grid.setWidget(0,1,airlineName);
    grid.setWidget(2,0,airlineNumberLabel);
    grid.setWidget(2,1,airlineNumber);

    grid.setWidget(4,0,airlineSourceLabel);
    grid.setWidget(4,1,airlineSource);
    grid.setWidget(5,0,airlineDepartDateLabel);
    grid.setWidget(5,1,departTimePickLabel);
    grid.setWidget(6,0,departDatePick);
    grid.setWidget(6,1,departTime);

    grid.setWidget(9,0,airlineDestinationLabel);
    grid.setWidget(9,1,airlineDestination);
    grid.setWidget(10,0,airlineArriveDateLabel);
    grid.setWidget(10,1,arriveTimePickLabel);
    grid.setWidget(11,0,arriveDatePick);
    grid.setWidget(11,1,arriveTime);

    grid.setWidget(12,1,submitButton);
    grid.setWidget(12,2,searchButton);

    panel.add(grid);

      /*
        final String name = new String(airlineName.getText().toString());
        final String number = new String(airlineNumber.getText().toString());
        final String source = new String(airlineSource.getText().toString());
        final String SourceTime = new String( departDatePick.getValue().toString()+" "
                                        +departHour.getSelectedValue().toString() +":"
                                        + departMin.getSelectedValue().toString()+" "+ departAmpm.getSelectedValue().toString());
        final String dest = new String(airlineDestination.getText().toString());
        final String ArriveTime = new String(arriveDatePick.getValue().toString()+" "
                                       +arriveHour.getSelectedValue().toString()+":"
                                       +arriveMin.getSelectedValue().toString() +" "+arriveAmpm.getSelectedValue().toString());
                                       */
//    new String( "\nAirline Name: "  + name + "\nNumber: " + number + "\nSource: " + source + "\nDeparture: " + SourceTime + "\nDestination: " + dest + "\nArrival: " + ArriveTime);
    submitButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent)
      {
        Window.alert("Thanks for your submission.");
        Flight flight = new Flight();
        passAirline(flight);
      }
    });
  }

  private void passAirline( Flight flight ){
    logger.info("Adding a flight to server");
    airlineService.passAirline(flight, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex)
      {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid)
      {

      }
    });
  }

  private void addWidgets(VerticalPanel panel) {
    showAirlineButton = new Button("Show Airline");
    showAirlineButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showAirline();
      }
    });

    showUndeclaredExceptionButton = new Button("Show undeclared exception");
    showUndeclaredExceptionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showUndeclaredException();
      }
    });

    showDeclaredExceptionButton = new Button("Show declared exception");
    showDeclaredExceptionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showDeclaredException();
      }
    });

    showClientSideExceptionButton= new Button("Show client-side exception");
    showClientSideExceptionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        throwClientSideException();
      }
    });

    panel.add(showAirlineButton);
    panel.add(showUndeclaredExceptionButton);
    panel.add(showDeclaredExceptionButton);
    panel.add(showClientSideExceptionButton);
  }

  private void throwClientSideException() {
    logger.info("About to throw a client-side exception");
    throw new IllegalStateException("Expected exception on the client side");
  }

  private void showUndeclaredException() {
    logger.info("Calling throwUndeclaredException");
    airlineService.throwUndeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showDeclaredException() {
    logger.info("Calling throwDeclaredException");
    airlineService.throwDeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showAirline() {
    logger.info("Calling getAirline");
    airlineService.getAirline(new AsyncCallback<Airline>() {

      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Airline airline) {
        StringBuilder sb = new StringBuilder(airline.toString());
        Collection<Flight> flights = airline.getFlights();
        for (Flight flight : flights) {
          sb.append(flight);
          sb.append("\n");
        }
        alerter.alert(sb.toString());
      }
    });
  }

  @Override
  public void onModuleLoad() {
    setUpUncaughtExceptionHandler();

    // The UncaughtExceptionHandler won't catch exceptions during module load
    // So, you have to set up the UI after module load...
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        setupUI();
      }
    });

  }

  private void setupUI() {
    RootPanel rootPanel = RootPanel.get();
    VerticalPanel panel = new VerticalPanel();
    rootPanel.add(panel);

    addTextFields(panel);
    addWidgets(panel);
  }

  private void setUpUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable throwable) {
        alertOnException(throwable);
      }
    });
  }

  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }
}
