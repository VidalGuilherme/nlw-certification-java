package com.vidal.nlw_certification.modules.certifications.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Integer level;
}
