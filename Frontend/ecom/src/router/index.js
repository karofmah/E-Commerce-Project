import { createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import ItemView from '../views/ItemView.vue'



const routes = [
    {
        path: '/',
        name: 'Home',
        component: ItemView
    },
]


const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router