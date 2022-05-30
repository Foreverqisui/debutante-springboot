<template>
  <div id="container">
    <div class="container">
      <el-row :gutter="20">
        <el-col :span="20" :xs="20" :sm="20" :md="20">
          <el-card shadow="hover" class="card">
            <el-button type="success" round id="rbtn" @click="registFrom()"
              >注册</el-button
            >
            <el-tooltip
              class="box-item"
              effect="dark"
              content="多好看的头型啊 emoji"
              placement="top-start"
            >
              <el-avatar id="avatar"> user </el-avatar>
            </el-tooltip>
            <el-input
              v-model="param.uid"
              placeholder="请输入学号"
              id="inputText"
              class="hvr-grow"
            >
            </el-input>
            <el-input
              v-model="param.password"
              type="password"
              placeholder="请输入密码"
              show-password
              id="inputPassword"
              class="hvr-grow"
            />
            <el-button
              type="success"
              round
              id="btn"
              @click="submitForm()"
              class="hvr-buzz"
              >提交</el-button
            >
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { ref, reactive, computed } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import userInfo from "../api/login/login";
import cookie from "js-cookie";

export default {
  name: "index",
  setup() {
    const router = useRouter();
    let param = reactive({
      id: "",
      uid: 2020025,
      password: "123456",
      longinTime: "",
    });
    //登录信息
    //信息添加
    // function getUserInfo() {
    //   userInfo.login(param).then((res) => {
    //     if (res.data.list[0].id === -1) {
    //       ElMessage.error("请重新输入");
    //       setTimeout(() => {
    //         router.push("/");
    //       }, 200);
    //     } else {
    //       param = res.data.list[0];
    //       ElMessage.success("登录成功");
    //       router.push("/loginIndex/" + param.id);
    //     }
    //   });
    // }
    //jwt登录
    function loginByToken() {
      userInfo.loginByToken(param).then((res) => {
        //获取token值
        let token = res.data.token;
        //放入cookie中 【第一个参数 是名字，第二个参数是信息，第三个参数是作用域】
        cookie.set("cookieName", token, { domain: "localhost" });
        //根据头信息中的token查询信息
        //再将查询的信息放到cookie中
        getUserInfoByToken();
        router.push("/loginIndex").then((res) => {
          ElMessage.success("登录成功");
          setTimeout(() => {
            window.location.reload();
          }, 3);
        });
      });
    }
    //根据token查询信息的方法
    function getUserInfoByToken() {
      userInfo.getUserInfoByToken().then((res) => {
        let data = JSON.stringify(res.data.userInfo);
        cookie.set("userInfo", data, { domain: "localhost" });
      });
    }
    //登录按钮
    function submitForm() {
      loginByToken();
    }

    //注册按钮
    function registFrom() {
      router.push("/form");
    }
    return {
      param,
      submitForm,
      registFrom,
    };
  },
};
</script>
<style>
#container {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url(../assets/img/kenan.jpeg);
  background-size: 100%;
  margin-top: 20px;
}
.container {
  width: 350px;
  height: 100px;
  margin-top: 200px;
}
#inputText {
  width: 220px;
  height: 50px;
  margin-left: 0px;
}
#inputPassword {
  width: 220px;
  height: 50px;
  margin-top: 10px;
  margin-bottom: 10px;
}
#avatar {
  margin-left: 95px;
  margin-bottom: 20px;
  background-size: auto;
}
#btn {
  margin-left: 85px;
  margin-top: 20px;
  background-image: linear-gradient(
    to right top,
    #d16ba5,
    #c777b9,
    #ba83ca,
    #aa8fd8,
    #9a9ae1,
    #8aa7ec,
    #79b3f4,
    #69bff8,
    #52cffe,
    #41dfff,
    #46eefa,
    #5ffbf1
  );
}
#rbtn {
  width: 20px;
  height: 20px;
  margin-top: -20px;
  margin-left: 195px;
}

.hvr-grow {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
  -webkit-transition-duration: 0.3s;
  transition-duration: 0.3s;
  -webkit-transition-property: transform;
  transition-property: transform;
}
.hvr-grow:hover,
.hvr-grow:focus,
.hvr-grow:active {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
/* Buzz */
@-webkit-keyframes hvr-buzz {
  50% {
    -webkit-transform: translateX(3px) rotate(2deg);
    transform: translateX(3px) rotate(2deg);
  }
  100% {
    -webkit-transform: translateX(-3px) rotate(-2deg);
    transform: translateX(-3px) rotate(-2deg);
  }
}
@keyframes hvr-buzz {
  50% {
    -webkit-transform: translateX(3px) rotate(2deg);
    transform: translateX(3px) rotate(2deg);
  }
  100% {
    -webkit-transform: translateX(-3px) rotate(-2deg);
    transform: translateX(-3px) rotate(-2deg);
  }
}
.hvr-buzz {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
}
.hvr-buzz:hover,
.hvr-buzz:focus,
.hvr-buzz:active {
  -webkit-animation-name: hvr-buzz;
  animation-name: hvr-buzz;
  -webkit-animation-duration: 0.15s;
  animation-duration: 0.15s;
  -webkit-animation-timing-function: linear;
  animation-timing-function: linear;
  -webkit-animation-iteration-count: infinite;
  animation-iteration-count: infinite;
}
</style>