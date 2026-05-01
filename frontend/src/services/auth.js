import api from './api'

export async function login(email, password) {
  const res = await api.post('/auth/login', { email, password })
  const token = res.data.token
  localStorage.setItem('token', token)
  return token
}

export async function register(name, email, password) {
  const res = await api.post('/auth/register', { name, email, password })
  const token = res.data.token
  localStorage.setItem('token', token)
  return token
}

export function logout() {
  localStorage.removeItem('token')
}

export function getToken() {
  return localStorage.getItem('token')
}
