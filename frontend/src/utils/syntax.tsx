import React, {useState, useRef} from 'react';

export type AppProps = {
  name: 'string',
  age: 1,
  color?:string, //可选属性
  children: React.ReactNode
  style: React.CSSProperties,
  objArr: {color:'blue', opacity: '0.8'}[]
  buttonClick: (event:React.MouseEvent<HTMLButtonElement>) => void
  handleOnchange: (event:React.ChangeEvent<HTMLInputElement>) => void
}

export type appState = {
  name: 'sgyunzhu',
  age: 32
}

export type ACTIONTYPE =  { type: "increment"; payload: number } | { type: "decrement"; payload: string }; // 联合声明类型
 
export const APP = (props:AppProps):JSX.Element => {
  const {style, children, name, age, objArr, buttonClick, handleOnchange} = props;
  const [user, setUser] = useState<appState>({} as appState);
  const divRef = useRef<HTMLDivElement>(null);
  // 类型断言 someValue as string 等价于 <string>someValue,一般用于你已经确切的知道了元素的类型
  let someValue: any = "this is a string";
  let strLength: number = (someValue as string).length;
  // let str2Length: number = (<string>someValue).length;

  return(
    <div style={style}>
      {children}
      <span className="string">{name}</span>
      <span className="number">{age}</span>
      {objArr.map((v, index) => {
        return(
          <div key={index}>
            <span>{v?.color}</span>
            <span>{v?.opacity}</span>
            <button onClick={buttonClick}></button>
            <input onChange={handleOnchange}></input>
          </div>
        )
      })}
    </div>
  )
}