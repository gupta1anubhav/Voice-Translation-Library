package com.example.voicerecog;

import android.graphics.Typeface;
import android.widget.TextView;

import com.memetix.mst.language.Language;

/**
 * Provides machine translations using the Microsoft Bing Translator API. 
 * 
 * @author Robert Theis
 */
class TranslatorBing {

  private TranslatorBing() {  
    // Private constructor to enforce noninstantiability
  }

  /**
   * Check if the supplied language pair is valid for this translation service, and start an 
   * AsyncTask to request the translation.
   * 
   * @param text The text to translate
   * @param source The source language for translation
   * @param target The target language for translation
   * @param textView The text field that will receive the completed translation
   */
  static void translate(String text, String source, String target, TextView textView, boolean indianLanguage) {      
    if (source == null || target == null) {
      throw new IllegalArgumentException();
    }

    Language sourceLanguage = null;
    Language targetLanguage = null;
    try {
      sourceLanguage = toLanguage(source);
      if(indianLanguage){
    	  targetLanguage = toLanguage("English");
      }else{
    	  targetLanguage = toLanguage(target);    	  
      }
    } catch (IllegalArgumentException e) {
      textView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC), Typeface.ITALIC);
      textView.setTextSize(14);
      textView.setText("Unsupported language pair");
    }

    if (sourceLanguage != null && targetLanguage != null) {
      // Start an AsyncTask to perform the translation request.
      new TranslateBingAsyncTask(text, sourceLanguage, targetLanguage, textView, indianLanguage, target).execute();
    }
  }
  
  /**
   * Convert the given name of a natural language into a Language from the enum of Languages 
   * supported by this translation service.
   * 
   * @param languageName The name of the language, for example, "English"
   * @return The Language object representing this language
   * @throws IllegalArgumentException
   */
  private static Language toLanguage(String languageName) throws IllegalArgumentException {    
    // Convert string to all caps
    String standardizedName = languageName.toUpperCase();
    
    // Replace spaces with underscores
    standardizedName = standardizedName.replace(' ', '_');
    
    // Remove parentheses
    standardizedName = standardizedName.replace("(", "");   
    standardizedName = standardizedName.replace(")", "");
    return Language.valueOf(standardizedName);
  }
}