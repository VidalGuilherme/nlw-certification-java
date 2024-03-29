package com.vidal.nlw_certification.modules.certifications.dtos;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationCreateDTO {

    @NotBlank
    private String technology;
    @NotEmpty
    private List<UUID> questionsId;
}
