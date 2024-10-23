<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {CookieService} from '@/services/CookieService'; // Import your CookieService
import {UserService} from "@/services/UserService";
import type {UserProfile} from "@/models/user/UserProfile";
import router from "@/router";

// Create an instance of CookieService
const cookieService = new CookieService();
const userService: UserService = new UserService();
const user = ref<UserProfile>();

let popUpDeleteAccount = ref(false);

const fetchUser = async () => {
  const token = cookieService.getCookie(cookieService.accessTokenAlias);

  if (!token) {
    console.error('No token found');
    await router.push("/");
    return;
  }

  try {
    user.value = await userService.getUserByToken(token);
  } catch (error) {
    console.error('Error fetching user:', error);
  }
};

onMounted(() => {
  fetchUser();
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
  popUpDeleteAccount.value = true;
};

const confirmDeleteAccount = (): void => {

  popUpDeleteAccount.value = false;
  userService.deleteUser(user?.value.id);
  cookieService.removeTokenCookies();

  router.push('/').then(() => {
    window.location.reload();
  });

  return;
};

const cancelDeleteAccount = () => {
  popUpDeleteAccount.value = false;
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
        <p class="text-lg">{{ user?.username }}</p>
      </div>
      <div class="border border-gray-300 p-4 rounded-md">
        <p class="text-lg font-semibold"><strong>Email:</strong></p>
        <p class="text-lg">{{ user?.email }}</p>
      </div>

      <!-- Action Buttons -->
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

  <!-- Modal Pop-up for Confirm Delete -->
  <div v-if="popUpDeleteAccount" class="fixed inset-0 flex items-center justify-center z-50">
    <div class="bg-gray-800 bg-opacity-75 absolute inset-0"></div>
    <div class="bg-white p-6 rounded-lg shadow-lg z-10">
      <h2 class="text-xl font-bold mb-4">Confirm Account Deletion</h2>
      <p class="mb-4">Are you sure you want to delete your account? This action cannot be undone.</p>
      <div class="flex justify-end space-x-4">
        <button @click="cancelDeleteAccount" class="bg-gray-400 text-white py-2 px-4 rounded hover:bg-gray-500">
          Cancel
        </button>
        <button @click="confirmDeleteAccount" class="bg-red-600 text-white py-2 px-4 rounded hover:bg-red-700">
          Confirm
        </button>
      </div>
    </div>
  </div>
</template>

