<script setup lang="ts">
import { ref, onMounted } from "vue";
import Dialog from "primevue/dialog";
import Editor from 'primevue/editor';
import type { TopicResponse } from "@/models/forum/TopicResponse";
import { TopicService } from "@/services/TopicService";
import { formatDate } from "../../helper/formatDateHelper";

const topicService = new TopicService();

const topics = ref<TopicResponse[]>([]);
const currentPage = ref(0);
const pageSize = 5;
const isMoreAvailable = ref(true);

// State variables for the modal and Quill editor content
const isDialogVisible = ref(false);
const newTopicContent = ref("");
const newStatement = ref("");

const fetchData = async (page: number = 0) => {
  try {
    const response = await topicService.getTopics(page, pageSize, 'createdAt,asc');
    topics.value = [...topics.value, ...response.content];

    if (topics.value.length >= response.totalElements) {
      isMoreAvailable.value = false;
    }
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

onMounted(() => {
  fetchData();
});

const loadMore = () => {
  currentPage.value += 1;
  fetchData(currentPage.value);
};

// Open and close the modal
const openDialog = () => {
  isDialogVisible.value = true;
};

const closeDialog = () => {
  isDialogVisible.value = false;
};

const submitForm = () => {
  const response = topicService.getTopics()
};

</script>

<template>
  <section class="container mx-auto w-3/5 min-h-[400px]">
    <h1 class="text-3xl font-bold my-6">Topics</h1>

    <section class="py-1 w-full flex justify-between mb-4">
      <input
        class="w-3/4 px-4 py-2 text-lg border border-gray-300 rounded focus:outline-none focus:border-blue-600 transition-colors"
        type="text"
        placeholder="zoeken..."
      />
      <button
        @click="openDialog"
        class="px-5 py-2 text-lg font-bold text-white bg-[#5564c8] rounded cursor-pointer hover:bg-blue-700 transition-colors">
        Topic +
      </button>
    </section>

    <!-- Topics List -->
    <section class="w-full flex flex-col space-y-4 pb-20">
      <div v-for="(topic, index) in topics"
           :key="topic.id"
           class="w-full pl-4 bg-gray-200 rounded-lg flex justify-between h-28">
        <div class="left-container w-7/12 flex flex-col justify-center">
          <h3 class="text-lg font-semibold">{{ topic.statement }}</h3>
          <section class="flex">
            <p class="text-sm mr-2"><small>Likes: {{ topic.likes }}</small></p>
            <p class="text-sm"><small>Dislikes: {{ topic.dislikes }}</small></p>
          </section>
          <p class="mr-2"><small>Created at: {{ formatDate(topic.createdAt) }}</small></p>
          <p><small><strong>Antwoorden: {{ topic.amountOfAnswers }}</strong></small></p>
        </div>
        <div class="ml-2 h-full w-4/12 bg-[#5564c8] flex flex-col justify-center items-center">
          <img class="w-16 h-16 rounded-full object-cover shadow-lg" src="../../../public/no-proflile-img.png" alt="profile-img"/>
          <p><strong>Username: {{ topic.username }}</strong></p>
        </div>
        <div class="h-full w-1/6 flex flex-col justify-center items-center">
          <img class="w-16 h-16 object-cover" src="../../../public/live-chat-icon.png" alt="profile-img"/>
          <p><strong>Live chat</strong></p>
        </div>
      </div>

      <!-- Load More Button -->
      <p
        v-if="isMoreAvailable"
        @click="loadMore"
        class="ml-auto text-blue-600 hover:underline cursor-pointer transition-colors">
        <strong>Meer...</strong>
      </p>
    </section>

    <Dialog v-model:visible="isDialogVisible" header="New Topic" :style="{ width: '50vw' }" @hide="closeDialog">

      <Editor v-model="newStatement" editorStyle="height: 100px">
        <template v-slot:toolbar>
        <span class="ql-formats">
            <button v-tooltip.bottom="'Bold'" class="ql-bold"></button>
            <button v-tooltip.bottom="'Italic'" class="ql-italic"></button>
            <button v-tooltip.bottom="'Underline'" class="ql-underline"></button>
        </span>
        </template>
      </Editor>
      <Editor v-model="newTopicContent" editorStyle="height: 320px" />
      <div class="flex justify-end mt-4">
        <button @click="closeDialog" class="mr-2 px-4 py-2 bg-gray-400 rounded text-white">Cancel</button>
        <button @click="submitNewTopic" class="px-4 py-2 bg-blue-600 rounded text-white">Submit</button>
      </div>
    </Dialog>
  </section>
</template>

