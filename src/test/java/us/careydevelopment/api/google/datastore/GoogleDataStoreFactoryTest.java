package us.careydevelopment.api.google.datastore;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.careydevelopment.api.google.datastore.config.GoogleDataStoreConfig;
import us.careydevelopment.api.google.datastore.exception.GoogleDataStoreException;
import us.careydevelopment.api.google.datastore.harness.GoogleDataStoreConfigHarness;
import us.careydevelopment.api.google.datastore.harness.StoredCredentialPersisterHarness;
import us.careydevelopment.api.google.datastore.harness.StoredCredentialRetrieverHarness;
import us.careydevelopment.api.google.datastore.util.StoredCredentialPersister;
import us.careydevelopment.api.google.datastore.util.StoredCredentialRetriever;

import java.io.Serializable;

public class GoogleDataStoreFactoryTest {


    @Test
    public void testGetInstance() {
        GoogleDataStoreConfigHarness.setUpConfig();
        GoogleDataStoreFactory factory = GoogleDataStoreFactory.getInstance();
        Assertions.assertNotNull(factory);
    }

    @Test
    public void testGetCredentialDataStore() throws Exception {
        GoogleDataStoreConfigHarness.setUpConfig();
        GoogleDataStoreFactory factory = GoogleDataStoreFactory.getInstance();

        DataStore<StoredCredential> ds = factory.getDataStore(GoogleDataStoreFactory.CREDENTIAL_STORE_ID);
        Assertions.assertNotNull(ds);
        Assertions.assertTrue(ds instanceof GoogleCredentialDataStore);
    }

    @Test
    public void testGetDataStoreWithBogusId() throws Exception {
        GoogleDataStoreConfigHarness.setUpConfig();
        GoogleDataStoreFactory factory = GoogleDataStoreFactory.getInstance();
        Assertions.assertThrows(GoogleDataStoreException.class, () -> factory.getDataStore("bogus"));
    }
}
