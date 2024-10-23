<script setup lang="ts">
import { ref } from 'vue'
import { UserService } from '@/services/UserService';
import { CookieService } from '@/services/CookieService'
import { useRouter } from 'vue-router'
import type { LoginUser } from '@/models/LoginUser'
import IftaLabel from 'primevue/iftalabel'
import Password from 'primevue/password'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import { useToast } from 'primevue/usetoast'



const email = ref<string>('');
const password = ref<string>('');
const loading = ref<boolean>(false);
const errorMessage = ref<string>('');
const successMessage = ref<string>('');

const userService = new UserService();
const cookieService = new CookieService();
const toast = useToast()

const router = useRouter();

const onSubmit = async () => {
  loading.value = true;
  errorMessage.value = '';
  successMessage.value = '';

  const loginUser: LoginUser = {
    password: password.value,
    email: email.value
  };

  let result: any = null;
  try {
     result = await userService.authenticateUser(loginUser);
  } catch (error: any) {
    switch (error.status) {
      case 403:
        toast.add({ severity: 'warn', summary: 'Warning', detail: 'This account has not yet been confirmed', life: 3000 })
        break;
      case 401:
        toast.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Invalid email or password.',
          life: 3000
        })
        break;
      default:
        toast.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to login user. Please try again.',
          life: 3000
        })
    }
  } finally {
    if(result) {
      cookieService.setTokenCookies(result.access_token, result.refresh_token);
      await router.push({name: 'home'}).then(() => {
        window.location.reload(); // Force full page reload after navigation
        });
    }
    loading.value = false;
  }
};
</script>
<template>
  <div class="create-user">
    <h2 class="create-title">Login</h2>

    <form class="create-form" @submit.prevent="onSubmit">
      <IftaLabel>
        <InputText id="Email" class="w-full" v-model="email" variant="filled" required />
        <label for="Email">Email</label>
      </IftaLabel>

      <IftaLabel>
        <Password id="password" class="w-full" v-model="password" variant="filled" required />
        <label for="password">Password</label>
      </IftaLabel>

      <Button label="Login" icon="pi pi-user" :loading="loading" type="submit" class="p-mt-3" />
      </form>
  </div>
</template>

<style scoped>
.create-user {
  max-width: 300px;
  margin: 10% auto;
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
