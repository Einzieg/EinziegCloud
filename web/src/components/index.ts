import Message from './Message'

export default {
	install(app) {
		app.config.globalProperties.$message = Message
	}
}
