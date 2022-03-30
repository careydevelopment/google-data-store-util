package us.careydevelopment.api.google.datastore;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;

import us.careydevelopment.api.google.datastore.config.GoogleDataStoreConfig;

public class GoogleCredentialDataStore extends AbstractDataStore<StoredCredential> {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleCredentialDataStore.class);
    
    private final Lock lock = new ReentrantLock();

    private GoogleDataStoreConfig config = GoogleDataStoreConfig.getInstance();
    
    protected GoogleCredentialDataStore(DataStoreFactory dataStoreFactory, String id) {
	super(dataStoreFactory, id);
    }
	
    public Set<String> keySet() throws IOException {
        return null;
    }
	
    public Collection<StoredCredential> values() throws IOException {
        return null;
    }

    public StoredCredential get(String key) throws IOException {
        LOG.debug("In StoredCredentialDataStore getting key " + key);
	    
        if (key != null) {
            lock.lock();
			
            try {
                StoredCredential storedCredential = config.getRetriever().retrieve(key);
                return storedCredential;
            } finally {
                lock.unlock();
            }
	    }
	
        LOG.debug("returning null");
        return null;
    }

	
    public DataStore<StoredCredential> set(String key, StoredCredential value) throws IOException {		
        if (key != null) {
            LOG.debug("Persisting StoredCredential " + value + " for " + key);
            lock.lock();
			
            try {
                config.getPersister().persist(key, value);
            } finally {
                lock.unlock();
            }
	    }
		
	    return this;
    }

	
    public DataStore<StoredCredential> clear() throws IOException {
        return null;
    }

	
    public DataStore<StoredCredential> delete(String key) throws IOException {
        return null;
    }
}
