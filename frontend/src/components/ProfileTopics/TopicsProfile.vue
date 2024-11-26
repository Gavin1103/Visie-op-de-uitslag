<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type {TopicResponse} from "@/models/forum/TopicResponse";
import {TopicService} from "@/services/TopicService";


const topics = ref<TopicResponse[]>([])
const currentPage = ref(0)
const pageSize = 5
const isMoreAvailable = ref(true)


const topicService = new TopicService();

const fetchTopics = async (page: number = 0) => {
  try {
    const response: PaginatedResponse<TopicResponse> = await topicService.getTopicsByUser(currentPage.value,pageSize)
    if (page === 0) {
      topics.value = response.content
    } else {
      topics.value = [...topics.value, ...response.content]
    }
    if (topics.value.length >= response.totalElements) {
      isMoreAvailable.value = false
    }
    console.log(topics)
  } catch (error) {
    console.error('Error fetching data:', error)
  }
};

// Fetch topics on component mount
onMounted(async () => {
  await fetchTopics();

});

const loadMore = () => {
  currentPage.value += 1
  fetchTopics(currentPage.value)
}

</script>

<template>
  <div class="container mx-auto p-6">

    <div>
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
      <p
          v-if="isMoreAvailable"
          @click="loadMore"
          class="ml-auto text-blue-600 hover:underline cursor-pointer transition-colors">
        <strong>Meer...</strong>
      </p>
    </div>
  </div>
</template>

<style scoped>
/* Add your custom styles if necessary */
.container {
  max-width: 800px;
}
</style>
