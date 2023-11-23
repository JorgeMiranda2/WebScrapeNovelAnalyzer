
import {workModel} from "../models/Work";
import { Request, Response } from "express";
import {work} from "../interfaces/work";

type httpFunction = (req:Request,res:Response) => Promise<void>;

interface WorkController {
    getWorks:httpFunction,
    postWork:httpFunction,
    getWork:httpFunction,
    putWork:httpFunction,
    deleteWork:httpFunction,
};


export const WorkController:WorkController = {

getWorks: async (req, res) => {
    try{
        const works = await workModel.find({});
        res.status(200).json(works); 
    } catch(e){
        console.log("error getting works: ", e);
        res.status(400).json({error:"something went wrong"});
    }

}, 

postWork: async (req, res) =>{

const { 
production_id,
just_year,
rating,
tags_id,
year,
_id
}:work = req.body;

const newWork = new workModel({
    production_id,
    just_year,
    rating,
    tags_id,
    year,
    _id
})

try{
    await newWork.save();
    res.status(200).json({ message: 'Work created successfully', work: newWork });
} catch(e){
    console.log("error creating work: ", e);
    res.status(400).json({error:"something went wrong"});
}




},
getWork:async (req, res)=>{
    try{
        const WorkObtained:work| null = await  workModel.findById(req.params.id);
        WorkObtained != null ?
        res.status(200).json(WorkObtained)
        :
        res.status(404).json({error:"work not found"})
    } catch (e){
        console.log("error getting work by id: ", e);
        res.status(400).json({error:"something went wrong"});
    }
},

putWork:async (req,res)=>{

    const {
        production_id,
        just_year,
        rating,
        tags_id,
        year,
        _id
    } : work = req.body;
    
    try{
    await workModel.findByIdAndUpdate(req.params.id,{
        production_id,
        just_year,
        rating,
        tags_id,
        year,
        _id
    })

    res.status(404).json({message:"updated successfully"});

} catch (e) {
    console.log("error updating work");
    res.status(400).json({error:"something went wrong"});
}

},
deleteWork:async (req,res)=>{

    try{
        await workModel.findByIdAndDelete(req.params.id);
        res.status(200).json({message:"Work deleted successfully"})
    } catch (e){
        console.log("error deleting work");
        res.status(400).json({error:"something went wrong"});
    }

}


}

