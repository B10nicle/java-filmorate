package ru.yandex.practicum.filmorate.constraints;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidator;
import java.time.LocalDate;
import java.util.Calendar;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Oleg Khilko
 */

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    @Override
    public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {
        var zonedDateTime = valueToValidate.atStartOfDay(ZoneId.systemDefault());
        var instant = zonedDateTime.toInstant();
        var date = Date.from(instant);

        var dateInCalendar = Calendar.getInstance();
        var earliestDateOfRelease = Calendar.getInstance();

        earliestDateOfRelease.set(1895, Calendar.DECEMBER, 28);
        dateInCalendar.setTime(date);

        return dateInCalendar.after(earliestDateOfRelease);
    }
}
