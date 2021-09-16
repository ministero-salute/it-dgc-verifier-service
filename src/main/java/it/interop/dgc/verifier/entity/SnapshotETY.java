package it.interop.dgc.verifier.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model per il salvataggio di un pass.
 */
@Getter
@Setter
@Document(collection = "snapshot")
@Data 
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotETY {
 
	
	/**
	 * Pk documento.
	 */
	@Id
	private String id;

	/**
	 * Versione.
	 */
	@Field(name = "version")
	private Long version;

	/**
	 * Lista dei pass revocati.
	 */
	@Field(name = "revoked_ucvi")
	private List<String> revokedUcvi;

	/**
	 * Data creazione lista.
	 */
	@Field(name = "creation_date")
	private Date creationDate;

}
