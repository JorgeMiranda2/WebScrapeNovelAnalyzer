import axios, { AxiosInstance } from 'axios'
import apiConfig from '../../../../variables.mjs'

const api : AxiosInstance = axios.create({
  baseURL: `${apiConfig.apiBaseUrl}`,
  withCredentials: true // Esto asegura que las cookies se incluyan en cada solicitud

})

export default api
