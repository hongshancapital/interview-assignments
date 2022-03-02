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
    
    
    func run(_ request: URLRequest, completion:((_ data: APIService.Wrapped) -> ())?) { // 2
        let task = URLSession.shared.dataTask(with: request) { data, res, error in
            if let data = data {
                do  {
                    let data = try JSONSerialization.jsonObject(with: data, options: [])
                    Logger.print("data:\(data)")

                    let wrapped = APIService.Wrapped(code: 200, msg: "", data: data)
                    completion?(wrapped)
                    
                    return
                }
                catch{
                    
                }
            }
            else {
                Logger.print("error:\(error?.localizedDescription ?? "")")
            }
            
            let wrapped = APIService.Wrapped(code: 0, msg: error?.localizedDescription ?? "", data: nil)
            completion?(wrapped)
            
        }
        task.resume()
    }
}
