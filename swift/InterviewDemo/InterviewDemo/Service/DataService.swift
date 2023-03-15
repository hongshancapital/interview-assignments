//
//  DataService.swift
//  InterviewDemo
//
//  Created by Lays on 2023/3/15.
//

import Foundation

enum ServiceError: Error {
    case loadingFailure
    
    var localDescription: String {
        switch self {
        case .loadingFailure:
            return "Loading data failure, please try again!"
        }
    }
}

typealias DataResult = Result<(results: [AppMoel], hasMore: Bool), ServiceError>

protocol DataService {
    var pageSize: Int { get }
    
    func fetchData() async -> DataResult
    
    func refresh() async -> DataResult
    
    func updateCollectedStatus(byTrackId id: String)
}

class AppService: DataService {
    let pageSize: Int
    private var currentPage: Int = 0
    private struct ServerResult: Codable {
        let results:  [AppInfo]
        
        struct AppInfo: Codable {
            let trackId: Int
            let artworkUrl100: String
            let trackName: String
            let description: String
        }
    }
    private struct Constant {
        static let collectedApps = "collectedApps"
    }
    private var datas: [ServerResult.AppInfo] = []
    
    init(pageSize: Int) {
        self.pageSize = pageSize
    }
    
    // MARK: - Protocol Methods
    func fetchData() async -> DataResult {
        if datas.isEmpty {
            do {
                try fetchMockData()
            }catch {
                return .failure(ServiceError.loadingFailure)
            }
        }
        
        let start: Int = currentPage * pageSize
        currentPage += 1
        let end: Int = min(currentPage * pageSize, datas.count)
        let userCollectedApps = fetchUserCollectedApps()
        let results = datas[start..<end].map { appInfo in
            return AppMoel(
                trackId: String(appInfo.trackId),
                iconUrl: appInfo.artworkUrl100,
                trackName: appInfo.trackName,
                description: appInfo.description,
                collected: userCollectedApps.contains(String(appInfo.trackId))
            )
        }
        // Simulating request from server
        try? await Task.sleep(nanoseconds: 1_500_000_000)
        return .success((results: results, hasMore: !(end >= datas.count)))
    }
    
    func refresh() async -> DataResult {
        currentPage = 0
        return await fetchData()
    }
    
    func updateCollectedStatus(byTrackId id: String) {
        var userCollectedApps = fetchUserCollectedApps()
        if let index = userCollectedApps.firstIndex(of: id) {
            userCollectedApps.remove(at: index)
        } else {
            userCollectedApps.append(id)
        }
        UserDefaults.standard.set(userCollectedApps, forKey: Constant.collectedApps)
    }
    
    // MARK: - Private Methods
    private func fetchMockData() throws {
        let rawData = try Data(contentsOf: Bundle.main.url(forResource: "MockData", withExtension: "json")!)
        let decodedData = try JSONDecoder().decode(ServerResult.self, from: rawData)
        datas = decodedData.results
    }
    
    private func fetchUserCollectedApps() -> [String] {
        if let array = UserDefaults.standard.array(forKey: Constant.collectedApps),
           let liked = array as? [String] {
            return liked
        }
        return []
    }
    
}
