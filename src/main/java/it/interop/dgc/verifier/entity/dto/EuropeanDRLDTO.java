package it.interop.dgc.verifier.entity.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EuropeanDRLDTO implements Serializable {

    /**
     * Serializzation.
     */
    private static final long serialVersionUID = -2569851440652674636L;

    /**
     * ucvi hashed list.
     */
    @Schema(description = "Lista dei certificati revocati EU")
    private EuropeanSnapshotDTO drl;

    /**
     * Delta version.
     */
    @Schema(description = "Delta versioni EU")
    private EuropeanDeltaDTO deltaVers;

}
