import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import InspireMySocialClient from '../api/inspireMySocialClient';
import DataStore from '../util/DataStore';
import { marked } from 'marked';

class Dashboard extends BindingClass {

    

    constructor() {
        super();
        this.userEmail=null;
        this.bindClassMethods(['mount','fbSubmit','instaSubmit','linkedInSubmit','twitterSubmit','ytShortSubmit', 'ytLongSubmit','generatePost','generateAvailableCredits', 'deleteContent'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.client = new InspireMySocialClient(); 
        // Create a enw datastore with an initial "empty" state.
        console.log("home constructor");
        this.dataStore.addChangeListener(this.displaySearchResults);
        // console.log("searchPlaylists constructor");
    }

    /**
    * Add the header to the page and load the InspireMySocialClient.
    */
    async mount() {
        this.header.addHeaderToPage();
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        const postContainer = document.getElementById('list');
        // Add event delegation for delete buttons
        postContainer.addEventListener('click', (event) => {
            if (event.target.classList.contains('delete-button')) {
                this.deleteContent(event);
            } else if (event.target.classList.contains('createImageForContent-button')) {
                this.createImageForContent(event);
            }
        });
                    
        const userObject = await this.client.getIdentity();
        console.log("userEmail is: " + userObject.email);
        const socialPosts = await this.client.getContentForUser(userObject.email);
        // PAUSE ON IMAGES FOR NOW
        // await makeImageMap.call(this);

        async function getImagesUrlMap(email, contentIds) {
            const urlMap = {};
            const promises = [];
        
            for (const contentId of contentIds) {
                const promise = this.client.getImagesForContent(email, contentId).then(function(imageUrls) {
                    urlMap[contentId] = imageUrls;
                });
                promises.push(promise);
            }
        
            await Promise.all(promises);

            return urlMap;
        }
        
        async function makeImageMap() {
            const contentIdList = socialPosts.map(post => post.contentId);
            const imageUrlMap = await getImagesUrlMap.call(this, userObject.email, contentIdList);
            console.log(imageUrlMap);
        }
        //PAGINATION
        // Number of items per page
        const itemsPerPage = 10;

        // Array of items
        const contentIdList = socialPosts.map(post => post.cont)
        //const items = socialPosts.map((post, index) => this.generatePost(urlMap, post.topic, post.aiMessage, index, post.contentType, post.contentId));
        const items = socialPosts.map((post, index) => this.generatePost(userObject.email, post.topic, post.aiMessage, index, post.contentType, post.contentId));;
        console.log("items are:" + items)
        
        // Function to display items for the given page
        function displayItems(page) {
            const startIndex = (page - 1) * itemsPerPage;
            const endIndex = startIndex + itemsPerPage;
            const section1Items = items.slice(startIndex, endIndex);
      
            const section1ItemsHtml = section1Items
              .map(item => `<li class="list-group-item">${item}</li>`)
              .join('');
          
            document.getElementById('section1Items').innerHTML = section1ItemsHtml;
            
        }
               
        // Function to generate pagination links
        function generatePaginationLinks(totalPages) {
        const paginationElement = document.getElementById('pagination');
        paginationElement.innerHTML = '';
        
         // Add "Previous" button
        const liPrev = document.createElement('li');
        liPrev.classList.add('page-item');
        liPrev.innerHTML = `<a class='page-link' href='#' id='previous'>Previous</a>`;
        paginationElement.appendChild(liPrev);
        
        for (let i = 1; i <= totalPages; i++) {
            const li = document.createElement('li');
            li.classList.add('page-item');
            const link = document.createElement('a');
            link.classList.add('page-link');
            link.href = '#';
            link.textContent = i;

            link.addEventListener('click', (event) => {
            event.preventDefault();
            const selectedPage = parseInt(event.target.textContent);
            displayItems(selectedPage);
            updatePagination(selectedPage);
            });

            li.appendChild(link);
            paginationElement.appendChild(li);
            }

            // Add "Next" button
            const liNext = document.createElement('li');
            liNext.classList.add('page-item');
            liNext.innerHTML = `<a class='btn btn-outlined page-link' href='#' id='next'>Next</a>`;
            paginationElement.appendChild(liNext);
            }

            // Function to update active pagination link
            function updatePagination(currentPage) {
            const paginationLinks = document.querySelectorAll('#pagination li a');

            paginationLinks.forEach(link => {
                if (parseInt(link.textContent) === currentPage) {
                link.classList.add('active');
                } else {
                link.classList.remove('active');
                }
            });
            }

            // Initial setup
            function init() {
            const totalPages = Math.ceil(items.length / itemsPerPage);
            generatePaginationLinks(totalPages);
            displayItems(1);
            updatePagination(1);
             // Add event listeners for Next and Previous buttons
            const nextBtn = document.getElementById('next');
            const prevBtn = document.getElementById('previous');
            let currentPage = 1;

                nextBtn.addEventListener('click', (event) => {
                    event.preventDefault();
                    if (currentPage < totalPages) {
                        currentPage += 1;
                        displayItems(currentPage);
                        updatePagination(currentPage);
                    }
                });

                prevBtn.addEventListener('click', (event) => {
                    event.preventDefault();
                    if (currentPage > 1) {
                        currentPage -= 1;
                        displayItems(currentPage);
                        updatePagination(currentPage);
                    }
            });

            }
            
            init();
            // END PAGINATION 
        const userModel = await this.client.getCreditsByUser();
        this.userEmail=userModel.data;
        console.log("this is the user model from mount " + JSON.stringify(userModel));
        remainingCredits.innerHTML += this.generateAvailableCredits(userModel.data.userModel.creditBalance);
        // document.getElementById('createImageForm').addEventListener('submit', this.createImageForContent);
        document.getElementById('fbForm').addEventListener('submit', this.fbSubmit);
        document.getElementById('instaForm').addEventListener('submit', this.instaSubmit);
        document.getElementById('linkedInForm').addEventListener('submit', this.linkedInSubmit);
        document.getElementById('twitterForm').addEventListener('submit', this.twitterSubmit);
        document.getElementById('ytShortForm').addEventListener('submit', this.ytShortSubmit);
        document.getElementById('ytLongForm').addEventListener('submit', this.ytLongSubmit);
    }
 
    async populateDataStore(){
        this.dataStore.get("contentIdList");
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

    async createImageForContent(event){
        event.preventDefault();
        // const contentId = this.dataStore.get("contentId");
        const contentId = event.target.getAttribute('data-content-id');
      
        if (!event.target.checkValidity()) {
            submit.vpreventDefault()
            submit.stopPropagation()
            return false;
          }

        
        console.log("Hello from create Image for content " + contentId);
        const errorMessageDisplay = document.getElementById('error-message-createImageForContent');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');              
        event.target.innerText = 'Creating Image...';
        const contendIdToCreateImageFor = contentId;
        console.log("contentId "+ contendIdToCreateImageFor);
        const pictureSize = "1024x1024"
        // const prompt = "Please create an image related to the topic of this post that can be used as a thumbnail image if this is a script, or am image to include in the body of the post if this is a post."
        const prompt = "Create an appealing and informative image for a Facebook post about the article '4 Reasons You Should Learn to Code'. The image should have clear visuals representing the benefits of coding, such as a person coding on a laptop, a light bulb for ideas, and a rising graph for career growth. Please avoid using any text on the image and ensure the visuals are easy to understand."

        console.log("the prompt is: " + prompt);
        console.log("from the createImage method the email is: " + JSON.stringify(this.userEmail));

        try {
            const content = await this.client.createImageForContent(contendIdToCreateImageFor, prompt, pictureSize, (error)=> {         
            createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            location.reload();
            });
        } catch (error) {
            console.error('Error creating image:', error);
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
  

    generatePost(urlMap , title, content, index, contentType, contentId) {
            let carouselTemplate = "";
            const specificContentId = contentId; //  contentId you are looking for
            console.log("contentId in generate post was: "+specificContentId)
            // Check if the specific contentId exists in the urlMap
            if (urlMap.hasOwnProperty(specificContentId)) {
                // Access the image URLs associated with the specific contentId
                const urls = urlMap[specificContentId];
                if(urls.length > 0){
                    const carouselIndicators = imageUrls.map((url, i) => {
                    return `<button type="button" data-bs-target="#carousel${contentId}" data-bs-slide-to="${i}"
                            class="${i === 0 ? "active" : ""}" aria-current="${i === 0 ? "true" : "false"}"
                            aria-label="Slide ${i + 1}"></button>`;
                }).join('');
                
                const carouselItems = imageUrls.map((url, i) => {
                    return `<div class="carousel-item ${i === 0 ? "active" : ""}">
                        <img src="${url}" class="d-block w-100" alt="Image ${i + 1}">
                        </div>`;
                }).join('');
                
                carouselTemplate = `<div id="carousel${contentId}" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-indicators">
                    ${carouselIndicators}
                    </div>
                    <div class="carousel-inner">
                    ${carouselItems}
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carousel${contentId}" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carousel${contentId}" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                    </button>
                </div>;
            </div>`;
                // Do something with the urls or log them to console
                console.log('URLs associated with contentId:', specificContentId, 'are:', urls);
            } else {
                console.log('The contentId', specificContentId, 'is not present in the imageUrlMap.');
            }
           
            }
        
       
        return `<div class="accordion-item">
        <h2 class="accordion-header" id="heading${index}">
            <button class="accordion-button accordion-button-collapse custom-accordion-bg" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapse${index}" aria-expanded="${index === 0 ? true : false}" aria-controls="collapse${index}", data-bs-parent="#list">
                ${contentType} : ${title}
            </button>
        </h2>
        <div id="collapse${index}" class="${index === 0 ? "accordion-collapse collapse show" : "accordion-collapse collapse"}" aria-labelledby="heading${index}"
            data-bs-parent="#list">
            <div class="accordion-body markdown="1"">
            <span>
            
            ${marked.parse(content)}     
            
                </span>
                <div>

                        <button type="button" id="delete${contentId}" data-content-id="${contentId}" class="delete-button btn btn-outline-danger btn-sm">Delete this Post</button>
                        <p class="hidden error" id="error-message-delete"> </p>
                        <p class="hidden error" id="error-message-createImageForContent"> </p>
                </div>
             <div>${carouselTemplate}</div>
            </div>
        </div>`
    }

    generateAvailableCredits(creditBalance) {
        return`<div class="display-8 text-center text-muted">you have ${creditBalance} credits.
        </div>`
    }


    

    async deleteContent(event) {
        event.preventDefault();
        const contentId = event.target.getAttribute('data-content-id');
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
            // location.reload();
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
// stashing my button that I've disable here for now
// <button type="button" id="createImage${contentId}" data-content-id="${contentId}"
// class="createImageForContent-button btn btn-outline-primary me-4 btn-sm">Create Image for this Post</button>
