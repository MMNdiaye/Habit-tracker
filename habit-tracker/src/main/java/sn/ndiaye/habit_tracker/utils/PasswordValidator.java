package sn.ndiaye.habit_tracker.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String string, ConstraintValidatorContext context) {
        var isValid = true;
        context.disableDefaultConstraintViolation();
        var message = new StringBuilder();

        if (string.length() < 8) {
            isValid = false;
            message.append("Length inferior to 8");
        }

        if (!string.matches(".*[A-Z].*")) {
            isValid = false;
            appendWithSeparation(message, "No uppercase");
        }

        if (!string.matches(".*[0-9].*")) {
            isValid = false;
            appendWithSeparation(message, "No number");
        }


        if (!string.matches(".*[^A-Za-z0-9\\s].*")) {
            isValid = false;
            appendWithSeparation(message, "No special char");
        };

        context.buildConstraintViolationWithTemplate(message.toString())
                .addConstraintViolation();
        return isValid ;
    }

    private void appendWithSeparation(StringBuilder message, String error) {
        if (message.isEmpty())
            message.append(error);
        else
            message.append("; ").append(error);
    }
}
