'use client'
import { useFormik } from 'formik'
import { useAuthDispatch } from '../lib/Utils/AuthContext'
import * as yup from 'yup'
import { login } from '../lib/Services/AuthService'
import Cookies from 'js-cookie'
import { useRouter } from 'next/navigation'
import { useState } from 'react'

export const LoginForm = () => {
  const dispatch = useAuthDispatch()
  const router = useRouter()
  const [sessionError, setSessionError] = useState(false)
  const handleLogin = async (values: { identifier: string; password: string }) => {
    try {
      const username = await login({ identifier: values.identifier, password: values.password })
      if (username) {
        Cookies.set('auth', JSON.stringify({ isAuth: true, username }))
        dispatch({ type: 'LOGIN', payload: username })
        router.push('/') // Redirige a la página de inicio u otra página
      }
    } catch (e) {
      console.error('Error during login', e)
      setSessionError(true)
    }
  }

  const validationSchema: yup.ObjectSchema<any> = yup.object({
    identifier: yup.string().min(4, 'identifier too short').required('Identifier is required'),
    password: yup.string().min(4, 'password too short, it must have atleast 4 characters').required('Password is required')
  })

  const formik = useFormik({
    initialValues: {
      identifier: '',
      password: ''
    },
    validationSchema,
    onSubmit: (values) => {
      handleLogin(values)
    }
  })

  return (
    <form onSubmit={formik.handleSubmit} className='space-y-6 w-full max-w-md mx-auto p-4 border rounded-lg shadow-lg'>
      <div className='grid w-full items-center gap-1.5'>
        <label htmlFor='identifier' className='block text-sm font-medium text-gray-700'>
          Username or Email
        </label>
        <input
          className='w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
          required
          placeholder='example123'
          value={formik.values.identifier}
          onChange={formik.handleChange}
          id='identifier'
          name='identifier'
          type='text'
        />
        {formik.errors.identifier && formik.touched.identifier
          ? (
            <div className='text-red-600 text-sm'>{formik.errors.identifier}</div>
            )
          : null}
      </div>
      <div className='grid w-full items-center gap-1.5'>
        <label htmlFor='password' className='block text-sm font-medium text-gray-700'>
          Password
        </label>
        <input
          className='w-full px-3 text-gray-700 py-2 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
          required
          placeholder='**********'
          value={formik.values.password}
          onChange={formik.handleChange}
          id='password'
          name='password'
          type='password'
        />
        {formik.errors.password && formik.touched.password
          ? (
            <div className='text-red-600 text-sm'>{formik.errors.password}</div>
            )
          : null}
      </div>
      <div className='w-full'>
        <button
          type='submit'
          className='w-full bg-indigo-600 text-white py-2 px-4 rounded-lg shadow hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50'
        >
          Login
        </button>
        {sessionError && <div className='text-red-600 text-sm align-self'>User or password incorrect</div>}
      </div>
    </form>
  )
}

export default LoginForm
