import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CreateUser from '../views/CreateUser.vue'
import UserOverview from '../views/UserOverview.vue'
import LoginUser from '../views/LoginUser.vue'
import EmailConfirmation from '../views/EmailConfirmation.vue'
import { UserService } from '@/services/UserService'
import Unauthorized from '@/components/Unauthorized.vue'
import PartyOverview from '@/views/PartyOverview.vue'
import PartyView from '@/views/PartyView.vue'
import ForumView from '@/views/ForumView.vue'


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
      component: ForumView
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
      path: '/profile',
      name: 'profile',
      component: () => import('../views/ProfileView.vue') // Assuming you'll create ProfileView.vue in the views folder
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
