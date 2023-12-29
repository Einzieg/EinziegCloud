<template>
	<div class="body">
		<Menu/>
		<div class="set">
			<H1>Welcome Setting</H1>
		</div>
		<div class="sun-moon">
			<NADA/>
		</div>

	</div>
</template>

<script setup>
import Menu from "../components/Menu.vue";
import NADA from "../components/NightAndDayAnimation.vue";
</script>

<style scoped>
.body {
	display: flex;
}

.sun-moon {
	margin-left: auto;
	margin-right: 12px;
}

.set {
	margin-left: 100px;
}
</style>