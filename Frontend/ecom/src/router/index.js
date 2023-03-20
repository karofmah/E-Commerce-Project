import { createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import EditView from '../views/EditView.vue'
import NewItemView from '../views/NewItemView.vue'
import ItemView from '../views/ItemView.vue'
import CartView from '../views/CartView.vue'
import UserInfoView from '../views/UserInfoView.vue'



const routes = [
    {
        path: '/Home',
        name: 'Home',
        component: HomeView
    },
    {
        path: '/Item',
        name: 'Item',
        component: ItemView
    },
    {
        path: '/Cart',
        name: 'Cart',
        component: CartView
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
    },
    {
        path: '/',
        name: 'UserInfo',
        component: UserInfoView 
    }

]


const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router