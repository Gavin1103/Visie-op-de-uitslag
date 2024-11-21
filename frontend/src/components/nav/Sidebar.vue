<template>
  <template v-if="isCmsRoute">
    <PanelMenu :model="cmsMenuItems" class="vertical-menu" />
  </template>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import PanelMenu from 'primevue/panelmenu';
import { useRoute } from 'vue-router';
import { CookieService } from '@/services/CookieService';
import { UserService } from '@/services/UserService';
import router from '@/router'

const cookieService = new CookieService();
const userService = new UserService();

const route = useRoute();
const isCmsRoute = ref(route.path.startsWith('/cms'));

watch(route, (newRoute) => {
  isCmsRoute.value = newRoute.path.startsWith('/cms');
});

const cmsMenuItems = ref([
  {
    label: 'Dashboard',
    icon: 'pi pi-fw pi-home',
    command: () => router.push('/cms/dashboard'),
  },
  {
    label: 'Users',
    icon: 'pi pi-fw pi-users',
    command: () => router.push('/cms/userOverview'),
  },
  {
    label: 'Reports',
    icon: 'pi pi-flag',
    command: () => router.push('/cms/reports'),
  },
  {
    label: 'Settings',
    icon: 'pi pi-fw pi-cog',
    command: () => router.push('/cms/settings'),
  },
]);
</script>

<style scoped>
.vertical-menu {
  width: 200px;
  margin: 2rem 2rem 0 1rem;
}
</style>
