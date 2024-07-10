import GetInitialConfig from '../Utils/GetInitialConfig'
import apiConfig from '../../../../variables.mjs'

export default async function Post<T> (endpoint: string, info: any): Promise<T | null> {
  const config: any = GetInitialConfig('POST')

  const fullEndpoint = `${apiConfig.apiBaseUrl}${endpoint}`

  try {
    const response = await fetch(fullEndpoint, {
      ...config,
      body: JSON.stringify(info) // Se añade el cuerpo a la configuración
    })

    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`)
    }

    const data = await response.json()

    console.log('Doing Post request in route: ' + fullEndpoint)
    return data
  } catch (error) {
    console.log('Error in the Post Api:' + error)
    console.log('Route: ' + fullEndpoint)
    throw new Error('Request failed')
  }
}
