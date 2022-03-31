package us.careydevelopment.api.google.datastore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.careydevelopment.api.google.datastore.GoogleDataStoreFactory;
import us.careydevelopment.api.google.datastore.util.StoredCredentialPersister;
import us.careydevelopment.api.google.datastore.util.StoredCredentialRetriever;
import us.careydevelopment.api.google.datastore.exception.GoogleDataStoreConfigException;

public class GoogleDataStoreConfig {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleDataStoreConfig.class);

    private StoredCredentialPersister persister;
    private StoredCredentialRetriever retriever;

    private static GoogleDataStoreConfig INSTANCE = null;

    private GoogleDataStoreConfig(Builder builder) { 
        persister = builder.persister;
        retriever = builder.retriever;
        
        INSTANCE = this;
    }
    
    public static GoogleDataStoreConfig getInstance() {
        if (INSTANCE == null) {
            throw new GoogleDataStoreConfigException("Google Data Store not configured!");
        }
        
        return INSTANCE;
    }

    public static void shutdown() {
        INSTANCE = null;
    }

    public StoredCredentialPersister getPersister() {
        return persister;
    }

    public StoredCredentialRetriever getRetriever() {
        return retriever;
    }


    public static class Builder {
        private StoredCredentialPersister persister;
        private StoredCredentialRetriever retriever;

        public static Builder instance() {
            return new Builder();
        }

        public Builder setStoredCredentialPersister(StoredCredentialPersister persister) {
            this.persister = persister;
            return this;
        }
        
        public Builder setStoredCredentialRetriever(StoredCredentialRetriever retriever) {
            this.retriever = retriever;
            return this;
        }
        
        public GoogleDataStoreConfig build() {
            if (INSTANCE == null) {
                return new GoogleDataStoreConfig(this);                
            } else {
                LOG.warn("GoogleDataStoreConfig already created - returning existing instance");
                return INSTANCE;
            }
        }
    }
}
