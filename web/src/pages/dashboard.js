import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import InspireMySocialClient from '../api/inspireMySocialClient';

class Dashboard extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount'], this);
        this.header = new Header(this.dataStore);
        // Create a enw datastore with an initial "empty" state.
        // this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        console.log("home constructor");
        // this.dataStore.addChangeListener(this.displaySearchResults);
        // console.log("searchPlaylists constructor");
    }

    /**
    * Add the header to the page and load the MusicPlaylistClient.
    */
    mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        // document.getElementById('search-playlists-form').addEventListener('submit', this.search);
        // document.getElementById('search-btn').addEventListener('click', this.search);
        document.getElementById('fbFormSubmit').addEventListener('click', this.fbSubmit);
        this.header.addHeaderToPage();
        this.client = new InspireMySocialClient();
    }

    /**
     * Method to run when the create FaceBook Post submit button is pressed. Call the InspireMySocialClient to create the
     * content.
     */
    async fbSubmit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('fbFormSubmit');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const contentType = 'face book post';
        const tone = document.getElementById('fb-tone').value;
        const audience = document.getElementById('fb-audience').value;
        const topic = document.getElementById('fb-topic').value;
        const wordcount = document.getElementById('fb-wordcount').value;


        const content = await this.client.createContent(contentType, tone, audience, topic, wordcount, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('content', content);
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const dashboard = new Dashboard();
    dashboard.mount();
};

window.addEventListener('DOMContentLoaded', main);
