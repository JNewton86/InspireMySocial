import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import InspireMySocialClient from '../api/inspireMySocialClient';

class Dashboard extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'fbSubmit', 'generatePost'], this);
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
    async mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        // document.getElementById('search-playlists-form').addEventListener('submit', this.search);
        const postContainer = document.getElementById('accordionExample');
        document.getElementById('fbFormSubmit').addEventListener('click', this.fbSubmit);
        this.header.addHeaderToPage();
        this.client = new InspireMySocialClient();
        const userObject = await this.client.getIdentity();
        console.log(userObject);
        const socialPosts = await this.client.getContentForUser(userObject.email);
        console.log(socialPosts);
        console.log(socialPosts)
        socialPosts.forEach((post, index) => postContainer.innerHTML += this.generatePost(post.topic, post.aiMessage, index, post.contentType, post.contentId))
    }

    /**
     * Method to run when the create FaceBook Post submit button is pressed. Call the InspireMySocialClient to create the
     * content.
     */
    // async fbSubmit(evt) {
    //     evt.preventDefault();

    //     const errorMessageDisplay = document.getElementById('error-message-fb');
    //     errorMessageDisplay.innerText = ``;
    //     errorMessageDisplay.classList.add('hidden');

    //     const createButton = document.getElementById('fbFormSubmit');
    //     const origButtonText = createButton.innerText;
    //     createButton.innerText = 'Loading...';

    //     const contentType = 'Face Book Post';
    //     const tone = document.getElementById('fb-tone').value;
    //     const audience = document.getElementById('fb-audience').value;
    //     const topic = document.getElementById('fb-topic').value;
    //     const wordcount = document.getElementById('fb-wordcount').value;


    //     const content = await this.client.createContent(contentType, tone, audience, topic, wordcount, (error) => {
    //         createButton.innerText = origButtonText;
    //         errorMessageDisplay.innerText = `Error: ${error.message}`;
    //         errorMessageDisplay.classList.remove('hidden');
    //     });
    //     this.dataStore.set('content', content);
    //     location.reload()
    // }

    async fbSubmit(evt) {
        evt.preventDefault();
    
        const errorMessageDisplay = document.getElementById('error-message-fb');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
    
        const createButton = document.getElementById('fbFormSubmit');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';
    
        const contentType = 'Face Book Post';
        const tone = document.getElementById('fb-tone').value;
        const audience = document.getElementById('fb-audience').value;
        const topic = document.getElementById('fb-topic').value;
        const wordcount = document.getElementById('fb-wordcount').value;
    
        try {
            const content = await this.client.createContent(contentType, tone, audience, topic, wordcount, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
    
            this.dataStore.set('content', content);
    
            // Make sure that reload is called after content is set
            location.reload();
        } catch (error) {
            // Handle any error that might occur during the await
            console.error('Error creating content:', error);
        } finally {
            // Optionally, you can put the reload here, to make sure it reloads whether or not the content creation was successful.
            location.reload();
        }
    }
    

    generatePost(title, content, index, contentType, contentId) {
        return `<div class="accordion-item">
        <h2 class="accordion-header" id="heading${index}">
            <button class="accordion-button accordion-button-collapse" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapse${index}" aria-expanded="${index === 0 ? true : false}" aria-controls="collapse${index}", data-bs-parent="#accordionExample">
                ${contentType} : ${title}
            </button>
        </h2>
        <div id="collapse${index}" class="${index === 0 ? "accordion-collapse collapse show" : "accordion-collapse collapse"}" aria-labelledby="heading${index}"
            data-bs-parent="#accordionExample">
            <div class="accordion-body">
                ${content}
                <div>
                <button type="button" id="deletebutton${contentId}" class="btn btn-outline-danger btn-sm">Delete this Post</button>
                </div>
            </div>
        </div>
    </div>`;
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
