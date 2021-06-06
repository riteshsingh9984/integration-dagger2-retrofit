package com.example.shiv.myapplication.services;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.shiv.myapplication.modals.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public interface ServiceBinder {

  public final static ObjectMapper MAPPER = new ObjectMapper();

  public static Intent addStringIntoIntent(Intent intent, String KEY, String VALUE){

      if(KEY != null) {
          intent.putExtra(KEY, VALUE);
      }
      return intent;
  }

  public static String getStringFromIntent(Intent intent, String KEY){

      return intent.getStringExtra(KEY);
  }

  public static String getJsonFromObject(Object object)  {
      try{
          return MAPPER.writeValueAsString(object);
      }catch (JsonProcessingException jpe){
          Log.e(ServiceBinder.class.toString() , "Json Processing failed while reading from object ");
      }
      return null;
  }

  public static <T> T getObjectFromJson(String json, Class<T> type) {
      try{
          return (T) MAPPER.readValue(json, type);
      }catch (IOException io){
          Log.e(ServiceBinder.class.toString() , "Json Processing failed while reading from objectString ");
      }
      return null;
  }

}
