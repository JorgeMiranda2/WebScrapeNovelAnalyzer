
//Document type Object

import { Schema } from "mongoose";

export interface work{
    _id?:Schema.Types.ObjectId,
    production_id:Schema.Types.ObjectId, 
    tags_id:Schema.Types.ObjectId,
    year:Date,
    just_year:Boolean,
    rating:Number
}