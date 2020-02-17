/*
 * Copyright 2000-2020 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.component.datetimepicker;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * View for testing validation with {@link DateTimePicker}.
 */
@Route("date-time-picker-validation")
public class DateTimePickerValidationPage extends Div {

    final String STATUS = "The Invalid state of the DateTimePicker is ";
    final String VALUE = "The Server side value is ";

    public DateTimePickerValidationPage() {
        initView();
        createPickerWithMaxAndMinValues();
    }

    private void initView() {
        final DateTimePicker dateTimePicker = new DateTimePicker();
        Label label = new Label();
        Label value = new Label();
        value.setId("server-side-value");
        dateTimePicker.setId("field");
        add(dateTimePicker);

        NativeButton button = new NativeButton("Make the input invalid");
        button.setId("invalidate");
        button.addClickListener(event -> {
            dateTimePicker.setErrorMessage("Invalidated from server");
            dateTimePicker.setInvalid(true);
            value.setText(VALUE + dateTimePicker.getValue());
            label.setText(STATUS + dateTimePicker.isInvalid());
        });
        add(button);

        button = new NativeButton("Make the input valid");
        button.setId("validate");
        button.addClickListener(event -> {
            dateTimePicker.setErrorMessage(null);
            dateTimePicker.setInvalid(false);
            value.setText(VALUE + dateTimePicker.getValue());
            label.setText(STATUS + dateTimePicker.isInvalid());
        });

        dateTimePicker.addValueChangeListener(event -> {
            label.setText(STATUS + dateTimePicker.isInvalid());
            if (dateTimePicker.isInvalid()) {
                dateTimePicker.setErrorMessage("Invalidated from server");
                value.setText(VALUE + dateTimePicker.getValue());
            } else {
                dateTimePicker.setErrorMessage(null);
                value.setText(VALUE + dateTimePicker.getValue());
            }
        });
        add(button);
        add(label, value);
    }


    private void createPickerWithMaxAndMinValues() {
        final DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setMin(
                LocalDateTime.of(LocalDate.of(2020, 6, 6),
                        LocalTime.of(12, 12)));
        dateTimePicker.setMax(
                LocalDateTime.of(LocalDate.of(2020, 7, 6),
                        LocalTime.of(14, 14)));
        dateTimePicker.setId("picker-with-valid-range");

        final Div isValid = new Div();
        isValid.setId("is-invalid");
        final NativeButton checkIsValid = new NativeButton(
            "Check if current value of the field is invalid");
        checkIsValid.setId("check-is-invalid");
        checkIsValid.addClickListener(event -> isValid
            .setText(dateTimePicker.isInvalid() ? "invalid" : "valid"));
        add(dateTimePicker, checkIsValid, isValid);
    }
}
