import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router";
import Home from "../views/Home.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/home",
  },
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
    meta: {requiredAuth: true},
  },
  {
    path: "/setting",
    name: "setting",
    component: () => import("../views/Setting.vue"),
    meta: {requiredAuth: true},
  },
  {
    path: "/login",
    name: "login",
    component: () => import("../views/login.vue"),
    meta: {requiredAuth: false},
  },
  {
    path: "/forget",
    name: "forget",
    component: () => import("../views/ForgetPassword.vue"),
    meta: {requiredAuth: false},
  },
  {
    path: "/404",
    name: "404",
    component: () => import("../views/error/404.vue"),
    meta: {requiredAuth: false},
  },
  {
    path: "/menu",
    name: "menu",
    component: () => import("../components/Menu.vue"),
    meta: {requiredAuth: false},
  },
  {
    path: "/night",
    name: "night",
    component: () => import("../components/NightAndDayAnimation.vue"),
    meta: {requiredAuth: false},
  },
  {
    path: "/cloud",
    name: "cloud",
    component: () => import("../views/Cloud.vue"),
    meta: {requiredAuth: true},
  },

  {
    path: "/:pathMatch(.*)*",
    redirect: "/404",
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to) => {
  if (to.meta.requiredAuth && !isAuthorization()) {
    return {name: "login", query: {return: to.path}};
  }
  return true;
});

function isAuthorization() {
  let USER = localStorage.getItem("EinziegCloud_Token");
  return !!USER;
}

export default router;
