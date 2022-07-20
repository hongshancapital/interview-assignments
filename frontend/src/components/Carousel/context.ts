import React from "react"

export interface ContextProps {
    register: (param: any) => any;
    unregister: (param: any) => any;
    getChildren: () => Array<any>;
    parentInfo: () => any;
}
export const Context = React.createContext<ContextProps>({
    register: () => {},
    unregister: () => {},
    getChildren: () => [],
    parentInfo: () => {},
})
