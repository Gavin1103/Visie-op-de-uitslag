<script setup lang="ts">
import Message from 'primevue/message'

const props = defineProps({
  message: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <ul>
    <li class="text-center flex justify-center my-2">
      <Message severity="info" class="mx-auto rounded-lg bg-blue-100 text-blue-700">
        {{ message.name }} {{ message.message }}
      </Message>
    </li>
  </ul>
</template>
