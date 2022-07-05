package com.codewithme.notification;

public record CustomerRegistrationRequest(String firstName,
                                          String lastName,
                                          String email) {
}
