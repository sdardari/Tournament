package be.TFTIC.Tournoi.il.validators.alreadyExist;

import be.TFTIC.Tournoi.il.validators.mustBeTheSame.MustBeTheSameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = AlreadyExistValidator.class)
public @interface AlreadyExist {

    String message() default "Username already used";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
