package org.dependency;

import dagger.Module;
import dagger.Provides;
import org.openaiservice.OpenAiService;
import org.openaiservice.SecretHolder;

import java.time.Duration;
import javax.inject.Singleton;


/**
 * Dagger Module providing dependencies for OpenAiDao classes.
 */
@Module
public class OpenAiDaoModule {

    /**
     * Provides a OpenAiService singleton instance.
     *
     * @return OpenAiService object
     */

    private SecretHolder secretHolder;

    @Singleton
    @Provides
    public OpenAiService provideOpenAiService() {
        return new OpenAiService(SecretHolder.getOpenAiApiKey(), Duration.ofSeconds(240));
    }
}

