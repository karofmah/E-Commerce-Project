describe('User Component Tests with Login', () => {
    let testUser;
  
    before(async () => {
      const email = '1@gmail.com';
      const password = '123';
  
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
      cy.visit('http://localhost:5173/login');
      cy.get('#email').type("1@gmail.com");
      cy.get('#password').type("123");
      cy.get('.submit > button').click();
      cy.url().should('include', '/');
  
      cy.wait(1000)
    });
  
    it('displays the correct user information', () => {
        cy.visit('http://localhost:5173/userInfo');
      cy.get('.user h3').should('contain', '1@gmail.com');
      cy.get('.userFields h3').eq(0).should('contain', 'First name: test');
      cy.get('.userFields h3').eq(1).should('contain', 'Last name: test');
      cy.get('.user h2').should('contain', 'test');
    });

    it('should update the user name and then change it back', () => {
        cy.visit('http://localhost:5173/userInfo');
        cy.get('#userImg').click();
        cy.contains('Edit').click();
    
        
        cy.get('#username').clear().type('newusername');
        cy.contains('Save Changes').click();
        cy.wait(1000)
        cy.get('.user h2').should('contain', 'newusername');
        
        cy.get('#userImg').click();
        cy.contains('Edit').click();
        cy.get('#username').clear().type('test');
        cy.contains('Save Changes').click();
        cy.wait(1000)
        cy.get('.user h2').should('contain', 'test');
       
      });
  
    it('logs out the user and redirects to login page', () => {
        cy.visit('http://localhost:5173/userInfo');
      cy.get('#logOut').click();
  
      cy.url().should('include', '/Login');
      
    });
  });