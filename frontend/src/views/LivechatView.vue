<script setup lang="ts">
import { computed, nextTick, onMounted, ref, watch } from 'vue'
import { WebSocketService } from '@/services/WebSocketService.ts'
import InputText from 'primevue/inputtext'
import { ChatMessageType } from '@/models/enum/ChatMessageType'
import { UserService } from '@/services/UserService'
import type { UserProfile } from '@/models/user/UserProfile'
import { CookieService } from '@/services/CookieService'
import JoinLeaveMessage from '@/components/livechat/JoinLeaveMessage.vue'
import MyChatMessage from '@/components/livechat/MyChatMessage.vue'
import OtherChatMessage from '@/components/livechat/OtherChatMessage.vue'


const props = defineProps({
  topic: Object,
});

const emit = defineEmits(['close']);

const webSocketService = new WebSocketService();
const userService = new UserService();
const cookieService = new CookieService();


const messages = computed(() => webSocketService.messages.value);
const chatMessage = ref("");
const activeUsers = ref(0);
const messagesContainer = ref(null);
let isUserScrolledUp = false;
const userProfile = ref<UserProfile | undefined>(undefined)

const handleScroll = () => {
  const container = messagesContainer.value;
  const nearBottom = container.scrollHeight - container.scrollTop <= container.clientHeight + 10;
  isUserScrolledUp = !nearBottom;
};

const scrollToBottom = () => {
  const container = messagesContainer.value;
  container.scrollTop = container.scrollHeight;
};

function connect() {
  webSocketService.connect(props.topic.id)
}

async function sendMessage() {
  webSocketService.sendMessage(props.topic.id, chatMessage.value, ChatMessageType.CHAT);
  chatMessage.value = "";
}

function disconnect() {
  webSocketService.disconnect(props.topic.id);
  emit('close');

}

watch(messages, async (newMessages) => {
  const lastMessageIndex = newMessages.findLastIndex((msg) => msg.type == ChatMessageType.JOIN || msg.type == ChatMessageType.LEAVE);
  if (lastMessageIndex !== -1) {
    const lastMessage = newMessages[lastMessageIndex];
    activeUsers.value = lastMessage.activeUsers;
  }
  if (!isUserScrolledUp) {
    await nextTick();
    scrollToBottom();

  }
});

onMounted(async () => {
  connect();
  await nextTick(scrollToBottom);
  userProfile.value = await userService.getUserByToken(cookieService.getCookie(cookieService.accessTokenAlias))
})
</script>

<template>
  <div class="bg-white shadow-xl rounded-lg p-4 max-w-2xl w-full h-full flex flex-col mx-auto">
    <div>
    <h2 class="text-3xl font-bold text-gray-800 mb-6">Live chat: {{ topic.statement }}</h2>
      <div class="m-3"><i class="pi pi-eye" style="color: blue"></i> {{activeUsers}}</div>
    </div>
    <div ref="messagesContainer"
         class="overflow-y-auto flex-grow border overflow-x-hidden rounded-lg p-6 bg-gray-50 space-y-4"
         @scroll="handleScroll"
         >
      <ul>
        <template v-for="(msg, index) in messages" :key="index">
          <JoinLeaveMessage v-if="msg.type === ChatMessageType.JOIN || msg.type === ChatMessageType.LEAVE" :message="msg" />
          <MyChatMessage v-else-if="msg.userId === userProfile.id" :message="msg" />
          <OtherChatMessage v-else :message="msg" />
        </template>
      </ul>
    </div>

    <div class="mt-4 flex items-center space-x-4">
      <InputText
        v-model="chatMessage"
        type="text"
        class="flex-1 border rounded-lg px-5 py-3 focus:outline-none focus:ring-2 focus:ring-indigo-600 text-lg"
        placeholder="Type bericht..."
      />
      <button
        @click="sendMessage"
        class="px-5 py-3 bg-indigo-600 text-white text-lg font-semibold rounded-lg hover:bg-indigo-700 transition"
      >
        verzend
      </button>
    </div>


    <button
      @click="disconnect"
      class="px-2 w-32 m-3 py-3 bg-red-500 text-white text-lg font-semibold rounded-lg hover:bg-red-700 transition"
    >
      verlaat chat
    </button>
  </div>

</template>
