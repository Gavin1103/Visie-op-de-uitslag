import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CreateUser from '../views/CreateUser.vue'
import LoginUser from '../views/LoginUser.vue'
import EmailConfirmation from '../views/EmailConfirmation.vue'
import { UserService } from '@/services/UserService'
import Unauthorized from '@/components/Unauthorized.vue'
import PartyOverview from '@/views/PartyOverview.vue'
import PartyView from '@/views/PartyView.vue'
import UserOverview from '@/views/UserOverview.vue'


const userService = new UserService();
let isAdmin = await userService.currentUserIsAdmin();

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/parties',
      name: 'parties',
      component: PartyOverview
    },
    {
      path: '/party/:id',
      name: 'party',
      component: PartyView,
      props: true
    },
    {
      path: '/login',
      name: 'login',
      component: LoginUser
    },
    {
      path: '/register',
      name: 'register',
      component: CreateUser
    },
    {
      path: '/confirm/:token',
      name: 'confirm',
      component: EmailConfirmation
    },
    {
      path: '/forum',
      name: 'forum',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: CreateUser
    },
    {
      path: '/cms/userOverview',
      name: 'userOverview',
      component: UserOverview,
      meta: { requiresAdmin: true }
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('../views/ProfileView.vue')
    },
    {
      path: '/unauthorized',
      name: 'unauthorized',
      component: Unauthorized
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAdmin)) {
    if (!isAdmin) {
      next({ name: 'unauthorized' })
    } else {
      next()
    }
  } else {
    next()
  }
})


export default router
