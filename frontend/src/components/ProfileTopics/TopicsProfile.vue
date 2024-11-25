<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { CookieService } from '@/services/CookieService'; // Assuming CookieService is being used to get the token
import { UserService } from '@/services/UserService'; // Assuming UserService is being used to fetch user details
import router from "@/router";

// Define the Topic type (you can adjust this as per your real data structure)
interface Topic {
  createdAt: string;
  updatedAt: string;
  statement: string;
  message: string;
}

// Ref to store the fetched data
const topics = ref<Topic[]>([]);
const userId = ref<number | null>(null);
const isLoading = ref(true);
const errorMessage = ref<string | null>(null);

// Fetch user data
const fetchUser = async () => {
  const token = new CookieService().getCookie('accessToken');
  if (!token) {
    console.error('No token found');
    await router.push("/"); // Redirect to login if token not found
    return;
  }

  try {
    const fetchedUser = await new UserService().getUserByToken(token);
    if (fetchedUser) {
      userId.value = fetchedUser.id;
      console.log('Fetched user:', fetchedUser); // Log the fetched user data
    }
  } catch (error) {
    console.error('Error fetching user:', error);
  }
};

// Fetch topics on component mount
onMounted(async () => {
  await fetchUser();

  if (userId.value) {
    console.log('User ID found:', userId.value); // Log the user ID
    try {
      const response = await axios.get(`http://your-api-url/topics/${userId.value}`, {
        params: { page: 0, size: 10 }, // Pagination params
      });
      console.log('Fetched topics response:', response.data); // Log the full response
      topics.value = response.data.content;
      console.log('Fetched topics:', topics.value); // Log the fetched topics
    } catch (error) {
      console.error('Error fetching topics:', error);
      errorMessage.value = 'Error fetching topics';
    } finally {
      isLoading.value = false;
    }
  } else {
    console.error('User ID not found');
    errorMessage.value = 'User ID not found';
    isLoading.value = false;
  }
});
</script>

<template>
  <div class="container mx-auto p-6">
    <!-- Loading state -->
    <div v-if="isLoading" class="text-center">Loading topics...</div>

    <!-- Error state -->
    <div v-else-if="errorMessage" class="text-center text-red-500">{{ errorMessage }}</div>

    <!-- Topics list -->
    <div v-else>
      <h1 class="text-2xl font-semibold mb-4">Topics</h1>
      <ul>
        <li v-for="(topic, index) in topics" :key="index" class="mb-4">
          <div class="bg-white p-4 rounded shadow-md">
            <h2 class="text-lg font-bold">{{ topic.statement }}</h2>
            <p class="text-sm text-gray-600">Created At: {{ topic.createdAt }}</p>
            <p class="mt-2">{{ topic.message }}</p>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
/* Add your custom styles if necessary */
.container {
  max-width: 800px;
}
</style>
