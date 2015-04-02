package sample;

import java.time.LocalDate;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 * Created by Pradipta on 02-04-2015.
 */
public class DateAdapter extends XmlAdapter<String,LocalDate>{
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
