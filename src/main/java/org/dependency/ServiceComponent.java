package org.dependency;

import dagger.Component;
import org.activity.CreateContentActivity;


import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, OpenAiDaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return CreatePlaylistActivity
     */
    CreateContentActivity provideCreateContentActivity();

    /**
     * Provides the relevant activity.
     * @return CreatePlaylistActivity
     */

}
