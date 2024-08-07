import GetInitialConfig from '../Utils/GetInitialConfig'
import apiConfig from '../../../../variables.mjs'

export default async function Get<T> (endpoint: string): Promise<T | null> {
  const config: any = GetInitialConfig('GET')

  const fullEndpoint = `${apiConfig.apiBaseUrl}${endpoint}`

  try {
    const response = await fetch(fullEndpoint, {
      ...config
     
    })

    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`)
    }

    const data = await response.json()

    console.log('Doing Get request in route: ' + fullEndpoint)
    return data
  } catch (error) {
    console.log('Error in the Get Api:' + error)
    console.log('Route: ' + fullEndpoint)
    throw new Error('Request failed')
  }
}
