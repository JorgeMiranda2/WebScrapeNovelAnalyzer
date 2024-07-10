// AuthService.ts
import Post from '../API/Post'
import { LoginResponse } from '../Interfaces/LoginResponse'

export async function login (info: { identifier: string; password: string }) {
  try {
    const result = await Post<LoginResponse>('/auth/login', info)
    return result?.username // Devuelve el nombre de usuario
  } catch (e) {
    throw new Error('Could not log in correctly')
  }
}
