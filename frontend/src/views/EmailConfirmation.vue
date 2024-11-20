<template>
  <div class="max-w-md mx-auto p-6 border rounded-lg shadow-md">
    <h2 class="text-2xl font-semibold mb-4">Email Confirmation</h2>
    <div v-if="loading" class="text-gray-700">Confirming your email, please wait...</div>
    <div v-if="error" class="text-red-500 mt-4">{{ error }}</div>
    <div v-if="success" class="text-green-500 mt-4">{{ success }}</div>
  </div>
</template>

<script lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { UserService } from '@/services/UserService';

export default {
  setup() {
    const route = useRoute();
    const loading = ref(true);
    const success = ref('');
    const error = ref('');
    const userService = new UserService();

    const confirmEmail = async () => {
      const token = route.params.token as string;
      console.log('Confirming email with token:', token);

      if (!token) {
        error.value = 'No token provided.';
        loading.value = false;
        return;
      }

      try {
        const response = await userService.confirmAccount(token);
        success.value = 'Your email has been confirmed successfully!';
      } catch (err: any) {
        console.error(err);
        error.value = err.response?.data?.message || 'An error occurred while confirming your email.';
      } finally {
        loading.value = false;
      }
    };

    onMounted(confirmEmail);

    return {
      loading,
      success,
      error,
    };
  },
};
</script>
