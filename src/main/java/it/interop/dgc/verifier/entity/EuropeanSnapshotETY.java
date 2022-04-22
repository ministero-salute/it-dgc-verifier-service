package it.interop.dgc.verifier.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import it.interop.dgc.verifier.utils.DRLEUConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = DRLEUConstants.SnapshotField.COLLECTION_NAME)
public class EuropeanSnapshotETY {

	/**
	 * Pk document.
	 */
	@Id
	private String id;

	/**
	 * Version.
	 */
	@Field(name = DRLEUConstants.SnapshotField.VERSION)
	private Long version;

	/**
	 * Date of creation.
	 */
	@Field(name = DRLEUConstants.SnapshotField.CREATION_DATE)
	private Date creationDate;

	/**
	 * Flag archived.
	 */
	@Field(name = DRLEUConstants.SnapshotField.FLAG_ARCHIVED)
	private Boolean flag_archived;

	/**
	 * Num totale revoke.
	 */
	@Field(name = DRLEUConstants.SnapshotField.TOTAL)
	private Integer numberRevocation;

}
