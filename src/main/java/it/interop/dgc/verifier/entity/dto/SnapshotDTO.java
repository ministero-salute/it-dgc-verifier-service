package it.interop.dgc.verifier.entity.dto;

import java.util.List;

import it.interop.dgc.verifier.entity.SnapshotETY;
import lombok.Data;

@Data
public class SnapshotDTO {

    SnapshotETY snapshot;
    
    List<String> revokedUcvi;
}
