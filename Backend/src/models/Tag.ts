import {Schema, model} from "mongoose";

const COL_NAME = "tags"

const tagsSchema = new Schema({
  name:{
    type:String,
    required:true,
    unique:true
  },
  description:{
    type:String,
    maxlength:300,
  }
}, { timestamps: true })

const tagModel = model(COL_NAME, tagsSchema);

export {tagModel, COL_NAME};