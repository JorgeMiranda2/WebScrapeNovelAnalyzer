'use client'
import { useRouter } from 'next/navigation'
import Cookies from 'js-cookie'
import { useAuthDispatch } from '../lib/Utils/AuthContext'

const LogoutButton = () => {
  const dispatch = useAuthDispatch()
  const router = useRouter()

  const handleLogout = () => {
    Cookies.remove('auth')
    dispatch({ type: 'LOGOUT' })
    router.push('/login')
  }
  return (
    <button
      onClick={handleLogout}
      className='bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded'
    >
      Logout
    </button>
  )
}

export default LogoutButton
