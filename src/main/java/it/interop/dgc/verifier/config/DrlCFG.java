package it.interop.dgc.verifier.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
 

/**
 *  @author vincenzoingenito
 *  Certification revoke list config.
 */
@Data
@Component
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "drl")
public class DrlCFG  implements Serializable {

    /**
     * Serialization.
     */
    private static final long serialVersionUID = -2532858590529884524L;

    /**
     * Num max item in chunk.
     */
    private Integer numMaxItemInChunk;
    
}
