package com.services.ms.student.student_app.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token is required")
    @JsonProperty("refreshToken")
    private String refreshToken;
}