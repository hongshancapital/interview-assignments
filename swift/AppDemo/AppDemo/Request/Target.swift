//
// Created by Jeffrey Wei on 2022/6/29.
// Target协议,基于此协议来构造Api枚举
// 简单的仿Moya结合Combine实现基础功能
//

import Foundation
import Combine

// 自建后台api地址,数据为需求上的link地址的数据(link上的数据好像有的id不同或者没有,这里统一了下
// 注意:后台为简单搭建,并无用户体系,尽量不要多人同时跑此demo,否则可能点击收藏的逻辑会混乱
private let domain = "http://demo-api.jeffreywei.cn"

enum RequestMethod {
    case get
    case post
}

protocol Target {
    // 请求的子路径
    var urlString: String { get }
    // 请求的method
    var method: RequestMethod { get }
    // 请求的参数
    var data: [String: LosslessStringConvertible] { get }
}

extension Target {
    private var publisher: URLSession.DataTaskPublisher {
        switch method {
        case .get:
            var urlComponents = URLComponents(string: "\(domain)/\(urlString)")!
            urlComponents.queryItems = data.keys.compactMap { key in
                URLQueryItem(name: key, value: data[key]?.description)
            }
            let url = urlComponents.url!
            return URLSession.shared.dataTaskPublisher(for: url)
        case .post:
            var request = URLRequest.init(url: URL(string: "\(domain)/\(urlString)")!)
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
            request.httpMethod = "POST"
            request.httpBody = try? JSONSerialization.data(withJSONObject: data)
            return URLSession.shared.dataTaskPublisher(for: request)
        }
    }
}

extension Target {
    ///
    /// target的sink函数,请求的简单封装,内部会解析解析后台返回的response,code不为0会抛出一个BusinessError类型的业务错误,
    /// 否则直接将使用dataCls解析出data并返回供外部使用
    /// 回调函数将会在在主线程
    /// - Parameters:
    ///   - dataCls:解析后台返回数据结构Response里的data数据的类型
    ///   - receiveCompletion:完成和失败的回调
    ///   - receiveValue:接收到值的回调
    /// - Returns:可取消的AnyCancellable
    func sinkResponseData<T: Decodable>(dataCls: T.Type,
                                        receiveCompletion: @escaping (Subscribers.Completion<Error>) -> Void,
                                        receiveValue: @escaping (T?) -> Void) -> AnyCancellable {
        publisher.map {
                    $0.data
                }
                .decode(type: Response<T>.self, decoder: JSONDecoder())
                .tryMap { response -> T? in
                    switch response.code {
                    case 0:
                        return response.data
                    default:
                        throw BusinessError(code: response.code, message: response.message)
                    }
                }
                .receive(on: RunLoop.main)
                .sink(receiveCompletion: receiveCompletion, receiveValue: receiveValue)
    }
}
