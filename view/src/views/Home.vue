<template>
	<div class="body">
		<Menu class="menu"/>
		<div class="box"/>
		<div class="home">
			<h1>Welcome to the Home page</h1>
			<div class="login">
				<button @click="toLogin">去登录</button>
			</div>
			<div class="logout">
				<button @click="logout">登出</button>
			</div>
			<div class="test1">
				<button @click="test1">测试1</button>
			</div>
			<div class="test2">
				<button @click="test2">测试2</button>
			</div>
			<div class="test3">
				<button @click="test3">hello</button>
			</div>
		</div>
	</div>
</template>


<script setup>
import {useRouter} from "vue-router";
import {push} from "../main";
import {request} from "../utils/service";
import Menu from "../components/Menu.vue";
import {useStore} from "../store";

// 修改Menu中a组件为class="home"的颜色

const router = useRouter();

const logout = () => {
	var token = useStore().$state.token || null;
	console.log("登出" + token);
	axios.get(import.meta.env.VITE_BASE_API + "auth/logout", {
		headers: {
			"Authorization": "Bearer " + token
		}
	}).then((res) => {
		useStore().logoutAction();
		router.push("/login");
		push.success("已登出");
	});
};

const toLogin = () => {
	router.push({path: "/login"});
};
const test1 = () => {
	push.success({
		title: "成功了",
		message: "这是一条成功的提示消息",
	})
};

async function test2() {
	const notification = push.promise("上传中");
	try {
		await new Promise((resolve, reject) => {
			setTimeout(() => {
				resolve()
			}, 2000)
		})
		notification.resolve("上传结束")
	} catch (e) {
		notification.reject("上传失败")
	}
}

const test3 = () => {
	request({
		url: "/user/hello",
		method: "get",
	}).then((res) => {
		console.log(res);
	});
};
</script>

<style scoped>
.body {
	display: flex;
}

.box {
	width: 9%;
}

.home {
	bottom: 0;
}
</style>
