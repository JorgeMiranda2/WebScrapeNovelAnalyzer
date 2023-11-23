
import {userModel} from "../models/User";
import { Request, Response } from "express";
import {user} from "../interfaces/user";

type httpFunction = (req:Request,res:Response) => Promise<void>;

interface UserController {
    getUsers:httpFunction,
    postUser:httpFunction,
    getUser:httpFunction,
    putUser:httpFunction,
    deleteUser:httpFunction,
};


export const userController:UserController = {

getUsers: async (req, res) => {
    try{
        const users = await userModel.find({});
        res.status(200).json(users); 
    } catch(e){
        console.log("error getting users: ", e);
        res.status(400).json({error:"something went wrong"});
    }

}, 

postUser: async (req, res) =>{

const { 
    firstname, 
    lastname, 
    birthdate, 
    phone, 
    email, 
    state_id,
    password 
}:user = req.body;

const newUser = new userModel({
    firstname,
    lastname,
    birthdate,
    phone,
    email,
    state_id,
    password
})

try{
    await newUser.save();
    res.status(200).json({ message: 'User created successfully', user: newUser });
} catch(e){
    console.log("error creating user: ", e);
    res.status(400).json({error:"something went wrong"});
}




},
getUser:async (req, res)=>{
    try{
        const userObtained:user| null = await  userModel.findById(req.params.id);
        userObtained != null ?
        res.status(200).json(userObtained)
        :
        res.status(404).json({error:"user not found"})
    } catch (e){
        console.log("error getting user by id: ", e);
        res.status(400).json({error:"something went wrong"});
    }
},

putUser:async (req,res)=>{

    const {
        firstname, 
        lastname, 
        birthdate, 
        phone, 
        email, 
        state_id 
    } : user = req.body;
    
    try{
    await userModel.findByIdAndUpdate(req.params.id,{
        firstname, 
        lastname, 
        birthdate, 
        phone, 
        email, 
        state_id 
    })

    res.status(200).json({message:"updated successfully"});

} catch (e) {
    console.log("error updating user");
    res.status(400).json({error:"something went wrong"});
}

},
deleteUser:async (req,res)=>{

    try{
        await userModel.findByIdAndDelete(req.params.id);
        res.status(200).json({message:"User deleted successfully"})
    } catch (e){
        console.log("error deleting user");
        res.status(400).json({error:"something went wrong"});
    }

}


}

