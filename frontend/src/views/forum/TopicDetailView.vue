<template>
  <section class="container mx-auto w-3/5 min-h-[400px] py-8">
    <Card>
      <template #title>
        <h1 class="text-4xl font-bold" v-html="topicTitle"></h1>
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
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import Card from 'primevue/card'
import Button from 'primevue/button'
import type { TopicResponse } from '@/models/forum/TopicResponse'
import { formatDate } from '../../helper/formatDateHelper'
import { TopicService } from '@/services/TopicService'

const topicService = new TopicService()
const route = useRoute()
const router = useRouter()

const topic = ref<TopicResponse | null>(null)

const loadTopic = async () => {
  const topicId = route.params.id
  if (topicId) {
    topic.value = await topicService.getTopicById(topicId as string)
  }
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
