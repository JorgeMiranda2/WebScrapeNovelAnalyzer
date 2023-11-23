
import {tagModel} from "../models/Tag";
import { Request, Response } from "express";
import {tag} from "../interfaces/tag";

type httpFunction = (req:Request,res:Response) => Promise<void>;

interface TagController {
    getTags:httpFunction,
    postTag:httpFunction,
    getTag:httpFunction,
    putTag:httpFunction,
    deleteTag:httpFunction,
};


export const TagController:TagController = {

getTags: async (req, res) => {
    try{
        const tags = await tagModel.find({});
        res.status(200).json(tags); 
    } catch(e){
        console.log("error getting tags: ", e);
        res.status(400).json({error:"something went wrong"});
    }

}, 

postTag: async (req, res) =>{

const { 
 name,
 description
}:tag = req.body;

const newTag = new tagModel({
name,
description
})

try{
    await newTag.save();
    res.status(200).json({ message: 'Tag created successfully', tag: newTag });
} catch(e){
    console.log("error creating tag: ", e);
    res.status(400).json({error:"something went wrong"});
}




},
getTag:async (req, res)=>{
    try{
        const TagObtained:tag| null = await  tagModel.findById(req.params.id);
        TagObtained != null ?
        res.status(200).json(TagObtained)
        :
        res.status(404).json({error:"tag not found"})
    } catch (e){
        console.log("error getting tag by id: ", e);
        res.status(400).json({error:"something went wrong"});
    }
},

putTag:async (req,res)=>{

    const {
   name,
   description
    } : tag = req.body;
    
    try{
    await tagModel.findByIdAndUpdate(req.params.id,{
   name,
   description
    })

    res.status(404).json({message:"updated successfully"});

} catch (e) {
    console.log("error updating tag");
    res.status(400).json({error:"something went wrong"});
}

},
deleteTag:async (req,res)=>{

    try{
        await tagModel.findByIdAndDelete(req.params.id);
        res.status(200).json({message:"Tag deleted successfully"})
    } catch (e){
        console.log("error deleting tag");
        res.status(400).json({error:"something went wrong"});
    }

}


}

