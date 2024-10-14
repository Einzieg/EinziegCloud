import {defineStore} from "pinia";

export const useStore = defineStore("EinziegCloud_Token", {
    persist: true,
    state: () => {
        return {
            token: null,
            name: null,
            email: null,
        };
    },
    actions: {
        //   //  缓存用户权限
        //   permissionAction(payload) {
        //     this.permission = payload
        //   },
        //   //  缓存用户token

        loginAction(payload: any) {
            this.name = payload.name;
            this.email = payload.email;
            this.token = payload.token;
        },
        logoutAction() {
            this.name = null;
            this.email = null;
            this.token = null;
        },
        //   //  清空所有tabs
        //   clearTabAction() {
        //     this.openTabs = []
        //   }
    },
});
