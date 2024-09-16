package com.ofektom.gateway.handler;

import java.util.Map;

public record ErrorResponse (
        Map<String, String> errors
){
}
