//
//  NetApi.swift
//  SCDT-Demo
//
//  Created by wuzhe on 12/12/22.
//

import Foundation
import Combine

struct AppListResponse: Codable{
    let results: [AppInfomation]
    let hasMore: Bool
    let page: Int
}

enum NetApiError: Error, CustomStringConvertible{
    case URLError
    case DecodingError(error: Error)
    case ResponseError(error: Error)
    case unknown
    
    var description: String{
        switch self{
        case .URLError:
            return "Invalid URL."
        case .DecodingError(let err):
            return "Invalid JSON format received. \(err.localizedDescription)"
        case .ResponseError(let err):
            return "Invalid Response: \(err.localizedDescription)"
        case .unknown:
            return "Invalid data recived"
        }
    }
    
}

struct NetApi{
    typealias DTOutput = URLSession.DataTaskPublisher.Output
    typealias DTFail = URLSession.DataTaskPublisher.Failure
    typealias DTPublisher = AnyPublisher<DTOutput,
                                         DTFail>
    
    static let shared = NetApi()
    
    func fetchPage(_ page: Int = 0) -> AnyPublisher<AppListResponse, Error> {
        let url = URL(string: "https://w9ujdh6f96.execute-api.us-west-1.amazonaws.com/teststage/page/\(page)")!
        let request = URLRequest(url: url, timeoutInterval: 10)
        
        return mapDataTaskPubisher(URLSession.shared.dataTaskPublisher(for: request).eraseToAnyPublisher())
    }
    
    func mapDataTaskPubisher(_ publisher: DTPublisher) -> AnyPublisher<AppListResponse, Error> {
        publisher
            .tryMap{ (data: Data, response: URLResponse) -> Data in
                guard let httpRes = response as? HTTPURLResponse, httpRes.statusCode == 200 else{
                    throw URLError(.badServerResponse)
                }
                return data
            }
            .decode(type: AppListResponse.self, decoder: JSONDecoder())
            .mapError({ error -> NetApiError in
                switch error {
                case is URLError:
                    return .ResponseError(error: error)
                case is DecodingError:
                    return .DecodingError(error: error)
                default:
                    return error as? NetApiError ?? .unknown
                }
            })
            .eraseToAnyPublisher()
    }
    
    
    func loadjsonarray() -> [AppInfomation]{
        let file = Bundle.main.url(forResource: "AppInfos", withExtension: "json")
        let data = try? Data(contentsOf: file!)
        var array:[AppInfomation] = []
        do{
            array = try JSONDecoder().decode([AppInfomation].self, from:data!)
        }
        catch{
            print(error)
        }
        return array
    }
}

