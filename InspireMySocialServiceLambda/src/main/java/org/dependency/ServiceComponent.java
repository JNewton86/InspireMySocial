package org.dependency;

import dagger.Component;
import org.activity.*;

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

    /**
     * Provides the relevant activity.
     * @return DeleteContentActivity
     */
    DeleteContentActivity provideDeleteContentActivity();

    /**
     * Provides the relevant activity.
     * @return GetCreditsByUserActivity
     */
    GetCreditsByUserActivity provideGetCreditsByUserActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateUserCreditBalanceActivity
     */
    UpdateUserCreditBalanceActivity provideUpdateUserCreditBalanceActivity();

    /**
     * Provides the relevant activity.
     * @return CreateImageForContentActivity
     */
    CreateImageForContentActivity provideCreateImageForContentActivity();

    /**
     * Provides the relevant activity.
     * @return GetImagesForContentActivity
     */
    GetImagesForContentActivity provideGetImagesForContentActivity();
}
