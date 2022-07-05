<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8" :xs="24" :sm="8" :md="7"
        ><div class="grid-content bg-purple" />
        <el-card shadow="hover" class="mgb20" style="height: 252px">
          <div class="user-info">
            <el-avatar
              class="card-avator"
              src="http://pc5201314.oss-cn-beijing.aliyuncs.com/2022/04/07/%E7%AD%BE%E5%90%8D.jpg"
            />
            <div class="user-info-cont">
              <div class="user-info-name">
                {{ formLabelAlign.params.username }}
              </div>
              <div>{{ formLabelAlign.params.uid }}</div>
            </div>
          </div>
          <el-divider>
            <el-icon><star-filled /></el-icon>
          </el-divider>
          <div class="user-info-list">
            上次登录时间：
            <span>{{ formLabelAlign.params.logintime }}</span>
          </div>
          <div class="user-info-list">
            是否提交截图：
            <span>
              {{ status }}
            </span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16" :xs="24"
        >
        <el-upload
          class="upload-demo"
          drag
          action="http://123.56.156.8:5580/api/oss/file/uploadFile"
          multiple
          :on-success="uploadOss"
        >
          <el-icon class="el-icon--upload"><upload-filled /></el-icon>
          <div class="el-upload__text">上传青年大学习截图 <em>click</em></div>
          <template #tip>
            <div class="el-upload__tip">
              jpg/png files with a size less than 500kb
            </div>
          </template>
        </el-upload>
      </el-col>
        <el-col :span="20" :xs="12" :sm="8" :md="24"
        ><div class="grid-content bg-purple" />
        <el-card shadow="hover" class="mgb20" style="height: 252px weight:400px">
          <GoodsList></GoodsList>
        </el-card>
      </el-col>
    </el-row>
    <el-row :span="16">
      <el-col :span="16" :xs="15">
        <el-button type="waring" round @click="logout()"> 退出登录 </el-button>
        <el-button type="waring" round @click="lookRankList()">
          查看排行榜
        </el-button>
      </el-col>
      <el-button type="warning" round @click="miaoshaRef()"
        >秒杀</el-button
      >
    </el-row>
  </div>
</template>

<script>
import login from "../api/login/login";
import { ref, reactive, computed } from "vue";
import { onBeforeRouteUpdate, useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import cookie from "js-cookie";

export default {
  components: {},
  setup() {
    //表内信息
    let formLabelAlign = reactive({
      params: {
        username: "",
        logintime: "",
        status: "",
        pictureoss:"",
      },
    });
    //是否提交截图
    let status = ref({});
    //头像地址
    let pictureoss = ref({});
    const router = useRouter();

    // //获取连接中的id
    // //得到id值
    // const id = router.currentRoute.value.params.id;

    // //根据id查询信息
    // function getUserInfo(id) {
    //   login.findById(id).then((res) => {
    //     formLabelAlign.params = res.data.userInfoById;
    //   });
    // }

    //根据cookie中的信息获取用户信息
    function getUserInfoByToken() {
      let cookieInfo = cookie.get("userInfo");
      if (cookieInfo) {
        formLabelAlign.params = JSON.parse(cookieInfo);
      }
      //根据状态码返回是否提交
      if (formLabelAlign.params.status == 1) {
        status = "已提交";
      } else if (formLabelAlign.params.status == 0) {
        status = "未提交";
      }
    }
    //退出登录
    function logout() {
      cookie.set("userInfo", "", { domain: "localhost" });
      cookie.set("cookieName", "", { domain: "localhost" });
      router.push("/").then(() => {
        window.location.reload();
      });
    }

    //上传照片后执行的操
    function uploadOss() {
      ElMessage.success("上传成功");
      uploadTimes(formLabelAlign.params);
      console.log("更新" + formLabelAlign.params);
      router.push("/uploadForm").then(() => {
        setTimeout(() => {
          window.location.reload();
        }, 3);
      });
    }

    //提交时间
    function uploadTimes(data) {
      login.uploadTime(data).then((res) => {
        console.log(data);
        console.log(res);
      });
    }
    //查看排行榜
    function lookRankList() {
      router.push("/uploadForm").then(() => {
        window.location.reload();
      });
    }
    //跳转至秒杀页面
    function miaoshaRef() {
      router.push("/to_miaoshagoods").then(() => {
        window.location.reload();
      });
    }
    getUserInfoByToken();
    // getUserInfo(id);
    return {
      formLabelAlign,
      logout,
      uploadOss,
      status,
      lookRankList,
      pictureoss,
      miaoshaRef,
    };
  },
};
</script>

<style>
.alert-header {
  height: 100px;
}
.card-avator {
  width: 70px;
  height: 70px;
  border-radius: 100%;
  margin-top: 20px;
  margin-left: 10px;
}
.user-info-name {
  margin-bottom: 10px;
  font-size: 20px;
}
.user-info-cont {
  font-size: 10px;
  color: #999;
  margin-left: 100px;
  margin-top: -70px;
}
.user-info-list {
  font-size: 14px;
  color: #999;
  line-height: 25px;
}
.upload-container {
  margin-top: 100px;
  margin-left: 20px;
}

.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>