package org.dependency;

import com.fasterxml.jackson.core.JsonProcessingException;
import dagger.Module;
import dagger.Provides;
import org.openaiservice.OpenAiService;
import org.utils.UtilsOpenAiAPI;

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

    private String getAPItoken()  {
        try {
            return UtilsOpenAiAPI.sortSecret().getOpenAiApiKey();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method to Provide for dagger a singleton of OpenAiService.
     * @return a instance of OpenAiService that can be used by dagger for dependency injection.
     */
    @Singleton
    @Provides
    public OpenAiService provideOpenAiService() {
        return new OpenAiService(this.getAPItoken(), Duration.ZERO);
    }
}

