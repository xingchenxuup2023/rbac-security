import Cookies from 'js-cookie'

const AccessTokenKey = 'accessToken'
const RefreshTokenKey = 'refreshToken'

export function getAccessToken() {
  return Cookies.get(AccessTokenKey)
}

export function getRefreshToken() {
  return Cookies.get(RefreshTokenKey)
}

export function setAccessToken(token) {
  return Cookies.set(AccessTokenKey, token)
}

export function setRefreshToken(token) {
  return Cookies.set(RefreshTokenKey, token)
}

export function refreshToken(token) {
  return Cookies.remove(AccessTokenKey) & setAccessToken(token)
}

export function removeToken() {
  return Cookies.remove(AccessTokenKey) & Cookies.remove(RefreshTokenKey)
}
