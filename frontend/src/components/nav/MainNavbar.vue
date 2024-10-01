<script setup lang="ts">
import { ref, watch } from 'vue' // Import ref
import IconLogo from "@/components/icons/IconLogo.vue";
import { CookieService } from '@/services/CookieService';
import router from '@/router'

let cookieService = new CookieService();


let isUserLoggedIn = ref(cookieService.tokenExists());
console.log(isUserLoggedIn.value);

function logout() {
  cookieService.removeTokenCookies();
  router.push('/login').then(() => {
    window.location.reload(); // Force full page reload after navigation
  });
}

</script>

<template>
  <nav class="w-full gap-6 bg-NavBlue m-0 flex items-center h-20">
    <section class="ml-4">
      <icon-logo height="h-20" width="w-32"/>
    </section>
    <ul class="flex ml-4 list-none gap-6 text-2xl font-bold flex-grow">
      <li><router-link class="text-white" to="/">Home</router-link></li>
      <li><router-link class="text-white" to="/about">About</router-link></li>
      <li><router-link class="text-white" to="/parties">Parties</router-link></li>
      <li><router-link class="text-white" to="/forum">Forum</router-link></li>
    </ul>
    <router-link v-if="!isUserLoggedIn" class="text-white text-2xl font-bold mr-6" to="/login">Login</router-link>
    <router-link v-if="!isUserLoggedIn" class="text-white text-2xl font-bold mr-6" to="/register">Register</router-link>
    <button v-else @click="logout" class="text-white text-2xl font-bold mr-6">Logout</button>
  </nav>
</template>
