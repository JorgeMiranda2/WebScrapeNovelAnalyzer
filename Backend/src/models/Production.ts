import {Schema, model} from "mongoose";
import {COL_NAME as COL_GENRE} from "./Genre"

const COL_NAME = "production"
const productionSchema = new Schema({

    title:{
        type:String,
        maxlength:300,
        required:true
    },
    alternative_names:{
        type:[String]
    },
    description:{
        type:String
    },
    author:{
        type:String,
        maxlength:70
    },
    genres_id:{
        type:[{
            type:Schema.Types.ObjectId,
            ref:COL_GENRE
        }]
    }

},{
    timestamps:true
})

const productionModel =  model(COL_NAME,productionSchema);
export {productionModel, COL_NAME}