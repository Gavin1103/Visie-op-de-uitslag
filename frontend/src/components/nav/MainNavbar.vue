<script setup lang="ts">
import { ref, watch } from 'vue'
import IconLogo from '@/components/icons/IconLogo.vue'
import { CookieService } from '@/services/CookieService'
import router from '@/router'
import Menubar from 'primevue/menubar'
import { UserService } from '@/services/UserService'

const cookieService = new CookieService()
const userService = new UserService()

let isUserLoggedIn = ref(cookieService.tokenExists())
let isUserAdmin = ref(userService.currentUserIsAdmin())

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
  { label: 'Forum', icon: 'pi pi-comments', command: () => router.push('/forum') }
])

const rightMenuItems = ref([
  {
    label: isUserLoggedIn.value ? 'Logout' : 'Login',
    icon: isUserLoggedIn.value ? 'pi pi-sign-out' : 'pi pi-sign-in',
    command: () => isUserLoggedIn.value ? logout() : router.push('/login')
  },
  {
    label: 'Register',
    icon: 'pi pi-user-plus',
    command: () => router.push('/register'),
    visible: !isUserLoggedIn.value
  },
  { label: 'Profile', icon: 'pi pi-user', command: () => router.push('/profile'), visible: isUserLoggedIn.value },
  { label: 'Admin', icon: 'pi pi-briefcase', command: () => router.push('/cms/dashboard'), visible: isUserAdmin.value }
])
</script>

<template>
  <div class="flex">
    <div class="content-area">
      <Menubar :model="leftMenuItems" class="custom-menubar">
        <template #start>
          <icon-logo height="h-20" width="w-32" />
        </template>
        <template #end>
          <Menubar :model="rightMenuItems" />
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
