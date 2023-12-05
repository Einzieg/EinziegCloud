import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";
import Home from "../views/Home.vue";

const routes: Array<RouteRecordRaw> = [
	{
		path: "/",
		name: "home",
		component: Home,
		meta: {requiredAuth: false},
	},
	{
		path: "/home",
		name: "home",
		component: Home,
		meta: {requiredAuth: false},
	},
	{
		path: "/login",
		name: "login",
		component: () => import("../views/Login.vue"),
		meta: {requiredAuth: false},
	},
	{
		path: "/forget",
		name: "forget",
		component: () => import("../views/ForgetPassword.vue"),
		meta: {requiredAuth: false},
	},
];

const router = createRouter({
	history: createWebHistory(),
	routes,
});

router.beforeEach((to, from) => {
	if (to.name !== "login" && to.meta.requiredAuth && !isAuthorizated()) {
		return {name: "login", query: {return: to.path}};
	}
	return true;
});

function isAuthorizated() {
	let USER = localStorage.getItem("EinziegCloud_USER");
	return !!USER;
}

export default router;
