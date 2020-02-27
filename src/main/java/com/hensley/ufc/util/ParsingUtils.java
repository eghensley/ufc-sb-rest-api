package com.hensley.ufc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class ParsingUtils {
	private Logger LOG = Logger.getLogger(ParsingUtils.class.toString());

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd,yyyy");

    public Date stringToDate(String dateInString) {
        try {

            Date date = dateFormatter.parse(dateInString);
            return date;

        } catch (ParseException e) {
			LOG.log(Level.WARNING, String.format("Error parsing %s to Date: %s", dateInString, e.getLocalizedMessage()));
            return null;
        }
    	
    	
    }
    
}
