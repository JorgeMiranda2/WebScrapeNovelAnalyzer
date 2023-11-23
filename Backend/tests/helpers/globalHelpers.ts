import { stateModel } from "../../src/models/State";
import { initialStates } from "./stateHelpers";
import {state} from "../../src/interfaces/state";
import { Schema } from "mongoose";
//putting inicial data in the database
async function insertInitialState():Promise<undefined>{
    await stateModel.deleteMany({});


    for (const state of initialStates){
        const newState = new stateModel(state);
        await newState.save();
    }
} 

async function ObtainDefaultState(): Promise<Schema.Types.ObjectId| null> {
    await insertInitialState();
    const firstState : state | null = await stateModel.findOne({name:initialStates[0].name});
    if(firstState){
     
        return firstState._id;
     
    }
    return null;

}

export {insertInitialState, ObtainDefaultState};