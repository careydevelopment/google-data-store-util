package us.careydevelopment.api.google.datastore.util;

import java.io.IOException;

import com.google.api.client.auth.oauth2.StoredCredential;

@FunctionalInterface
public interface StoredCredentialRetriever {

    StoredCredential retrieve(String id) throws IOException;
}
