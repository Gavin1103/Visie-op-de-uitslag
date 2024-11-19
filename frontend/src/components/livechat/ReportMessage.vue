<script setup lang="ts">
import { ref } from 'vue'
import { ChatService } from '@/services/ChatService'
import { useToast } from 'primevue/usetoast'
import { inject } from 'vue';
import type { CreateReport } from '@/models/chat/CreateReport'

const props = defineProps({
  messageId: Number
})

const chatService: ChatService = new ChatService();
const toast = useToast()
const userId = inject('userId');
const isModalShown = ref(false)
const selectedReason = ref("");
const customReason = ref("");
const report = ref<CreateReport>({reason: "", reporterId: userId})

const predefinedReasons = [
  "Spam",
  "Ongepaste inhoud",
  "Lastigvallen",
  "Anders",
];

const handleReport = async() => {
  const reason =
    selectedReason.value === "Anders" ? customReason.value : selectedReason.value;

  if (!reason) {
    toast.add({
      severity: 'error',
      summary: 'Reden',
      detail: 'Selecteer een reden.',
      life: 3000
    })
    return;
  }
  if(!report.value.reporterId){
    toast.add({
      severity: 'error',
      summary: 'log in',
      detail: 'Log in om te rapporteren',
      life: 3000
    })
    return;
  }
  report.value.reason = reason
  await chatService.ReportMessage(report.value, props.messageId)

  toast.add({
    severity: 'success',
    summary: 'Gerapporteerd',
    detail: 'bericht is gerapporteerd.',
    life: 3000
  })
  isModalShown.value = false;
  selectedReason.value = "";
  customReason.value = "";
};
</script>

<template>
    <button @click="isModalShown = true"><i class="pi pi-flag-fill button" style="color: red" ></i></button>

  <div
    v-if="isModalShown"
    class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50"
  >
    <div class="bg-white p-6 rounded-lg shadow-lg max-w-md w-full">
      <h2 class="text-lg font-semibold mb-4">Bericht Rapporteren</h2>

      <div class="mb-4">
        <p class="mb-2">Selecteer een reden:</p>
        <div v-for="(reason, index) in predefinedReasons" :key="index">
          <label class="flex items-center space-x-2">
            <input
              type="radio"
              :value="reason"
              v-model="selectedReason"
              class="cursor-pointer"
            />
            <span>{{ reason }}</span>
          </label>
        </div>
      </div>

      <div v-if="selectedReason === 'Anders'" class="mb-4">
        <p class="mb-2">Geef een reden op:</p>
        <textarea
          v-model="customReason"
          class="w-full p-2 border border-gray-300 rounded resize-none"
          rows="3"
          placeholder="Voer uw reden in"
        ></textarea>
      </div>

      <div class="flex justify-end space-x-2">
        <button
          @click="isModalShown = false"
          class="px-4 py-2 text-sm bg-gray-300 rounded hover:bg-gray-400"
        >
          Annuleren
        </button>
        <button
          @click="handleReport"
          class="px-4 py-2 text-sm bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          Verzenden
        </button>
      </div>
    </div>
  </div>

</template>

<style scoped>
  .button{
    font-size: 12px;
  }
</style>