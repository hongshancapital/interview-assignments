import React, { createContext, useState, useEffect } from "react";




interface interContextProps {
    test: string;
    setTest:(test: string)=>void;
}

export type TestContextProps = interContextProps;

export const TestContext: React.Context<TestContextProps>  = createContext<TestContextProps>({} as TestContextProps);


export default function useHomeTest():TestContextProps{

    const [ test, setTest ] = useState<string>('');


    useEffect(()=>{
        setTest('你好');
    }, [])



    return {
        test,
        setTest
    }
}
