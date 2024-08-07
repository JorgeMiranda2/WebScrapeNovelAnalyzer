import Link from "next/link";
import RegisterForm from "../ui/RegisterForm";

export default function registerPage () {
  return (
    <div className='p-3 flex justify-center items-center bg-slate-100'>
      <div className='sm:shadow-xl w-full px-8 pb-8 pt-12 sm:bg-white rounded-xl space-y-12'>
        <h1 className='font-bold text-2xl text-center text-gray-800'>Register</h1>
        <RegisterForm />
        <p className='text-center text-gray-700'>
          Already have an account?{' '}
          <Link className='text-indigo-500 hover:underline' href='/login'>
            Sign in
          </Link>{' '}
        </p>
      </div>
    </div>
  )
}
