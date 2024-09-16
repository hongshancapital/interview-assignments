//
//  Network.swift
//  Homework
//
//  Created by Andy on 2022/9/5.
//  网络请求工具类

import Foundation


struct Network {
    
    /// GET请求
    static func get<T: Codable>(from urlString: String,
                                completeClosure: @escaping (T) -> Void,
                                errorClosure: @escaping (String) -> Void) {
        guard let url = URL(string: urlString) else {
            errorClosure("error: need a vaild url")
            return
        }
        
         URLSession.shared.dataTask(with: URLRequest(url: url)) { data, response, error in
            if let error = error {
                errorClosure("error: \(error)")
                return
            }
            guard let response = response as? HTTPURLResponse else {
                return
            }
            
            if response.statusCode == 200 {
                guard let data = data else { return }
                do {
                    let decoded = try JSONDecoder().decode(T.self, from: data)
                    DispatchQueue.main.async {
                        completeClosure(decoded)
                    }
                } catch let error {
                    errorClosure("error: \(error)")
                }
            } else {
                DispatchQueue.main.async {
                    errorClosure("error occurred. status code: \(response.statusCode)")
                }
            }
        }.resume()
        
    }
    
    /// POST请求
    static func post<T: Codable, R: Codable>(from urlString: String,
                                             request: R,
                                             completeClosure: @escaping (T) -> Void,
                                             errorClosure: @escaping (String) -> Void) {
        guard let url = URL(string: urlString) else {
            errorClosure("error: need a vaild url")
            return
        }
        
        guard let encoded = try? JSONEncoder().encode(request) else {
            errorClosure("error: ecode request data fail...")
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        URLSession.shared.uploadTask(with: request, from: encoded) { data, response, error in
            guard let _ = error else {
                errorClosure("request error: \(String(describing: error))")
                return
            }
            
            guard let response = response as? HTTPURLResponse else {
                return
            }
            
            if response.statusCode == 200 {
                guard let data = data else {
                    return
                }
                do {
                    let decoded = try JSONDecoder().decode(T.self, from: data)
                    completeClosure(decoded)
                } catch let error{
                    errorClosure("error: \(error)")
                }
            } else {
                errorClosure("error occurred. status code: \(response.statusCode)")
            }
        }.resume()
    }
    
}
