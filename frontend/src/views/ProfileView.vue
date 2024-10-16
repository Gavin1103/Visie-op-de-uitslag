<script setup lang="ts">
import { ref, onMounted } from 'vue';
import {UserService} from '@/services/UserService'; // Import your UserService

// Create a reactive reference for user data
const user = ref({
  username: '',
  email: '',
});

// Assume the user ID is known or passed from a prop or route parameter
const userId = 1; // Replace with dynamic ID as needed

// Fetch user data using the UserService
onMounted(async () => {
  try {
    const userService = new UserService(); // Create an instance of UserService
    const [userData] = await Promise.all([userService.getUserById(userId)]); // Fetch user data by ID
    user.value = userData;
    console.log(user.value);
  } catch (error) {
    console.error('Error fetching user data:', error);
  }
});

// Mock methods for actions (you can replace them with actual logic)
const uploadPicture = () => {
  console.log("Uploading picture...");
};
const changePassword = () => {
  console.log("Change password...");
};
const editAccount = () => {
  console.log("Edit account...");
};
const deleteAccount = () => {
  console.log("Delete account...");
};
</script>

<template>
  <div class="profile container mx-auto p-6 flex items-start space-x-8">

    <!-- Profile Picture Section on the Left -->
    <div class="profile-picture-section flex flex-col items-center w-1/3">
      <h2 class="text-2xl font-semibold mb-4">Profile Picture</h2>
      <input type="file" @change="uploadPicture" class="text-sm text-gray-500
        file:mr-4 file:py-2 file:px-4
        file:rounded-full file:border-0
        file:text-sm file:font-semibold
        file:bg-blue-50 file:text-blue-700
        hover:file:bg-blue-100"/>
    </div>

    <!-- User Info Section on the Right -->
    <div class="user-info bg-white p-6 rounded-lg shadow-lg w-2/3 ml-auto space-y-4">
      <div class="border border-gray-300 p-4 rounded-md">
        <p class="text-lg font-semibold"><strong>Username:</strong></p>
        <p class="text-lg">{{ user.username   }}</p>
      </div>
      <div class="border border-gray-300 p-4 rounded-md">
        <p class="text-lg font-semibold"><strong>Email:</strong></p>
        <p class="text-lg">{{ user.email }}</p>
      </div>

      <!-- Action Buttons Aligned with User Info -->
      <div class="action-buttons flex justify-center space-x-4 mt-4">
        <button @click="changePassword" class="bg-[#5564c8] text-white py-2 px-4 rounded hover:bg-blue-400">
          Change Password
        </button>
        <button @click="editAccount" class="bg-[#5564c8] text-white py-2 px-4 rounded hover:bg-green-400">
          Edit Account
        </button>
        <button @click="deleteAccount" class="bg-[#5564c8] text-white py-2 px-4 rounded hover:bg-red-400">
          Delete Account
        </button>
      </div>
    </div>
  </div>
</template>

<style>
/* Add your custom styles if needed */
</style>
