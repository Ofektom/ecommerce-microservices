package com.example.static_client.controller;

public record Users(
        String firstname,
        String lastname,
        String email,
        Object address
) {
}
