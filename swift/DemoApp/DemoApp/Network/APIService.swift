//
//  DemoContentModel.swift
//  DemoApp
//
//  Created by Meteor 丶Shower on 2022/5/18.
//


import Foundation

public struct APIService {
    let baseURL = ""
    
    public static let shared = APIService()
    let decoder = JSONDecoder()
    
    
    public enum APIError: Error {
        case noResponse
        case jsonDecodingError(error: Error)
        case networkError(error: Error)
    }
    
    
    public enum Endpoint {
        case topFreeApplications(cid: String, country: String, limit: Int),
             topFreeiPadApplications(cid: String, country: String, limit: Int),
             topPaidApplications(cid: String, country: String, limit: Int),
             topPaidiPadApplications(cid: String, country: String, limit: Int),
             topGrossingApplications(cid: String, country: String, limit: Int),
             topGrossingiPadApplications(cid: String, country: String, limit: Int),
             newApplications(cid: String, country: String, limit: Int),
             newFreeApplications(cid: String, country: String, limit: Int),
             newPaidApplications(cid: String, country: String, limit: Int),
             searchApp(word: String, country: String, limit: Int),
             lookupApp(appid: String, country: String)

        func url() -> String {
            let url = APIService.shared.baseURL
            return url
        }
    }
    
    
    public func POST<T: Codable>(endpoint: Endpoint,
                         params: [String: String]?,
                         completionHandler: @escaping (Result<T, APIError>) -> Void) {
        let queryURL = endpoint.url()
        guard let url = URL(string: queryURL) else {
            debugPrint("error url: \(queryURL)")
            return
        }
        var components = URLComponents(url: url, resolvingAgainstBaseURL: true)!
        if let params = params {
            for (_, value) in params.enumerated() {
                components.queryItems?.append(URLQueryItem(name: value.key, value: value.value))
            }
        }
        debugPrint(components.url!)
        var request = URLRequest(url: components.url!, cachePolicy: .reloadIgnoringLocalAndRemoteCacheData, timeoutInterval: 60.0)
        request.httpMethod = "POST"
        request.setValue("iAppStore/1.0 Mobile/15E148 Safari/604.1", forHTTPHeaderField: "User-Agent")
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let data = data else {
                DispatchQueue.main.async {
                    completionHandler(.failure(.noResponse))
                }
                return
            }
            guard error == nil else {
                DispatchQueue.main.async {
                    completionHandler(.failure(.networkError(error: error!)))
                }
                return
            }
            guard let response = response as? HTTPURLResponse, response.statusCode == 200 else {
                DispatchQueue.main.async {
                    completionHandler(.failure(.noResponse))
                }
                return
            }
            do {
                let object = try self.decoder.decode(T.self, from: data)
                DispatchQueue.main.async {
                    completionHandler(.success(object))
                }
            } catch let error {
                DispatchQueue.main.async {
                    #if DEBUG
                    print("JSON Decoding Error: \(error)")
                    #endif
                    completionHandler(.failure(.jsonDecodingError(error: error)))
                }
            }
        }
        task.resume()
    }
    
    
    public func GET_JSON(endpoint: Endpoint,
                         params: [String: String]?,
                    completionHandler: @escaping (Result<Dictionary<String, Any>, APIError>) -> Void) {
        let queryURL = endpoint.url()
        var components = URLComponents(url: URL(string: queryURL)!, resolvingAgainstBaseURL: true)!
        if let params = params {
            for (_, value) in params.enumerated() {
                components.queryItems?.append(URLQueryItem(name: value.key, value: value.value))
            }
        }
        var request = URLRequest(url: components.url!)
        request.httpMethod = "GET"
        let task = URLSession.shared.dataTask(with: request) { (data, response, error) in
            guard let data = data else {
                DispatchQueue.main.async {
                    completionHandler(.failure(.noResponse))
                }
                return
            }
            guard error == nil else {
                DispatchQueue.main.async {
                    completionHandler(.failure(.networkError(error: error!)))
                }
                return
            }
            
            guard let response = response as? HTTPURLResponse, response.statusCode == 200 else {
                DispatchQueue.main.async {
                    completionHandler(.failure(.noResponse))
                }
                return
            }
            
            do {
                let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers)
                let object = json as! Dictionary<String, Any>
                DispatchQueue.main.async {
                    completionHandler(.success(object))
                }
            } catch let error {
                DispatchQueue.main.async {
                    #if DEBUG
                    print("JSON Decoding Error: \(error)")
                    #endif
                    completionHandler(.failure(.jsonDecodingError(error: error)))
                }
            }

            
        }
        task.resume()
    }
    
}
