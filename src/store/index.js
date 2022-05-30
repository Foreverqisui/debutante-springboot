import {createStore} from 'vuex'

export default createStore({
    //仓库存贮数据的地方
    state: {
        collapse: false
    },
    //修改state唯一的手段
    mutations: {
        // 侧边栏折叠
        handleCollapse(state, data) {
            state.collapse = data;
        }
    },
    //处理自己的逻辑，可以书写自己的业务逻辑，也可以处理异步
    actions: {},
    modules: {}
})