<template>
	<div class="body">
		<section id="background" class="background">
			<svg class="moon" viewBox="0 0 128 128" width="190" xmlns="http://www.w3.org/2000/svg" @click="home">
				<path d="M64 29A41.5 42 0 1 0 64 113A41.5 42 0 1 0 64 29Z" fill="#FCF7CB"/>
				<path d="M64,29.5c-2.2,0-4.4,0.2-6.5,0.5c19.9,3.2,35,20.5,35,41.5c0,21-15.2,38.3-35,41.5c2.1,0.3,4.3,0.5,6.5,0.5c22.9,0,41.5-18.8,41.5-42C105.5,48.3,86.9,29.5,64,29.5z"
				      fill="#D8D5B2"/>
				<path d="M64,116.5c-24.6,0-44.5-20.2-44.5-45s20-45,44.5-45s44.5,20.2,44.5,45S88.6,116.5,64,116.5z M64,32.5c-21.3,0-38.5,17.5-38.5,39s17.3,39,38.5,39s38.5-17.5,38.5-39S85.3,32.5,64,32.5z"
				      fill="#454B54"/>
				<path d="M56 71.6A7.5 7.5 0 1 0 56 86.6 7.5 7.5 0 1 0 56 71.6zM61 43.5A4 4 0 1 0 61 51.5 4 4 0 1 0 61 43.5zM91 61.5A6 6 0 1 0 91 73.5 6 6 0 1 0 91 61.5z"
				      fill="#D8D5B2"/>
			</svg>
		</section>
		<img v-for="cloud in clouds"
		     :key="cloud.id"
		     :src="cloud.src"
		     :style="{
			 	top: cloud.top + 'px',
			 	right: cloud.right + 'px',
			 	width: cloud.width + 'px',
			 	zIndex: cloud.zIndex
			 }"
		     class="Cloud"/>
		<p
			v-for="(star, index) in stars"
			:key="index"
			:style="{
		   	top: star.top + 'px',
		   	left: star.left + 'px',
		   	fontSize: star.fontSize + 'px',
		   	color: star.color,
		   	textShadow: star.textShadow
		   }"
			class="Star">
			{{ star.symbol }}
		</p>
		<section class="T404">
			<h1>4</h1>
			<h1 class="X">0</h1>
			<h1>4</h1>
		</section>
	</div>
</template>

<script lang="js" setup>
import {useRouter} from "vue-router";

let clouds = reactive([])
let stars = reactive([])
let starsSymbols = reactive(["★", "☆", "⚝", "✩", "✮", "✵", "✹", "⭑", "🟉", "🟊", "🟌", "🟒"])
let starsColors = reactive(["#ffffff", "#ff0000", "#ff8c00", "#ffff00", "#00ff00", "#0000ff", "#ff00ff"])
let cloud_len = window.innerWidth >= 768 ? 16 : 8;
const router = useRouter();

onMounted(() => {

	let background = document.getElementById("background");

	function CreateCloud(length) {
		for (let i = 0; i < length; i++) {
			clouds.push({
				src: `public/assets/${randomPosition(1, 5)}.png`,
				top: randomPosition(-10, background.offsetHeight),
				right: -randomPosition(100, 1000),
				width: randomPosition(100, 600),
				zIndex: randomPosition(1, 2),
			});
			sleep(1000);
		}
	}

	function sleep(ms) {
		return new Promise(resolve => setTimeout(resolve, ms))
	}

	function Cloud_Animation() {
		for (let i = 0; i < clouds.length; i++) {
			clouds[i].right += 15;
			if (clouds[i].right > 1600) {
				clouds.splice(i, 1);
				CreateCloud(1);
			}
		}
	}

	CreateCloud(cloud_len);
	setInterval(Cloud_Animation, 70);

	function CreateStar(length) {
		for (let i = 0; i < length; i++) {
			stars.push({
				symbol: starsSymbols[randomPosition(0, starsSymbols.length - 1)],
				top: randomPosition(-10, background.offsetHeight),
				left: randomPosition(0, background.offsetWidth),
				fontSize: randomPosition(1, 10),
				color: starsColors[randomPosition(0, starsColors.length - 1)],
				textShadow: `${starsColors[randomPosition(0, starsColors.length - 1)]} 0 0 ${randomPosition(3, 20)}px`,
			});
		}
	}

	CreateStar(60);

	function randomPosition(min, max) {
		return Math.floor(Math.random() * (max - (min) + 1)) + (min);
	}

})

const home = () => {
	router.push({path: '/'})
}
</script>

<style lang="css" scoped>
* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}

.body {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	font-size: 16px;
	font-family: Poppins, sans-serif;
	overflow: hidden !important;
	background: #0f0f0f;
	display: flex;
	justify-content: center;
	align-items: center;
}

.background {
	width: 100%;
	height: 100%;
	position: absolute;
}

.moon {
	z-index: 2;
}

.Cloud {
	position: absolute;
	user-select: none;
	filter: brightness(30%);
}

.Star {
	position: absolute;
	user-select: none;
}

.T404 {
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 10em;
	color: #fafafa;
	text-shadow: #fafafa 0 0 10px;
	user-select: none;
}

h1 {
	z-index: 1;
	-webkit-animation: fly 5s linear infinite;
	-o-animation: fly 5s linear infinite;
	animation: fly 5s linear infinite;
}

.X {
	margin: 0 2rem;
}

h1:nth-child(2) {
	animation-duration: 7s;
}

h1:nth-child(3) {
	animation-duration: 9s;
}

@keyframes fly {

	0%,
	100% {
		transform: translateY(0);
	}

	50% {
		transform: translateY(60px);
	}
}

@media (min-width: 1024px) {
	.T404 {
		font-size: 20vw;
	}
}

@media (max-width: 768px) {
	.T404 {
		font-size: 7em;
	}
}

@media (max-width: 412px) {
	.T404 {
		font-size: 6.5em;
	}
}

@media (max-width: 300px) {
	.T404 {
		font-size: 4.5em;
	}
}
</style>