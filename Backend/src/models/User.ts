import {Schema, model} from "mongoose";
import {COL_NAME as COL_STATE} from "./State";

const COL_NAME = "user"

const userSchema = new Schema({
    firstname:{
        type:String,
        required:true,
        maxlength:50
    },
    lastname: {
        type:String,
        required:true,
        maxlength:50
    },
    birthdate:{
        type:Date
    },
    email:{
        type:String,
        maxlength:80,
        required:true,
        unique:true
    },
    phone:{
        type:String,
        maxlength:15
    },
    password:{
        type:String,
        required:true,
        minLength:8
    },
    state_id:{
        type:Schema.Types.ObjectId,
        ref:COL_STATE
    }

},
{
    timestamps:true
})

const modelSchema = model(COL_NAME, userSchema);
export {modelSchema as userModel, COL_NAME};