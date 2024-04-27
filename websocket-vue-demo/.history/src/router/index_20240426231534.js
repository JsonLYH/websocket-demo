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
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: 
    }
  ]
})

export default router
