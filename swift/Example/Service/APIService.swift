//
//  APIService.swift
//  Example
//
//  Created by 聂高涛 on 2022/3/1.
//

import Foundation


struct APIService {
    
    struct Wrapped {
        var code = 0 //200
        var msg = ""
        var data : Any? = nil
    }
    
    //get请求
    func get(_ url: String, completion:((_ data: APIService.Wrapped) -> ())?) {
        guard let url = URL(string: url) else {
            let wrapped = APIService.Wrapped(code: 0, msg: "无效的URL:\(url)", data: nil)
            self.returnWrapped(wrapped, completion: completion)
            return
        }
        let task = URLSession.shared.dataTask(with: URLRequest(url: url)) { data, res, error in
            if let data = data {
                do  {
                    let data = try JSONSerialization.jsonObject(with: data, options: [])
                    let wrapped = APIService.Wrapped(code: 200, msg: "", data: data)
                    self.returnWrapped(wrapped, completion: completion)
                    return
                }
                catch{
                    
                }
            }
            
            let wrapped = APIService.Wrapped(code: 0, msg: error?.localizedDescription ?? "出错了", data: nil)
            self.returnWrapped(wrapped, completion: completion)
        }
        task.resume()
    }
    
    //post请求
    func post(_ request: URLRequest, completion:((_ data: APIService.Wrapped) -> ())?) {
        
    }
    
    //全局处理异常
    func returnWrapped(_ wrapped:APIService.Wrapped, completion:((_ data: APIService.Wrapped) -> ())?){
        /*
        错误处理
         */
        if let data = wrapped.data {
            Logger.print("data:\(data)")
        }
        else {
            Logger.print("error:\(wrapped.msg)")
        }
        completion?(wrapped)
    }
}
