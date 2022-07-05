import request from '../../utils/request';

export default {
    // 登录
    goodsList() {
        return request({
            url: `/admin/debutante/goods/findGoodsList`,
            method: 'GET',
        })
    },
    // 根据goodsId查询信息
    getdetailByGoosId(goodsId) {
        return request({
            url: `/admin/debutante/goods/detailById/${goodsId}`,
            method: 'GET',
        })
    },
    // 做秒杀功能 下订单 减库存
    doSekill(uid, goodsId) {
        return request({
            url: `/admin/debutante/miaosha/seckill/${goodsId}/${uid}`,
            method: 'GET',
        })
    },
    // 做订单查询
    orderInfoBy(uid) {
        return request({
            url: `/admin/debutante/order/getOrderInfo?uid=${uid}`,
            method: 'GET',
        })
    },
    // 支付后扣钱
    reduceProperty(uid) {
        return request({
            url: `/admin/debutante/reduceProperty/${uid}`,
            method: 'POST',
        })
    },
}