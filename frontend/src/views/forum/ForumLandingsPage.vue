<script setup lang="ts">

import {onBeforeMount, onMounted, ref, watch} from 'vue'
import type {TopicResponse} from '@/models/forum/TopicResponse'
import {formatDate} from '../../helper/formatDateHelper'
import {useToast} from 'primevue/usetoast'
import {TopicService} from '@/services/TopicService'
import {CookieService} from '@/services/CookieService'
import type {CreateTopic} from '@/models/topic/CreateTopic'
import LivechatView from '@/views/LivechatView.vue'
import Dialog from 'primevue/dialog'
import Editor from 'primevue/editor'
import {debounce} from 'lodash';
import {dummytopicResponse} from '@/stores/EmptyCandidateList'
import RatingComponent from "@/components/forum/RatingComponent.vue";
import {RatingTypeEnum} from "@/models/enum/rating/RatingTypeEnum";

const topicService = new TopicService()
const cookieService = new CookieService()
const toast = useToast()

const isDialogVisible = ref(false)
const newTopicContent = ref('')
const newStatement = ref('')
const searchQuery = ref('')

enum SortOptions {
  newest = "createdAt,desc",
  oldest = "createdAt,asc",
  likes = "likes",
  dislikes = "dislikes",
}

// TODO: fix sort by likes and dislikes

const topics = ref<TopicResponse[]>([])
const currentPage = ref(0)
const pageSize = 5
const sort = ref(SortOptions.newest)
const isMoreAvailable = ref(true)

const isUserLoggedIn = ref<boolean | null>(null)

onBeforeMount(async () => {
  isUserLoggedIn.value = cookieService.tokenExists()
})

const isChatModalVisible = ref(false)
const selectedTopic = ref<TopicResponse>(dummytopicResponse)

const fetchData = async (page: number = 0) => {
  try {
    isMoreAvailable.value = true
    const response: PaginatedResponse<TopicResponse> = await topicService.getTopics(page, pageSize, sort.value)

    if (page === 0) {
      topics.value = response.content
    } else {
      topics.value = [...topics.value, ...response.content]
    }
    if (topics.value.length >= response.totalElements) {
      isMoreAvailable.value = false
    }
  } catch (error) {
    console.error('Error fetching data:', error)
  }
}

watch(sort, () => {
  fetchData();
});

onMounted(() => {
  fetchData()
})

const searchTopic = debounce(async () => {
  if (searchQuery.value.length > 1) {
    isMoreAvailable.value = false;
    topics.value = await topicService.searchTopicByStatement(searchQuery.value);
  } else if (searchQuery.value.length === 0) {
    await fetchData();
  }
}, 300);

const loadMore = () => {
  currentPage.value += 1
  fetchData(currentPage.value)
}

const openDialog = () => {
  if (!isUserLoggedIn.value) {
    toast.add({
      severity: 'info',
      summary: 'Log in to Participate',
      detail: 'You need to be logged in to create a topic. Sign in to get started!',
      life: 5000
    })
    return
  }
  isDialogVisible.value = true
}

const closeDialog = () => {
  isDialogVisible.value = false
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

const validateForm = async () => {
  let error = false

  if (newTopicContent.value.length < 1) {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Content cannot be empty.',
      life: 3000
    })
    error = true
  }

  if (newStatement.value.length < 1) {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Statement cannot be empty.',
      life: 3000
    })
    error = true
  }

  return error
}

const submitNewTopic = async () => {
  if (await validateForm()) {
    return
  }

  try {
    const data: CreateTopic = {
      statement: newStatement.value,
      message: newTopicContent.value
    }
    await topicService.createTopic(data)
  } catch (error) {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Failed to create topic. Please try again.',
      life: 3000
    })
  } finally {
    closeDialog()
    await fetchData()
    toast.add({severity: 'success', summary: 'Success', detail: 'Your topic was created successfully!', life: 3000})
  }
}

