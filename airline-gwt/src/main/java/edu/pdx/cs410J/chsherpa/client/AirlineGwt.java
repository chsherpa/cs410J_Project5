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
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.Collection;
import java.util.Date;
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
    String placeHolder = new String("Enter in values here");
    InputTextBox airlineName = new InputTextBox(
      "Airline Name",placeHolder,panel );
    InputTextBox airlineNumber = new InputTextBox(
      "Flight Number",placeHolder, panel );
    InputTextBox airlineSource = new InputTextBox(
      "Flight Source",placeHolder, panel );

    DateSelect departure = new DateSelect("Departure", panel);

    InputTextBox airlineDestination = new InputTextBox(
        "Flight Destination",placeHolder, panel );

    DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
    DateBox datePicker = new DateBox();
    datePicker.setFormat(new DateBox.DefaultFormat(dateFormat));
    final Label text = new Label("\ndatebox");
    //DatePicker datePicker = new DatePicker( );

    panel.add(text);
    panel.add(datePicker);

    final DateBox.Format timeFormat = new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.TIME_SHORT));
    DateBox begin = new DateBox();
    begin.setFormat(timeFormat);
    begin.getDatePicker().setVisible(false);
    final Label text2 = new Label("\nTimeBox");

    panel.add(text2);
    panel.add(begin);


    datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
      @Override
      public void onValueChange(ValueChangeEvent<Date> valueChangeEvent)
      {
        Date date = valueChangeEvent.getValue();
        String dateString = DateTimeFormat.getFormat("MM/dd/yyyy").format(date);

//        text.setText(dateString);
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
