import {constantRoutes} from '@/router'
import {treeDataTranslate} from '@/utils'
import {menuList} from '@/api/rbac/menu'
import Layout from '@/layout'
import store from '@/store'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = {...route}
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({commit}) {
    return new Promise((resolve, reject) => {
      menuList().then(response => {
        const {data} = response
        for (const menu of data) {
          if (menu.component === 'Layout') {
            menu.component = Layout
          } else {
            menu.component = loadView(menu.component)
          }
        }
        const accessedRoutes = treeDataTranslate(data)
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      }).catch(error => {
        console.log(error)
        reject(error)
      })
    })
  }
}

export const loadView = (view) => { // 路由懒加载
  return () => Promise.resolve(require(`@/views/${view}`).default)
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
