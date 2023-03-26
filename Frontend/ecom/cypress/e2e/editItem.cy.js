import { getJwtToken } from "../../httputils"

describe('Edit Item Test with Login', () => {
  let testUser;

  before(async () => {
    const email = '1@gmail.com';
    const password = '123';

    // Get the token for the test user
    try {
      const response = await getJwtToken(email, password);
      testUser = {
        email: email,
        token: response.data,
      };
    } catch (error) {
      console.error('Error getting test user token:', error);
    }
  });

  beforeEach(() => {
    cy.visit('/login');
    cy.get('#email').type("1@gmail.com");
    cy.get('#password').type("123");
    cy.get('.submit > button').click();
    cy.url().should('include', '/');

    cy.wait(1000)
    cy.visit('/update-item/1652');
  });

  it('Updates the brief description', () => {
    cy.get('#brief-description')
    .clear()
    .should('have.value', "test");
  });
  
  it('Updates the full description', () => {
    cy.get('#brief-description')
    .clear()
    .should('have.value', "test");
  });
  
  it('Updates the price', () => {
    cy.get('#price')
      .should('have.value',"123");
  });
  
  it('Submits the form successfully', () => {
   
  
    cy.get('#submit').click();
    
    cy.url().should('include', '/');
  });
  

  // Add more tests related to the Edit Item page
});
