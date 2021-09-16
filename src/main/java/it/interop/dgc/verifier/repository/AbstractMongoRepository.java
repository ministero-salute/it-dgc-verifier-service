package it.interop.dgc.verifier.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;

/**
 *  Abstract mongo repository. 
 */
@Component
public abstract class AbstractMongoRepository<T, K> {

	/**
	 * Template per accedere a MongoDB.
	 */
    @Autowired
    private MongoTemplate mongoTemplate;

	/**
	 * Insert or update.
	 * 
	 * @param entity	entità
	 * @return			entitàt aggiornata
	 */
	protected T upsert(final T entity) {
		return mongoTemplate.save(entity);
	}

	/**
	 * Rimuovi entità.
	 * 
	 * @param entity	entità da rimuovere
	 */
	protected void remove(final T entity) {
		mongoTemplate.remove(entity);
	}

	/**
	 * Seleziona per id.
	 * 
	 * @param primaryKey	id
	 * @return				entità selezionata
	 */
	protected T selectByID(final K primaryKey) {
		return mongoTemplate.findById(primaryKey, getCls());
	}

	/**
	 * Seleziona tutte entità.
	 * 
	 * @return	entità risultati
	 */
	protected List<T> selectAll() {
		return mongoTemplate.findAll(getCls());
	}
	
	/**
	 * Creazione collezione.
	 * 
	 * @return	collezione
	 */
	protected MongoCollection<Document> createCollection() {
		return mongoTemplate.createCollection(getCls());
	}
	
	/**
	 * Recupera lista collezioni.
	 * 
	 * @return	lista collezioni
	 */
	protected Set<String> getCollections() {
		return mongoTemplate.getCollectionNames();
	}

	private Class<T> getCls() {
		ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
		return ((Class<T>) pt.getActualTypeArguments()[0]);
		
	}

}
