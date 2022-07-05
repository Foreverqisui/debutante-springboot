<template>
  <div>
    <p>商品名：{{ query.goodsName }}</p>
    <p>价格：{{ query.goodsPrice }}</p>
    <p v-if="query.status == 1">待支付</p>
    <p v-if="query.status == 0">已支付</p>
    <el-button type="button" @click="reduceProperty(query.uid)"
      >立即支付</el-button
    >
    <el-button type="button" @click="toRef()">回主页</el-button>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import goodsList from "../api/miaosha/miaoshagoods.js";
import { reactive, ref } from "vue";
import { ElMessageBox } from "element-plus";
export default {
  setup() {
    const router = useRouter();
    const uid = router.currentRoute.value.query.uid;
    const query = reactive({
      goodsName: "",
      goodsPrice: "",
      status: "",
      uid: "",
    });
    //获取订单信息
    function getInfo(uid) {
      goodsList.orderInfoBy(uid).then((res) => {
        query.goodsName = res.data.orderInfo.goodsName;
        query.goodsPrice = res.data.orderInfo.goodsPrice;
        query.status = res.data.orderInfo.status;
        query.uid = router.currentRoute.value.query.uid;
      });
    }
    //扣钱
    function reduceProperty(uid) {
      goodsList.reduceProperty(uid).then((res) => {
        ElMessageBox.alert("支付成功");
      });
    }
    //回主页
    function toRef() {
      router.push("/loginIndex").then(() => {
        uploadTimes();
        window.location.reload();
      });
    }
    getInfo(uid);
    return { query, reduceProperty,toRef };
  },
};
</script>
