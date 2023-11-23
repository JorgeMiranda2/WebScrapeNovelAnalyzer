import {model, Schema} from "mongoose";
import {COL_NAME as COL_PRODUCTION} from "./Production";
import {COL_NAME as COL_TAGS} from "./Tag";

const COL_NAME = "work";

const workSchema = new Schema({
    production_id:{
        type:Schema.Types.ObjectId,
        ref:COL_PRODUCTION
    }, 
       tags_id:{
        type:Schema.Types.ObjectId,
        ref:COL_TAGS
    },
    year:{
        type:Date
    },
    just_year:{
        type:Boolean,
        default:true
    },
    rating:{
        type:Number,
        min:0,
        max:5
    }
 
}, { timestamps: true })

const workModel =  model(COL_NAME,workSchema);
export {workModel, COL_NAME};