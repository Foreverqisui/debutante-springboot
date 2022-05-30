import { createApp } from 'vue'
import App from './App.vue'
//router
import router from './router'
//elementUI
import * as icons from '@element-plus/icons-vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//vuex
import store from './store'
//bootstrap 
import 'jquery/dist/jquery.min'
import 'bootstrap/dist/css/bootstrap.css'


const app = createApp(App)


Object.keys(icons).forEach(key => {
    app.component(key, icons[key])
})

app.use(ElementPlus).use(router).use(store)
app.mount('#app')
