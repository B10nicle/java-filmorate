package ru.yandex.practicum.filmorate.constraints;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidator;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Oleg Khilko
 */

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, Date> {

    @Override
    public boolean isValid(final Date valueToValidate, final ConstraintValidatorContext context) {
        Calendar dateInCalendar = Calendar.getInstance();
        Calendar notLaterThen = Calendar.getInstance();
        dateInCalendar.setTime(valueToValidate);
        notLaterThen.set(1895, Calendar.DECEMBER, 28);

        return dateInCalendar.after(notLaterThen);
    }
}
