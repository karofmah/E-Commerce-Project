import { getJwtToken } from "../../httputils"

describe('User Login', () => {
    beforeEach(() => {
      cy.visit('/login');
    });
  
    it('logs in successfully', () => {
      cy.get('.email input').type('user@example.com');
      cy.get('.password input').type('UserPassword123');
  
      cy.intercept('POST', '/api/users/login/user', {
        statusCode: 200,
        body: {
          jwtToken: 'your_jwt_token_here',
        },
      });
  
      cy.get('.submit button').click();
      cy.url().should('include', '/');
    });
  
    it('shows an error message when login fails', () => {
      cy.get('.email input').type('user@example.com');
      cy.get('.password input').type('UserPassword123');
  
      cy.intercept('POST', '/api/users/login/user', {
        statusCode: 401,
      });
  
      cy.get('.submit button').click();
      cy.get('.main-container p').should('contain', 'Logg in feilet. Har du rett email og passord?');
    });
  
    it('shows an error message when input fields are empty', () => {
      cy.get('.submit button').click();
      cy.get('.main-container p').should('contain', 'Epost og password er nødvendige felt');
    });
  
    it('navigates to the registration page', () => {
      cy.get('.register button').click();
      cy.url().should('include', '/register'); 
    });
  });
  