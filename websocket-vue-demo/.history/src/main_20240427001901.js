import './assets/main.css'
import UUID from "vue-uuid";

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(UUID)
app.use(router)

app.mount('#app')
