<template>
	<div class="body">
		<ul>
			<li>
				<a href="#">
					<SvgIcon icon-class="home"/>
					<span>主页</span>
				</a>
			</li>
			<li>
				<a href="#">
					<SvgIcon icon-class="+"/>
					<span>+</span>
				</a>
			</li>
			<li>
				<a href="#">
					<SvgIcon icon-class="+"/>
					<span>+1</span>
				</a>
			</li>
			<li>
				<a href="#">
					<SvgIcon icon-class="+"/>
					<span>+</span>
				</a>
			</li>

			<li>
				<a href="#">
					<SvgIcon icon-class="+"/>
					<span>+</span>
				</a>
			</li>
			<li>
				<a href="#">
					<SvgIcon icon-class="cloud"/>
					<span>云</span>
				</a>
			</li>


			<li>
				<a href="#">
					<SvgIcon icon-class="PersonCircle24Regular"/>
					<span>个人中心</span>
				</a>
			</li>
			<li>
				<a href="#">
					<SvgIcon icon-class="mail"/>
					<span>消息</span>
				</a>
			</li>
			<li>
				<a href="#">
					<SvgIcon icon-class="setting"/>
					<span>设置</span>
				</a>
			</li>

		</ul>
	</div>
</template>

<script setup>
import SvgIcon from "./SvgIcon.vue";
</script>

<style scoped>
* {
	margin: 0;
	padding: 0;
}

/* 初始化样式+页面居中 */
.body {
	margin-left: 12px;
	height: 100vh;
	display: flex;
	align-items: center;
}

/* 导航整体样式 */
ul {
	list-style: none;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	background-color: rgb(255, 255, 255);
	height: 620px;
	padding: 40px 12px;
	border-radius: 24px;
	/* 阴影 */
	box-shadow: 0 10px 50px rgba(137, 137, 148, 0.25);
}

/* 分割线 */
ul li:nth-child(7) {
	margin-top: 20px;
	padding-top: 20px;
	border-top: 1px solid #979d9e;
}

ul a {
	position: relative;
	color: #000000;
	text-decoration: none;
	display: flex;
	justify-content: center;
	align-items: center;
	width: 36px;
	height: 36px;
	/* 圆角 */
	border-radius: 26px;
}

/* 导航文本 */
ul a span {
	position: absolute;
	left: calc(100% + 24px);
	background-color: #fafafa;
	white-space: nowrap;
	padding: 8px 16px;
	border-radius: 6px;
	transform-origin: center left;
	transition: 0.15s ease;
	box-shadow: 0 10px 50px rgba(137, 137, 148, 0.25);
}

ul a span::before {
	content: "";
	width: 12px;
	height: 12px;
	background-color: rgb(82, 182, 196);
	position: absolute;
	left: -5px;
	top: 50%;
	border-radius: 3px;
	transform: translateY(-50%) rotate(45deg);
}

/* 鼠标切换效果 */
ul a span {
	opacity: 0;
}

ul a:hover,
ul a:focus,
ul a:active {
	background-color: #fafafa;
	outline: none;
}

ul a:hover span,
ul a:focus span,
ul a:active span {
	opacity: 1;
	transform: scale(1);
}
</style>