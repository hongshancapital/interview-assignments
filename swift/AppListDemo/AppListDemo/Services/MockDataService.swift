//
//  MockDataService.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/7.
//

import Foundation

private struct MockDataModel: Codable {
    var results: [AppModel]
}

actor MockDataService {
    static let shared = MockDataService()
    
    private var mockData: MockDataModel = load("appList.json")
    private var favouritedIds: Set<String> = []
    
    private static func load<T: Decodable>(_ filename: String) -> T {
        let data: Data
        
        guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
        else {
            fatalError("Couldn't find \(filename) in main bundle.")
        }
        
        do {
            data = try Data(contentsOf: file)
        } catch {
            fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
        }
        
        do {
            let decoder = JSONDecoder()
            return try decoder.decode(T.self, from: data)
        } catch {
            fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
        }
    }
}

// MARK: - RequestDataService
extension MockDataService: RequestDataService {
    func fetchAppList(atPage page: Int, pageCount: Int) async throws -> AppListResponseModel {
        // mocking network delay
        try await Task.sleep(for: Duration.seconds(0.5))
                
        let startIndex = max(0, page * pageCount)
        let endIndex = min(mockData.results.count - 1, (page + 1) * pageCount - 1)
        
        var appModels: [AppModel] = []
        if startIndex < endIndex {
            appModels = Array(mockData.results[startIndex...endIndex])
        }
        
        // query is favourited
        for i in appModels.indices {
            appModels[i].isFavourite = favouritedIds.contains(appModels[i].id)
        }
        
        return AppListResponseModel (
            code: .success,
            appModels: appModels
        )
    }
    
    func toggleFavouriteApp(_ appModel: AppModel) async throws -> FavouriteResponseModel {
        // mocking network delay
        try await Task.sleep(for: Duration.seconds(0.5))
        
        if favouritedIds.contains(appModel.id) {
            favouritedIds.remove(appModel.id)
        } else {
            favouritedIds.insert(appModel.id)
        }
        
        return FavouriteResponseModel(code: .success)
    }
}
