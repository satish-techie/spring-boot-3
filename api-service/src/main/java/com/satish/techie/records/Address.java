package com.satish.techie.records;

import lombok.Builder;

@Builder
public record Address(String flatNo, String street, String state, String country) {
}
