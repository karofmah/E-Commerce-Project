import { createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import EditView from '../views/EditView.vue'
import NewItemView from '../views/NewItemView.vue'


const routes = [
    {
        path: '/',
        name: 'Home',
        component: HomeView
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginView
    },
    {
        path: '/register',
        name: 'Register',
        component: RegisterView
    },
    {
        path: '/Edit',
        name: 'Edit',
        component: EditView
    },
    {
        path: '/NewItem',
        name: 'NewItem',
        component: NewItemView
    }

]


const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router