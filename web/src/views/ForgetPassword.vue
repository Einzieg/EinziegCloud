<template>
	<h1>ForgerPassword</h1>
	<button @click="getData">点击</button>
	{{ msg }}
</template>

<script setup lang="ts">
import axios from 'axios';
// import Message from "../components/Message";

let msg = ref("123");

const getData = () => {
	// axios
	// 	.get("http://localhost:8118/cloud/user/hello")
	// 	.then((res) => {
	// 		if (res.data.code === 200) {
	// 			Message({
	// 				text: res.data.msg,
	// 				type: "success",
	// 			})
	// 			msg.value = res.data.data;
	// 		} else if (res.data.code === 403) {
	// 			Message({
	// 				text: res.data.msg,
	// 				type: "error",
	// 			})
	// 			msg.value = res.data.msg;
	// 		} else if (res.data.code === 429) {
	// 			Message({
	// 				text: res.data.msg,
	// 				type: "error",
	// 			})
	// 			msg.value = res.data.msg;
	// 		}
	// 		console.log(res);
	// 	});
	axios
		.get("/cloud/user/hello")
		.then((res) => {
			console.log(res);
		});
};


</script>


<style lang="less" scoped></style>
