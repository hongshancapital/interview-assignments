//
//  TRXHTTPClient.swift
//  STBaseProject
//
//  Created by stack on 2018/12/10.
//  Copyright © 2018年 ST. All rights reserved.
//

import UIKit

public enum STRequestTask {
    case data
    case download
    case upload
    case stream
}

public enum STHTTPMethod: String {
    case options = "OPTIONS"
    case get     = "GET"
    case head    = "HEAD"
    case post    = "POST"
    case put     = "PUT"
    case patch   = "PATCH"
    case delete  = "DELETE"
    case trace   = "TRACE"
    case connect = "CONNECT"
}

public class STHTTPClient: NSObject {

    open var timeout: TimeInterval = 6
    public static let shareSession: STHTTPClient = STHTTPClient()
    
    private func st_http(request: URLRequest, requestTask: STRequestTask, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) {
        switch requestTask {
        case .data:
            self.st_http(request: request, completionHandler: completionHandler)
            break
        case .download:
            break
        case .upload:
            break
        case .stream:
            break
        }
    }
    
    private func st_http(request: URLRequest, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) {
        self.session.dataTask(with: request) { (data, response, error) in
            DispatchQueue.main.async {
                completionHandler(data, response, error)
            }
        }
    }
    
    public func st_encode(urlRequest: URLRequest, with parameters: Dictionary<String, Any>?) throws -> URLRequest {
        guard let parameters = parameters else { return urlRequest }
        var request = urlRequest
        if let method = STHTTPMethod(rawValue: request.httpMethod ?? "GET"), st_encodesParametersInURL(with: method) {
            guard let url = request.url else {
                throw NSError.init(domain: "missingURL", code: 0, userInfo: [:])
            }
            if var urlComponents = URLComponents(url: url, resolvingAgainstBaseURL: false), !parameters.isEmpty {
                let percentEncodedQuery = (urlComponents.percentEncodedQuery.map { $0 + "&" } ?? "") + self.st_query(parameters)
                urlComponents.percentEncodedQuery = percentEncodedQuery
                request.url = urlComponents.url
            }
        } else {
            if request.value(forHTTPHeaderField: "Content-Type") == nil {
                request.setValue("application/x-www-form-urlencoded; charset=utf-8", forHTTPHeaderField: "Content-Type")
            }
            request.httpBody = self.st_query(parameters).data(using: .utf8, allowLossyConversion: false)
        }
        return request
    }
    
    private func st_encodesParametersInURL(with method: STHTTPMethod) -> Bool {
        switch method {
        case .get, .head, .delete:
            return true
        default:
            return false
        }
    }
    
    private func st_query(_ parameters: [String: Any]) -> String {
       var components: [(String, String)] = []
       for key in parameters.keys.sorted(by: <) {
           let value = parameters[key]!
           components += self.st_queryComponents(fromKey: key, value: value)
       }
       return components.map { "\($0)=\($1)" }.joined(separator: "&")
    }
    
    private func st_queryComponents(fromKey key: String, value: Any) -> [(String, String)] {
       var components: [(String, String)] = []
       if let dictionary = value as? [String: Any] {
            for (nestedKey, value) in dictionary {
                components += st_queryComponents(fromKey: "\(key)[\(nestedKey)]", value: value)
            }
       } else if let array = value as? [Any] {
            for value in array {
                components += st_queryComponents(fromKey: "\(key)[]", value: value)
            }
       } else if let value = value as? NSNumber {
            components.append((st_escape(key), st_escape("\(value)")))
       } else if let bool = value as? Bool {
            components.append((st_escape(key), st_escape(bool ? "1" : "0")))
       } else {
            components.append((st_escape(key), st_escape("\(value)")))
       }
       return components
    }
    
    private func st_escape(_ string: String) -> String {
        let generalDelimitersToEncode = ":#[]@" // does not include "?" or "/" due to RFC 3986 - Section 3.4
        let subDelimitersToEncode = "!$&'()*+,;="
        var allowedCharacterSet = CharacterSet.urlQueryAllowed
        allowedCharacterSet.remove(charactersIn: "\(generalDelimitersToEncode)\(subDelimitersToEncode)")
        var escaped = ""
        if #available(iOS 8.3, *) {
            escaped = string.addingPercentEncoding(withAllowedCharacters: allowedCharacterSet) ?? string
        } else {
            let batchSize = 50
            var index = string.startIndex
            while index != string.endIndex {
                let startIndex = index
                let endIndex = string.index(index, offsetBy: batchSize, limitedBy: string.endIndex) ?? string.endIndex
                let range = startIndex..<endIndex
                let substring = string[range]
                escaped += substring.addingPercentEncoding(withAllowedCharacters: allowedCharacterSet) ?? String(substring)
                index = endIndex
            }
        }
        return escaped
    }
    
    lazy var session: URLSession = {
        let configuration: URLSessionConfiguration = URLSessionConfiguration.default
        let session = URLSession(configuration: configuration, delegate: self, delegateQueue: nil)
        return session
    } ()
}

//MARK: get request
public extension STHTTPClient {
    func st_httpGetRequest(urlString: String, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) {
       self.st_httpGetRequest(urlString: urlString, parameters: [:], completionHandler: completionHandler)
   }
   
