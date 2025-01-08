import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CreateUser from '../views/CreateUser.vue'
import LoginUser from '../views/LoginUser.vue'
import EmailConfirmation from '../views/EmailConfirmation.vue'
import { UserService } from '@/services/UserService'
import UnauthorizedView from '@/components/Unauthorized.vue'
import PartyOverview from '@/views/PartyOverview.vue'
import PartyView from '@/views/PartyView.vue'
import UserOverview from '@/views/cms/UserOverview.vue'
import DashboardView from '@/views/cms/DashboardView.vue'
import ForumLandingsPage from "@/views/forum/ForumLandingsPage.vue";
import TopicDetailView from '@/views/forum/TopicDetailView.vue'
import ReportsView from '@/views/cms/ReportsView.vue'
import MapView from '@/views/MapView.vue'

const userService = new UserService();

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
      component: ForumLandingsPage
    },
    {
      path: '/topic/:id',
      name: 'TopicDetail',
      component: TopicDetailView, props: true },
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
      path: '/cms/dashboard',
      name: 'dashboard',
      component: DashboardView,
      meta: { requiresAdmin: true }
    },
    {
      path: '/cms/reports',
      name: 'reports',
      component: ReportsView,
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
      component: UnauthorizedView
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/map',
      name: '/map',
      component: MapView,
    }
  ]
})

router.beforeEach(async (to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAdmin)) {
    const isAdmin = await userService.currentUserIsAdmin();
    if (!isAdmin) {
      next({ name: 'unauthorized' });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
