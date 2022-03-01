//
//  ExampleApp.swift
//  Example
//
//  Created by 聂高涛 on 2022/3/1.
//


//  APIClient.swift
//  FoloPro
//
//  Created by GUNNER on 2021/7/28.
//

import Foundation
import Combine




struct APINetworking {
    
    struct Wrapped {
        var resultCount = 0
        var results : Any? = nil
    }
    
    
    func run(_ request: URLRequest, completion:((_ data: APINetworking.Wrapped) -> ())?) { // 2
        let task = URLSession.shared.dataTask(with: request) { data, res, error in
            if let data = data {
                do  {
                    let data = try JSONSerialization.jsonObject(with: data, options: [])
                    var wrapped = APINetworking.Wrapped()
                    if let dicValue = data as? [String:Any]{
                        wrapped.resultCount = dicValue["resultCount"] as? Int ?? 0;
                        wrapped.results = dicValue["results"]
                        print("\(wrapped.results)")
                        completion?(wrapped)

                        return
                    }
                }
                catch{
                    
                }
            }
            
            let wrapped = APINetworking.Wrapped()
            completion?(wrapped)
            
        }
        task.resume()
    }
}
