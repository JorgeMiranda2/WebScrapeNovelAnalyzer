import {Router} from "express";

import { StateController } from "../controllers/StateController";

const router = Router();


const {getStates, getState, postState, deleteState, putState} = StateController;

router.route("/")
.get(getStates)
.post(postState)

router.route("/:id")
.get(getState)
.delete(deleteState)
.put(putState)

export default router;