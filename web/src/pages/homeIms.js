import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class HomeIms extends BindingClass {
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
        this.header.addHeaderToPage();
        this.client = new MusicPlaylistClient();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const homeIms = new HomeIms();
    homeIms.mount();
};

window.addEventListener('DOMContentLoaded', main);
