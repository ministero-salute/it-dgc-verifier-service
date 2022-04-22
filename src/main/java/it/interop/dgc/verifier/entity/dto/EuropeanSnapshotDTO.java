package it.interop.dgc.verifier.entity.dto;

import java.util.List;

import it.interop.dgc.verifier.entity.EuropeanSnapshotETY;
import lombok.Data;

@Data
public class EuropeanSnapshotDTO {

    EuropeanSnapshotETY snapshot;
    
    List<String> revokedIdentifiers;
}
