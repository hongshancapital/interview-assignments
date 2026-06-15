//
//  NetworkService.swift
//  ListDemo
//
//  Created by Chr1s on 2022/2/21.
//

import Foundation
import SwiftUI
import Combine

enum FetchListError: Error, CustomStringConvertible {
    
    case fileNotExist
    case fileReadError(error: Error)
    case decodeError(error: Error)
    case unknown

    var description: String {
        switch self {
        case .fileNotExist:
            return "File not Exist!"
        case .fileReadError(let error):
            return "File read error: \(error.localizedDescription)"
        case .decodeError(let error):
            return "File decode error: \(error.localizedDescription)"
        case .unknown:
            return "[Error unknown]"
        }
    }
}

class ApiService: ApiProtocol {
    
    let userDefaults = UserDefaults.standard
    var isFavoriteArray: [Bool]? = nil

    // 获取列表数据
    func fetchListData() -> AnyPublisher<[ListCell], FetchListError> {
        return readJSONFile()
    }
    
    // 获取`收藏`状态
    func fetchFavoriteData() -> [Bool] {
        let favorite = userDefaults.object(forKey: "isFavorite") as? [Bool]
        
        guard favorite != nil else {
            return []
        }
        return favorite!
    }
    
    // 更新`喜欢`状态
    func updateListData(id: Int, isFavorite: Bool) -> AnyPublisher<[Bool], Never> {
        
        var favorite = userDefaults.object(forKey: "isFavorite") as? [Bool]
        
        guard favorite != nil else {
            return Just([]).eraseToAnyPublisher()
        }
        
        guard id >= 0 && id < favorite!.count else {
            return Just([]).eraseToAnyPublisher()
        }
            
        favorite![id] = isFavorite
        userDefaults.set(favorite, forKey: "isFavorite")
        return Just(favorite!).eraseToAnyPublisher()
    }
    
    // MARK: - 初始化`收藏`状态数组并持久化存储
    private func initFavoriteStates(_ count: Int) {
        if isFavoriteArray == nil && count > 0 {
            isFavoriteArray = [Bool](repeating: false, count: count)
            userDefaults.set(isFavoriteArray, forKey: "isFavorite")
        }
    }
    
    // MARK: - 读取文件获取JSON内容,模拟服务端请求返回JSON
    private func readJSONFile() -> AnyPublisher<[ListCell], FetchListError> {
        return Future<[ListCell], FetchListError> { promise in
            if let url = Bundle.main.url(forResource: "mockAPI", withExtension: "txt") {
                do {
                    let data = try Data(contentsOf: url)
                    do {
                        let listData = try JSONDecoder().decode(ListDataModel.self, from: data)
                        self.initFavoriteStates(listData.resultCount)
                        promise(.success(listData.results))
                    } catch {
                        promise(.failure(FetchListError.decodeError(error: error)))
                    }
                } catch {
                    promise(.failure(FetchListError.fileReadError(error: error)))
                }
            } else {
                promise(.failure(FetchListError.fileNotExist))
            }
        }.eraseToAnyPublisher()
    }
}
