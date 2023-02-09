import axios from 'axios'
import {Message} from 'element-ui'
import store from '@/store'
import {getAccessToken} from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.accessToken) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['X-Token'] = getAccessToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    // if the custom code is not 000000, it is judged as an error.
    if (res.code !== "000000") {
      Message({
        message: res.msg || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      if (res.code === '999997') {
        // R_TOKEN_EXPIRES
        // to re-login
        return new Promise(() => {
          store.dispatch('user/logout').then(() => {
            location.reload()
          })
        })

      }
      if (res.code === '999998') {
        // A_TOKEN_EXPIRES
        // 发送请求刷新token
        const refreshToken = store.getters.refreshToken;
        return service({
          url: '/token/refresh',
          method: 'get',
          params: {refreshToken}
        }).then(async refreshResponse => {
          // 获取到新的token
          let tokenInfo = refreshResponse.data
          await store.dispatch("user/refreshAccessToken", tokenInfo)
          // 在请求头中设置新的token
          response.config.headers['X-Token'] = tokenInfo.accessToken
          // 重新发送请求
          return axios(response.config)
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
