<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import MainNavbar from "@/components/nav/MainNavbar.vue";
</script>

<template>
  <main-navbar />
  <br />
  <RouterView />
</template>

<style scoped>

</style>