   func st_httpGetRequest(urlString: String, parameters: Dictionary<String, Any>, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) {
       self.st_httpGetRequest(urlString: urlString, parameters: parameters, httpHeaderFields: [:], completionHandler: completionHandler)
   }
   
   /*
   *  httpHeaderFields = ["Content-Type" : "application/json"]
   */
   func st_httpGetRequest(urlString: String?, parameters: Dictionary<String, Any>?, httpHeaderFields: Dictionary<String, String>?, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) {
       guard let newUrlStr = urlString, newUrlStr.count > 0 else {
           DispatchQueue.main.async {
                completionHandler(Data(), URLResponse(), NSError.init(domain: "missingURL", code: 0, userInfo: [:]))
           }
           return
       }
       
       if let url = URL.init(string: newUrlStr) {
           var urlRequest = URLRequest(url: url)
           urlRequest.timeoutInterval = self.timeout
           urlRequest.httpMethod = STHTTPMethod.get.rawValue
           if let headers = httpHeaderFields {
               for (headerField, headerValue) in headers {
                   urlRequest.setValue(headerValue, forHTTPHeaderField: headerField)
               }
           }
           do {
               let reqeust = try self.st_encode(urlRequest: urlRequest
               , with: parameters)
               self.st_http(request: reqeust, completionHandler: completionHandler)
           } catch (let error) {
                DispatchQueue.main.async {
                     completionHandler(Data(), URLResponse(), error)
                }
           }
       } else {
            DispatchQueue.main.async {
                 completionHandler(Data(), URLResponse(), NSError.init(domain: "missingURL", code: 0, userInfo: [:]))
            }
       }
   }
}

//MARK: post request
public extension STHTTPClient {
    func st_httpPostRquest(urlString: String, httpBody: Data, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) {
        self.st_httpPostRquest(urlString: urlString, httpBody: httpBody, httpHeaderFields: [:], completionHandler: completionHandler)
    }
    
    /*
     *  httpHeaderFields = ["Content-Type" : "application/json"]
     */
    func st_httpPostRquest(urlString: String?, httpBody: Data, httpHeaderFields: Dictionary<String, String>?, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) {
        guard let newUrlStr = urlString, newUrlStr.count > 0 else {
            DispatchQueue.main.async {
                 completionHandler(Data(), URLResponse(), NSError.init(domain: "missingURL", code: 0, userInfo: [:]))
            }
            return
        }
        
        if let url = URL.init(string: newUrlStr) {
            var urlRequest = URLRequest(url: url)
            urlRequest.httpBody = httpBody
            urlRequest.timeoutInterval = self.timeout
            urlRequest.httpMethod = STHTTPMethod.post.rawValue
            if let headers = httpHeaderFields {
                for (headerField, headerValue) in headers {
                    urlRequest.setValue(headerValue, forHTTPHeaderField: headerField)
                }
            } else {
                urlRequest.setValue("application/x-www-form-urlencoded; charset=utf-8", forHTTPHeaderField: "Content-Type")
            }
            self.st_http(request: urlRequest, requestTask: .data, completionHandler: completionHandler)
        } else {
            DispatchQueue.main.async {
                 completionHandler(Data(), URLResponse(), NSError.init(domain: "missingURL", code: 0, userInfo: [:]))
            }
        }
    }
    
    func st_httpPostRquest(urlString: String, parameters: Dictionary<String, Any>, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) {
        self.st_httpPostRquest(urlString: urlString, parameters: parameters, httpHeaderFields: [:], completionHandler: completionHandler)
    }
    
    /*
     *  httpHeaderFields = ["Content-Type" : "application/json"]
     */
    func st_httpPostRquest(urlString: String?, parameters: Dictionary<String, Any>?, httpHeaderFields: Dictionary<String, String>?, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) {
        guard let newUrlStr = urlString, newUrlStr.count > 0 else {
            DispatchQueue.main.async {
                 completionHandler(Data(), URLResponse(), NSError.init(domain: "missingURL", code: 0, userInfo: [:]))
            }
            return
        }
        
        if let url = URL.init(string: newUrlStr) {
            var urlRequest = URLRequest(url: url)
            urlRequest.timeoutInterval = self.timeout
            urlRequest.httpMethod = STHTTPMethod.post.rawValue
            if let headers = httpHeaderFields {
                for (headerField, headerValue) in headers {
                    urlRequest.setValue(headerValue, forHTTPHeaderField: headerField)
                }
            } else {
                urlRequest.setValue("application/x-www-form-urlencoded; charset=utf-8", forHTTPHeaderField: "Content-Type")
            }
            do {
                let reqeust = try self.st_encode(urlRequest: urlRequest
                , with: parameters)
                self.st_http(request: reqeust, completionHandler: completionHandler)
            } catch (let error) {
                DispatchQueue.main.async {
                     completionHandler(Data(), URLResponse(), error)
                }
            }
        } else {
            DispatchQueue.main.async {
                 completionHandler(Data(), URLResponse(), NSError.init(domain: "missingURL", code: 0, userInfo: [:]))
            }
        }
    }
}

extension STHTTPClient: URLSessionTaskDelegate {
    public func urlSession(_ session: URLSession, task: URLSessionTask, didCompleteWithError error: Error?) {
        
    }
}
