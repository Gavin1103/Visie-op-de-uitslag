import { defineConfig } from 'cypress';
import codeCoverage from '@cypress/code-coverage/task';

export default defineConfig({
  e2e: {
    specPattern: 'cypress/e2e/**/*.{cy,spec}.{js,jsx,ts,tsx}',
    baseUrl: 'http://localhost:7419',
    setupNodeEvents(on, config) {
      codeCoverage(on, config); // Use the imported `codeCoverage` directly
      return config;
    },
  },
});
