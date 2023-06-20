import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class InspireMySocialClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout','signUp','createContent', 'getImagesForContent','softDeleteContent', 'getCreditsByUser', 'createImageForContent'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async signUp(){
        this.authenticator.signUp();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the credits for the given ID.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user's metadata.
     */
      async getCreditsByUser(errorCallback) {
        try { 
            const token = await this.getTokenOrThrow("Only authenticated users can access credit balance.");
            const response = await this.axiosClient.get(`users`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async getImagesForContent(userEmail, contentId, errorCallback) {
        try { 
            const token = await this.getTokenOrThrow("Only authenticated users can access credit balance.");
            const response = await this.axiosClient.get(`content/${userEmail}/${contentId}/images`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async createImageForContent(contentId, prompt, imageSize, errorCallback) {
        try{
            const token = await this.getTokenOrThrow("Only authenticated users can access credit balance.");
            const response = await this.axiosClient.post('content/image', {
                contentId: contentId,
                prompt: prompt,
                imageSize: imageSize


            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            
            
            return response.content; 
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Get the songs on a given playlist by the playlist's identifier.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of songs on a playlist.
     */
    async createContent(contentType, tone, audience, topic, wordCount, creditUsage, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");
            const response = await this.axiosClient.post(`content`, {
                contentType: contentType,
                tone: tone,
                audience: audience,
                topic: topic,
                wordCount: wordCount
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const credtCost = await this.axiosClient.put('users/update',{
                creditUsage: creditUsage
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                } 
            });
            return response.content;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async softDeleteContent(contentId, userEmail, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");
            const response = await this.axiosClient.delete(`content/${userEmail}/${contentId}`, {data:{
                contentId: contentId,
                userId: userEmail,
            }, headers: {
                Authorization: `Bearer ${token}`
            }}, 
                
            );
            return response.content;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    async getContentForUser(userEmail) {
        try {
            const response = await this.axiosClient.get(`content/${userEmail}`);
            return response.data.contentList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
