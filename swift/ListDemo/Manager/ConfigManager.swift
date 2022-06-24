//
//  ConfigManager.swift
//  ListDemo
//
//  Created by renhe on 2022/6/24.
//

import Foundation

typealias LoadResult = Result<(result: [ResultModel.InfoModel], hasMore: Bool), Error>

struct Config{
    static let keys = "ids"
    static let pageSize = 20
}

struct ConfigManager {
    var pageIndex = 0
    private var dataSource = [ResultModel.InfoModel]()
    mutating func loadAllData() throws {
        let dataJson = try Data(contentsOf: Bundle.main.url(forResource: "DataJson", withExtension: "json")!)
        let decodedData = try JSONDecoder().decode(ResultModel.self, from: dataJson)
        dataSource = decodedData.results ?? []
    }
    
    mutating func refresh() async -> LoadResult{
        pageIndex = 0
        return await loadMore()
    }
    
    func getDataSource() -> [ResultModel.InfoModel]{
        return dataSource
    }
    
    mutating func loadMore() async -> LoadResult{
        
        if self.dataSource.isEmpty {
            do {
                try loadAllData()
            } catch {
                return .failure(LoadError.loadError)
            }
        }
        let likeIds = UserDefaults.standard.stringArray(forKey: Config.keys)
        let startIndex = pageIndex * Config.pageSize
        
        let endIndex = (startIndex + Config.pageSize) >= self.dataSource.count ? self.dataSource.count : (startIndex + Config.pageSize)
        let reslut = self.dataSource[startIndex..<endIndex].map { info -> ResultModel.InfoModel in
            var value : ResultModel.InfoModel = info
            value.isLiked = likeIds?.contains(where: {$0 == "\(info.trackId)"}) ?? false
            return value
        }
        try? await Task.sleep(nanoseconds: 2_000_000_000)
        pageIndex += 1
        return .success((result:reslut, hasMore: !(endIndex >= dataSource.count)))
    }
    
}
enum LoadError : Error{
    case loadError
    var description: String{
        switch self {
        case .loadError:
            return "load failure"
        }
    }
}

