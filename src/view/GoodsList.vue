<template>
  <div>
    <el-row :gutter="20">
      <el-col span="24" xs="10">
        <e-card shadow="hover" class="magrinTop">
          <el-table
            :data="query.list"
            height="300px"
            style="width: 100%"
            class="magrinTop"
          >
            <el-table-column prop="id" label="ID" v-if="false" />
            <el-table-column prop="stockCount" label="库存数量" width="180" />
            <el-table-column prop="goodsImg" label="商品图片" width="180" />
            <el-table-column prop="goodsName" label="商品名" width="180" />
            <el-table-column prop="goodsPrice" label="原价" width="180" />
            <el-table-column prop="miaoshaPrice" label="秒杀价" width="180" />
            <el-table-column prop="startDate" label="开始时间" width="180" />
            <el-table-column prop="endDate" label="结束时间" width="180" />
            <el-table-column width="180">
              <template #default="scope">
                <el-button type="info" @click="refTo(scope.row.id)"
                  >详情</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </e-card>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import { Delete, Edit } from "@element-plus/icons-vue";
import ShengDanOldMan from "../components/ShengDanOldMan.vue";
import goodsList from "../api/miaosha/miaoshagoods.js";

export default {
  components: {
    ShengDanOldMan,
  },
  setup() {
    const router = useRouter();
    const query = reactive({
      list: [],
    });
    // 获取表格数据
    function getData() {
      goodsList.goodsList().then((res) => {
        query.list = res.data.goodsVos;
      });
    }
    //跳转至详情页
    function refTo(goodsId) {
        //调用接口 通过goodsId 获取信息
      goodsList.getdetailByGoosId(goodsId).then((res) => {
        //记录倒计时
        var time = res.data.remainSeconds;
        //根据返回状态码确定状态 是否开始秒杀
        if (res.code == 201) {
          if (time == -1) {
            ElMessageBox.alert("秒杀已经结束");
          } else {
            ElMessageBox.alert("秒杀还未开始,剩余时间为：" + time);
          }
        } else {
            //通过路由传递 goodsId
          router.push({path: "/to_detail",query: {id:goodsId}}).then(() => {
            ElMessage.success("秒杀正在进行，请抓紧时间抢购");
            window.location.reload();
          });
        }
      });
    }
    getData();
    return {
      query,
      refTo,
    };
  },
};
</script>

<style>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>