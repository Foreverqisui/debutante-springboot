<template>
  <div>
    <h1>秒杀</h1>
    <p>
      {{ query.goodsName }}
      {{ query.goodsPrice }}
      {{ query.stockCount }}
    </p>
    <el-button type="danger" @click="costMoney()">抢购</el-button>
    <el-button type="info" @click="toRef()">订单</el-button>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import goodsList from "../api/miaosha/miaoshagoods.js";
import cookie from "js-cookie";
import { ElMessageBox } from "element-plus";

export default {
  setup() {
    const query = reactive({
      list: [],
      goodsName: "",
      goodsPrice: "",
      stockCount: "",
    });
    var uid = "";
    //通过路由传递id参数
    const router = useRouter();
    const goodsId = router.currentRoute.value.query.id;
    //通过goodsId获取信息
    function getInfo(goodsId) {
      goodsList.getdetailByGoosId(goodsId).then((res) => {
        const info = res.data.good;
        query.goodsName = info.goodsName;
        query.goodsPrice = info.goodsPrice;
        query.stockCount = info.stockCount;
      });
    }
    //抢购
    function costMoney() {
      goodsList.doSekill(uid, goodsId).then((res) => {
        if (res.data.questionStatus == 500) {
          ElMessageBox.alert("商品已经秒空");
        } else if (res.data.questionStatus == 666) {
          ElMessageBox.alert("你已经购买过了，请不要重复购买");
        }
        ElMessageBox.alert("恭喜抢到" + res.data.orderInfo.goodsName);
      });
    }
    //通过cookie做二次 判断 并获得用户信息
    //根据cookie中的信息获取用户信息
    function getUserInfoByToken() {
      let cookieInfo = cookie.get("userInfo");
      if (cookieInfo) {
        const info = JSON.parse(cookieInfo);
        uid = info.uid;
      }
    }
    // 跳转至订单详情页
    function toRef() {
      router.push({ path: "/to_order", query: { uid: uid } }).then(() => {
        window.location.reload();
      });
    }
    getInfo(goodsId);
    getUserInfoByToken();
    return { query, costMoney,toRef};
  },
};
</script>