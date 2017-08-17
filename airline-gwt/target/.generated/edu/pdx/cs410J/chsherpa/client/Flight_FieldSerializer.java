package edu.pdx.cs410J.chsherpa.client;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class Flight_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getArriveTime(edu.pdx.cs410J.chsherpa.client.Flight instance) /*-{
    return instance.@edu.pdx.cs410J.chsherpa.client.Flight::arriveTime;
  }-*/;
  
  private static native void setArriveTime(edu.pdx.cs410J.chsherpa.client.Flight instance, java.lang.String value) 
  /*-{
    instance.@edu.pdx.cs410J.chsherpa.client.Flight::arriveTime = value;
  }-*/;
  
  private static native java.lang.String getDepartTime(edu.pdx.cs410J.chsherpa.client.Flight instance) /*-{
    return instance.@edu.pdx.cs410J.chsherpa.client.Flight::departTime;
  }-*/;
  
  private static native void setDepartTime(edu.pdx.cs410J.chsherpa.client.Flight instance, java.lang.String value) 
  /*-{
    instance.@edu.pdx.cs410J.chsherpa.client.Flight::departTime = value;
  }-*/;
  
  private static native java.lang.String getDest(edu.pdx.cs410J.chsherpa.client.Flight instance) /*-{
    return instance.@edu.pdx.cs410J.chsherpa.client.Flight::dest;
  }-*/;
  
  private static native void setDest(edu.pdx.cs410J.chsherpa.client.Flight instance, java.lang.String value) 
  /*-{
    instance.@edu.pdx.cs410J.chsherpa.client.Flight::dest = value;
  }-*/;
  
  private static native java.lang.String getFlightName(edu.pdx.cs410J.chsherpa.client.Flight instance) /*-{
    return instance.@edu.pdx.cs410J.chsherpa.client.Flight::flightName;
  }-*/;
  
  private static native void setFlightName(edu.pdx.cs410J.chsherpa.client.Flight instance, java.lang.String value) 
  /*-{
    instance.@edu.pdx.cs410J.chsherpa.client.Flight::flightName = value;
  }-*/;
  
  private static native int getFlightNumber(edu.pdx.cs410J.chsherpa.client.Flight instance) /*-{
    return instance.@edu.pdx.cs410J.chsherpa.client.Flight::flightNumber;
  }-*/;
  
  private static native void setFlightNumber(edu.pdx.cs410J.chsherpa.client.Flight instance, int value) 
  /*-{
    instance.@edu.pdx.cs410J.chsherpa.client.Flight::flightNumber = value;
  }-*/;
  
  private static native java.lang.String getSrc(edu.pdx.cs410J.chsherpa.client.Flight instance) /*-{
    return instance.@edu.pdx.cs410J.chsherpa.client.Flight::src;
  }-*/;
  
  private static native void setSrc(edu.pdx.cs410J.chsherpa.client.Flight instance, java.lang.String value) 
  /*-{
    instance.@edu.pdx.cs410J.chsherpa.client.Flight::src = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, edu.pdx.cs410J.chsherpa.client.Flight instance) throws SerializationException {
    setArriveTime(instance, streamReader.readString());
    setDepartTime(instance, streamReader.readString());
    setDest(instance, streamReader.readString());
    setFlightName(instance, streamReader.readString());
    setFlightNumber(instance, streamReader.readInt());
    setSrc(instance, streamReader.readString());
    
    edu.pdx.cs410J.AbstractFlight_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static edu.pdx.cs410J.chsherpa.client.Flight instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new edu.pdx.cs410J.chsherpa.client.Flight();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, edu.pdx.cs410J.chsherpa.client.Flight instance) throws SerializationException {
    streamWriter.writeString(getArriveTime(instance));
    streamWriter.writeString(getDepartTime(instance));
    streamWriter.writeString(getDest(instance));
    streamWriter.writeString(getFlightName(instance));
    streamWriter.writeInt(getFlightNumber(instance));
    streamWriter.writeString(getSrc(instance));
    
    edu.pdx.cs410J.AbstractFlight_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return edu.pdx.cs410J.chsherpa.client.Flight_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    edu.pdx.cs410J.chsherpa.client.Flight_FieldSerializer.deserialize(reader, (edu.pdx.cs410J.chsherpa.client.Flight)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    edu.pdx.cs410J.chsherpa.client.Flight_FieldSerializer.serialize(writer, (edu.pdx.cs410J.chsherpa.client.Flight)object);
  }
  
}
