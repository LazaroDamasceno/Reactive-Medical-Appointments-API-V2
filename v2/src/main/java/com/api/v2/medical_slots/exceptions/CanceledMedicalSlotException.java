package com.api.v2.medical_slots.exceptions;

import org.bson.types.ObjectId;

public class CanceledMedicalSlotException extends RuntimeException {
    public CanceledMedicalSlotException(ObjectId id) {
        super("Medical slot whose id is %s is already canceled.".formatted(id));
    }
}
