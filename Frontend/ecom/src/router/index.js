import { createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import EditView from '../views/EditView.vue'
import NewItemView from '../views/NewItemView.vue'
import ItemView from '../views/ItemView.vue'
import CartView from '../views/CartView.vue'
import UserView from '../views/UserView.vue'
import UpdateItemView from '../views/UpdateItemView.vue'


const routes = [
    {
        path: '/',
        name: 'Home',
        component: HomeView
    },
    {
        path: '/item/:id',
        name: 'Item',
        component: ItemView
    },
    {
        path: '/cart',
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
        path: '/edit',
        name: 'Edit',
        component: EditView
    },
    {
        path: '/newItem',
        name: 'NewItem',
        component: NewItemView
    },
    {
        path: '/userInfo',
        name: 'UserInfo',
        component: UserView 
    },
    {
        path: '/EditItem',
        name: 'EditItem',
        component: UpdateItemView 
    }

]


const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router