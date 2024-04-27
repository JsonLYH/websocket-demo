import './assets/main.css'
import UUID from "vue-uuid";
import VueCookies from 'vue-cookies';
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(UUID)
app.use(VueCookies)
app.use(router)

app.mount('#app')
