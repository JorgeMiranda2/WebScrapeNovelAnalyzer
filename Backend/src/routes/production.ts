import {Router} from "express";

import { ProductionController } from "../controllers/ProductionController";

const router = Router();


const {getProductions, getProduction, postProduction, deleteProduction, putProduction} = ProductionController;

router.route("/")
.get(getProductions)
.post(postProduction)

router.route("/:id")
.get(getProduction)
.delete(deleteProduction)
.put(putProduction)

export default router;