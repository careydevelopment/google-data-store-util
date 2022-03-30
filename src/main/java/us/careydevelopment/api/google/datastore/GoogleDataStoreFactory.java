package us.careydevelopment.api.google.datastore;

import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.DataStore;
import us.careydevelopment.api.google.datastore.exception.GoogleDataStoreException;

public class GoogleDataStoreFactory extends AbstractDataStoreFactory {
    
    private static final Logger LOG = LoggerFactory.getLogger(GoogleDataStoreFactory.class);
    
    public static final String CREDENTIAL_STORE_ID = "careyCredentialStore";
    
    private static GoogleDataStoreFactory INSTANCE = null;
    
    private GoogleDataStoreFactory() { }
    
    public static GoogleDataStoreFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GoogleDataStoreFactory();
        }
        
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <V extends Serializable> DataStore<V> createDataStore(String id) throws IOException {
        LOG.debug("Creating data store for " + id);
        
        DataStore<V> dataStore = null;
        
        switch(id) {
            case CREDENTIAL_STORE_ID:
                dataStore = (DataStore<V>) new GoogleCredentialDataStore(this, id);
                break;
            default:
                throw new GoogleDataStoreException("No data store with ID " + id + " exists!");
        }
        
        return dataStore;
    }    
}
