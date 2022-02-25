import { validationResult } from "express-validator";
import {nanoid} from 'nanoid'

function getData(){
    return "syy";
}

async function getData2(){
    return "syy2";
}


async function Add(n1: number, n2:number) {
    return (n1+n2);
}

async function test1() {
    let n: number = await Add(1, 1);
    return n;
}


export function DemoMain() {

  //  Promise.resolve(test1()).then(val=>{console.log(val)});
//    Promise.resolve("hello2").then(val=>{console.log(val)});
    

  //  Promise.resolve(test1()).then(val=>{console.log(val)});
//    test1().then(value=>{console.log(value)})
//    .then(value2=>{console.log(value2)});

    Promise.resolve(Add(1,1)).then(val=>{console.log(val)});

   // console.log("hello0");

   let str = nanoid(8);
   console.log(str);

}