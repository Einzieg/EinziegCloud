import {defineStore} from "pinia";

export const useStore = defineStore('cloud', {
        persist: true,
        state: () => {
            return {
                token: null,
                refreshToken: null,//  刷新token
                userInfo: null,//  当前登录用户信息
                isLoadMenu: false,//  是否已拉取用户菜单
                routers: null, //  所有菜单（系统+用户动态授权）
                permission: null,//  权限列表
                activeIndex: '页',//  当前激活菜单
                openTabs: []//  所有已打开的菜单
            }
        },
        // actions: {
        //   //  是否拉取用户菜单
        //   loadMenuAction(payload) {
        //     this.isLoadMenu = payload
        //   },
        //   //  缓存用户菜单列表
        //   routerAction(payload) {
        //     this.routers = payload
        //   },
        //   //  缓存用户权限
        //   permissionAction(payload) {
        //     this.permission = payload
        //   },
        //   //  缓存用户token
        //   tokenAction(payload) {
        //     this.token = payload
        //   },
        //   //  当前激活菜单
        //   activeIndexAction(payload) {
        //     this.activeIndex = payload
        //   },
        //   //  添加已打开的菜单
        //   addTabAction(payload) {
        //     //  如果不存在才添加
        //     if (this.openTabs.filter(tab => tab.name === payload.name).length === 0) {
        //       payload.isClose = payload.name !== '首页'
        //       this.openTabs.push(payload)
        //     }
        //   },
        //   //  关闭已打开的菜单
        //   removeTabAction(payload) {
        //     this.openTabs = this.openTabs.filter((tab) => tab.name !== payload)
        //   },
        //   //  清空所有tabs
        //   clearTabAction() {
        //     this.openTabs = []
        //   }
        // }
    }
)