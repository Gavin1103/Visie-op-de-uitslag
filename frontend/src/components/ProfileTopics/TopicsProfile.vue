<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type {TopicResponse} from "@/models/forum/TopicResponse";
import {TopicService} from "@/services/TopicService";
import {formatDate} from "../../helper/formatDateHelper";
import {dummytopicResponse} from "@/stores/EmptyCandidateList";
import LivechatView from "@/views/LivechatView.vue";


const topics = ref<TopicResponse[]>([])
const currentPage = ref(0)
const pageSize = 5
const isMoreAvailable = ref(true)

const isChatModalVisible = ref(false)
const selectedTopic = ref<TopicResponse>(dummytopicResponse)


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

const joinLiveChat = (topic: TopicResponse) => {
  console.log(topic);
  selectedTopic.value = topic
  isChatModalVisible.value = true
}

const closeChatModal = () => {
  isChatModalVisible.value = false
  selectedTopic.value = dummytopicResponse
}


</script>

<template>
  <div class="container mx-auto p-6">
    <div>
      <h1 class="text-2xl font-semibold mb-4">Topics</h1>
      <ul>
        <li
            v-for="(topic, index) in topics"
            :key="index"
            class="mb-4">
          <div class="bg-white p-4 rounded shadow-md relative">
            <!-- Topic Details -->
            <div>
              <h2 class="text-lg font-bold">{{ topic.statement }}</h2>
              <p class="text-sm text-gray-600">Created At: {{ formatDate(topic.createdAt) }}</p>
              <section class="flex">
                <p class="text-sm mr-2"><small>Likes: {{ topic.amountOfRatings.likes }}</small></p>
                <p class="text-sm"><small>Dislikes: {{ topic.amountOfRatings.dislikes }}</small></p>
              </section>
              <p><small><strong>Antwoorden: {{ topic.amountOfAnswers }}</strong></small></p>
            </div>

            <!-- Live Chat Button Inside Box -->
            <div
                @click="joinLiveChat(topic)"
                class="absolute top-4 right-4 flex flex-col justify-center items-center hover:cursor-pointer">
              <img
                  class="w-10 h-10 object-cover"
                  src="../../../public/live-chat-icon.png"
                  alt="Live chat icon"
              />
              <p class="text-xs"><strong>Live chat</strong></p>
            </div>
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

  <div v-if="isChatModalVisible" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
    <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-2xl h-5/6 overflow-y-auto relative">
      <LivechatView :topic="selectedTopic" @close="closeChatModal" />
    </div>
  </div>
</template>


<style scoped>
/* Add your custom styles if necessary */
.container {
  max-width: 800px;
}
</style>
