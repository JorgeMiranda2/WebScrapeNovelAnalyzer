export default function GetInitialConfig (method: 'POST' | 'GET' | 'DELETE' | 'PUT') {
  return {
    method, // Aquí se establece el método correctamente
    credentials: 'include',
    cache: 'no-store', // Usamos 'no-store' para evitar el almacenamiento en caché
    headers: {
      'Content-Type': 'application/json'
    }
  }
}
