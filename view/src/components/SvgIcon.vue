<template>
	<svg aria-hidden="true" class="svg-icon">
		<use :fill="color" :xlink:href="symbolId"/>
	</svg>
</template>

<script lang="ts" setup>

const props = defineProps({
	prefix: {
		type: String,
		default: 'icon',
	},
	iconClass: {
		type: String,
		required: false,
	},
	color: {
		type: String,
		default: '',
	},
});

const symbolId = computed(() => `#${props.iconClass}`);
</script>

<style scoped>
.svg-icon {
	fill: currentColor;
	aspect-ratio: 1 / 1;
}
</style>