//
//  NetworkService.swift
//  Network
//
//  Created by shencong on 2022/6/10.
//

import Foundation
import Combine
import SwiftUI

let Kboundary: String = "----WebKitFormBoundary"

// 请求方式
enum RequestMethod: String {
    case GET = "GET"
    case POST = "POST"
    case FORM = "FORM"
    case RAW = "RAW"
}

class NetworkService {
    
    static let shared = NetworkService()
    private init() {}
    
    func fetch<T>(with request: NNetworkRequest) -> AnyPublisher<T, Error> where T: Codable  {
        
        guard let reachability = try? Reachability.init(hostname: "https://developer.apple.com"),reachability.connection != .unavailable  else {
            return Fail(error: NetworkError.notNetwork).eraseToAnyPublisher()
        }
        
        if request.host.isEmpty {
            return Fail(error: NetworkError.URLError).eraseToAnyPublisher()
        }
        // Create an instance of JSONDecoder to decode our incoming data.
        let decoder = JSONDecoder()
        // We can use the built in convertFromSnakeCase keyDecodingStrategy to convert the incoming snake case to camel case.
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        var requestString: String = request.host + request.methodName
        switch request.requestMethod {
        case .GET:
            if !request.params.isEmpty {
                // 排序 - 根据字典 key 首字母
                let array = request.params.sorted{ (t1, t2) -> Bool in
                    return t1.0 < t2.0
                }
                // 变形 - “k=v”
                let stringArray = array.map{ (key: String, value: Any) -> String in
                    return String(format:"%@=%@",key,String(describing: value))
                }
                // 拼接 - “k1=v1&k2=v2&k3=v3...”
                let str = stringArray.joined(separator:"&")
                requestString = requestString + "?" + str;
            }
            break
        default: break
        }
        guard let requestString = requestString.addingPercentEncoding(withAllowedCharacters: CharacterSet.urlQueryAllowed) else {
            return Fail(error: NetworkError.URLError).eraseToAnyPublisher()
        }
        let url = URL.init(string: requestString)
        guard let url = url else {
            return Fail(error: NetworkError.URLError).eraseToAnyPublisher()
        }
        let buildRequestResult = buildMutableRequest(with: url, request: request)
        if let error = buildRequestResult.error {
            return Fail(error: error).eraseToAnyPublisher()
        }
        return URLSession.shared.dataTaskPublisher(for: buildRequestResult.request)
            .tryMap { element -> Data in
                debugPrint(requestString + " ==> " + (String.init(data: element.data, encoding: String.Encoding.utf8) ?? ""))

                guard let httpRespnse = element.response as? HTTPURLResponse, httpRespnse.statusCode == 200 else {
                    throw URLError(.badServerResponse)
                }
                return element.data
            }
            .decode(type: T.self, decoder: decoder)
            .receive(on: DispatchQueue.main)
            .mapError { error -> NetworkError in
                // 转换为自定义Error
                switch error {
                case is URLError:
                    return .ResponseError(error: error)
                case is DecodingError:
                    return .DecodeError
                default :
                    return error as? NetworkError ?? .unknown
                }
            }
            .eraseToAnyPublisher()
    }
}

extension NetworkService {
    func buildMutableRequest(with url: URL, request: NNetworkRequest) -> (request: URLRequest, error: Error?) {
        var mutableRequest: URLRequest = URLRequest.init(url: url)
        mutableRequest.timeoutInterval = request.timeout
        if(!request.header.isEmpty) {
            for (key, value) in request.header {
                mutableRequest.addValue(value, forHTTPHeaderField: key);
            }
        }
        if (request.requestMethod == .GET) {
            mutableRequest.httpMethod = "GET"
        } else {
            mutableRequest.httpMethod = "POST"
            if request.requestMethod == .FORM {
                mutableRequest.setValue("multipart/form-data; boundary=\(Kboundary)", forHTTPHeaderField: "Content-Type")
                mutableRequest.httpBody = buildFormData(with: request)
    
            } else if request.requestMethod == .POST {
                if !request.params.isEmpty {
                    let postString = request.params.compactMap({ (key, value) -> String in
                        return "\(key)=\(String(describing: value))"
                    }).joined(separator: "&")
                    mutableRequest.httpBody = postString.data(using: .utf8)
                }
            } else if request.requestMethod == .RAW {
                guard let httpBody: Data = try? JSONSerialization.data(withJSONObject: request.params, options: []) else {
                    return (mutableRequest, NetworkError.EncodeError)
                    //Fail(error: NetworkError.EncodeError).eraseToAnyPublisher()
                }
                mutableRequest.httpBody = httpBody
            }
        }
        return (mutableRequest, nil)
    }
}

extension NetworkService {
    // 拼接表单格式
    func buildFormData(with request: NNetworkRequest) -> Data {
        let KNewLine = "\r\n".toData()
        var mdata = Data()
        //01 文件参数
        /*
         --分隔符
         Content-Disposition: form-data; name="file"; filename="Snip20160716_103.png"
         Content-Type: image/png
         空行
         文件数据
         */
        if !request.files.isEmpty {
            for (_, fileData) in request.files {
                mdata.append("--\(Kboundary)".toData())
                mdata.append(KNewLine)
                let content = "Content-Disposition: form-data; name=\(fileData.name); filename=\(fileData.filename)".toData()
                mdata.append(content)
                mdata.append(KNewLine)

                //Content-Type 文件的数据类型
                mdata.append("Content-Type: \(fileData.contentType)".toData())
                mdata.append(KNewLine)
                mdata.append(KNewLine)

                // 文件
                mdata.append(fileData.data)
                mdata.append(KNewLine)
            }
        }

        //02 非文件参数
        /*
         --分隔符
         Content-Disposition: form-data; name="username"
         空行
         xiaomage
         */
        if !request.params.isEmpty {
            for (key, value) in request.params {
                mdata.append("--\(Kboundary)".toData())
                mdata.append(KNewLine)
                //username 参数名称
                let keyContent = "Content-Disposition: form-data; name=\(key)".toData()
                mdata.append(keyContent)
                mdata.append(KNewLine)
                mdata.append(KNewLine)
                mdata.append(String(describing: value).toData())
                mdata.append(KNewLine)

            }
        }
        //03 结尾标识
        /*
         --分隔符--
         */
        mdata.append("--\(Kboundary)--".toData())
        return mdata
    }
}



