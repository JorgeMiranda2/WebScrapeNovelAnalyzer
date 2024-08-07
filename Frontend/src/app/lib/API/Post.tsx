import GetInitialConfig from '../Utils/GetInitialConfig'
import apiConfig from '../../../../variables.mjs'

export default async function Post<T>(endpoint: string, info: any): Promise<T | null> {
  const config: any = GetInitialConfig('POST')

  const fullEndpoint = `${apiConfig.apiBaseUrl}${endpoint}`

  try {
    const response = await fetch(fullEndpoint, {
      ...config,
      body: JSON.stringify(info) // Añadir el cuerpo a la configuración
    })

    console.log('Doing Post request in route: ' + fullEndpoint)

    if (!response.ok) {
      const errorText = await response.text(); // Leer el texto de error para obtener más detalles
      console.log(`Error in the Post Api: ${errorText}`)
      console.log('Route: ' + fullEndpoint)
      throw new Error(`Error ${response.status}: ${response.statusText}`)
    }

    const data = await response.json()
    return data
  } catch (error) {
    console.error('Error in the Post Api:', error)
    throw new Error('Request failed')
  }
}
