<script lang="ts" setup>
import {Notivue, Notifications} from 'notivue'
</script>
<template>
	<router-view/>
	<Notivue v-slot="item">
		<Notifications :item="item"/>
	</Notivue>
</template>

<style>
:root {
	--dur-easing: 300ms cubic-bezier(0.25, 0.46, 0.45, 0.94);
}

.pop-in {
	animation: pop-in-kf var(--dur-easing);
}

.pop-out {
	animation: pop-out-kf var(--dur-easing);
}

.fade {
	animation: fade-kf 500ms ease-out;
}

@keyframes pop-in-kf {
	0% {
		transform: scale(0.2);
		opacity: 0;
	}

	100% {
		transform: scale(1);
		opacity: 1;
	}
}

@keyframes pop-out-kf {
	0% {
		transform: scale(1);
		opacity: 0.5;
	}

	100% {
		transform: scale(1.05);
		opacity: 0;
	}
}

@keyframes fade-kf {
	0% {
		opacity: 1;
	}

	100% {
		opacity: 0;
	}
}
</style>
