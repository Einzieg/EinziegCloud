<template>
	<div class="bg">
		<div :class="{ 'panel-active': isActive }" class="container">
			<div class="container-form container-signup">
				<form id="form1" :model="registerForm" action="#" class="form">
					<h2 class="form-title">注册账号</h2>
					<input class="input"
					       placeholder="用户名"
					       v-model="registerForm.username"
					       type="text"/>
					<input class="input"
					       placeholder="邮箱"
					       v-model="registerForm.email"
					       type="email"/>
					<div style="width:100%;display:flex;align-items:center;margin-left:-10%">
						<input v-model="registerForm.verificationCode"
						       class="input"
						       placeholder="验证码"
						       type="text">
						<div style="margin-right:5px;margin-right:10px"></div>
						<button :disabled="text !== '发送验证码'" class="code-btn" type="button" @click="getCode()">
							{{ text }}
						</button>
					</div>
					<input class="input"
					       placeholder="密码"
					       v-model="registerForm.password"
					       type="password"/>
					<button class="button" type="button" @click="register">点击注册</button>
				</form>
			</div>

			<div class="container-form container-signin">
				<form id="form2" :model="loginForm" action="#" class="form">
					<h2 class="form-title">欢迎登录</h2>
					<input class="input"
					       placeholder="用户名"
					       v-model="loginForm.username"
					       type="text"/>
					<input class="input"
					       placeholder="密码"
					       v-model="loginForm.password"
					       type="password"/>
					<!--<label><input type="checkbox">记住我</label>-->
					<router-link to="/forget" class="link" href="#">忘记密码?</router-link>
					<button class="button" type="button" @click="login">登录</button>
				</form>
			</div>

			<div class="container-overlay">
				<div class="overlay">
					<div class="overlay-panel overlay-left">
						<button @click="togglePanel(false)" id="signIn" class="button">
							已有账号，直接登录
						</button>
					</div>
					<div class="overlay-panel overlay-right">
						<button @click="togglePanel(true)" id="signUp" class="button">
							没有账号，点击注册
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>


<script lang="ts" setup>
import axios from 'axios';
import Message from '../components/Message';
import {useRoute, useRouter} from 'vue-router';

let route = useRoute();
let router = useRouter();

const isActive = ref(false);
const togglePanel = (flag: boolean) => {
	isActive.value = flag;
};

interface LoginData {
	username: string;
	password: string;
}

interface RegisterData {
	username: string;
	email: string;
	verificationCode: string;
	password: string;
}

const loginForm = reactive<LoginData>({
	username: '',
	password: '',
});
const registerForm = reactive<RegisterData>({
	username: '',
	email: '',
	verificationCode: '',
	password: '',
});

const emailReg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
const text = ref('发送验证码');
const getCode = () => {
	if (!emailReg.test(registerForm.email)) {
		Message({
			text: '邮箱格式不正确',
			type: 'error',
		});
		return;
	}
	text.value = '发送中';
	axios.get(`http://localhost:8118/cloud/mail/register?email=${registerForm.email}`)
		.then((res) => {
			if (res.data.code === 200) {
				Message({
					text: res.data.msg,
					type: 'success',
				});
			} else if (res.data.code === 507) {
				Message({
					text: res.data.msg,
					type: "error",
				})
				clearInterval(timer);
				text.value = '发送验证码';
				return;
			} else {
				let data = res.data.data != null ? res.data.data : "";
				Message({
					text: res.data.msg + "\r\n" + data,
					type: "error",
				})
			}
		})
	let time = 60;
	const timer = setInterval(() => {
		time--;
		text.value = `${time}s后重新发送`;
		if (time === 0) {
			clearInterval(timer);
			text.value = '发送验证码';
		}
	}, 1000);
};

const register = () => {
	let formData = new FormData();
	formData.append('username', registerForm.username);
	formData.append('email', registerForm.email);
	formData.append('verificationCode', registerForm.verificationCode);
	formData.append("password", registerForm.password);

	axios
		.post("http://localhost:8118/cloud/auth/register", formData, {
			headers: {
				'Content-Type': 'multipart/form-data'
			}
		})
		.then((res) => {
			if (res.data.code === 200) {
				Message({
					text: "注册成功",
					type: "success",
				})
				console.log(res.data.data);
				localStorage.setItem("EinziegCloud_USER", res.data.data.token);
				let returnUrl = route.query.returnUrl || '/home';
				router.replace({path: returnUrl + ""})
				// userStore.setToken(res.data.data.token);
			} else {
				let data = res.data.data != null ? res.data.data : "";
				Message({
					text: res.data.msg + "\r\n" + data,
					type: "warning",
				})
			}
		});
};
const login = () => {
	let formData = new FormData();
	formData.append('username', loginForm.username);
	formData.append('password', loginForm.password);

	axios
		.post("http://localhost:8118/cloud/auth/login", formData, {
			headers: {
				'Content-Type': 'multipart/form-data'
			}
		})
		.then((res) => {
			if (res.data.code === 200) {
				Message({
					text: "登录成功",
					type: "success",
				})
				localStorage.setItem("EinziegCloud_USER", res.data.data.token);
				let returnUrl = route.query.returnUrl || '/home';
				router.replace({path: returnUrl + ""})
			} else {
				let data = res.data.data != null ? res.data.data : "";
				Message({
					text: res.data.msg + "\r\n" + data,
					type: "error",
				})
			}
		});
};
</script>


