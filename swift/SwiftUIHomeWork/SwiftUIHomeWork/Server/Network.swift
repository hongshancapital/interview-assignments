//
//  Network.swift
//  SwiftUIHomeWork
//
//  Created by quanwei chen on 2022/9/4.
//

import Foundation
struct Network{
    
    func httpGet<T:Codable>(from url:String,  complete:@escaping(T)->Void, onerror: @escaping (String)->Void){
        guard let urlUl = URL(string: url) else {
            print("missing url")
            onerror("missing url")
            return
        }
        
        let urlRequest = URLRequest(url: urlUl)
        
        let dataTask = URLSession.shared.dataTask(with: urlRequest){ (data, response, error) in
            if let error = error {
                print("request error:", error)
                onerror("request error: \(error)")
                return
            }
            guard let response  = response as? HTTPURLResponse else {return}
            
            if response.statusCode == 200{
                guard let data = data else {return}
                do{
                    let decoded = try JSONDecoder().decode(T.self, from: data)
                    DispatchQueue.main.async {
                        complete(decoded)
                    }
                }catch let error{
                    print("error decoding:", error)
                    onerror("error decoding: \(error)")
                }
            }else{
                DispatchQueue.main.async {
                    onerror("error occurred. status code: \(response.statusCode)")
                }
            }
        }
        
        dataTask.resume()
    }
    
    func httpPost<T:Codable, R:Codable>(from url:String, request:R, complete: @escaping (T)->Void, onerror: @escaping (String)->Void){
        guard let urlUl = URL(string: url) else {
            print("missing url")
            onerror("missing url")
            return
        }
        
        guard let encoded = try? JSONEncoder().encode(request) else{
            print("encode request data error")
            onerror("encode request data error")
            return
        }
        
        var urlRequest = URLRequest(url: urlUl)
        urlRequest.httpMethod = "POST"
        urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        
        let dataTask = URLSession.shared.uploadTask(with: urlRequest, from: encoded){ (data, response, error) in
            if let error = error {
                print("request error:", error)
                onerror("request error: \(error)")
                return
            }
            guard let response  = response as? HTTPURLResponse else {return}
            
            if response.statusCode == 200{
                guard let data = data else {return}
                do{
                    let decoded = try JSONDecoder().decode(T.self, from: data)
                    complete(decoded)
                }catch let error{
                    print("error decoding:", error)
                    onerror("error decoding: \(error)")
                }
            }else{
                onerror("error occurred. status code: \(response.statusCode)")
            }
        }
        
        dataTask.resume()
    }
}
