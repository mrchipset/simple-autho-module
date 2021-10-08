import { createRouter, createWebHashHistory } from 'vue-router' 
import Content from '../components/Content.vue'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'

const routes = [
    {
        path: "/",
        component: Content,
    },
    {
        path: "/login",
        component: Login,
    },
    {
        path: "/register",
        component: Register
    }
]

const Router = createRouter({
    history: createWebHashHistory(),
    routes: routes
})

export default Router