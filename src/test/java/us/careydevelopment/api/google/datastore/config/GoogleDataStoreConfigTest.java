package us.careydevelopment.api.google.datastore.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import us.careydevelopment.api.google.datastore.exception.GoogleDataStoreConfigException;
import us.careydevelopment.api.google.datastore.harness.StoredCredentialHarness;
import us.careydevelopment.api.google.datastore.harness.StoredCredentialPersisterHarness;
import us.careydevelopment.api.google.datastore.harness.StoredCredentialRetrieverHarness;
import us.careydevelopment.api.google.datastore.util.StoredCredentialPersister;
import us.careydevelopment.api.google.datastore.util.StoredCredentialRetriever;

public class GoogleDataStoreConfigTest {

    @AfterEach
    void cleanup() {
        GoogleDataStoreConfig.shutdown();
    }

    @Test
    public void testStandardBuild() throws Exception {
        final StoredCredentialPersister persister = StoredCredentialPersisterHarness.getGoodPersister();
        final StoredCredentialRetriever retriever = StoredCredentialRetrieverHarness.getGoodRetriever();

        final GoogleDataStoreConfig config = GoogleDataStoreConfig.Builder
                .instance()
                .setStoredCredentialPersister(persister)
                .setStoredCredentialRetriever(retriever)
                .build();

        Assertions.assertNotNull(config);

        Assertions.assertEquals(StoredCredentialHarness.ACCESS_TOKEN,
                config
                        .getRetriever()
                        .retrieve(StoredCredentialRetrieverHarness.ID)
                        .getAccessToken());

        Assertions.assertFalse(config.getPersister().persist(StoredCredentialRetrieverHarness.ID, StoredCredentialHarness.getGoodStoredCredential()));
    }

    @Test
    public void testRepeatedBuild() {
        GoogleDataStoreConfig config = GoogleDataStoreConfig.Builder
                .instance()
                .setStoredCredentialPersister((id, storedCredential) -> true)
                .setStoredCredentialRetriever((id) -> null)
                .build();

        GoogleDataStoreConfig.Builder secondBuilder = GoogleDataStoreConfig.Builder
                .instance()
                .setStoredCredentialPersister((id, storedCredential) -> true)
                .setStoredCredentialRetriever((id) -> null);

        Assertions.assertNotNull(config);
        Assertions.assertThrows(GoogleDataStoreConfigException.class, () -> secondBuilder.build());
    }

    @Test
    public void testGetInstanceWithoutBuilder() {
        Assertions.assertThrows(GoogleDataStoreConfigException.class, () -> GoogleDataStoreConfig.getInstance());
    }
}
