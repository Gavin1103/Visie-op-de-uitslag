<script lang="ts">
import { onMounted, ref } from 'vue'
import { type getUser } from '@/models/User'
import { UserService } from '@/services/UserService';

export default {
  setup() {
    const users = ref<getUser[]>([]);
    const usersService = new UserService();

    const fetchUsers = async () => {
      try {
        users.value = await usersService.getUsers();
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };

    onMounted(() => {
      fetchUsers();
    });

    return {
      users,
    };
  }
}
</script>


<template>
  <div>
    <h1>User Overview</h1>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Email</th>
        <th>Enabled</th>
        <th>Role</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.userId">
        <td>{{ user.userId }}</td>
        <td>{{ user.username }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.enabled }}</td>
        <td>{{ user.roles[0].name }}</td>
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