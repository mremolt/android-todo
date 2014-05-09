package de.dcs.mrtodoapp.app.models;

import org.javalid.core.AnnotationValidator;
import org.javalid.core.AnnotationValidatorImpl;
import org.javalid.core.config.JvConfiguration;

public class Validator {
    private static AnnotationValidator instance = null;

    private Validator() {}

    public static AnnotationValidator getInstance() {
        if (instance == null) {
            instance = new AnnotationValidatorImpl(
                    JvConfiguration.JV_CONFIG_FILE_METHOD_FILE_WITH_MSG_RESOLVING
            );
        }

        return instance;
    }
}
