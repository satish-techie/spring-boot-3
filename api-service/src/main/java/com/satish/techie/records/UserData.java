package com.satish.techie.records;

import lombok.Builder;

@Builder
public record UserData(String firstName, String lastName) {
}
