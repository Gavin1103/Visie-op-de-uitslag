<template>
  <section class="container mx-auto w-3/5 min-h-[400px] py-8">
    <Card>
      <template #title>
      <div class="flex items-center justify-between">
        <h1 class="text-4xl font-bold" v-html="topicTitle"></h1>
        <button @click="joinLiveChat"
          class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 focus:outline-none">
          join chat
        </button>
      </div>
      <hr class="my-4 border-t-2 border-gray-300" />
    </template>
      <template #content>
        <div class="text-lg mb-4 topic-content" v-html="topicContent"></div>
        <div class="flex justify-between mt-4">
          <p><small>Created by: <strong>{{ topic?.username ?? "unknown" }}</strong></small></p>
          <p><small>Created at: {{ formatDate(topic?.createdAt) }}</small></p>
        </div>
      </template>
      <Button
        label="Back to Topics"
        icon="pi pi-arrow-left"
        class="p-button-primary mt-4"
        @click="goBack"
      />
    </Card>

    <!-- Responses Section -->
    <section class="mt-8">
      <h2 class="text-2xl font-semibold">Responses</h2>
      <div v-if="responses.length === 0" class="mt-4">
        <p>No responses yet. Be the first to respond!</p>
      </div>
      <ul v-else class="mt-4 space-y-4">
        <li
          v-for="response in responses"
          :key="response.id"
          class="border border-gray-300 rounded-md p-4"
        >
          <div class="flex justify-between items-center">
            <p><strong>{{ response?.username ?? "unknown" }}</strong></p>
            <p><small>{{ formatDate(response.createdAt) }}</small></p>
          </div>
          <div
            class="mt-2 markdown-response"
            v-html="response.message"
          ></div>
        </li>
      </ul>

      <div class="mt-8">
        <h3 class="text-xl font-semibold">Add a Response</h3>
        <div v-if="isUserLoggedIn">
          <Editor
            v-model="newResponse"
            editorStyle="height: 320px"
            placeholder="Write your response"
          />
          <Button
            label="Submit"
            icon="pi pi-send"
            class="p-button-primary mt-4"
            :disabled="!newResponse"
            @click="addResponse"
          />
        </div>
        <div v-else>
          <p class="text-red-500">You must be logged in to add a response. Please <a href="/login" class="text-blue-500">log in</a>.</p>
        </div>
      </div>
    </section>
    <div v-if="isChatModalVisible" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
      <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-2xl h-5/6 overflow-y-auto relative">
        <LivechatView :topic="selectedTopic" @close="closeChatModal"/>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, onBeforeMount } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import Card from 'primevue/card'
import Button from 'primevue/button'
import Editor from 'primevue/editor'
import type { TopicResponse } from '@/models/forum/TopicResponse'
import { formatDate } from '../../helper/formatDateHelper'
import { TopicService } from '@/services/TopicService'
import { CookieService } from '@/services/CookieService'
import { AnswerService } from '@/services/AnswerService'
import { CreateAnswerDto } from '@/models/answer/CreateAnswerDto'
import LivechatView from '@/views/LivechatView.vue'
import { dummytopicResponse } from '@/stores/EmptyCandidateList'
const topicService = new TopicService()
const answerService = new AnswerService()
const cookieService = new CookieService()
const route = useRoute()
const router = useRouter()

const topic = ref<TopicResponse | null>(null)

const responses = ref<Array<{ id: number; username: string; message: string; createdAt: string }>>([])
const newResponse = ref('')
const isUserLoggedIn = ref<boolean | null>(null)

const isChatModalVisible = ref(false)
const selectedTopic = ref<TopicResponse>(dummytopicResponse)

onBeforeMount(async () => {
  isUserLoggedIn.value = cookieService.tokenExists()
})

const loadTopic = async () => {
  const topicId = parseInt(route.params.id as string, 10)
  if (topicId) {
    topic.value = await topicService.getTopicById(topicId)
    responses.value = await answerService.getAnswersByTopicId(topicId)
  }
}

const joinLiveChat = () => {
  console.log(topic);
  selectedTopic.value = topic.value
  isChatModalVisible.value = true
}

const closeChatModal = () => {
  isChatModalVisible.value = false
  selectedTopic.value = dummytopicResponse
}

const addResponse = async () => {
  const topicId = parseInt(route.params.id as string, 10)
  if (newResponse.value.trim()) {
    const createAnswerDto = new CreateAnswerDto(newResponse.value, topicId)
    const response = await answerService.addAnswer(createAnswerDto)
    response.username = response.user.username;
    responses.value.push(response)
    newResponse.value = ''
  }
}

const deleteResponse = async (responseId: number) => {
  const topicId = parseInt(route.params.id as string, 10)
  await answerService.deleteAnswer(responseId)
  responses.value = responses.value.filter((r) => r.id !== responseId)
}

const goBack = () => {
  router.push({ name: 'Topics' })
}

onMounted(() => {
  loadTopic()
})

const topicTitle = computed(() => topic.value?.statement || '')
const topicContent = computed(() => topic.value?.message || '')
</script>


<style scoped>
.container {
  max-width: 80%;
  margin: auto;
}

.topic-content >>> h1 {
  font-size: 2rem;
}

.topic-content >>> h2 {
  font-size: 1.5rem;
}

.topic-content >>> .ql-code-block {
  background-color: #f5f5f5;
  border-left: 4px solid #3498db;
  color: #333;
  padding: 10px;
  font-family: "Courier New", Courier, monospace;
  font-size: 0.95em;
  overflow-x: auto;
  white-space: pre-wrap;
}

.topic-content >>> .ql-code-block.ql-syntax {
  padding-left: 8px;
}

</style>
