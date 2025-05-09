package com.api.v1.people.utils;

import com.api.v1.people.domain.exposed.Person;

public final class FullNameFormatter {

    public static String format(Person person) {
        if (person.getMiddleName() == null) {
            return String.format("%s %s", person.getFirstName(), person.getLastName());
        }
        return String.format("%s %s %s", person.getFirstName(), person.getMiddleName(), person.getLastName());
    }

}
