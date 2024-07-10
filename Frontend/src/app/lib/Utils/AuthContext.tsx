'use client'
import React, { createContext, useContext, useReducer, useEffect, useState } from 'react'
import Cookies from 'js-cookie'
import { authAction } from '../Interfaces/auth'

const AuthStateContext = createContext<any>(undefined)
const AuthDispatchContext = createContext<any>(undefined)

const authReducer = (state: any, action: authAction) => {
  switch (action.type) {
    case 'LOGIN':
      return { ...state, isAuth: true, username: action.payload }
    case 'LOGOUT':
      Cookies.remove('token')
      return { ...state, isAuth: false, username: null }
    default:
      return state
  }
}

export const AuthProvider = ({ children } : {children: React.ReactNode}) => {
  const [state, dispatch] = useReducer(authReducer, {

    isAuth: false,
    username: null
  })

  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const cookie = Cookies.get('auth')
    if (cookie) {
      const authData = JSON.parse(cookie)
      if (authData.isAuth) {
        dispatch({ type: 'LOGIN', payload: authData.username })
      }
    }
    setLoading(false)
  }, [])

  return (
    <AuthStateContext.Provider value={{ ...state, loading }}>
      <AuthDispatchContext.Provider value={dispatch}>
        {children}
      </AuthDispatchContext.Provider>
    </AuthStateContext.Provider>
  )
}

export const useAuthState = () => useContext(AuthStateContext)
export const useAuthDispatch = () => useContext(AuthDispatchContext)
