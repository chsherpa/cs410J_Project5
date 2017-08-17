package edu.pdx.cs410J.chsherpa.client;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class Airline_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getAirlineName(edu.pdx.cs410J.chsherpa.client.Airline instance) /*-{
    return instance.@edu.pdx.cs410J.chsherpa.client.Airline::airlineName;
  }-*/;
  
  private static native void setAirlineName(edu.pdx.cs410J.chsherpa.client.Airline instance, java.lang.String value) 
  /*-{
    instance.@edu.pdx.cs410J.chsherpa.client.Airline::airlineName = value;
  }-*/;
  
  private static native java.util.List getFlights(edu.pdx.cs410J.chsherpa.client.Airline instance) /*-{
    return instance.@edu.pdx.cs410J.chsherpa.client.Airline::flights;
  }-*/;
  
  private static native void setFlights(edu.pdx.cs410J.chsherpa.client.Airline instance, java.util.List value) 
  /*-{
    instance.@edu.pdx.cs410J.chsherpa.client.Airline::flights = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, edu.pdx.cs410J.chsherpa.client.Airline instance) throws SerializationException {
    setAirlineName(instance, streamReader.readString());
    setFlights(instance, (java.util.List) streamReader.readObject());
    
    edu.pdx.cs410J.AbstractAirline_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static edu.pdx.cs410J.chsherpa.client.Airline instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new edu.pdx.cs410J.chsherpa.client.Airline();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, edu.pdx.cs410J.chsherpa.client.Airline instance) throws SerializationException {
    streamWriter.writeString(getAirlineName(instance));
    streamWriter.writeObject(getFlights(instance));
    
    edu.pdx.cs410J.AbstractAirline_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return edu.pdx.cs410J.chsherpa.client.Airline_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    edu.pdx.cs410J.chsherpa.client.Airline_FieldSerializer.deserialize(reader, (edu.pdx.cs410J.chsherpa.client.Airline)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    edu.pdx.cs410J.chsherpa.client.Airline_FieldSerializer.serialize(writer, (edu.pdx.cs410J.chsherpa.client.Airline)object);
  }
  
}
