package us.careydevelopment.api.google.datastore.util;

import java.io.IOException;

import com.google.api.client.auth.oauth2.StoredCredential;

/**
 * Implement this interface to persist the StoredCredential object.
 *
 * The framework code will use the implementation to handle persistence.
 */
@FunctionalInterface
public interface StoredCredentialPersister {

    /**
     * Persists the StoredCredential object.
     *
     * @param id
     * @param storedCredential
     * @return a boolean indicating whether the persistence was successful
     * @throws IOException
     */
    boolean persist(String id, StoredCredential storedCredential) throws IOException;
}
