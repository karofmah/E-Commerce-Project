describe('New Ad Post', () => {
    beforeEach(() => {
      cy.visit('/newItem');
    });
  
    it('should display an error message when required fields are empty', () => {
      cy.get('button').contains('Legg ut annonse').click();
      cy.get('.error-message').should('contain', 'Vennligst fyll ut alle obligatoriske felter');
    });
  
    it('should upload an image and display an error message when brief description is longer than 42 characters and other fields are filled', () => {
        // Intercept the API call for categories and wait for it to complete
        cy.intercept('http://localhost:9090/api/categories/getCategories').as('getCategories');
      
        // Visit the page and wait for the API call to complete
        cy.visit('/newItem');
        cy.wait('@getCategories');
      
        // Attach a test image to the input field
        cy.fixture('logotest.png').then(fileContent => {
            cy.get('#images').attachFile({
              fileContent: fileContent.toString(),
              fileName: 'logotest.png',
              mimeType: 'image/png',
            });
          });

          
      
        // Fill in the other required fields with some valid values
        cy.get('#full-description').type('Sample Full Description');
        cy.get('#location').type('New York');
        cy.get('#price').type('100');
      
       
        // Type a brief description longer than 42 characters
        cy.get('#brief-description').type('This description is longer than 42 characters, which is not allowed');
      
        // Click the submit button and check for the error message
        cy.get('button').contains('Legg ut annonse').click();
        cy.get('.error-message').should('contain', 'Breif Description can be longer than 42 characters');
      });
      
  
      it('should successfully submit a new ad', () => {
        cy.intercept('GET', '**/api/categories/getCategories', {
          statusCode: 200,
          body: [
            { categoryName: 'Category 1' },
            { categoryName: 'Category 2' },
          ],
        }).as('getCategories');
      
        cy.intercept('POST', '/api/items/add', {
          statusCode: 200,
        });
      
        cy.visit('/newItem');
        cy.wait('@getCategories');
      
        cy.get('#brief-description').type('Sample Brief Description');
        cy.get('#category').select('Category 1');
        cy.get('#full-description').type('Sample Full Description');
        cy.get('#location').type('New York');
        cy.get('#price').type('100');
      
        cy.get('button').contains('Legg ut annonse').click();
        cy.url().should('include', '/');
      });
      
  });
  