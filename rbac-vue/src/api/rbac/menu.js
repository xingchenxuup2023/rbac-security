import request from '@/utils/request'

export function menuList() {
  return request({
    url: '/menu/route',
    method: 'get'
  })
}
