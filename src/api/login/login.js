import request from '../../utils/request';

export default {
    // 登录
    login(userInfo) {
        return request({
            url: `/admin/debutante/login`,
            method: 'POST',
            data: userInfo
        })
    },
    //根据token登录
    loginByToken(userInfo) {
        return request({
            url: `/admin/debutante/loginByToken`,
            method: 'POST',
            data: userInfo
        })
    },
    //根据id查询
    findById(id) {
        return request({
            url: `/admin/debutante/findLoginById/${id}`,
            method: 'GET',
        })
    },
    //oss文件上传
    uploadFile(file) {
        return request({
            url: `/api/oss/file/uploadFile`,
            method: 'POST',
            data: file
        })
    },
    //注册功能
    register(formItem) {
        return request({
            url: `/admin/debutante/register`,
            method: 'POST',
            data: formItem
        })
    },
    //根据token获取用户信息
    getUserInfoByToken() {
        return request({
            url: `/admin/debutante/getByToken`,
            method: "GET"
        })
    },
    //分页排行榜
    uploadForm(current, limit, formInfo) {
        return request({
            url: `/admin/debutante/upLoadDesc/${current}/${limit}`,
            method: "POST",
            data: formInfo
        })
    },
    //更新上传时间
    uploadTime(uploadTime) {
        return request({
            url: `/admin/debutante/uploadTime`,
            method: "POST",
            data: uploadTime
        })
    },
}