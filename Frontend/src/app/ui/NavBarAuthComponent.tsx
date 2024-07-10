import Link from 'next/link'
import { useAuthState } from '../lib/Utils/AuthContext'
import LogoutButton from './LogoutButton'

export default function NavBarAuthComponent () {
  const { isAuth, username, loading } = useAuthState()

  return (
    <div>
      {isAuth ? (
        <div className='flex items-center space-x-4'>
          <span className='text-gray-700'>{username}</span>
          <LogoutButton /> {/* Usa el componente LogoutButton */}
        </div>
      ) : (
        <Link href='/login'>
          <button
            className={`bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded ${
              loading ? 'invisible' : 'visible'
            }`}
          >
            Sign In
          </button>
        </Link>
      )}
    </div>
  )
}
