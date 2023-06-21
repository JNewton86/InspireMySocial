import InspireMySocialClient from '../api/inspireMySocialClient';
import BindingClass from "../util/bindingClass";


/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLoginButton', 'createLogoutButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new InspireMySocialClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header-menu');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

    createSiteTitle() {
        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'index.html';


        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);

        return siteTitle;
    }

    // createUserInfoForHeader(currentUser) {
    //     const userInfo = document.createElement('div');
    //     userInfo.classList.add('user');

    //     const childContent = currentUser
    //         ? this.createLogoutButton(currentUser)
    //         : this.createLoginButton() 
    //         : this.createSignUpButton();

    //     userInfo.appendChild(childContent);

    //     return userInfo;
    // }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        if (currentUser) {
            // If the user is logged in, append the logout button
            const logoutButton = this.createLogoutButton(currentUser);
            userInfo.appendChild(logoutButton);
        } else {
            // If the user is not logged in, append both the login and sign-up buttons
            const loginButton = this.createLoginButton();
            const signUpButton = this.createSignUpButton();

            userInfo.appendChild(loginButton);
            userInfo.appendChild(signUpButton);
        }

        return userInfo;
    }


    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createSignUpButton() {
        return this.createButton('Create Account', this.client.login); // Use the `client.login` method for signup as well
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button', 'btn', 'btn-primary', 'rounded-pill', 'border-2', 'ms-2');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }

}
