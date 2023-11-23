import {Schema, model} from "mongoose";


const COL_NAME = "state";

const stateSchema = new Schema({
    name:{
        type:String,
        required:true,
        unique:true
    },
    description:{
        type:String
    }

},{
    timestamps:true
})

const stateModel =  model(COL_NAME,stateSchema);
export {COL_NAME, stateModel};