<script setup lang="ts">
import Message from 'primevue/message'
import { extractTime } from '@/helper/formatDateHelper'

const props = defineProps({
  message: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <li class="flex justify-start my-2">
    <div class="min-w-[100px] border-2 p-3 border-indigo-200 max-w-xs h-auto rounded-lg shadow-md bg-indigo-50 relative pb-6 text-left">
      <span class="font-semibold text-indigo-700 block">{{ message.name }}:</span>
      <span class="text-black text-lg break-words">{{ message.message }}</span>
      <p class="absolute bottom-0 right-0 p-2 text-sm text-black">{{ extractTime(message.timestamp) }}</p>
    </div>
  </li>
</template>
