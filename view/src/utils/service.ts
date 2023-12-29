import axios, {type AxiosInstance, type AxiosRequestConfig} from "axios";
import {push} from "../main";
import router from "../router";
import {useStore} from "../store";

/** 退出登录并强制刷新页面（会重定向到登录页） */
// function logout() {
//   useUserStoreHook().logout();
//   location.reload();
// }

/** 创建请求实例 */
function createService() {
    // 创建一个 axios 实例命名为 service
    const service = axios.create();
    // 请求拦截
    service.interceptors.request.use(
        (config) => config,
        (error) => Promise.reject(error)
    );
    // 响应拦截
    service.interceptors.response.use(
        (response) => {
            const data = response.data;
            const responseType = response.request?.responseType;
            if (responseType === "blob" || responseType === "arraybuffer") {
                return data;
            }
            switch (data.code) {
                case 200:
                    push.success({
                        message: data.msg || "",
                    });
                    return data;
                case 403:
                    push.error({title: data.msg || "", message: data.data || ""});
                    useStore().logoutAction();
                    router.push("/login");
                default:
                    push.error({title: data.msg || "", message: data.data || ""});
                    return Promise.reject(new Error("Error"));
            }
        },
        (error) => {
            console.log(error);
            // status 是 HTTP 状态码
            switch (error.response.status) {
                case 400:
                    error.message = "请求错误";
                    break;
                case 403:
                    router.push("/login");
                    error.message = "拒绝访问/认证过期/账号封禁";
                    useStore().logoutAction();
                    break;
                case 404:
                    router.push("/404");
                    error.message = "请求地址出错";
                    break;
                case 408:
                    error.message = "请求超时";
                    break;
                case 500:
                    error.message = "服务器内部错误";
                    break;
                case 501:
                    error.message = "服务未实现";
                    break;
                case 502:
                    error.message = "网关错误";
                    break;
                case 503:
                    error.message = "服务不可用";
                    break;
                case 504:
                    error.message = "网关超时";
                    break;
                case 505:
                    error.message = "HTTP 版本不受支持";
                    break;
                default:
                    break;
            }
            push.error(error.message);
            return Promise.reject(error);
        }
    );
    return service;
}

/** 创建请求方法 */
function createRequest(service: AxiosInstance) {
    return function <T>(config: AxiosRequestConfig): Promise<T> {
        const token = useStore().$state.token;
        const defaultConfig = {
            headers: {
                // 携带 Token
                Authorization: token ? `Bearer ${token}` : undefined,
                "Content-Type": "application/json",
            },
            timeout: 5000,
            baseURL: import.meta.env.VITE_BASE_API,
            data: {},
        };
        return service
            .request<T>(Object.assign(defaultConfig, config))
            .then((res) => res.data);
    };
}

/** 用于网络请求的实例 */
const service = createService();
/** 用于网络请求的方法 */
export const request = createRequest(service);
