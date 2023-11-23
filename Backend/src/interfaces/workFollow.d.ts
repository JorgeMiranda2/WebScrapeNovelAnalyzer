
//Document type Object

import { Schema } from "mongoose";

type followStates = "Following"| "Paused" |"Dropped"| "read"| "favorite";

export interface workFollow{
    _id?:Schema.Types.ObjectId,
    user_id:Schema.Types.ObjectId,
    work_id:Schema.Types.ObjectId,
    follow_state:followStates
}