import * as React from 'react';
import { useState } from 'react';
// @ts-ignore
import copy from "copy-to-clipboard";

/* HOOK REACT EXAMPLE */
const InputByApi = (props: InputByApi) => {
    const [url, setUrl] = useState('')
    const [res, setRes] = useState('');
    const [address, setAddress] = useState('');

    async function onSubmit(){
        setRes('');
        setAddress('');

        let res;
        let reg = /(http|https):\/\/\S*/;
        if(!new RegExp(reg).test(url)){
            alert('请输入一个正确的url!')
            return false;
        }

        if(props.type === 'short'){
            res = await fetch(`/api/long/to/short?oriUrl=${url}`);
        }else{
            res = await fetch(`/api/short/to/long?shortUrl=${url}`);
        }
        const data = await res.json();
        if(data.code === 0){
            if(data.data){
                setAddress(props.type === 'short' ? data.data.shortUrl : data.data.oriUrl);
            }
        }
        setRes(`result: ${JSON.stringify(data)}`);
    }

    function onChange(e: React.ChangeEvent<HTMLInputElement>){
        setUrl(e.target.value);
    }

    function CopyText(){
        if(copy(address)){
            alert(`${address} 已复制`);
        }else{
            alert(`复制失败，请手动复制!`);
        }
    }

    return (
        <div>
            <div className="form-horizontal p-5">
                <div className="form-group row justify-content-md-center">
                    <label className="text-primary text-center" htmlFor="shortLink">Hello, please enter your {props.type} link!</label>
                    <div className="col-sm-8">
                        <input className="form-control" type="text" name={props.type+'Link'} id={props.type+'Link'} value={url} onChange={onChange} />
                    </div>
                </div>
                <div className="form-group row justify-content-md-center">
                    <div className="col-sm-8 text-center">
                        <button className="btn btn-primary" onClick={onSubmit}><i className="fa fa-envelope"></i>Send</button>
                    </div>
                </div>
                <div className="form-group row justify-content-md-center">
                    <p>{res}</p>
                    <label className="text-primary text-center font-weight-bold" onClick={CopyText}>{address ? `Take it : ${address}`:''}</label>
                </div>
            </div>
        </div>
    );
};

interface InputByApi {
    type: 'short' | 'long';
}
export default InputByApi;
