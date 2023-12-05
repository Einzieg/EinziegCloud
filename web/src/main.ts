import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import "./assets/fonts/font.css";
import Msg from "./components";

createApp(App).use(router).use(Msg).mount("#app");
