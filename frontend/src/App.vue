<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import MainNavbar from "@/components/nav/MainNavbar.vue";
import MainFooter from "@/components/footer/MainFooter.vue";
</script>

<template>

  <main-navbar />
  <main class="p-5">
    <RouterView />
  </main>
  <MainFooter />

</template>

<style scoped>
main{
  flex: 1;
}

</style>
