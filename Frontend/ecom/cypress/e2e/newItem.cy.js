describe('New Ad Post', () => {
    beforeEach(() => {
      cy.visit('http://localhost:5173/newItem');
    });
  
    it('should display an error message when required fields are empty', () => {
      cy.get('button').contains('Publish Item').click();
      cy.get('.error-message').should('contain', 'Please fill in all mandatory fields');
    });
  
    it('should upload an image and display an error message after inputs', () => {
        cy.intercept('http://localhost:9090/api/categories/getCategories').as('getCategories');
      
        cy.visit('http://localhost:5173/newItem');
        cy.wait('@getCategories');
      
        cy.fixture('logotest.png').then(fileContent => {
          cy.get('#images').attachFile({
            fileContent: fileContent.toString(),
            fileName: 'logotest.png',
            mimeType: 'image/png',
          });
        });

        cy.get('#brief-description').type('This description is longer than 42 characters, which is not allowed This description is longer than 42 characters, which is not allowed');
        cy.wait(10000)
        cy.get('#category').select('test');
        cy.wait(2000)
        cy.get('#full-description').type('Sample Full Description');
        cy.wait(2000)
        cy.get('#location').type('New York');
        cy.wait(2000)
        cy.get('#price').type('100');
        cy.wait(2000)
        cy.get('button').contains('Publish Item').click();
        cy.wait(2000)
        cy.get('.error-message', { timeout: 10000 }).should('contain', 'Please fill in all mandatory fields');
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
      
        cy.visit('http://localhost:5173/newItem');
        cy.wait('@getCategories');
      
        cy.get('#brief-description').type('Sample Brief Description');
        cy.get('#category').select('Category 1');
        cy.get('#full-description').type('Sample Full Description');
        cy.get('#location').type('New York');
        cy.get('#price').type('100');
      
        cy.get('button').contains('Publish Item').click();
        cy.url().should('include', '/');
      });
      
  });
  