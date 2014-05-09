package de.dcs.mrtodoapp.app.models;

import android.app.Application;
import android.util.Log;

import com.activeandroid.Model;

import org.javalid.core.AnnotationValidator;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.ValidationMessage;
import org.javalid.core.config.JvConfiguration;

import java.util.ArrayList;
import java.util.List;

import de.dcs.mrtodoapp.app.R;

public class Base extends Model {
    protected List<ValidationMessage> errors;
    AnnotationValidator validator;

    public Base() {
        super();
        validator = Validator.getInstance();
    }

    public void setValidator(AnnotationValidator validator) {
        this.validator = validator;
    }

    public void validate() {
        errors = validator.validateObject(this);
    }

    public Boolean isValid() {
        validate();
        return errors.size() == 0;
    }

    public List<ValidationMessage> getErrors() {
        return errors;
    }

    public ArrayList<String> getErrorMessages() {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < errors.size(); i++) {
            result.add(errors.get(i).getResolvedMessage());
        }

        return result;
    }
}
