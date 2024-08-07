// AuthService.ts
import Post from '../API/Post'
import { LoginResponse } from '../Interfaces/LoginResponse'

export async function login (info: { identifier: string; password: string }) {
  try {
    console.log("funcionax2")
    const result = await Post<LoginResponse>('/auth/login', info)
    return result?.username // Devuelve el nombre de usuario
  } catch (e) {
    throw new Error('Could not log in correctly')
  }
}

export async function register(values:IRegister){
  try{
    const result = await Post<any>('/auth/createaccount', values);
    return result // Devuelve el nombre de usuario
  }catch(error){
    console.log("error trying to create a new account: " + error );
    console.log(values);
    throw new Error();
  }
}