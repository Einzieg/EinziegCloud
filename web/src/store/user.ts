import {defineStore} from "pinia";

export const useUserStore = defineStore({
	id: "user",
	state: () => {
		return {
			token: "",
		};
	},
	actions: {
		setToken(token) {
			this.token = token;
		},
	},
	persist: {
		enabled: true,
		strategies: [
			{
				key: "EinziegCloud_user",
				storage: localStorage,
				// paths: ["token"],
			},
		],
	},
});
