package com.keke.keke.dto.response;

import com.keke.keke.dao.entity.PhoneNumber;

/**
 * DTO for {@link PhoneNumber}
 */
public record PhoneNumberDto  (
    Long id,
    String number,
    String network){

    public PhoneNumberDto(PhoneNumber phoneNumber) {
        this(phoneNumber.getId(), phoneNumber.getNumber(), phoneNumber.getNetwork());
    }
}