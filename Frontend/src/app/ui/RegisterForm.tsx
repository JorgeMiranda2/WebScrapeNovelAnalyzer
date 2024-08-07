'use client'
import { useFormik } from 'formik'
import { useAuthDispatch } from '../lib/Utils/AuthContext'
import * as yup from 'yup'
import { register } from '../lib/Services/AuthService'
import { useRouter } from 'next/navigation'
import { useEffect, useRef, useState } from 'react'
import useIdentificationType from '../lib/CustomHooks/useIdentificationType'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import { format } from 'date-fns';

export const RegisterForm = () => {
  const { identificationTypes, getIdentificationTypes } = useIdentificationType();
  const router = useRouter()
  const [registrationError, setRegistrationError] = useState(false)
  const initialized = useRef(false)
  useEffect(() => {
    const fetchIdentificationTypes = async () => {
       await getIdentificationTypes();
      console.log("funciona... 2");
    }
    if (!initialized.current) {
      initialized.current = true
  
      fetchIdentificationTypes();
    }
    
  }, [])

  const handleRegister = async (values: IRegister) => {
    await register(values);
    router.push("/");
  }

  const validationSchema: yup.ObjectSchema<any> = yup.object({
    name: yup.string().required('Name is required'),
    email: yup.string().email('Invalid email format').required('Email is required'),
    phone: yup.string().required('Phone number is required'),
    birthdate: yup.date().required('Date of birth is required'),
    identification: yup.string().required('Identification is required'),
    identificationTypeId: yup.number().required('Type of identification is required'),
    username: yup.string().min(4, 'Username too short').required('Username is required'),
    password: yup.string().min(6, 'Password too short, it must have at least 6 characters').required('Password is required')
  })

  const formik = useFormik({
    initialValues: {
      name: '',
      email: '',
      phone: '',
      birthdate: new Date() ,
      identification: '',
      identificationTypeId: 0,
      username: '',
      password: ''
    },
    validationSchema,
    onSubmit: (values) => {
      handleRegister(values)
    }
  })

  return (
    <form onSubmit={formik.handleSubmit} className='space-y-6 w-full max-w-md mx-auto p-4 border rounded-lg shadow-lg'>
      <div className='grid w-full items-center gap-1.5'>
        <label htmlFor='name' className='block text-sm font-medium text-gray-700'>
          Name
        </label>
        <input
          className='w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
          required
          placeholder='John Doe'
          value={formik.values.name}
          onChange={formik.handleChange}
          id='name'
          name='name'
          type='text'
        />
        {formik.errors.name && formik.touched.name ? (
          <div className='text-red-600 text-sm'>{formik.errors.name}</div>
        ) : null}
      </div>
      <div className='grid w-full items-center gap-1.5'>
        <label htmlFor='email' className='block text-sm font-medium text-gray-700'>
          Email
        </label>
        <input
          className='w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
          required
          placeholder='example@example.com'
          value={formik.values.email}
          onChange={formik.handleChange}
          id='email'
          name='email'
          type='email'
        />
        {formik.errors.email && formik.touched.email ? (
          <div className='text-red-600 text-sm'>{formik.errors.email}</div>
        ) : null}
      </div>
      <div className='grid w-full items-center gap-1.5'>
        <label htmlFor='phone' className='block text-sm font-medium text-gray-700'>
          Phone
        </label>
        <input
          className='w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
          required
          placeholder='123-456-7890'
          value={formik.values.phone}
          onChange={formik.handleChange}
          id='phone'
          name='phone'
          type='tel'
        />
        {formik.errors.phone && formik.touched.phone ? (
          <div className='text-red-600 text-sm'>{formik.errors.phone}</div>
        ) : null}
      </div>
      <div className='grid w-full items-center gap-1.5'>
  <label htmlFor='birthdate' className='block text-sm font-medium text-gray-700'>
    Date of Birth
  </label>
  <DatePicker
    selected={formik.values.birthdate}
    onChange={(date) => {
      if (date) {
        // Formatear la fecha a "yyyy-MM-dd" si date no es null
        const formattedDate = format(date, 'yyyy-MM-dd');
        formik.setFieldValue('birthdate', formattedDate);
      } else {
        // Manejar el caso cuando date es null (opcional)
        formik.setFieldValue('birthdate', null);
      }
    }}
    className='w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
    id='birthdate'
    name='birthdate'
  />
  {formik.errors.birthdate && formik.touched.birthdate ? (
    <div className='text-red-600 text-sm'>{String(formik.errors.birthdate)}</div>
  ) : null}
</div>
      <div className='grid w-full items-center gap-1.5'>
        <label htmlFor='identification' className='block text-sm font-medium text-gray-700'>
          Identification
        </label>
        <input
          className='w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
          required
          placeholder='12345678'
          value={formik.values.identification}
          onChange={formik.handleChange}
          id='identification'
          name='identification'
          type='text'
        />
        {formik.errors.identification && formik.touched.identification ? (
          <div className='text-red-600 text-sm'>{formik.errors.identification}</div>
        ) : null}
      </div>
      <div className='grid w-full items-center gap-1.5'>
        <label htmlFor='identificationTypeId' className='block text-sm font-medium text-gray-700'>
          Type of Identification
        </label>
        <select
          className='w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
          required
          value={formik.values.identificationTypeId}
          onChange={formik.handleChange}
          id='identificationTypeId'
          name='identificationTypeId'
        >
          <option value='' label='Select identification type' />
          {identificationTypes ? identificationTypes.map((type) => (
            <option key={type.id} value={type.id}>
              {type.name}
            </option>
          )): <option>loading...</option>}
        </select>
        {formik.errors.identificationTypeId && formik.touched.identificationTypeId ? (
          <div className='text-red-600 text-sm'>{String(formik.errors.identificationTypeId)}</div>
        ) : null}
      </div>
      <div className='grid w-full items-center gap-1.5'>
        <label htmlFor='username' className='block text-sm font-medium text-gray-700'>
          Username
        </label>
        <input
          className='w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
          required
          placeholder='example123'
          value={formik.values.username}
          onChange={formik.handleChange}
          id='username'
          name='username'
          type='text'
        />
        {formik.errors.username && formik.touched.username ? (
          <div className='text-red-600 text-sm'>{formik.errors.username}</div>
        ) : null}
      </div>
      <div className='grid w-full items-center gap-1.5'>
        <label htmlFor='password' className='block text-sm font-medium text-gray-700'>
          Password
        </label>
        <input
          className='w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500'
          required
          placeholder='**********'
          value={formik.values.password}
          onChange={formik.handleChange}
          id='password'
          name='password'
          type='password'
        />
        {formik.errors.password && formik.touched.password ? (
          <div className='text-red-600 text-sm'>{formik.errors.password}</div>
        ) : null}
      </div>
      <div className='w-full'>
        <button
          type='submit'
          className='w-full bg-indigo-600 text-white py-2 px-4 rounded-lg shadow hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-opacity-50'
        >
          Register
        </button>
        {registrationError && <div className='text-red-600 text-sm align-self'>Registration failed. Please try again.</div>}
      </div>
    </form>
  )
}

export default RegisterForm