</script>
<template>
  <section class="container mx-auto md:w-3/4 xl:w-3/5 min-h-[400px]">
    <h1 class="text-3xl font-bold my-6">Topics</h1>
    <section class="search-container py-1 w-full flex justify-between mb-4">
      <input
          v-model="searchQuery"
          @input="searchTopic"
          class="search-input w-1/2 mr-2.5 px-4 py-2 text-lg border border-gray-300 rounded focus:outline-none focus:border-blue-600 transition-colors"
          type="text"
          placeholder="zoeken..."
      />
      <select
          v-model="sort"
          class="select-filter mr-auto px-2 text-sm font-bold text-white bg-[#5564c8] rounded shadow hover:bg-gray-200 hover:text-black focus:outline-none focus:ring-2 focus:ring-blue-300 transition-all">
        <option v-for="key in Object.keys(SortOptions) as (keyof typeof SortOptions)[]" :key="key"
                :value="SortOptions[key]">
          {{ key }}
        </option>
      </select>
      <button
          @click="openDialog"
          class="create-topic-btn px-5 py-2 text-lg font-bold text-white bg-[#5564c8] rounded cursor-pointer hover:bg-blue-700 transition-colors">
        Topic +
      </button>
    </section>

    <section class="w-full flex flex-col space-y-4 pb-20">
      <div v-if="topics.length === 0" class="text-center py-4 text-gray-500">
        <p>No topics found.</p>
      </div>
      <div v-else v-for="(topic) in topics" :key="topic.id"
           class="topic-container w-full pl-4 bg-gray-200 rounded-lg flex justify-between h-28">
        <div class="left-container w-7/12 flex flex-col justify-center">
          <router-link :to="{ name: 'TopicDetail', params: { id: topic.id } }" v-html="topic.statement"></router-link>
          <RatingComponent
              :ratingType="RatingTypeEnum.TOPIC"
              :ratingTypeId="topic.id"
          />
          <p class="mr-2"><small>Created at: {{ formatDate(topic.createdAt) }}</small></p>
          <p><small><strong>Antwoorden: {{ topic.amountOfAnswers }}</strong></small></p>
        </div>

        <div class="live-chat-icon-mobile hidden">
          <img
              @click="joinLiveChat(topic)"
              class="mobile-chat-icon"
              src="../../../public/live-chat-icon.png"
              alt="profile-img-mobile"
          />
        </div>

        <div class="user-info-container ml-2 h-full w-4/12 bg-[#5564c8] flex flex-col justify-center items-center">
          <img class="w-16 h-16 rounded-full object-cover shadow-lg" src="../../../public/no-proflile-img.png"
               alt="profile-img"/>
          <p><strong>{{ topic.username }}</strong></p>
        </div>
        <div @click="joinLiveChat(topic)"
             class="live-chat-icon-container h-full w-1/6 flex flex-col justify-center items-center hover:cursor-pointer">
          <img
              class="w-16 h-16 object-cover"
              src="../../../public/live-chat-icon.png"
              alt="profile-img"
          />
          <p><strong>Live chat</strong></p>
        </div>
      </div>
      <p
          v-if="isMoreAvailable"
          @click="loadMore"
          class="ml-auto text-blue-600 hover:underline cursor-pointer transition-colors">
        <strong>Meer...</strong>
      </p>
    </section>

    <Dialog v-model:visible="isDialogVisible" header="New Topic" :style="{ width: '50vw' }" @hide="closeDialog">
      <h1>Statement</h1>
      <Editor v-model="newStatement" editorStyle="height: 100px">
        <template v-slot:toolbar>
        <span class="ql-formats">
            <button v-tooltip.bottom="'Bold'" class="ql-bold"></button>
            <button v-tooltip.bottom="'Italic'" class="ql-italic"></button>
            <button v-tooltip.bottom="'Underline'" class="ql-underline"></button>
        </span>
        </template>
      </Editor>
      <br>
      <h1>Content</h1>
      <Editor v-model="newTopicContent" editorStyle="height: 320px"/>
      <div class="flex justify-end mt-4">
        <button @click="closeDialog" class="mr-2 px-4 py-2 bg-gray-400 rounded text-white">Cancel</button>
        <button @click="submitNewTopic" class="px-4 py-2 bg-blue-600 rounded text-white">Submit</button>
      </div>
    </Dialog>
    <div v-if="isChatModalVisible" class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
      <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-2xl h-5/6 overflow-y-auto relative">
        <LivechatView :topic="selectedTopic" @close="closeChatModal"/>
      </div>
    </div>
  </section>
</template>


<style>
@media (max-width: 700px) {
  .container {
    width: 95%;
  }

  .live-chat-icon-container {
    display: none;
  }

  .user-info-container {
    border-top-right-radius: 0.5rem;
    border-bottom-right-radius: 0.5rem;
    margin: 0;
  }

  .live-chat-icon-mobile {
    height: 100%;
    width: auto;
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;

    .mobile-chat-icon {
      margin: 0 5px 5px 0;
      height: 50px;
    }
  }
}

@media (max-width: 450px) {
  .search-container{
    flex-direction: column;
    justify-content: space-around  ;
    height: 200px;

    .search-input{
      width:100%;
    }

    .create-topic-btn{
      width: 100%;
    }

    .select-filter{
      width: 100%;
      height: 50px;
    }
  }
}


</style>