import { createApp } from 'vue'
import App from './App.vue'
import installElementPlus from './plugins/element'
import Router from './router/Router.js'

const app = createApp(App)
installElementPlus(app)
app.use(Router)
app.mount('#app')