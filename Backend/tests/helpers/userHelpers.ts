import { ObtainDefaultState } from "./globalHelpers"
import {user} from "../../src/interfaces/user";



async function initializeUsers() {
    const state_id = await ObtainDefaultState(); 
    
    if (state_id) {
        const initialUsers: Array<user> = [
            {
                firstname: "Name 1",
                lastname: "lastaname 1",
                birthdate: new Date(),
                email: "emailexample1.@gmail.com",
                phone: "333333333",
                password: "password1",
                state_id: state_id
            },
            {
                firstname:"Name 2",
                lastname: "lastaname 2",
                birthdate:new Date(),
                email:"emailexample2.@gmail.com",
                phone:"222222222",
                password:"password2",
                state_id:state_id 
            }
        ];

        return initialUsers;
    } else {
        throw new Error("can't get state_id");
    }
}

export { initializeUsers };