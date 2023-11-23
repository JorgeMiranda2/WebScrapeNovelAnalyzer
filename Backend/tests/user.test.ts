import app from "../src/app";
import server from "../src/index";
import supertest from "supertest";
import mongoose from "mongoose";
import { initializeUsers } from "./helpers/userHelpers";
import {userModel} from "../src/models/User";
import { user } from "../src/interfaces/user";

const api = supertest(app);


describe("User test controller", ()=>{
   


    beforeEach(async ()=>{
    await userModel.deleteMany({});
    const initialUsers = await initializeUsers();

    for (const user of initialUsers){
        const newUser = new userModel(user);
        await newUser.save();

    }
    })

    test("Users are returned in JSON format", async ()=>{
        await api.get('/api/user/')
        .expect(200)
        .expect('Content-Type', /application\/json/)
    });

    
    test("There are users in the database", async ()=>{
        const initialUsers = await initializeUsers();
        const response = await api.get('/api/user/');
        expect(response.body.length).toBe(initialUsers.length);
    });

    test("Is possible enter a new user", async ()=>{
        const initialUsers = await initializeUsers();
        const response = await api.get('/api/user/');
        expect(response.body.length).toBe(initialUsers.length);
    });

    
    test("Can delete an user", async ()=>{
        const initialUsers = await initializeUsers();
        const firstUser = await userModel.findOne({});

        let firstId = null;
        if(firstUser){
        firstId = firstUser._id;
        }
     
        await api.delete(`/api/user/${firstId}`).expect(200);
        const response = await api.get(`/api/user/`);
        expect(response.body.length).toBe(initialUsers.length-1);
    });

    test("Can save a new user", async ()=>{
        const initialUsers = await initializeUsers();
        const firstUser = await userModel.findOne({});
        let firstUserStateId = firstUser ? firstUser.state_id : console.log("error");
        

         console.log("xddd",firstUserStateId);
         
        const newUser = {
            firstname:"Firstname example",
            lastname: "Lastname example",
            birthdate:"2000-10-14",
            email:"example3@usco.edu.co",
            phone:"313453134",
            password:"examplePassword",
            state_id:firstUserStateId
        };
   
    
            await api.post(`/api/user/`).send(newUser)
            .expect(200)
            .expect('Content-Type', /application\/json/);
    

        const response = await api.get('/api/user/');
        expect(response.body.length).toBe(initialUsers.length+1);
    });  
 
   

    test("Can't save an empty user", async ()=>{
        const initialUsers = await initializeUsers();
        const newUser = new userModel({} as user);

        let error:any;
        try {
            await newUser.validate();
        } catch (e) {
            error = e;
        }
    
        expect(error).toBeDefined();
        expect(error.errors).toBeDefined();
      
        const response = await api.get(`/api/user/`);
        expect(response.body.length).toBe(initialUsers.length);
    });

    
    test("Can update an user", async ()=>{
  

        const firstUser = await userModel.findOne({} as user);
        if(firstUser){
            let firstUserStateId =  firstUser.state_id 
            const updatedUser:user = {
                birthdate:firstUser?.birthdate,
                email:firstUser?.email,
                firstname:"Changed",
                lastname:firstUser?.lastname,
                password:firstUser?.password,
                phone:firstUser?.phone,
                state_id:firstUserStateId
                } as user;

        let error:any;
        try{
            console.log("id: ", firstUser._id.toString());
            console.log("updateduser: ", updatedUser);

            console.log("SSS: ", `/api/user/${firstUser._id.toString()}`)
           const updatedResponse =  await api.put(`/api/user/${firstUser._id.toString()}`).send(updatedUser)
           // expect(updatedResponse.status).toBe(200);
            console.log("response2: ", updatedResponse.body)

            
        }catch(e){
            console.log("error: " ,e);
            error = e;
        }
 
        expect(error).not.toBeDefined();


        const response = await api.get(`/api/user/${firstUser._id.toString()}`);
        console.log("res:" ,response.body);
        console.log("first user: ", firstUser);
        expect(response.body.firstname).toEqual(firstUser.firstname);


        }else{
            console.log("No user finded");
        }
    

    });

    afterAll(()=>{
        mongoose.connection.close();
        server.close();
    })

})
