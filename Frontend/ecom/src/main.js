import { createApp } from 'vue'
import { createPinia } from "pinia";
import piniaPluginPersistedState from "pinia-plugin-persistedstate"
import { i18n } from './i18n';
import App from './App.vue'
import router from './router/index';

import "./assets/main.css";

const pinia = createPinia();
pinia.use(piniaPluginPersistedState)

createApp(App).use(pinia).use(router).use(i18n).mount('#app')