package org.dependency;

import dagger.Component;
import org.activity.CreateContentActivity;
import org.activity.DeleteContentActivity;
import org.activity.GetContentForUserActivity;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, OpenAiDaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return CreateContentActivity
     */
    CreateContentActivity provideCreateContentActivity();

    /**
     * Provides the relevant activity.
     * @return GetContentByUserActivity
     */
    GetContentForUserActivity provideGetContentForUserActivity();

    DeleteContentActivity provideDeleteContentActivity();
}
