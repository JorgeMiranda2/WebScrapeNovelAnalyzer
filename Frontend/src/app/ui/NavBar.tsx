'use client'
import Link from 'next/link'

import NavBarAuthComponent from './NavBarAuthComponent'

export default function NavBar () {
  return (

    <header className='shadow-xl'>
      <nav className='border mx-auto flex items-center justify-between p-6 lg:px-8'>
        {/* Sección del logo */}
        <div className='text-xl font-bold'>
          Logo
        </div>

        {/* Sección de la barra de navegación */}
        <div className='flex items-center space-x-4'>
          {/* Enlaces de navegación */}
          <Link href='/XD' className='text-gray-700 hover:text-gray-900'>Home</Link>
          <Link href='#' className='text-gray-700 hover:text-gray-900'>About</Link>
          <Link href='#' className='text-gray-700 hover:text-gray-900'>Contact  </Link>

        </div>

        {/* Sección del botón de inicio de sesión o el nombre de usuario */}

        <NavBarAuthComponent />

      </nav>
    </header>

  )
}
