
import {workFollowModel} from "../models/WorkFollow";
import { Request, Response } from "express";
import {workFollow} from "../interfaces/workFollow";

type httpFunction = (req:Request,res:Response) => Promise<void>;

interface WorkFollowController {
    getWorksFollow:httpFunction,
    postWorkFollow:httpFunction,
    getWorkFollow:httpFunction,
    putWorkFollow:httpFunction,
    deleteWorkFollow:httpFunction,
};


export const WorkFollowController:WorkFollowController = {

getWorksFollow: async (req, res) => {
    try{
        const worksFollow = await workFollowModel.find({});
        res.status(200).json(worksFollow); 
    } catch(e){
        console.log("error getting worksFollow: ", e);
        res.status(400).json({error:"something went wrong"});
    }

}, 

postWorkFollow: async (req, res) =>{

const { 
follow_state,
user_id,
work_id
}:workFollow = req.body;

const newWorkFollow = new workFollowModel({
    follow_state,
    user_id,
    work_id
})

try{
    await newWorkFollow.save();
    res.status(200).json({ message: 'WorkFollow created successfully', workFollow: newWorkFollow });
} catch(e){
    console.log("error creating workFollow: ", e);
    res.status(400).json({error:"something went wrong"});
}




},
getWorkFollow:async (req, res)=>{
    try{
        const WorkFollowObtained:workFollow| null = await  workFollowModel.findById(req.params.id);
        WorkFollowObtained != null ?
        res.status(200).json(WorkFollowObtained)
        :
        res.status(404).json({error:"workFollow not found"})
    } catch (e){
        console.log("error getting workFollow by id: ", e);
        res.status(400).json({error:"something went wrong"});
    }
},

putWorkFollow:async (req,res)=>{

    const {
        follow_state,
        user_id,
        work_id 
    } : workFollow = req.body;
    
    try{
    await workFollowModel.findByIdAndUpdate(req.params.id,{
        follow_state,
        user_id,
        work_id
    })

    res.status(404).json({message:"updated successfully"});

} catch (e) {
    console.log("error updating workFollow");
    res.status(400).json({error:"something went wrong"});
}

},
deleteWorkFollow:async (req,res)=>{

    try{
        await workFollowModel.findByIdAndDelete(req.params.id);
        res.status(200).json({message:"WorkFollow deleted successfully"})
    } catch (e){
        console.log("error deleting workFollow");
        res.status(400).json({error:"something went wrong"});
    }

}


}

