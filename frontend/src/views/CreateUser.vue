<script setup lang="ts">
import { ref } from 'vue'
import { UserService } from '@/services/UserService'
import Password from 'primevue/password'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import Divider from 'primevue/divider'
import IftaLabel from 'primevue/iftalabel'
import Message from 'primevue/message'
import type { NewUser } from '@/models/NewUser'
import { useToast } from 'primevue/usetoast'

const name = ref<string>('')
const email = ref<string>('')
const password = ref<string>('')
const confirmPassword = ref<string>('')
const loading = ref<boolean>(false)
const toast = useToast()

const userService = new UserService()

const validateForm = () => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  let error = false;

  if (!email.value.match(emailPattern)) {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Invalid email format.',
      life: 3000
    })
    error = true;
  }

  if (password.value.length < 8) {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Password must be at least 8 characters.',
      life: 3000
    })
    error = true;
  }

  if (password.value !== confirmPassword.value) {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Passwords do not match.',
      life: 3000
    })
    error = true;
  }

  return error;
}

const onSubmit = async () => {
  if (validateForm()) {
    return
  }

  loading.value = true

  const newUser: NewUser = {
    username: name.value,
    email: email.value,
    password: password.value
  }

  try {
    const createdUser: any = await userService.createUser(newUser)
    switch (createdUser.statusCodeValue) {
      case 201:
        toast.add({ severity: 'success', summary: 'Success', detail: 'Your account was created successfully!', life: 3000 })
        break
      case 409:
        toast.add({ severity: 'warn', summary: 'Warning', detail: 'User with that email already exists.', life: 3000 })
        break
      default:
        toast.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to create user. Please try again.',
          life: 3000
        })
    }
  } catch (error: any) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to create user. Please try again.', life: 3000 })
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="create-user">
    <h2 class="create-title">Create User</h2>

    <form class="create-form" @submit.prevent="onSubmit">
      <IftaLabel>
        <InputText id="username" class="w-full" v-model="name" variant="filled" required />
        <label for="username">Username</label>
      </IftaLabel>

      <IftaLabel>
        <InputText id="email" class="w-full" v-model="email" variant="filled" required />
        <label for="email">Email</label>
      </IftaLabel>

      <IftaLabel>
        <Password id="password" v-model="password" variant="filled" class="w-full" inputClass="w-full" toggleMask
                  feedback required>
          <template #header>
            <div class="font-semibold text-xm mb-4">Pick a password</div>
          </template>
          <template #footer>
            <Divider />
            <ul class="pl-2 ml-2 my-0 leading-normal">
              <li>At least one lowercase</li>
              <li>At least one uppercase</li>
              <li>At least one numeric</li>
              <li>Minimum 8 characters</li>
            </ul>
          </template>
        </Password>
        <label for="password">Password</label>
      </IftaLabel>

      <IftaLabel>
        <Password id="confirmPassword" v-model="confirmPassword" variant="filled" class="w-full" inputClass="w-full"
                  toggleMask feedback />
        <label for="confirmPassword">Confirm Password</label>
      </IftaLabel>

      <Button label="Create User" icon="pi pi-user" :loading="loading" type="submit" class="p-mt-3" />
    </form>
  </div>
</template>


<style scoped>
.create-user {
  max-width: 350px;
  margin: 10% auto;
  border: solid 1px rgb(22 31 64);
  border-radius: 25px;
  padding: 1.5rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.create-title {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 1rem;
}

.create-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.p-float-label {
  width: 100%;
}

input[type="email"] {
  padding-left: 2.5rem;
}

button {
  width: 100%;
}

p-message {
  margin-top: 1rem;
}
</style>
