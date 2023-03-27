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
      cy.visit('/login');
      cy.get('#email').type("1@gmail.com");
      cy.get('#password').type("123");
      cy.get('.submit > button').click();
      cy.url().should('include', '/');
  
      cy.wait(1000)
    });
  
    it('displays the correct user information', () => {
        cy.visit('/userInfo');
      cy.get('.user h2').should('contain', '1@gmail.com');
      cy.get('.userFields h3').eq(0).should('contain', 'Fornavn: test');
      cy.get('.userFields h3').eq(1).should('contain', 'Etternavn: test');
      cy.get('.userFields h4').should('contain', 'Brukernavn: test');
    });

    it('should update the user name and then change it back', () => {
        cy.visit('/userInfo');
        cy.get('#userImg').click();
        cy.contains('Edit').click();
    
        
        cy.get('#username').clear().type('newusername');
        cy.contains('Lagre endringer').click();
        cy.wait(1000)
        cy.get('.userFields h4').should('contain', 'Brukernavn: newusername');
        
        cy.get('#userImg').click();
        cy.contains('Edit').click();
        cy.get('#username').clear().type('test');
        cy.contains('Lagre endringer').click();
        cy.wait(1000)
        cy.get('.userFields h4').should('contain', 'Brukernavn: test');
       
      });
  
    it('logs out the user and redirects to login page', () => {
        cy.visit('/userInfo');
      cy.get('#logOut').click();
  
      cy.url().should('include', '/Login');
      
    });
  });