<template>
	<h1>ForgerPassword</h1>
	<button @click="getData">点击</button>
	{{ msg }}
</template>

<script setup>
import {push} from "../main";
import {request} from "../utils/service";

let msg = ref("123");

const getData = () => {
	request({
		url: "user/hello",
		method: "get",
	}).then((res) => {
		push.success("成功");
		msg.value = res.data;
	});

};
</script>

<style scoped></style>
