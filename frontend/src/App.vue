<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import MainNavbar from "@/components/nav/MainNavbar.vue";
import SideBar from "@/components/nav/Sidebar.vue"
import MainFooter from "@/components/footer/MainFooter.vue";
import ConfirmDialog from 'primevue/confirmdialog';
import Toast from 'primevue/toast';
</script>

<template>
  <div class="app-container"> <!-- Main container for the app -->
    <main-navbar />
    <div class="layout-container">
      <SideBar />
      <div class="content-area">
        <RouterView />
        <Toast />
        <ConfirmDialog />
      </div>
    </div>
    <MainFooter />
  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.layout-container {
  display: flex;
  flex: 1;
}

.content-area {
  flex: 1;
  padding: 5px;
}

.sidebar {
  width: 200px;
}
</style>
