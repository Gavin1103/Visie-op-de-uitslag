<script setup lang="ts">
import { extractTime } from '@/helper/formatDateHelper'
import ReportMessage from '@/components/livechat/ReportMessage.vue'

const props = defineProps({
  message: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <li class="flex justify-start my-2">
    <div class="min-w-[100px] border-2 p-3 border-indigo-200 max-w-xs h-auto rounded-lg shadow-md bg-indigo-50 relative pb-6 text-left group">
      <span class="font-semibold text-indigo-700 block">{{ message.name }}:</span>
      <span class="text-black text-lg break-words">{{ message.message }}</span>
      <div class="absolute bottom-0 right-0 p-2 text-sm text-black">{{ extractTime(message.timestamp) }}
        <div class="display">
          <ReportMessage :message-id="message.id"></ReportMessage>
        </div>
      </div>
    </div>
  </li>
</template>
<style scoped>
.display {
  display: none;
}

.group:hover .display {
  display: inline;
}

</style>