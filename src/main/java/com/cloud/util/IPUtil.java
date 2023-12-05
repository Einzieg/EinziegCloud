package com.cloud.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Ip地址工具类
 * @Author Einzieg
 * @date 2023-08-10 15:000
 */
@Slf4j
public class IPUtil {
	private static final String IP_UTILS_FLAG = ",";
	private static final String UNKNOWN = "unknown";
	private static final String LOCALHOST_IP = "0:0:0:0:0:0:0:1";
	private static final String LOCALHOST_IP1 = "127.0.0.1";

	/**
	 * 获取IP地址
	 * <p>
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr() {
		HttpServletRequest request = HttpUtil.getHttpServletRequest();
		String ip = null;
		try {
			//以下两个获取在k8s中，将真实的客户端IP，放到了x-Original-Forwarded-For。而将WAF的回源地址放到了 x-Forwarded-For了。
			assert request != null;
			ip = request.getHeader("X-Original-Forwarded-For");
			if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("X-Forwarded-For");
			}
			//获取nginx等代理的ip
			if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("x-forwarded-for");
			}
			if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (!StringUtils.hasText(ip) || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			//兼容k8s集群获取ip
			if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				if (LOCALHOST_IP1.equalsIgnoreCase(ip) || LOCALHOST_IP.equalsIgnoreCase(ip)) {
					//根据网卡取本机配置的IP
					InetAddress iNet = null;
					try {
						iNet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						log.error("获取客户端IP错误:", e);
					}
					ip = iNet != null ? iNet.getHostAddress() : null;
				}
			}
		} catch (Exception e) {
			log.error("IPUtil ERROR ", e);
		}
		//使用代理，则获取第一个IP地址
		if (StringUtils.hasText(ip) && ip.indexOf(IP_UTILS_FLAG) > 0) {
			ip = ip.substring(0, ip.indexOf(IP_UTILS_FLAG));
		}

		return ip;
	}

}
