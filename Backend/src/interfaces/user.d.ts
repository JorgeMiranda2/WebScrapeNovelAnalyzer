
//Document type Object

export interface user{
    _id?:Schema.Types.ObjectId
    firstname:String,
    lastname:String,
    birthdate:Date,
    email:String,
    phone:String,
    password:String,
    state_id:Schema.Types.ObjectId
}