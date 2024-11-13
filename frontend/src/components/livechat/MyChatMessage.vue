<script setup lang="ts">
import Message from 'primevue/message'
import { extractTime } from '../../helper/formatDateHelper'
const props = defineProps({
  message: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <li class="flex justify-end my-2">
    <div class="min-w-[100px] border-2 p-3 border-green-200 max-w-xs h-auto rounded-lg shadow-md bg-green-50 relative pb-6">
      <span class="text-black text-lg break-words">{{ message.message }}</span>
      <p class="absolute bottom-0 right-0 p-2 text-sm text-black">{{ extractTime(message.timestamp) }}</p>
    </div>
  </li>
</template>

