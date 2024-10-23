<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import MainNavbar from "@/components/nav/MainNavbar.vue";
import MainFooter from "@/components/footer/MainFooter.vue";
import Toast from 'primevue/toast';

</script>
<template>
  <main-navbar />
  <main>
    <RouterView />
    <Toast />
  </main>
  <MainFooter />

</template>

<style scoped>
main{
  flex: 1;
}

</style>
