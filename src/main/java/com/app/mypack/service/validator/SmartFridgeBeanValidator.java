/**
 * SmartFridgeBeanValidator
 * M101J
 * <p>
 * Copyright (c) 2018, Apple Inc.
 * All rights reserved.
 */

package com.app.mypack.service.validator;

import com.app.mypack.exception.SmartFridgeManagerUnExpectedException;
import com.app.mypack.exception.ValidationFailedException;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SmartFridgeBeanValidator<T> {

    private static final Set<Object> REGISTERED_SET = new HashSet<>();

    private StringBuilder violationBuider;

    private String violations;

    public void validate(T beanType) throws ValidationFailedException {

        violationBuider = new StringBuilder();

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
                    violationBuider.append(" | ").append(iEx.getMessage()).append(" | ");
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
                    violationBuider.append(" | ").append(iEx.getMessage()).append(" | ");
                }
            }
        }

        violations = violationBuider.toString();

        if(!violations.isEmpty()) {
            throw new ValidationFailedException(violations);
        }
    }
}