package com.app.mypack.service.validator;

import com.app.mypack.exception.SmartFridgeManagerUnExpectedException;
import com.app.mypack.exception.ValidationFailedException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class SmartFridgeBeanValidator<T> {

    private StringBuilder violationBuilder;

    private String violations;

    public void validate(T beanType) throws ValidationFailedException {

        violationBuilder = new StringBuilder();

        Field[] smartFridgeManagerFields = beanType.getClass().getDeclaredFields();

        for (Field f : smartFridgeManagerFields) {
            if (f.getAnnotation(NotNull.class) != null) {
                try {
                    f.setAccessible(true);
                    Objects.requireNonNull(f.get(beanType), f.getName() + " cannot be null");
                } catch (IllegalArgumentException iEx) {
                    throw new SmartFridgeManagerUnExpectedException(iEx.getMessage());
                } catch (IllegalAccessException iEx) {
                    throw new SmartFridgeManagerUnExpectedException(iEx.getMessage());
                } catch (NullPointerException iEx) {
                    violationBuilder.append(" | ").append(iEx.getMessage()).append(" | ");
                }
            }

            if (f.getAnnotation(Size.class) != null) {
                try {
                    f.setAccessible(true);
                    Object field = f.get(beanType);
                    if (null != field) {
                        if (field instanceof List) {
                            int actualSizeOfField = ((List) (field)).size();
                            if (actualSizeOfField < f.getAnnotation(Size.class).min() || actualSizeOfField > f.getAnnotation(Size.class).max()) {
                                throw new ValidationFailedException(f.getName() + " does not meet the expected Size ");
                            }
                        } else if (field instanceof String) {
                            int actualSizeOfField = ((String) (field)).length();
                            if (actualSizeOfField < f.getAnnotation(Size.class).min() || actualSizeOfField > f.getAnnotation(Size.class).max()) {
                                throw new ValidationFailedException(f.getName() + " does not meet the expected Size ");
                            }
                        }

                    }
                } catch (IllegalAccessException iEx) {
                    throw new SmartFridgeManagerUnExpectedException(iEx.getMessage());
                } catch (IllegalArgumentException iEx) {
                    throw new SmartFridgeManagerUnExpectedException(iEx.getMessage());
                } catch(ValidationFailedException iEx) {
                    violationBuilder.append(" | ").append(iEx.getMessage()).append(" | ");
                }
            }
        }

        violations = violationBuilder.toString();

        if(!violations.isEmpty()) {
            throw new ValidationFailedException(violations);
        }
    }
}