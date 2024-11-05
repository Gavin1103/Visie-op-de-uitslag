<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import MainNavbar from "@/components/nav/MainNavbar.vue";
import SideBar from "@/components/nav/Sidebar.vue"
import MainFooter from "@/components/footer/MainFooter.vue";
import ConfirmDialog from 'primevue/confirmdialog';
import Toast from 'primevue/toast';
</script>

<template>
  <main-navbar />
  <main class="flex-container">
    <SideBar />
    <RouterView />
    <Toast />
    <ConfirmDialog></ConfirmDialog>
  </main>
  <MainFooter />
</template>

<style scoped>
.flex-container {
  display: flex;
  flex-direction: row; /* Aligns children in a row */
}

main {
  flex: 1; /* Allows main to take the remaining space */
  display: flex; /* Makes sure the RouterView and Toast are also flex items */
  flex-direction: column; /* Stack items vertically */
}

.sidebar {
  width: 200px; /* Fixed width for the sidebar */
}

.router-view {
  flex: 1; /* Allows RouterView to take the remaining space */
}
</style>
