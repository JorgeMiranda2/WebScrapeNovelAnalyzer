
import {genreModel} from "../models/Genre";
import { Request, Response } from "express";
import {genre} from "../interfaces/genre";

type httpFunction = (req:Request,res:Response) => Promise<void>;

interface GenreController {
    getGenres:httpFunction,
    postGenre:httpFunction,
    getGenre:httpFunction,
    putGenre:httpFunction,
    deleteGenre:httpFunction,
};


export const GenreController:GenreController = {

getGenres: async (req, res) => {
    try{
        const genres = await genreModel.find({});
        res.status(200).json(genres); 
    } catch(e){
        console.log("error getting genres: ", e);
        res.status(400).json({error:"something went wrong"});
    }

}, 

postGenre: async (req, res) =>{

const { 
 name,
 description
}:genre = req.body;

const newGenre = new genreModel({
name,
description
})

try{
    await newGenre.save();
    res.status(200).json({ message: 'Genre created successfully', genre: newGenre });
} catch(e){
    console.log("error creating genre: ", e);
    res.status(400).json({error:"something went wrong"});
}




},
getGenre:async (req, res)=>{
    try{
        const GenreObtained:genre| null = await  genreModel.findById(req.params.id);
        GenreObtained != null ?
        res.status(200).json(GenreObtained)
        :
        res.status(404).json({error:"genre not found"})
    } catch (e){
        console.log("error getting genre by id: ", e);
        res.status(400).json({error:"something went wrong"});
    }
},

putGenre:async (req,res)=>{

    const {
   name,
   description
    } : genre = req.body;
    
    try{
    await genreModel.findByIdAndUpdate(req.params.id,{
   name,
   description
    })

    res.status(404).json({message:"updated successfully"});

} catch (e) {
    console.log("error updating genre");
    res.status(400).json({error:"something went wrong"});
}

},
deleteGenre:async (req,res)=>{

    try{
        await genreModel.findByIdAndDelete(req.params.id);
        res.status(200).json({message:"Genre deleted successfully"})
    } catch (e){
        console.log("error deleting genre");
        res.status(400).json({error:"something went wrong"});
    }

}


}

