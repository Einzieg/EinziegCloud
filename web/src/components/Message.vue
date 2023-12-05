<template>
	<Transition name="down">
		<div v-show="visible" :style="style[type]" class="message">
			<!--<i class="ui-icon" :class="[style[type].icon]"></i>-->
			<span class="text">{{ text }}</span>
		</div>
	</Transition>
</template>

<script lang="ts" setup>
import {onMounted, ref} from 'vue';

const name = 'Message';
defineProps({
	text: {
		type: String,
		default: ''
	},
	type: {
		type: String,
		// warn 警告  error 错误  success 成功
		default: 'warn'
	}
});
const style = {
	warn: {
		icon: 'icon-warning',
		background: '#fffbe6',
		color: '#e6a23c'
	},
	error: {
		icon: 'icon-error',
		background: '#fef0f0',
		color: '#f56c6c'
	},
	success: {
		icon: 'icon-success',
		background: '#f0f9eb',
		color: '#67c23a'
	},
	message: {
		icon: 'icon-message',
		background: '#eef2f9',
		color: '#909399'
	}
};

const visible = ref(false);

onMounted(() => { // 需调用钩子函数，等dom渲染完成后，再进行赋值，如果在setup中直接赋值，则会被直接渲染成true
	visible.value = true
})
</script>

<style lang="less" scoped>
.down {
	&-enter {
		&-from {
			transform: translate3d(0, -75px, 0);
			opacity: 0;
		}

		&-active {
			transition: all 0.5s;
		}

		&-to {
			transform: none;
			opacity: 1;
		}
	}
}

.message {
	width: auto;
	min-width: 100px;
	height: 50px;
	position: fixed;
	z-index: 9999;
	left: 50%;
	margin-left: -150px;
	top: 25px;
	line-height: 50px;
	padding: 0 25px;
	border: 1px solid #e4e4e4;
	background: #f5f5f5;
	color: #999;
	border-radius: 30px;
	text-align: center;

	i {
		margin-right: 4px;
		vertical-align: middle;
	}

	.text {
		vertical-align: middle;
	}
}
</style>
