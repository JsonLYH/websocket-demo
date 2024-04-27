import { createRouter, createWebHistory } from 'vue-router'
import index from '../views/index.vue'
import gameInfo from '../views/GameInfo/gameInfo.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      component: index
    },
    {
      path: '/gameInfo',
      name: 'gameInfo',
      component: gameInfo
    },
    {
      path: '/group',
      name: 'group',
      component: gameInfo
    }
  ]
})

export default router
