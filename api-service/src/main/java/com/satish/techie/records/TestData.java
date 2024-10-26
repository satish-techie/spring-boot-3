package com.satish.techie.records;

import lombok.Builder;

@Builder
public record TestData(UserData user, Address address, long exp) {
}
