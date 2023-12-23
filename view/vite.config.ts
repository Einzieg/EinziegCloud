import {defineConfig} from "vite";
import vue from "@vitejs/plugin-vue";
import VueDevTools from "vite-plugin-vue-devtools";
import AutoImport from "unplugin-auto-import/vite";
import {createSvgIconsPlugin} from "vite-plugin-svg-icons";
import path from "path";

export default defineConfig({
    plugins: [
        vue(),
        VueDevTools(),
        createSvgIconsPlugin({
            iconDirs: [path.resolve(process.cwd(), "src/assets/icons")],
            symbolId: "[name]",
        }),
        AutoImport({
            include: [
                /\.[tj]sx?$/, // .ts, .tsx, .js, .jsx
                /\.vue$/,
                /\.vue\?vue/, // .vue
                /\.md$/, // .md
            ],
            imports: [
                "vue",
                "vue-router",
                {
                    axios: [
                        ["default", "axios"], // import { default as axios } from 'axios',
                    ],
                },
            ],

            dts: "./auto-imports.d.ts",
            // eslint报错解决
            eslintrc: {
                enabled: false, // Default `false`
                filepath: "./.eslintrc-auto-import.json", // Default `./.eslintrc-auto-import.json`
                globalsPropValue: true, // Default `true`, (true | false | 'readonly' | 'readable' | 'writable' | 'writeable')
            },
        }),
        // 这能解决 Vite HMR 仅在开发模式下发生的问题
        {
            name: "SingleHMR",
            handleHotUpdate({modules}) {
                modules.map((m) => {
                    m.importers = new Set();
                });

                return modules;
            },
        },
    ],
    optimizeDeps: {
        include: ["axios"],
    },
    server: {
        port: 8019,
        proxy: {
            "/cloud/": {
                target: "http://127.0.0.1:8118/",
                changeOrigin: true,
            },
        },
    },
});