<style scoped>
* {
	margin: 0;
	padding: 0;
}

.bg {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	overflow-y: auto;
	background-color: red;
}

.bg {
	height: 100vh;
	background: #e7e7e7 url("../assets/images/clouds.jpg") center no-repeat fixed;
	background-size: cover;
	backdrop-filter: blur(5px);
}

.bg {
	display: flex;
	justify-content: center;
	align-items: center;
}

.container {
	background-color: #e7e7e7;
	border-radius: 0.7rem;
	box-shadow: 0 0.9rem 1.7rem rgba(0, 0, 0, 0.25), 0 0.7rem 0.7rem rgba(0, 0, 0, 0.22);
	height: 420px;
	max-width: 750px;
	overflow: hidden;
	position: relative;
	width: 100%;
}

.container-form {
	height: 100%;
	position: absolute;
	top: 0;
	transition: all 0.6s ease-in-out;
}

.container-signin {
	left: 0;
	width: 50%;
	z-index: 2;
}

.container-signup {
	left: 0;
	opacity: 0;
	width: 50%;
	z-index: 1;
}

.form {
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
	padding: 0 3rem;
	height: 100%;
	text-align: center;
	background-color: #e7e7e7;
}

.form-title {
	font-weight: 300;
	margin: 0 0 1.25rem;
}

.link {
	color: #333;
	font-size: 0.9rem;
	margin: 1.5rem 0;
	text-decoration: none;
}

.input {
	width: 100%;
	background-color: #fff;
	padding: 0.9rem 0.9rem;
	margin: 0.5rem 0;
	border-radius: 15px;
	border: none;
	outline: none;
}

.code-btn {
	width: 77%;
	background-color: #f25d8e;
	box-shadow: 0 4px 4px rgba(255, 112, 159, .3);
	border-radius: 32px;
	color: #e7e7e7;
	border: none;
	cursor: pointer;
	font-size: 0.9rem;
	letter-spacing: 0.1rem;
	text-transform: uppercase;
	padding: 0.9rem 0;
	transition: transform 80ms ease-in;

	margin-right: -11%;
}

.code-btn:active {
	transform: scale(0.95);
}

.button {
	background-color: #f25d8e;
	box-shadow: 0 4px 4px rgba(255, 112, 159, .3);
	border-radius: 32px;
	color: #e7e7e7;
	border: none;
	cursor: pointer;
	font-size: 0.9rem;
	letter-spacing: 0.1rem;
	padding: 0.9rem 4rem;
	text-transform: uppercase;
	transition: transform 80ms ease-in;
}

.form > .button {
	margin-top: 1.5rem;
}

.button:active {
	transform: scale(0.95);
}

.container-overlay {
	height: 100%;
	left: 50%;
	overflow: hidden;
	position: absolute;
	top: 0;
	transition: transform 0.6s ease-in-out;
	width: 50%;
	z-index: 100;
}

.overlay {
	width: 200%;
	height: 100%;
	position: relative;
	left: -100%;
	background: url("../assets/images/australia.jpg") no-repeat center fixed;
	background-size: cover;
	transition: transform 0.6s ease-in-out;
	transform: translateX(0);
}

.overlay-panel {
	height: 100%;
	width: 50%;
	position: absolute;
	top: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
	transform: translateX(0);
	transition: transform 0.6s ease-in-out;
}

.overlay-left {
	transform: translateX(-20%);
}

.overlay-right {
	right: 0;
	transform: translateX(0);
}

.panel-active .overlay-left {
	transform: translateX(0);
}

.panel-active .container-overlay {
	transform: translateX(-100%);
}

.panel-active .overlay {
	transform: translateX(50%);
}

.panel-active .container-signin {
	transform: translateX(100%);
}

.panel-active .container-signup {
	opacity: 1;
	z-index: 5;
	transform: translateX(100%);
}
</style>
