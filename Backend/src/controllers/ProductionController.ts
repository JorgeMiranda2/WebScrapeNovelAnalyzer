
import {productionModel} from "../models/Production";
import { Request, Response } from "express";
import {production} from "../interfaces/production";

type httpFunction = (req:Request,res:Response) => Promise<void>;

interface ProductionController {
    getProductions:httpFunction,
    postProduction:httpFunction,
    getProduction:httpFunction,
    putProduction:httpFunction,
    deleteProduction:httpFunction,
};


export const ProductionController:ProductionController = {

getProductions: async (req, res) => {
    try{
        const productions = await productionModel.find({});
        res.status(200).json(productions); 
    } catch(e){
        console.log("error getting productions: ", e);
        res.status(400).json({error:"something went wrong"});
    }

}, 

postProduction: async (req, res) =>{

const { 
author,
alternative_names,
description,
genres_id,
title
}:production = req.body;

const newProduction = new productionModel({
    author,
    alternative_names,
    description,
    genres_id,
    title
})

try{
    await newProduction.save();
    res.status(200).json({ message: 'Production created successfully', production: newProduction });
} catch(e){
    console.log("error creating production: ", e);
    res.status(400).json({error:"something went wrong"});
}




},
getProduction:async (req, res)=>{
    try{
        const ProductionObtained:production| null = await  productionModel.findById(req.params.id);
        ProductionObtained != null ?
        res.status(200).json(ProductionObtained)
        :
        res.status(404).json({error:"production not found"})
    } catch (e){
        console.log("error getting production by id: ", e);
        res.status(400).json({error:"something went wrong"});
    }
},

putProduction:async (req,res)=>{

    const {
        author,
        alternative_names,
        description,
        genres_id,
        title
    } : production = req.body;
    
    try{
    await productionModel.findByIdAndUpdate(req.params.id,{
        author,
        alternative_names,
        description,
        genres_id,
        title
    })

    res.status(404).json({message:"updated successfully"});

} catch (e) {
    console.log("error updating production");
    res.status(400).json({error:"something went wrong"});
}

},
deleteProduction:async (req,res)=>{

    try{
        await productionModel.findByIdAndDelete(req.params.id);
        res.status(200).json({message:"Production deleted successfully"})
    } catch (e){
        console.log("error deleting production");
        res.status(400).json({error:"something went wrong"});
    }

}


}

