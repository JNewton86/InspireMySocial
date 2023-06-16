import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import InspireMySocialClient from '../api/inspireMySocialClient';
import { marked } from 'marked';

class Dashboard extends BindingClass {

    

    constructor() {
        super();
        this.userEmail=null;
        this.bindClassMethods(['mount', 'fbSubmit','instaSubmit','linkedInSubmit','twitterSubmit','ytShortSubmit', 'ytLongSubmit','generatePost','generateAvailableCredits', 'deleteContent'], this);
        this.header = new Header(this.dataStore);
        // Create a enw datastore with an initial "empty" state.
        // this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        console.log("home constructor");
        // this.dataStore.addChangeListener(this.displaySearchResults);
        // console.log("searchPlaylists constructor");
    }

    /**
    * Add the header to the page and load the InspireMySocialClient.
    */
    async mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        const postContainer = document.getElementById('accordionExample');
        this.header.addHeaderToPage();
        this.client = new InspireMySocialClient();            
        const userObject = await this.client.getIdentity();
        const socialPosts = await this.client.getContentForUser(userObject.email);
        socialPosts.forEach((post, index) => postContainer.innerHTML += this.generatePost(post.topic, post.aiMessage, index, post.contentType, post.contentId))
        const deleteButtons = document.querySelectorAll('.delete-button');
        deleteButtons.forEach((btn => btn.addEventListener('click', this.deleteContent)));
        const userModel = await this.client.getCreditsByUser();
        this.userEmail=userModel.data;
        console.log("this is the user model from mount " + JSON.stringify(userModel));
        remainingCredits.innerHTML += this.generateAvailableCredits(userModel.data.userModel.creditBalance);
        document.getElementById('fbForm').addEventListener('submit', this.fbSubmit);
        // document.getElementById('fbFormSubmit').addEventListener('click', this.fbSubmit);
        document.getElementById('instaForm').addEventListener('submit', this.instaSubmit);
        document.getElementById('linkedInForm').addEventListener('submit', this.linkedInSubmit);
        document.getElementById('twitterForm').addEventListener('submit', this.twitterSubmit);
        document.getElementById('ytShortForm').addEventListener('submit', this.ytShortSubmit);
        document.getElementById('ytLongForm').addEventListener('submit', this.ytLongSubmit);
    }

     /**
     * Method to run when the create FaceBook Post submit button is pressed. Call the InspireMySocialClient to create the
     * content. Refreshes page when complete.
     */
    async fbSubmit(evt) {
        evt.preventDefault();
        if (!evt.target.checkValidity()) {
            submit.vpreventDefault()
            submit.stopPropagation()
            return false;
          }
        
      
    
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
        const creditCost = "-1";
    
        try {
            const content = await this.client.createContent(contentType, tone, audience, topic, wordcount,creditCost, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
    
            this.dataStore.set('content', content);
            location.reload();
        } catch (error) {
            console.error('Error creating content:', error);
        } finally {
            location.reload();
        }
    }

    async instaSubmit(evt) {
        evt.preventDefault();
        if (!evt.target.checkValidity()) {
            submit.vpreventDefault()
            submit.stopPropagation()
            return false;
          }
        console.log("you hit the insta listener")
        const errorMessageDisplay = document.getElementById('error-message-insta');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
    
        const createButton = document.getElementById('instaFormSubmit');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';
    
        const contentType = 'Instagram Post';
        const tone = document.getElementById('insta-tone').value;
        const audience = document.getElementById('insta-audience').value;
        const topic = document.getElementById('insta-topic').value;
        const wordcount = document.getElementById('insta-wordcount').value;
        const creditCost = "-1";
    
        try {
            const content = await this.client.createContent(contentType, tone, audience, topic, wordcount, creditCost, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
    
            this.dataStore.set('content', content);
    
            // Make sure that reload is called after content is set
            location.reload();
        } catch (error) {
                       console.error('Error creating content:', error);
        } finally {
            location.reload();
        }
    }
    
    async linkedInSubmit(evt) {
        evt.preventDefault();
        if (!evt.target.checkValidity()) {
            submit.vpreventDefault()
            submit.stopPropagation()
            return false;
          }
    
        const errorMessageDisplay = document.getElementById('error-message-linkedIn');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
    
        const createButton = document.getElementById('linkedInFormSubmit');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';
    
        const contentType = 'LinkedIn Post';
        const tone = document.getElementById('linkedin-tone').value;
        const audience = document.getElementById('linkedin-audience').value;
        const topic = document.getElementById('linkedin-topic').value;
        const wordcount = document.getElementById('linkedin-wordcount').value;
        const creditCost = "-1";
    
        try {
            const content = await this.client.createContent(contentType, tone, audience, topic, wordcount, creditCost, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
    
            this.dataStore.set('content', content);
    
            // Make sure that reload is called after content is set
            location.reload();
        } catch (error) {
                       console.error('Error creating content:', error);
        } finally {
            location.reload();
        }
    }

    async twitterSubmit(evt) {
        evt.preventDefault();
        if (!evt.target.checkValidity()) {
            submit.vpreventDefault()
            submit.stopPropagation()
            return false;
          }
    
        const errorMessageDisplay = document.getElementById('error-message-twitter');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
    
        const createButton = document.getElementById('twitterFormSubmit');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';
    
        const contentType = 'Twitter Post';
        const tone = document.getElementById('Twitter-tone').value;
        const audience = document.getElementById('Twitter-audience').value;
        const topic = document.getElementById('Twitter-topic').value;
        const wordcount = document.getElementById('Twitter-wordcount').value;
        const creditCost = "-1";
    
        try {
            const content = await this.client.createContent(contentType, tone, audience, topic, wordcount,creditCost, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
    
            this.dataStore.set('content', content);
    
            // Make sure that reload is called after content is set
            location.reload();
        } catch (error) {
                       console.error('Error creating content:', error);
        } finally {
            location.reload();
        }
    }

    async ytShortSubmit(evt) {
        evt.preventDefault();
        if (!evt.target.checkValidity()) {
            submit.vpreventDefault()
            submit.stopPropagation()
            return false;
          }
    
        const errorMessageDisplay = document.getElementById('error-message-ytShortSubmit');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
    
        const createButton = document.getElementById('ytShortFormSubmit');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';
    
        const contentType = 'YouTube Short Script';
        const tone = document.getElementById('YTS-tone').value;
        const audience = document.getElementById('YTS-audience').value;
        const topic = document.getElementById('YTS-topic').value;
        const wordcount = "2";
        const creditCost = "-1";
    
        try {
            const content = await this.client.createContent(contentType, tone, audience, topic, wordcount, creditCost, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
    
            this.dataStore.set('content', content);
    
            // Make sure that reload is called after content is set
            location.reload();
        } catch (error) {
                       console.error('Error creating content:', error);
        } finally {
            location.reload();
        }
    }

    async ytLongSubmit(evt) {
        evt.preventDefault();
        if (!evt.target.checkValidity()) {
            submit.vpreventDefault()
            submit.stopPropagation()
            return false;
          }
    
        const errorMessageDisplay = document.getElementById('error-message-ytLongSubmit');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
    
        const createButton = document.getElementById('ytLongFormSubmit');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';
    
        const contentType = 'YouTube Long Script';
        const tone = document.getElementById('YTL-tone').value;
        const audience = document.getElementById('YTL-audience').value;
        const topic = document.getElementById('YTL-topic').value;
        const wordcount = document.getElementById('YTL-wordcount').value;
        const creditCost = "-2";
    
        try {
            const content = await this.client.createContent(contentType, tone, audience, topic, wordcount,creditCost, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
    
            this.dataStore.set('content', content);
    
            // Make sure that reload is called after content is set
            location.reload();
        } catch (error) {
                       console.error('Error creating content:', error);
        } finally {
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
            <div class="accordion-body markdown="1"">
            <span>
            
            ${marked.parse(content)}     
            
                </span>
                <div>
                <button type="button" id="delete${contentId}" data-content-id="${contentId}" class="delete-button btn btn-outline-danger btn-sm">Delete this Post</button>
                <p class="hidden error" id="error-message-delete"> </p>
                </div>
            </div>
        </div>
    </div>`;
    }

    generateAvailableCredits(creditBalance) {
        return`<div class="display-8 text-center text-muted">you have ${creditBalance} credits.
        </div>`
    }


    async deleteContent(event) {
        event.preventDefault();
        const contentId= event.target.getAttribute('data-content-id');
        console.log("Hello from delete content" + contentId);
        const errorMessageDisplay = document.getElementById('error-message-delete');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');              
        event.target.innerText = 'deleting...';
        const contendIdToDelete = contentId;
        console.log("from the deletecontent method the email is: " + JSON.stringify(this.userEmail));
        try {
            const content = await this.client.softDeleteContent(contendIdToDelete, this.userEmail.userModel.userId, ()=> null);         
    
            location.reload();
        } catch (error) {
            console.error('Error deleting content:', error);
        } finally {
            location.reload();
        }
    }
//end of class    
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const dashboard = new Dashboard();
    dashboard.mount();
};

window.addEventListener('DOMContentLoaded', main);
