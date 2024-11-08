<script setup>
import {Client} from '@stomp/stompjs';
import { ref } from 'vue'




const chat = ref("")

let socket = null
let stompClient
function connect() {
  // Create a WebSocket connection
  socket = new WebSocket('ws://localhost:8080/api/ws');

  // Create a STOMP client using the WebSocket
  stompClient = new Client({
    brokerURL: 'ws://localhost:8080/api/ws',  // The URL to the WebSocket server
    onConnect: function(frame) {
      console.log('Connected: ' + frame);

      // Subscribe to a topic
      stompClient.subscribe('/topic/messages', function(messageOutput) {
        console.log("Received message: ", messageOutput.body);
      });

      // Send a message to the server
      stompClient.publish({
        destination: "/app/chat",  // Correct destination for the backend endpoint
        body: JSON.stringify({
          'nickname': 'User',
          'content': 'someone joined',
          'timestamp': new Date().toISOString()
        })
      });
    },
    onStompError: function(error) {
      console.error('STOMP error: ', error);
    }
  });

  stompClient.activate();
}

function sendMessage() {
  stompClient.publish({
    destination: "/app/chat",  // Correct destination for the backend endpoint
    body: JSON.stringify({
      'nickname': 'User',
      'content': chat.value,
      'timestamp': new Date().toISOString()
    })
  });
}

function disconnect() {
  stompClient.deactivate();
}
</script>

<template>
  <input v-model="chat" type="text">
  <button @click="sendMessage">send message</button>

  <button @click="connect">connect</button>
  <button @click="disconnect">disconnect</button>
</template>

<style scoped>

</style>