//
//  RefreshDataRequest.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/5/3.
//

import Foundation
import Combine

/*
 数据获取协议
 */
protocol DataTaskPublisher {
    func dataTaskPublisher(for url: URL) -> AnyPublisher<URLSession.DataTaskPublisher.Output , URLError>
}

/*
 默认采用UrlSession进行数据获取
 */
struct UrlSessionDataTaskPublisher : DataTaskPublisher {
    func dataTaskPublisher(for url: URL) -> AnyPublisher<URLSession.DataTaskPublisher.Output , URLError>{
        return URLSession.shared.dataTaskPublisher(for: url).eraseToAnyPublisher()
    }
}

/*
 拉取App信息请求
 */

struct FetchDataRequest {
    let modelUrl = "https://itunes.apple.com/search"
    let entity: String
    let term : String
    let limit : Int
    let offset : Int
    var publisherProvider: DataTaskPublisher = UrlSessionDataTaskPublisher()
    ///默认使用URLSession
    var publisher : AnyPublisher<[AppInfo], Error> {
        let requestUrl = buildSearchUrl(with: entity, term: term, offset: offset, limit: limit)
        return publisherProvider.dataTaskPublisher(for: requestUrl)
            .map {$0.data}
            .decode(type: AppList.self, decoder: JSONDecoder())
            .map {$0.results}
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
    
    private func buildSearchUrl(with entity : String, term : String ,offset: Int, limit : Int) -> URL {
        guard let url = URL(string: modelUrl) else {
            assertionFailure("[RefreshDataRequest] Wrong URL!")
            return URL(fileURLWithPath: "")
        }
        var usingUrl = url
        usingUrl.append(queryItems: [URLQueryItem(name: "entity", value: entity),
                                    URLQueryItem(name: "term", value: term),
                                    URLQueryItem(name: "offset", value: "\(offset)"),
                                    URLQueryItem(name: "limit", value: "\(limit)")])
        
        return usingUrl
    }    
   
}
