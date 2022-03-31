package us.careydevelopment.api.google.datastore.util;

import java.io.IOException;

import com.google.api.client.auth.oauth2.StoredCredential;

/**
 * Implement this interface to retrieve the StoredCredential object from a persistent store.
 *
 * The framework code will use the implementation to handle retrieval.
 */
@FunctionalInterface
public interface StoredCredentialRetriever {

    /**
     * Retrieves the StoredCredential associated with the ID.
     *
     * Note: the ID is typically a user ID in the system.
     *
     * @param id
     * @return the StoredCredential object
     * @throws IOException
     */
    StoredCredential retrieve(String id) throws IOException;
}
