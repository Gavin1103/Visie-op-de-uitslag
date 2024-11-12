<script setup lang="ts">

import {onMounted, ref} from 'vue';
import {CookieService} from '@/services/CookieService'; // Import your CookieService
import {UserService} from "@/services/UserService";
import type {UserProfile} from "@/models/user/UserProfile";
import router from "@/router";
import {NewUser} from "@/models/user/NewUser";

// Create an instance of CookieService
const cookieService = new CookieService();
const userService: UserService = new UserService();

const user = ref<UserProfile>({
  id: 0,
  username: '',
  email: ''
});

let popUpDeleteAccount = ref(false);
let editAccountStatus = ref(false);

const fetchUser = async () => {
  const token = cookieService.getCookie(cookieService.accessTokenAlias);
  console.log(token);

  if (!token) {
    console.error('No token found');
    await router.push("/");
    return;
  }

  try {
    const fetchedUser = await userService.getUserByToken(token);

    if (fetchedUser) {
      user.value = fetchedUser;
    }
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
  editAccountStatus.value = true;
};

const deleteAccount = () => {
  popUpDeleteAccount.value = true;
};

const confirmDeleteAccount = (): void => {

  popUpDeleteAccount.value = false;
  userService.deleteUser(user.value.id);
  cookieService.removeTokenCookies();
  router.push('/').then(() => {
    window.location.reload();
  });

  return;
};

const cancelDeleteAccount = () => {
  popUpDeleteAccount.value = false;
};

const saveChanges = async () => {

  const updatedUser: NewUser = {
    id:  user.value.id,
    username: user.value.username,
    email: user.value.email,
    password: "",
    enabled: true
  }

  try {
    console.log(updatedUser)
    await userService.updateUserSelf(updatedUser);
    // await fetchUser();
    console.log(updatedUser);
  } catch (error) {
    console.error('Error updating user:', error);
  }

  editAccountStatus.value = false;
};

const cancelEdit = () => {
  fetchUser();
  editAccountStatus.value = false;
}
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
        <input
            class="text-lg p-2 w-full rounded-md border"
            v-model="user.username"
            :disabled="!editAccountStatus"
            :class="editAccountStatus ? 'bg-white border-blue-[#5564c8]' : 'bg-gray-100 border-gray-300 text-gray-500 cursor-not-allowed'"
        />
      </div>
      <div class="border border-gray-300 p-4 rounded-md mt-4">
        <p class="text-lg font-semibold"><strong>Email:</strong></p>
        <input
            class="text-lg p-2 w-full rounded-md border"
            v-model="user.email"
            :disabled="!editAccountStatus"
            :class="editAccountStatus ? 'bg-white border-blue-[#5564c8]' : 'bg-gray-100 border-gray-300 text-gray-500 cursor-not-allowed'"
        />
      </div>

      <!-- Action Buttons -->
      <div class="action-buttons flex justify-center space-x-4 mt-4">
        <!-- Show these buttons when editAccountStatus is false -->
        <div v-if="!editAccountStatus" class="space-x-4">
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

        <!-- Show Save and Cancel buttons when editAccountStatus is true -->
        <div v-else class="space-x-4">
          <button @click="saveChanges" class="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600">
            Save
          </button>
          <button @click="cancelEdit" class="bg-gray-500 text-white py-2 px-4 rounded hover:bg-gray-600">
            Cancel
          </button>
        </div>
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
