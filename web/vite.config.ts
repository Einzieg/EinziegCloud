import {defineConfig} from "vite";
import vue from "@vitejs/plugin-vue";
import AutoImport from "unplugin-auto-import/vite";

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [
		vue(),
		AutoImport({
			dts: true,
			imports: ["vue", "vue-router"],
		}),
	],
	optimizeDeps: {
		include: ["axios"],
	},
	server: {
		port: 8019,
		https: false,
		proxy: {
			"/cloud": {
				target: "http://127.0.0.1:8118/",
				changeOrigin: true,
				// rewrite: (path) => path.replace(/^\/cloud/, ""),
			},
		},
	},
});
