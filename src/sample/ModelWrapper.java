package sample;

import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * Model Wrapper is a wrapper for the Person List in Main.java. To convert to XML, we need to use annotations which isn't
 * possible with a Observable List. So we create a custom wrapper
 */
@XmlRootElement(name = "persons")
public class ModelWrapper {
    private List<Model> persons;

    @XmlElement(name = "person")
    public List<Model> getPersons(){
        return persons;
    }
    public void setPersons(List<Model> p){
        this.persons = p;
    }
}
