package com.sequoiacap.data;

import org.springframework.util.StringUtils;  

import java.beans.PropertyEditorSupport;  
import java.sql.Timestamp;
import java.text.DateFormat;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Date;
  
public class DateEditor
    extends PropertyEditorSupport
{  
    private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");  
    private static final DateFormat TIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
  
    private DateFormat dateFormat;  
    private boolean allowEmpty = true;  
  
    public DateEditor() {
        dateFormat = null;
    }  
  
    public DateEditor(DateFormat dateFormat) {  
        this.dateFormat = dateFormat;  
    }  
  
    public DateEditor(DateFormat dateFormat, boolean allowEmpty) {  
        this.dateFormat = dateFormat;  
        this.allowEmpty = allowEmpty;  
    }  
  
    /** 
     * Parse the Date from the given text, using the specified DateFormat. 
     */  
    @Override  
    public void setAsText(String text)
        throws IllegalArgumentException
    {
        if (this.allowEmpty && !StringUtils.hasText(text))
        { 
            setValue(null);  
        } else
        {
            if (this.dateFormat != null)
            {
                try
                {
                    setValue(new Timestamp(this.dateFormat.parse(text).getTime())); 
                    return;
                } catch(Exception ex)
                {
                    throw new IllegalArgumentException(
                        "Could not parse date: " + ex.getMessage(), ex);  
                }
            } else
            {
                try
                {
                    if (text.contains(":"))
                    {
                        setValue(new Timestamp(TIMEFORMAT.parse(text).getTime()));  
                    } else
                    {
                        setValue(new Timestamp(DATEFORMAT.parse(text).getTime()));
                    }
                    
                    return;
                } catch(Exception e)
                { }
                
                try
                {
                   setValue(Timestamp.valueOf(text));
                   return; 
                } catch(Exception e)
                { }
                
                try
                {
                   setValue(new Timestamp(Long.parseLong(text)));
                   return; 
                } catch(Exception e)
                { }
                
                throw new IllegalArgumentException(
                    "Could not parse date: " + text);
            }
        }  
    }  
  
    /** 
     * Format the Date as String, using the specified DateFormat. 
     */  
    @Override  
    public String getAsText() {  
        Date value = (Date) getValue();  
        DateFormat dateFormat = this.dateFormat;  
        if(dateFormat == null)  
            dateFormat = TIMEFORMAT;  
        return (value != null ? dateFormat.format(value) : "");  
    }  
}

