package validator;

import java.time.LocalDateTime;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import domain.Event;


public class EventValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return Event.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Event event = (Event) target;

		ValidationUtils.rejectIfEmpty(errors, "date", "validation.date.NotNull.message");

		if (event.getDate() != null) {

			if (event.getDate().isBefore(LocalDateTime.now())) {

				errors.rejectValue("date", "validation.date.Future.message");
			}

			if (event.getDate().isBefore(LocalDateTime.of(2024, 7, 26, 8, 0))
					|| event.getDate().isAfter(LocalDateTime.of(2024, 8, 12, 0, 0))) {

				errors.rejectValue("date", "validation.date.Range.message");
			}

			if (event.getDate().getHour() < 8) {

				errors.rejectValue("date", "validation.date.Hour.message");
			}
		}

		ValidationUtils.rejectIfEmpty(errors, "olympicNumber1", "validation.olympicNumber1.NotNull.message");

		if (event.getOlympicNumber1() < 10000) {

			errors.rejectValue("olympicNumber1", "validation.olympicNumber1.Min.message");
		} else if (event.getOlympicNumber1() > 99999) {

			errors.rejectValue("olympicNumber1", "validation.olympicNumber1.Max.message");
		}

		if (event.getOlympicNumber2() < event.getOlympicNumber1() - 1000
				|| event.getOlympicNumber2() > event.getOlympicNumber1() + 1000) {

			errors.rejectValue("olympicNumber2", "error.event",
					"Olympic Number 2 must be within a range of 1000 from Olympic Number 1");
		}

		if (event.getDiscipline1() != null && event.getDiscipline1().equals(event.getDiscipline2())) {

			errors.rejectValue("discipline2", "error.event", "Disciplines must be different");
		}

	}

}
