<script lang="ts">
import { onMounted, ref } from 'vue';
import { UserService } from '@/services/UserService';
import type { NewUser } from '@/models/NewUser'
import { Role } from '@/models/enum/Role'
import type { GetUser } from '@/models/GetUser'
import type { User } from '@/models/User'

export default {
  setup() {
    const users = ref<GetUser[]>([]);
    const userService = new UserService();
    const newUser = ref<NewUser>({
      username: '',
      email: '',
      password: '',
    });

    const selectedRole = ref('USER');

    const fetchUsers = async () => {
      try {
        users.value = await userService.getUsers();
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };

    const createUser = async () => {
      try {
        await userService.createAsAdmin(newUser.value!);
        fetchUsers();
      } catch (error) {
        console.error('Error creating user:', error);
      }
    };

    const updateUser = async (user: User) => {
        const updatedUser: NewUser = NewUser.fromUser(user);

        updatedUser.roleName = selectedRole.value;

        await userService.updateUser(updatedUser);
        fetchUsers();
    };

    const deleteUser = async (userId: string) => {
      try {
        await userService.deleteUser(userId);
        fetchUsers();
      } catch (error) {
        console.error('Error deleting user:', error);
      }
    };

    onMounted(() => {
      fetchUsers();
    });

    return {
      users,
      newUser,
      selectedRole,
      fetchUsers,
      createUser,
      updateUser,
      deleteUser,
    };
  }
}
</script>

<template>
  <div>
    <h1 class="text-xl font-bold mb-4">User Overview</h1>

    <div class="mb-6">
      <h2 class="text-lg font-semibold">Create New User</h2>
      <form @submit.prevent="createUser" class="flex flex-col space-y-4">
        <input v-model="newUser.username" type="text" placeholder="Username" class="border p-2 rounded" required />
        <input v-model="newUser.password" type="password" placeholder="Password" class="border p-2 rounded" required />
        <input v-model="newUser.email" type="email" placeholder="Email" class="border p-2 rounded" required />
        <div>
          <label for="role" class="block">Role:</label>
          <select v-model="newUser.roleName" class="border p-2 rounded" id="role">
            <option value="ADMIN">ADMIN</option>
            <option value="PARTYMEMBER">PARTYMEMBER</option>
            <option value="USER">USER</option>
            <option value="VERIFIED">VERIFIED</option>
          </select>
        </div>
        <button type="submit" class="bg-blue-500 text-white p-2 rounded">Create User</button>
      </form>
    </div>

    <table class="min-w-full bg-white border border-gray-300">
      <thead>
      <tr>
        <th class="px-4 py-2">ID</th>
        <th class="px-4 py-2">Username</th>
        <th class="px-4 py-2">Email</th>
        <th class="px-4 py-2">Enabled</th>
        <th class="px-4 py-2">Role</th>
        <th class="px-4 py-2">Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td class="border px-4 py-2">{{ user.id }}</td>
        <td class="border px-4 py-2">{{ user.username }}</td>
        <td class="border px-4 py-2">{{ user.email }}</td>
        <td class="border px-4 py-2">{{ user.enabled }}</td>
        <td class="border px-4 py-2">
          <select v-model="user.roles[0].name" class="border p-2 rounded">
            <option value="ADMIN">ADMIN</option>
            <option value="PARTYMEMBER">PARTYMEMBER</option>
            <option value="USER">USER</option>
            <option value="VERIFIED">VERIFIED</option>
          </select>
        </td>
        <td class="border px-4 py-2">
          <button @click="updateUser(user)" class="bg-green-500 text-white px-2 py-1 rounded mr-2">Update</button>
          <button @click="deleteUser(user.id)" class="bg-red-500 text-white px-2 py-1 rounded">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
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