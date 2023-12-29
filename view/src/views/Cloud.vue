<template>
	<div class="body">
		<Menu/>
		<div class="cloud">
			<h1>Welcome to the Cloud page</h1>
		</div>
	</div>
</template>

<script setup>
import Menu from "../components/Menu.vue";
</script>

<style scoped>
.body {
	display: flex;
}

.cloud {
	margin-left: 100px;
}
</style>