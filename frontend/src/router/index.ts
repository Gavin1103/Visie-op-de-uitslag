import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CreateUser from '../views/CreateUser.vue'
import UserOverview from '../views/UserOverview.vue'
import LoginUser from '../views/LoginUser.vue'
import { UserService } from '@/services/UserService'
import Unauthorized from '@/components/Unauthorized.vue'


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
      component: () => import('../views/AboutView.vue')
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
      path: '/userOverview',
      name: 'userOverview',
      component: UserOverview,
      meta: { requiresAdmin: true }
    },
    {
      path: '/unauthorized',
      name: 'unauthorized',
      component: Unauthorized
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
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
