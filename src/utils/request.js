import axios from 'axios';
import cookie from "js-cookie";
import { ElMessage } from "element-plus";
const service = axios.create({
    NODE_ENV: '"development"',
    baseURL: 'http://localhost:8080',
    timeout: 5000
});

//请求拦截器
service.interceptors.request.use(
    //config配置对象
    config => {
        //判断cookie中是否包含名称是cookiname的数据
        if(cookie.get('cookieName')){
            //把cookie里面的值放到header里面
            config.headers['token'] = cookie.get('cookieName');
        }
        return config;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);
//响应拦截器
service.interceptors.response.use(
    response => {
        //响应成功的回调函数
        if (response.status === 200) {
            return response.data;
        } else {
            //失败的回调函数
            Promise.reject();
        }
    },
    error => {
        ElMessage.error("用户名或密码错误")
        return Promise.reject();
    }
);

export default service;
