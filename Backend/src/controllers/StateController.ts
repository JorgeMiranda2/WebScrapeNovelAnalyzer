
import {stateModel} from "../models/State";
import { Request, Response } from "express";
import {state} from "../interfaces/state";

type httpFunction = (req:Request,res:Response) => Promise<void>;

interface StateController {
    getStates:httpFunction,
    postState:httpFunction,
    getState:httpFunction,
    putState:httpFunction,
    deleteState:httpFunction,
};


export const StateController:StateController = {

getStates: async (req, res) => {
    try{
        const productions = await stateModel.find({});
        res.status(200).json(productions); 
    } catch(e){
        console.log("error getting productions: ", e);
        res.status(400).json({error:"something went wrong"});
    }

}, 

postState: async (req, res) =>{

const { 
   name,
   description
}:state = req.body;

const newState = new stateModel({
    name,
   description
})

try{
    await newState.save();
    res.status(200).json({ message: 'state created successfully', state: newState });
} catch(e){
    console.log("error creating state: ", e);
    res.status(400).json({error:"something went wrong"});
}




},
getState:async (req, res)=>{
    try{
        const StateObtained:state| null = await  stateModel.findById(req.params.id);
        StateObtained != null ?
        res.status(200).json(StateObtained)
        :
        res.status(404).json({error:"state not found"})
    } catch (e){
        console.log("error getting state by id: ", e);
        res.status(400).json({error:"something went wrong"});
    }
},

putState:async (req,res)=>{

    const {
        name,
        description
    } : state = req.body;
    
    try{
    await stateModel.findByIdAndUpdate(req.params.id,{
        name,
        description
    })

    res.status(404).json({message:"updated successfully"});

} catch (e) {
    console.log("error updating state");
    res.status(400).json({error:"something went wrong"});
}

},
deleteState:async (req,res)=>{

    try{
        await stateModel.findByIdAndDelete(req.params.id);
        res.status(200).json({message:"state deleted successfully"})
    } catch (e){
        console.log("error deleting state");
        res.status(400).json({error:"something went wrong"});
    }

}


}

