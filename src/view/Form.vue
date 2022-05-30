<template>
  <div class="container">
    <el-form
      ref="form"
      :model="sizeForm"
      label-width="auto"
      :label-position="labelPosition"
      :size="size"
      :rules="rules"
    >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="sizeForm.username" />
      </el-form-item>
      <el-form-item label="学号" prop="uid">
        <el-input v-model="sizeForm.uid" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="sizeForm.password" type="password" show-password />
      </el-form-item>
      <el-form-item label="学院">
        <el-select v-model="sizeForm.collage" placeholder="请选择学院">
          <el-option label="管理学院" value="管理学院" />
          <el-option label="经济学院" value="经济学院" />
        </el-select>
      </el-form-item>
      <el-form-item label="所在年级">
        <el-radio-group v-model="sizeForm.grade">
          <el-radio border label="大一" />
          <el-radio border label="大二" />
          <el-radio border label="大三" />
          <el-radio border label="大四" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="所在班级">
        <el-radio-group v-model="sizeForm.classes">
          <el-radio border label="数管20-1班" />
          <el-radio border label="数管20-2班" />
          <el-radio border label="数管20-3班" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="邀请码" prop="inventcode">
        <el-input v-model="sizeForm.inventcode" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import formRegister from "../api/login/login";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
export default {
  setup() {
    const size = ref("large");
    const labelPosition = ref("top");

    const router = useRouter();

    const sizeForm = reactive({
      username: "",
      uid: 2020025,
      password: "",
      collage: "",
      grade: "",
      classes: "",
      inventcode: "",
    });

    const rules = reactive({
      username: [
        {
          required: true,
          message: "请输入用户名",
          trigger: "blur",
        },
        {
          min: 1,
          max: 10,
          message: "请输入长度在1到10的用户名",
          trigger: "blur",
        },
      ],
      uid: [
        {
          required: true,
          message: "请输入学号",
          trigger: "blur",
        },
        { min: 10, max: 10, message: "请输入十位数的学号", trigger: "blur" },
      ],
      password: [
        {
          required: true,
          message: "请输入密码",
          trigger: "blur",
        },
        { min: 6, max: 20, message: "请输入长度小于20的密码", trigger: "blur" },
      ],
      inventcode: [
        {
          required: true,
          message: "没有邀请码，请联系作者",
          trigger: "blur",
        },
        { min: 10, max: 10, message: "请不要随意测试", trigger: "blur" },
      ],
    });

    function onSubmit() {
      formRegister.register(sizeForm).then((res) => {
        if (res.data[0] != -1) {
          ElMessage.success("注册成功");
          router.push("/").then(() => {
            window.location.reload();
          })
        } else {
          ElMessage.error("注册失败，请仔细检查");
          setTimeout(() => {
            window.location.reload();
          }, 500);
        }
      });
    }

    return {
      size,
      labelPosition,
      sizeForm,
      rules,
      onSubmit,
    };
  },
};
</script>

<style>
.el-radio-group {
  margin-right: 12px;
}
.container {
  width: 50%;
}
</style>
