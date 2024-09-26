<script setup lang="ts">
// TypeScript enabled
import { ref } from 'vue'
import { UserService } from '@/services/UserService';
import type { LoginUser, NewUser } from '@/models/User'



// Reactive variables
const email = ref<string>('');
const password = ref<string>('');
const loading = ref<boolean>(false);
const errorMessage = ref<string>('');
const successMessage = ref<string>('');

// Instantiate the user service
const userService = new UserService();

// Function to handle form submission
const onSubmit = async () => {
  loading.value = true;
  errorMessage.value = '';
  successMessage.value = '';

  const loginUser: LoginUser = {
    password: password.value,
    email: email.value
  };

  try {
    const result: any = await userService.authenticateUser(loginUser);
  } catch (error: any) {
    switch (error.status) {
      case 403:
        errorMessage.value = 'This account has not yet been confirmed';
        break;
      default:
        errorMessage.value = 'Failed to login user. Please try again.';
    }
  } finally {
    loading.value = false;
  }
};
</script>
<template>
  <div class="create-user">
    <h2 class="create-title">Login</h2>

    <form class="create-form" @submit.prevent="onSubmit">
      <div>
        <input class="create-input" placeholder="Email" type="email" v-model="email" id="email" required />
      </div>

      <div>
        <input class="create-input" placeholder="Password" type="password" v-model="password" id="password" required />
      </div>

      <button class="create-submit" type="submit" :disabled="loading">
        {{ loading ? 'Logging in...' : 'Login' }}
      </button>

      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success">{{ successMessage }}</p>
    </form>
  </div>
</template>

<style scoped>
.create-user {
  max-width: 300px;
  margin: 0 auto;
  border: solid 1px rgb(22 31 64);
  border-radius: 25px;
  padding: 1rem;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);

  .create-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
  }

  .create-title {
    text-align: center;
    font-size: 2rem;
  }

  .create-input {
    padding: 0.5rem;
    font-size: 1rem;
    border: 1px solid rgb(22 31 64);
    border-radius: 0.25rem;
  }

  .create-submit {
    padding: 0.5rem;
    font-size: 1rem;
    border: none;
    border-radius: 0.25rem;
    background-color: rgb(22 31 64);
    color: white;
    cursor: pointer;
  }
}

.error {
  color: red;
}

.success {
  color: green;
}
</style>
