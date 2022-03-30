package us.careydevelopment.api.google.datastore.util;

import java.io.IOException;

import com.google.api.client.auth.oauth2.StoredCredential;

@FunctionalInterface
public interface StoredCredentialPersister {

    boolean persist(String id, StoredCredential storedCredential) throws IOException;
}
