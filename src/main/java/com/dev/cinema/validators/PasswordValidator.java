package com.dev.cinema.validators;

import com.dev.cinema.model.dto.UserRequestDto;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements
        ConstraintValidator<ValidPassword, UserRequestDto> {
    @Override
    public boolean isValid(UserRequestDto requestDto,
                           ConstraintValidatorContext context) {
        return requestDto.getPassword() != null
                && Objects.equals(requestDto.getPassword(), requestDto.getRepeatedPassword());
    }
}
