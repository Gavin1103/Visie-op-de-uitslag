<script lang="ts">
import { onMounted, ref } from 'vue'
import { UserService } from '@/services/UserService'
import type { NewUser } from '@/models/NewUser'
import type { GetUser } from '@/models/GetUser'
import type { User } from '@/models/User'

import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Dropdown from 'primevue/dropdown'
import Checkbox from 'primevue/checkbox'
import Divider from 'primevue/divider'
import IftaLabel from 'primevue/iftalabel'
import { useConfirm } from 'primevue/useconfirm'
import { useToast } from 'primevue/usetoast'

export default {
  components: {
    IftaLabel, Divider,
    InputText,
    Password,
    Button,
    Dialog,
    DataTable,
    Column,
    Dropdown,
    Checkbox
  },
  setup() {
    const users = ref<GetUser[]>([])
    const userService = new UserService()
    const confirm = useConfirm();
    const toast = useToast();
    const newUser = ref<NewUser>({
      username: '',
      email: '',
      password: ''
    })

    const confirmPassword = ref<string>('')

    const dialogVisible = ref(false)

    const fetchUsers = async () => {
      try {
        users.value = await userService.getUsers()
      } catch (error) {
        console.error('Error fetching users:', error)
      }
    }

    const validateForm = async () => {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      let error = false;

      if (!newUser.value.email.match(emailPattern)) {
        toast.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Invalid email format.',
          life: 3000
        })
        error = true;
      }

      if (newUser.value.password.length < 8) {
        toast.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Password must be at least 8 characters.',
          life: 3000
        })
        error = true;
      }

      if (newUser.value.password !== confirmPassword.value) {
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

    const createUser = async () => {
      if (await validateForm()) {
        return
      }

      try {
        const createdUser: any = await userService.createAsAdmin(newUser.value!)

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

        await fetchUsers()
        dialogVisible.value = false
      } catch (error) {
        console.error('Error creating user:', error)
      }
    }

    const updateUser = async (user: User) => {
      confirm.require({
        message: 'Are you sure you want to update this user?',
        header: 'Confirmation',
        rejectProps: {
          label: 'Cancel',
          severity: 'secondary',
          outlined: true
        },
        acceptProps: {
          label: 'Update'
        },
        accept: async () => {
          toast.add({ severity: 'success', summary: 'Success', detail: 'You have updated the user.', life: 3000 });
          const updatedUser: NewUser = {
            id: user.id,
            username: user.username,
            email: user.email,
            password: user.password,
            roleName: user.roles[0].name,
            enabled: user.enabled,
          }

          await userService.updateUser(updatedUser)
          await fetchUsers()
        },
      });
    }

    const deleteUser = async (userId: string) => {
      confirm.require({
        message: 'Are you sure you want to delete this user?',
        header: 'Confirmation',
        rejectProps: {
          label: 'Cancel',
          severity: 'secondary',
          outlined: true
        },
        acceptProps: {
          label: 'Delete',
          severity: 'danger',
        },
        accept: async () => {
          toast.add({ severity: 'error', summary: 'Success', detail: 'You have deleted the user', life: 3000 });
          try {
            await userService.deleteUser(userId)
            fetchUsers()
          } catch (error) {
            console.error('Error deleting user:', error)
          }
        },
      });
    }

    onMounted(() => {
      fetchUsers()
    })

    return {
      users,
      newUser,
      dialogVisible,
      confirmPassword,
      fetchUsers,
      createUser,
      updateUser,
      deleteUser
    }
  }
}
</script>

<template>
  <div>
    <h1 class="text-xl font-bold mb-4">User Overview</h1>

      <Button label="Create New User" icon="pi pi-user-plus" @click="dialogVisible = true" class="mb-4" />

    <Dialog v-model:visible="dialogVisible" :position="top" header="Create New User" modal>
      <form @submit.prevent="createUser" class="flex flex-col space-y-4 ">
        <IftaLabel>
          <InputText id="username" class="w-full" v-model="newUser.username" variant="filled" required />
          <label for="username">Username</label>
        </IftaLabel>

        <IftaLabel>
          <InputText id="email" class="w-full" v-model="newUser.email" variant="filled" required />
          <label for="email">Email</label>
        </IftaLabel>

        <IftaLabel>
          <Password id="password" v-model="newUser.password" variant="filled" class="w-full" inputClass="w-full" toggleMask
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

        <Dropdown v-model="newUser.roleName" :options="['ADMIN', 'PARTYMEMBER', 'USER', 'VERIFIED']" placeholder="Select Role" />

        <Button label="Create" type="submit" class="bg-blue-500 text-white p-2 rounded" />
      </form>
    </Dialog>

    <DataTable :value="users" class="p-datatable-gridlines">
      <Column field="id" header="ID" />
      <Column field="username" header="Username">
        <template #body="slotProps">
          <InputText v-model="slotProps.data.username" class="w-full" />
        </template>
      </Column>
      <Column field="email" header="Email">
        <template #body="slotProps">
          <InputText v-model="slotProps.data.email" type="email" class="w-full" />
        </template>
      </Column>
      <Column field="enabled" header="Enabled">
        <template #body="slotProps">
          <Checkbox v-model="slotProps.data.enabled" :binary="true" />
        </template>
      </Column>
      <Column header="Role">
        <template #body="slotProps">
          <Dropdown v-model="slotProps.data.roles[0].name" :options="['ADMIN', 'PARTYMEMBER', 'USER', 'VERIFIED']" class="w-full" />
        </template>
      </Column>
      <Column header="Actions">
        <template #body="slotProps">
          <Button label="Update" icon="pi pi-save" class="p-button-success mr-2" @click="updateUser(slotProps.data)" />
          <Button label="Delete" icon="pi pi-trash" class="p-button-danger" @click="deleteUser(slotProps.data.id)" />
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f4f4f4;
}
</style>


<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f4f4f4;
}
</style>