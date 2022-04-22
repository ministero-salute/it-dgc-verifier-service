package it.interop.dgc.verifier.config;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "european-drl")
public class EuropeanDrlCFG implements Serializable {

    /**
     * Serialization.
     */
    private static final long serialVersionUID = -2532858590529884524L;

    /**
     * Num max item in chunk.
     */
    private Integer numMaxItemInChunk;

    private Long sizeSingleHash;
}
