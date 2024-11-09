<script setup>
import { ref, computed } from 'vue'
import {WebSocketService} from '@/services/WebSocketService.ts'

const webSocketService = new WebSocketService();

const messages = computed(() => webSocketService.messages.value);
const chatMessage = ref("")
function connect(){
  webSocketService.connect(1);
}
function sendMessage() {
  webSocketService.sendMessage(1, chatMessage.value);
}

function disconnect() {
  webSocketService.disconnect(1);
}


</script>

<template>
  <input v-model="chatMessage" type="text">
  <button @click="sendMessage">send message</button>

  <button @click="connect">connect</button>
  <button @click="disconnect">disconnect</button>
  <ul>
    <li v-for="(msg, index) in messages" :key="index">
      {{ msg.name }}: {{ msg.message }}
    </li>
  </ul>
</template>

<style scoped>

</style>