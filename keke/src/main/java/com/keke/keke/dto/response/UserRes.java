package com.keke.keke.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keke.keke.dao.entity.User;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * DTO for {@link User}
 */
@Data
public class UserRes implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedOn;

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumbers;
    private boolean active;


    public UserRes(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.createdOn = user.getCreatedOn();
        this.updatedOn = user.getUpdatedOn();
        this.active = user.isActive();
        this.phoneNumbers = user.getPhoneNumbers();
    }

    // private static Set<PhoneNumberDto> mapPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    //     if (CollectionUtils.isEmpty(phoneNumbers)) {
    //         return Collections.emptySet();
    //     }
    //     return phoneNumbers.stream()
    //             .map(PhoneNumberDto::new)
    //             .collect(Collectors.toUnmodifiableSet());
    // }
}