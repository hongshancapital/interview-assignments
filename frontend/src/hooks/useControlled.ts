import { useEffect } from "react"
const useControlled = <V, S extends ((v:V)=>void)>(value:V,valueSetter:S,debugFc?:S)=>{
    useEffect(() => {
        valueSetter(value)
        if (debugFc){
            debugFc(value)
        }
    }, [value, valueSetter, debugFc])
}
export default useControlled