import * as mongoose from "mongoose";

type CounterDocument = mongoose.Document & {
  _id:string;
  seq_val: number;
};

const CounterSchema = new mongoose.Schema({
   _id: {type:String ,required:true},
   seq_val : {type:Number,default:0},
});

export const Counter = mongoose.model<CounterDocument>('Counter',CounterSchema);
