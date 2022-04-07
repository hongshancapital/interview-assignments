//
//  MockRepository.swift
//  InterviewDemo
//
//  Created by Chenjun Ren on 2022/4/1.
//

import Foundation

class MockRepository: AppInfoRepository {
    let numberPerFetch: Int
    private var nextIndexToFetch = 0
    private var data = [QueryResult.AppInfo]()
    
    init(numberPerFetch: Int) {
        self.numberPerFetch = numberPerFetch
    }
    
    func refresh() async -> FetchResult {
        nextIndexToFetch = 0
        return await fetchAppInfos()
    }
    
    func fetchAppInfos() async -> FetchResult {
        if data.isEmpty {
            do {
                try fetchAll()
            } catch {
                return .failure(.fetchingFailure)
            }
        }
        
        let userLikedApps = fetchUserLikedApps()
        let startIndex = nextIndexToFetch
        let tempEndIndex = nextIndexToFetch + numberPerFetch
        nextIndexToFetch = tempEndIndex >= data.count ? data.count : tempEndIndex
        let result = data[startIndex..<nextIndexToFetch]
            .map {
                AppInfo(
                    id: String($0.trackId),
                    name: $0.trackName,
                    description: $0.description,
                    iconUrl: $0.artworkUrl100,
                    isLiked: userLikedApps.contains(String($0.trackId))
                )
            }
        
        // simulating fetching from remote server
        try? await Task.sleep(nanoseconds: 2_000_000_000)
        return .success((result: result, hasMore: !(nextIndexToFetch >= data.count)))
    }
    
    func updateLikeStatus(byAppId id: String) {
        var userLikedApps = fetchUserLikedApps()
        if let index = userLikedApps.firstIndex(of: id) {
            userLikedApps.remove(at: index)
        } else {
            userLikedApps.append(id)
        }
        UserDefaults.standard.set(userLikedApps, forKey: Constant.keyStr)
    }
    
    // MARK: - Helpers
    
    private func fetchAll() throws {
        let rawData = try Data(contentsOf: Bundle.main.url(forResource: "MockData", withExtension: "json")!)
        let decodedData = try JSONDecoder().decode(QueryResult.self, from: rawData)
        data = decodedData.results
    }
    
    private func fetchUserLikedApps() -> [String] {
        if let array = UserDefaults.standard.array(forKey: Constant.keyStr),
           let liked = array as? [String] {
            return liked
        }
        return []
    }
    
    // MARK: - Types
    
    private struct Constant {
        static let keyStr = "likedApps"
    }
    
    private struct QueryResult: Codable {
        let results: [AppInfo]
        
        struct AppInfo: Codable {
            let trackId: Int
            let trackName: String
            let description: String
            let artworkUrl100: String
        }
    }
}
