package us.careydevelopment.api.google.datastore;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.careydevelopment.api.google.datastore.config.GoogleDataStoreConfig;
import us.careydevelopment.api.google.datastore.harness.GoogleDataStoreConfigHarness;
import us.careydevelopment.api.google.datastore.harness.StoredCredentialHarness;
import us.careydevelopment.api.google.datastore.harness.StoredCredentialPersisterHarness;
import us.careydevelopment.api.google.datastore.harness.StoredCredentialRetrieverHarness;
import us.careydevelopment.api.google.datastore.util.StoredCredentialPersister;
import us.careydevelopment.api.google.datastore.util.StoredCredentialRetriever;

public class GoogleCredentialDataStoreTest {

    private static final StoredCredentialPersister PERSISTER = StoredCredentialPersisterHarness.getGoodPersister();
    private static final StoredCredentialRetriever RETRIEVER = StoredCredentialRetrieverHarness.getGoodRetriever();

    @Test
    public void testGet() throws Exception {
        GoogleDataStoreConfigHarness.setUpConfig();

        final DataStore<StoredCredential> dataStore = GoogleDataStoreFactory
                .getInstance()
                .getDataStore(GoogleDataStoreFactory.CREDENTIAL_STORE_ID);

        final StoredCredential credential = dataStore.get(StoredCredentialRetrieverHarness.ID);

        Assertions.assertNotNull(credential);
        Assertions.assertEquals(StoredCredentialHarness.ACCESS_TOKEN, credential.getAccessToken());
        Assertions.assertEquals(StoredCredentialHarness.REFRESH_TOKEN, credential.getRefreshToken());
    }

    @Test
    public void testSet() throws Exception {
        GoogleDataStoreConfigHarness.setUpConfig();

        final DataStore<StoredCredential> dataStore = GoogleDataStoreFactory
                .getInstance()
                .getDataStore(GoogleDataStoreFactory.CREDENTIAL_STORE_ID);

        final DataStore<StoredCredential> ds = dataStore.set(StoredCredentialRetrieverHarness.ID, StoredCredentialHarness.getGoodStoredCredential());
        Assertions.assertNotNull(ds);
    }
}
