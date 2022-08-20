//
//  JXNetworkServices.swift
//  SwiftHomework
//
//  Created by ljx on 2022/8/14.
//

import Combine
import Foundation
import UIKit

enum JXNetworkError:Error, CustomStringConvertible {
    
    case URLError
    case DecodeError
    case ResponseError(error:Error)
    case Unknown
    
    var description: String {
        switch self {
        case .URLError:
            return "Invalid Url";
        case .DecodeError:
            return "Decoding Error";
        case .ResponseError(let error):
            return "Network Error: \(error.localizedDescription)";
        case .Unknown:
            return "Unknown Network Error";
        }
    }
}

enum JXDataError: Error {
    case someError
}


class JXNetworkService {
    //初始化参数
    static let shared = JXNetworkService()
    var cancellable = Set<AnyCancellable>()
    
    //异步加载图片部分
    var semaphore = DispatchSemaphore(value: 2)
    var group = DispatchGroup.init()
    
    private init() {
        print("初始化service")
    }
    
    func getChatAppList() -> AnyPublisher<AppInfo,Error> {
        return self.urlRequest(urlString: "https://itunes.apple.com/search?entity=software&limit=50&term=social")
    }
    
    func urlRequest<T:Decodable>(urlString:String) -> AnyPublisher<T,Error> {
        let url = URL(string: urlString);
        guard let url = url else {
            return Fail(error: JXNetworkError.URLError).eraseToAnyPublisher()
        }
        
        return URLSession.shared.dataTaskPublisher(for: url).tryMap { element -> Data in
            guard let httpResponse = element.response as? HTTPURLResponse,
                  httpResponse.statusCode == 200 else {
                throw URLError(.badServerResponse)
            }
            return element.data
        }.decode(type: T.self, decoder: JSONDecoder())
            .mapError({ error -> JXNetworkError in
                switch error {
                case is URLError:
                    return JXNetworkError.ResponseError(error: error);
                case is DecodingError:
                    return JXNetworkError.DecodeError;
                default:
                    return error as? JXNetworkError ?? JXNetworkError.Unknown;
                }
            })
            .eraseToAnyPublisher()
    }
    
}


// MARK: 网络异步加载图片
extension JXNetworkService {
    
    func loadImageWithUrl(urlString:String)  -> AnyPublisher<UIImage,Error> {
        let queue = DispatchQueue.init(label: "netImagequeue", qos: .default, attributes: DispatchQueue.Attributes.concurrent, autoreleaseFrequency:.workItem, target: nil)
        let url = URL(string: urlString);
        guard let url = url else {
            return Fail(error:JXNetworkError.URLError)
                .eraseToAnyPublisher()
        }
        let request = URLRequest(url: url, cachePolicy: .returnCacheDataElseLoad, timeoutInterval:1)
        return URLSession.shared.dataTaskPublisher(for: request)
            .receive(on: queue)
            .handleEvents(receiveSubscription: { [weak self] _ in
                self?.semaphore.wait()
            }, receiveOutput: { _ in
             
            }, receiveCompletion: { [weak self] _ in
                self?.semaphore.signal()
            }, receiveCancel: {
                self.semaphore.signal()
            })
            .tryMap { element -> UIImage in
            guard let httpResponse = element.response as? HTTPURLResponse,
                  httpResponse.statusCode == 200 else {
                throw URLError(.badServerResponse)
            }
            guard let image = UIImage(data: element.data) else {
                throw JXNetworkError.DecodeError
            }
            return image
            }
            .mapError({error -> JXNetworkError in
            switch error {
            case is URLError:
                return JXNetworkError.ResponseError(error: error);
            case is DecodingError:
                return JXNetworkError.DecodeError;
            default:
                return error as? JXNetworkError ?? JXNetworkError.Unknown;
            }
        }).eraseToAnyPublisher()
    }
    
}

