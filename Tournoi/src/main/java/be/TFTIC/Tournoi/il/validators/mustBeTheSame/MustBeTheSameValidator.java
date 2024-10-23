package be.TFTIC.Tournoi.il.validators.mustBeTheSame;

import be.TFTIC.Tournoi.bll.exception.request.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class MustBeTheSameValidator implements ConstraintValidator<MustBeTheSame, Object> {

    private String field1;
    private String field2;


    @Override
    public void initialize(MustBeTheSame constraintAnnotation) {
        field1 = constraintAnnotation.field1();
        field2 = constraintAnnotation.field2();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Class<?> c = o.getClass();
            Field field1 = c.getDeclaredField(this.field1);
            Field field2 = c.getDeclaredField(this.field2);
            field1.setAccessible(true);
            field2.setAccessible(true);

            Object field1Value = field1.get(o);
            Object field2Value = field2.get(o);

            if(field1Value == null){
                return true;
            }

            return field1Value.equals(field2Value);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new BadRequestException("ConfirmPassword not be the same");
        }
    }
}
