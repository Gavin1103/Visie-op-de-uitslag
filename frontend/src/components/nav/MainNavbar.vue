<script setup lang="ts">
import { onBeforeMount, onMounted, ref, watch } from 'vue'
import IconLogo from '@/components/icons/IconLogo.vue'
import { CookieService } from '@/services/CookieService'
import router from '@/router'
import Menubar from 'primevue/menubar'
import { UserService } from '@/services/UserService'

const cookieService = new CookieService()
const userService = new UserService()

const isUserLoggedIn = ref<boolean | null>(null)
const isUserAdmin = ref<boolean | null>(null)

onBeforeMount(async () => {
  isUserAdmin.value = await userService.currentUserIsAdmin()
  isUserLoggedIn.value = cookieService.tokenExists()
})

function logout() {
  cookieService.removeTokenCookies()
  router.push('/login').then(() => {
    window.location.reload()
  })
}

const leftMenuItems = ref([
  { label: 'Home', icon: 'pi pi-home', command: () => router.push('/') },
  { label: 'About', icon: 'pi pi-info', command: () => router.push('/about') },
  { label: 'Parties', icon: 'pi pi-users', command: () => router.push('/parties') },
  { label: 'Forum', icon: 'pi pi-comments', command: () => router.push('/forum') },
  { label: 'Map', icon: 'pi pi-map', command: () => router.push('/map') }

])

// Define a function to update the rightMenuItems based on login status and admin role
const rightMenuItems = ref([{
  label: '',
  icon: '',
  command: () => router.push(''),
  visible: false
},])

watch([isUserLoggedIn, isUserAdmin], () => {
  if (isUserLoggedIn.value !== null && isUserAdmin.value !== null) {
    rightMenuItems.value = [
      {
        label: isUserLoggedIn.value ? 'Logout' : 'Login',
        icon: isUserLoggedIn.value ? 'pi pi-sign-out' : 'pi pi-sign-in',
        command: async () => isUserLoggedIn.value ? logout() : router.push('/login'),
        visible: true
      },
      {
        label: 'Register',
        icon: 'pi pi-user-plus',
        command: () => router.push('/register'),
        visible: !isUserLoggedIn.value
      },
      { label: 'Profile', icon: 'pi pi-user', command: () => router.push('/profile'), visible: isUserLoggedIn.value },
      { label: 'Admin', icon: 'pi pi-briefcase', command: () => router.push('/cms/dashboard'), visible: isUserLoggedIn.value && isUserAdmin.value }
    ]
  }
}, { immediate: true })
</script>

<template>
  <div class="flex">
    <div class="content-area">
      <Menubar :model="leftMenuItems" class="custom-menubar">
        <template #start>
          <icon-logo height="h-20" width="w-32" />
        </template>
        <template #end>
          <Menubar v-if="isUserLoggedIn !== null && isUserAdmin !== null" :model="rightMenuItems" />
        </template>
      </Menubar>
    </div>
  </div>
</template>

<style scoped>
.flex {
  display: flex;
}

.content-area {
  flex: 1;
  padding: 5px;
}
</style>
