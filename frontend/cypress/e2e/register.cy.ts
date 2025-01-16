describe('Register Form', () => {
  beforeEach(() => {
    cy.visit('http://localhost:7419/register'); 
  });

  it('should display validation errors for empty fields', () => {
    cy.get('[data-cy="registerButton"]').click();
    cy.get('input:invalid').should('have.length', 3);
  });

  it('should display an error for invalid email format', () => {
    cy.get('#username').type('testuser');
    cy.get('#email').type('invalid-email');
    cy.get('#password').type('Password123');
    cy.get('body').click(0, 0);
    cy.get('#confirmPassword').type('Password123');
    cy.get('body').click(0, 0);
    cy.get('[data-cy="registerButton"]').click();

    cy.contains('Invalid email format').should('be.visible');
  });

  it('should display an error when passwords do not match', () => {
    cy.get('#username').type('testuser');
    cy.get('#email').type('testuser@example.com');
    cy.get('#password').type('Password123');
    cy.get('body').click(0, 0);
    cy.get('#confirmPassword').type('Password321');
    cy.get('body').click(0, 0);
    cy.get('[data-cy="registerButton"]').click();

    cy.contains('Passwords do not match').should('be.visible');
  });

  it('should successfully register a user with valid inputs', () => {
    cy.intercept('POST', '**/auth/register', {
      statusCode: 201,
      body: { statusCodeValue: 201 },
    }).as('registerUser');

    cy.get('#username').type('testuser');
    cy.get('#email').type('testuser@example.com');
    cy.get('#password').type('Password123');
    cy.get('body').click(0, 0);
    cy.get('#confirmPassword').type('Password123');
    cy.get('body').click(0, 0);
    cy.get('[data-cy="registerButton"]').click();

    cy.wait('@registerUser');

    cy.contains('Your account was created successfully!').should('be.visible');
  });

  it('should display a warning for duplicate email registration', () => {
    cy.get('#username').type('testuser');
    cy.get('#email').type('admin@admin.com');
    cy.get('#password').type('Password123');
    cy.get('body').click(0, 0);
    cy.get('#confirmPassword').type('Password123');
    cy.get('body').click(0, 0);
    cy.get('[data-cy="registerButton"]').click();

    cy.contains('User with that email already exists.').should('be.visible');
  });

  it('should display a generic error for unexpected server issues', () => {
    cy.intercept('POST', '**/auth/register', {
      statusCode: 500,
      body: { statusCodeValue: 500 },
    }).as('registerServerError');

    cy.get('#username').type('testuser');
    cy.get('#email').type('testuser@example.com');
    cy.get('#password').type('Password123');
    cy.get('body').click(0, 0);
    cy.get('#confirmPassword').type('Password123');
    cy.get('body').click(0, 0);
    cy.get('[data-cy="registerButton"]').click();

    cy.wait('@registerServerError')

    cy.contains('Failed to create user. Please try again.').should('be.visible');
  });

});