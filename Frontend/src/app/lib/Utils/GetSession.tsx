import Cookies from 'js-cookie'

export default function GetSession () {
  const token = Cookies.get('token') || undefined
  const username = Cookies.get('username') || undefined
  const isAuth = token && username

  const auth = {
    isAuth,
    token,
    username
  }

  return auth
}
