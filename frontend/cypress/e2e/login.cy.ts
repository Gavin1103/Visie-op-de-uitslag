describe('login page', () => {
  it('should show validation when leaving empty inputs', () => {
    cy.visit('http://localhost:7419/login')
    cy.get('[data-cy="loginSubmit"]').click();
    cy.get('input:invalid').should('have.length', 2);
  })

  it("should give back an error when the user the user puts in wrong data", () => {
    cy.intercept('POST', '**/auth/authenticate',{
      statusCode: 404,
    })
    cy.visit('http://localhost:7419/login')
    cy.get('[data-cy="loginPasswordInput"]').type("1234")
    cy.get('[data-cy="loginEmailInput"]').type("1234")
    cy.get('[data-cy="loginSubmit"]').click();
    cy.get('.p-toast').should('exist')
      .and('contain', 'Error')
      .and('contain', 'Failed to login user. Please try again.');
  })

  it('should show a warning toast for unconfirmed accounts', () => {
    cy.intercept('POST', '**/auth/authenticate', {
      statusCode: 403,
    });
    cy.visit('http://localhost:7419/login');
    cy.get('[data-cy="loginEmailInput"]').type('nobody@example.com');
    cy.get('[data-cy="loginPasswordInput"]').type('password123');
    cy.get('[data-cy="loginSubmit"]').click();
    cy.get('.p-toast').should('be.visible')
      .and('contain', 'Warning')
      .and('contain', 'This account has not yet been confirmed');
  });

  it('should show an error toast for invalid credentials', () => {
    cy.intercept('POST', '**/auth/authenticate', {
      statusCode: 401,
    });
    cy.visit('http://localhost:7419/login');
    cy.get('[data-cy="loginEmailInput"]').type('nobody@example.com');
    cy.get('[data-cy="loginPasswordInput"]').type('password123');
    cy.get('[data-cy="loginSubmit"]').click();
    cy.get('.p-toast').should('be.visible')
      .and('contain', 'Error')
      .and('contain', 'Invalid email or password.');
  });

  it("should log in and redirect to the homepage", () => {
    cy.intercept('POST', '**/auth/authenticate', {
      statusCode: 200,
      body: {
        access_token: 'mock-access-token',
        refresh_token: 'mock-refresh-token',
      },
    });
    cy.intercept('GET', '**/api/election/parties/votes', {
      statusCode: 200,
      body: [],
    });
    cy.intercept('GET', '**/api/election/electedParty', {
      statusCode: 200,
      body: {},
    });
    cy.visit('http://localhost:7419/login');
    cy.get('[data-cy="loginEmailInput"]').type('nobody@example.com');
    cy.get('[data-cy="loginPasswordInput"]').type('password123');
    cy.get('[data-cy="loginSubmit"]').click();
    cy.url().should('eq', 'http://localhost:7419/');
  })
})