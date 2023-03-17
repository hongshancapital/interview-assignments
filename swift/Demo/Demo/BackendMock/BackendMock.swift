//
//  BackendMock.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import Foundation

let appDecoder: JSONDecoder = {
    let decoder = JSONDecoder()
    decoder.keyDecodingStrategy = .convertFromSnakeCase
    return decoder
}()

func loadData<T: Decodable>(fileName: String) throws -> T {
    guard let url = Bundle.main.url(forResource: fileName, withExtension: "json") else {
        fatalError("未发现文件\(fileName)")
    }
    
    let data: Data
    do {
        data = try Data(contentsOf: url)
    } catch {
        fatalError("\(fileName)读取失败，原因：\(error.localizedDescription)")
    }
    
    return try appDecoder.decode(T.self, from: data)
}

struct MockData: Decodable {
    @DecodableDefault.Zero var resultCount: Int
    @DecodableDefault.EmptyList var results: [ArtistModel]
}

var mockData: MockData = {
    return try! loadData(fileName: "mockData")
}()

enum MockApiError: Error, CustomStringConvertible {
    case transError(des: String)
    case paramException // 参数异常
    
    var description: String {
        switch self {
        case .transError(let des):
            return des
        case .paramException:
            return "参数异常"
        }
    }
}

// mock api
struct MockApi {
    
    static func artistListRequest(_ params: [String: Any]) async throws -> [ArtistModel] {
        guard let page = params["page"] as? Int, let pageSize = params["pageSize"] as? Int else {
            throw MockApiError.paramException
        }
        guard pageSize > 0 else { return [] }
        let total = mockData.results.count
        var totalPage = total / pageSize
        if total % pageSize > 0 {
            totalPage += 1
        }
        guard page < totalPage else { return [] }
        
        let start = page * pageSize
        let remain = total - start
        let offset = remain > pageSize ? pageSize : remain
        print("start: \(start), offset: \(offset)")
        let result = Array(mockData.results[start ..< (offset + start)])
        // mock network
        try await Task.sleep(nanoseconds: 1 * 1_000_000_000)
        return result
    }
    
    static func likeRequest(_ params: [String: Any]) async throws {
        guard let isLike = params["isLike"] as? Bool, let trackId = params["trackId"] as? Int else {
            throw MockApiError.paramException
        }
        guard let index = mockData.results.firstIndex(where: { $0.trackId == trackId }) else {
            throw MockApiError.transError(des: "no existed \(trackId)")
        }
        mockData.results[index].isLike = isLike
    }
}
