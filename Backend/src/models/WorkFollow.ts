import {Schema, model} from "mongoose";
import {COL_NAME as COL_USER} from "./User";
import {COL_NAME as COL_WORK} from "./Work";

const COL_NAME = "work_follow";

const workFollowSchema = new Schema({
user_id:{
    type:Schema.Types.ObjectId,
    ref:COL_USER
},
work_id:{
    type:Schema.Types.ObjectId,
    ref:COL_WORK
},
follow_state:{
    type:String,
    enum: ["Following", "Paused", "Dropped", "read", "favorite"],
    default:"Following"
}
}, { timestamps: true });

const workFollowModel = model(COL_NAME, workFollowSchema);
export {workFollowModel, COL_NAME}
