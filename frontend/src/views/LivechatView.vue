<script setup lang="ts">
import { ref, computed } from 'vue'
import {WebSocketService} from '@/services/WebSocketService.ts'
import InputText from 'primevue/inputtext'
import { ChatMessageType } from '@/models/enum/ChatMessageType.ts'


const webSocketService = new WebSocketService();

const messages = computed(() => webSocketService.messages.value);
const chatMessage = ref("");
function connect(){
 webSocketService.connect(1);
}
function sendMessage() {
  webSocketService.sendMessage(1, chatMessage.value, ChatMessageType.CHAT);
  chatMessage.value = "";
}

function disconnect() {
  webSocketService.disconnect(1);
}


</script>

<template>
  <div class="flex justify-center m-16 items-center w-full h-full">
    <div class="flex gap-6">
      <section class="flex flex-col gap-6">
        <InputText v-model="chatMessage" type="text"></InputText>
        <button class="border" @click="sendMessage">send message</button>
        <ul>
          <li v-for="(msg, index) in messages" :key="index">
            {{ msg.name }}: {{ msg.message }}
          </li>
        </ul>
      </section>
      <section class="flex flex-col">
  <button class="border" @click="connect">connect</button>
  <button class="border" @click="disconnect">disconnect</button>
      </section>


    </div>
  </div>
</template>

<style scoped>

</style>