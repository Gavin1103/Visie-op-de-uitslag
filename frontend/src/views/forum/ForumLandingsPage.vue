<script setup lang="ts">

import {onMounted, ref} from "vue";
import type {TopicResponse} from "@/models/forum/TopicResponse";
import {TopicService} from "@/services/TopicService";

const topicService = new TopicService();

const topics = ref<TopicResponse[]>([]);

const fetchData = async () => {

  try {
    const response = await topicService.getTopics(0, 10, 'createdAt,asc');
    topics.value = response.content;
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

onMounted(() => {
  fetchData();
});

</script>

<template>

  <section class="container">

    <section>
      <input class="search-bar" type="text" placeholder="zoeken..."/>
      <button class="create-button">Topic +</button>
    </section>

    <section class="topics-container">
      <div v-for="(topic, index) in topics" :key="topic.id" class="topic-item">
        <section class="left-container">
          <h3>{{ topic.statement }}</h3>
          
          <section>

          </section>

          <section>

          </section>
          <p><small>Created at: {{ topic.createdAt }}</small></p>
          <p><small>Updated at: {{ topic.updatedAt }}</small></p>
        </section>

      </div>
    </section>

  </section>
</template>

<style>

.container {
  margin: 0 auto;
  width: 60%;
  min-height: 400px;

  section:first-of-type {
    width: 100%;
    display: flex;
    justify-content: space-between;

    .search-bar {
      width: 70%;
      padding: 10px 15px;
      font-size: 16px;
      border: 1px solid #ccc;
      border-radius: 4px;
      outline: none;
      transition: border-color 0.3s ease;

      &:focus {
        border-color: #5564c8;
      }

    }

    .create-button {
      padding: 10px 20px;
      font-size: 16px;
      font-weight: bolder;
      color: white;
      background-color: #5564c8;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      transition: background-color 0.3s ease;

      &:hover {
        background-color: #5564c8;

      }

    }
  }

  section:last-of-type {
    width: 100%;
    display: flex;
    flex-direction: column;

    .topic-item {
      width: 100%;
      margin: 10px 0 10px 0;
      background: lightgrey;
    }
  }
}


</style>
