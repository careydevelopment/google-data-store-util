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

    @Test
    public void testStandardBuild() throws Exception {
        GoogleDataStoreConfig.shutdown();

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
        GoogleDataStoreConfig.shutdown();

        GoogleDataStoreConfig config = GoogleDataStoreConfig.Builder
                .instance()
                .setStoredCredentialPersister((id, storedCredential) -> true)
                .setStoredCredentialRetriever((id) -> null)
                .build();

        GoogleDataStoreConfig config2 = GoogleDataStoreConfig.Builder
                .instance()
                .setStoredCredentialPersister((id, storedCredential) -> false)
                .setStoredCredentialRetriever((id) -> null)
                .build();

        Assertions.assertEquals(config, config2);
    }

    @Test
    public void testGetInstanceWithoutBuilder() {
        GoogleDataStoreConfig.shutdown();
        Assertions.assertThrows(GoogleDataStoreConfigException.class, () -> GoogleDataStoreConfig.getInstance());
    }
}
