package be.TFTIC.Tournoi.il.validators.alreadyExist;

import be.TFTIC.Tournoi.bll.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlreadyExistValidator implements ConstraintValidator<AlreadyExist, String> {

    private final UserService userService;


    @Override
    public void initialize(AlreadyExist constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.isBlank()) {
            return true;
        }
        return !userService.existsByUsername(username);
    }


}
