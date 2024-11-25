<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { CookieService } from '@/services/CookieService'; // Import your CookieService
import { UserService } from "@/services/UserService";
import { TopicService } from "@/services/TopicService"; // Make sure this is imported
import type { UserProfile } from "@/models/user/UserProfile";
import router from "@/router";
import { NewUser } from "@/models/user/NewUser";

// Create instances of services
const cookieService = new CookieService();
const userService: UserService = new UserService();
const topicService: TopicService = new TopicService(); // Instance for TopicService

// State variables for user and topics
const user = ref<UserProfile>({
  id: 0,
  username: '',
  email: ''
});

const topics = ref([]); // This will store the fetched topics
const isLoading = ref(true);
const errorMessage = ref<string | null>(null);

let popUpDeleteAccount = ref(false);
let editAccountStatus = ref(false);

// Fetch user data
const fetchUser = async () => {
  const token = cookieService.getCookie(cookieService.accessTokenAlias);
  console.log('Token:', token); // Log token for verification

  if (!token) {
    console.error('No token found');
    await router.push("/"); // Redirect to login if token not found
    return;
  }

  try {
    const fetchedUser = await userService.getUserByToken(token);
    console.log('Fetched User:', fetchedUser); // Log the fetched user data

    if (fetchedUser) {
      user.value = fetchedUser; // Set the user details
    }
  } catch (error) {
    console.error('Error fetching user:', error);
  }
};

const fetchTopics = async () => {
  const token = cookieService.getCookie(cookieService.accessTokenAlias);

  if (!token || !user.value.id) {
    console.error('No user or token found');
    return;
  }

  try {
    const pageable = { page: 0, size: 10 }; // Example pagination
    console.log('Fetching topics for User ID:', user.value.id); // Log user ID

    const fetchedTopics = await topicService.getTopicById(user.value.id, pageable); // Fetch topics using user ID
    console.log('Fetched Topics:', fetchedTopics); // Log the full response object

    // Check if fetchedTopics is an array or a single object
    if (Array.isArray(fetchedTopics)) {
      console.log('fetchedTopics is an array');
      topics.value = fetchedTopics; // If it's an array, use it directly
    } else if (typeof fetchedTopics === 'object' && fetchedTopics !== null) {
      console.log('fetchedTopics is a single object');
      // If it's a single object, wrap it in an array
      topics.value = [fetchedTopics];
    } else {
      console.error('Unexpected response structure: No topics found');
    }

    console.log('Topics:', topics.value); // Log the final topics list

  } catch (error) {
    errorMessage.value = error instanceof Error ? error.message : 'An error occurred';
    console.error('Error fetching topics:', error);
  } finally {
    isLoading.value = false;
  }
};



onMounted(async () => {
  try {
    await fetchUser(); // Fetch user data first
    await fetchTopics(); // Then fetch the topics for the user
  } catch (error) {
    errorMessage.value = error instanceof Error ? error.message : 'An error occurred';
    console.error('Error during component mounting:', error);
  }
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
    id: user.value.id,
    username: user.value.username,
    email: user.value.email,
    password: "",
    enabled: true
  }

  try {
    console.log('Updated User:', updatedUser);
    await userService.updateUserSelf(updatedUser);
    console.log('User Updated:', updatedUser);
  } catch (error) {
    console.error('Error updating user:', error);
  }

  editAccountStatus.value = false;
};

const cancelEdit = () => {
  fetchUser();
  editAccountStatus.value = false;
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
        <input
            class="text-lg p-2 w-full rounded-md border"
            v-model="user.username"
            :disabled="!editAccountStatus"
            :class="editAccountStatus ? 'bg-white border-blue-[#5564c8]' : 'bg-gray-100 border-gray-300 text-gray-500 cursor-not-allowed'"/>
      </div>
      <div class="border border-gray-300 p-4 rounded-md mt-4">
        <p class="text-lg font-semibold"><strong>Email:</strong></p>
        <input
            class="text-lg p-2 w-full rounded-md border"
            v-model="user.email"
            :disabled="!editAccountStatus"
            :class="editAccountStatus ? 'bg-white border-blue-[#5564c8]' : 'bg-gray-100 border-gray-300 text-gray-500 cursor-not-allowed'"/>
      </div>

      <!-- Action Buttons -->
      <div class="action-buttons flex justify-center space-x-4 mt-4">
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

  <!-- Loading and Error States for Topics -->
  <div v-if="isLoading">Loading topics...</div>
  <div v-else-if="errorMessage" class="error">{{ errorMessage }}</div>
  <ul v-else>
    <li v-for="topic in topics" :key="topic.id">{{ topic.statement }}</li>
  </ul>
</template>

<style scoped>
.error {
  color: red;
}
</style>
