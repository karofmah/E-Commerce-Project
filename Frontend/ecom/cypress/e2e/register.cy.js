describe('New User Registration', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173/register');
  });

  it('registers a new user successfully', () => {
    cy.get('.email input').type('test@example.com');
    cy.get('.username input').type('testuser');
    cy.get('.password input').type('TestPassword123');
    cy.get('.Forname input').type('Test');
    cy.get('.Surname input').type('User');

    cy.intercept('POST', 'http://localhost:9090/api/users/register', {
      statusCode: 200,
      body: {
        data: {
          username: 'testuser',
          email: 'test@example.com',
          firstName: 'Test',
          lastName: 'User',
          role: 'NORMAL_USER',
        },
      },
    });

    cy.get('.submit button').click();
    cy.url().should('include', '/login');
  });

  it('shows an error message when registration fails', () => {
    cy.get('.email input').type('test@example.com');
    cy.get('.username input').type('testuser');
    cy.get('.password input').type('TestPassword123');
    cy.get('.Forname input').type('Test');
    cy.get('.Surname input').type('User');

    cy.intercept('POST', 'http://localhost:9090/api/users/register', {
      statusCode: 400,
      body: {
        data: null,
      },
    });

    cy.get('.submit button').click();
  });

  it('shows an error message when input fields are empty', () => {
    cy.get('.submit button').click();
    cy.get('.main-container p').should('contain', 'All fields must be filled in');
  });
});
