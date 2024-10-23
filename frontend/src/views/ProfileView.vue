<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {CookieService} from '@/services/CookieService'; // Import your CookieService
import {UserService} from "@/services/UserService";
import type {UserProfile} from "@/models/user/UserProfile";

// Create an instance of CookieService
const cookieService = new CookieService();
const userService: UserService = new UserService();
const user = ref<UserProfile | null>(null);

console.log(user);
const fetchCurrentUser = async () => {
  const token = cookieService.getCookie(cookieService.accessTokenAlias);
  console.log(token);

  if (!token) {
    console.error('No token found');
    window.location.replace("/");
    return;
  }

  try {
    user.value = await userService.getUserByToken(token);
    console.log(user.value);

  } catch (error) {
    console.error('Error fetching user:', error);
  }
};


onMounted(() => {
  fetchCurrentUser();
});

const uploadPicture = () => {
  console.log("Uploading picture...");
};
const changePassword = () => {
  console.log("Changing password...");
};
const editAccount = () => {
  console.log("Editing account...");
};
const deleteAccount = () => {
  console.log("Deleting account...");
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
        <p class="text-lg">{{ user?.username }}</p> <!-- Display the fetched username -->
      </div>
      <div class="border border-gray-300 p-4 rounded-md">
        <p class="text-lg font-semibold"><strong>Email:</strong></p>
        <p class="text-lg">{{ user?.email }}</p> <!-- Access the email via user.value -->
      </div>
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
</template>

<style>
/* Add your custom styles if needed */
</style>
