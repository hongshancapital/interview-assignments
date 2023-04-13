//
//  AppListViewModel.swift
//  APPList
//
//  Created by three on 2023/4/10.
//

import SwiftUI
import Combine

struct AppListRequsetModel {
    let pageId: Int
    let perPage: Int
}

enum LoadingState {
    case loading, failed, noMoreData
}

class AppListViewModel: ObservableObject {
    @Published var apps = [ListItemModel]()
    @Published var loadingState: LoadingState = .loading
    @Published var savedFavs: Set<Int> = []
    
    private var cancellable = Set<AnyCancellable>()
    var pageIndex = 0
    
    init() {
        self.savedFavs = LocalStorage().loadFavs()
    }
    
    func reloadData() {
        fetchAppsByCombine(pageId: 0)
    }
    
    func loadMoreData() {
        if self.loadingState == .noMoreData {
            return
        }
        Task {
            do {
                let index = pageIndex+1
                try await fetchApps(pageId: index)
                pageIndex = index
            } catch {
                print("Error: \(error)")
            }
        }
    }
    
    func fetchApps(pageId: Int, perPage: Int = 10) async throws {
        
        DispatchQueue.main.async {
            self.loadingState = .loading
        }
        let urlString = "http://www.test.com/getAppList"
        
        guard let url = URL(string: urlString) else {
            throw HttpError.badURL
        }
        
        let params = ["pageId": pageId, "perPage": perPage]
        
        let response: AppResult = try await APIService.shared.sendData(to: url, params: params, httpMethod: "Post")
        
        DispatchQueue.main.async {
            if pageId == 0 {
                self.apps.removeAll()
            }
            response.results.forEach { app in
                let isFavourited = self.contains(app.trackId)
                self.apps.append(ListItemModel(imagePath: app.artworkUrl100, title: app.trackName, info: app.description, isFavourite: isFavourited, trackId: app.trackId))
            }
            if (self.apps.count >= response.resultCount) {
                self.loadingState = .noMoreData
            }
        }
    }
    
    func contains(_ trackId: Int) -> Bool {
        return savedFavs.contains(trackId)
    }

    func updateFavourite(trackId: Int) {
        if savedFavs.contains(trackId) {
            savedFavs.remove(trackId)
        } else {
            savedFavs.insert(trackId)
        }
        LocalStorage().saveFavs(items: savedFavs)
    }
    
    func fetchAppsByCombine(pageId: Int, perPage: Int = 10) {
        _ = appListPublisher(pageId: pageId, perPage: perPage)
            .receive(on: DispatchQueue.main)
            .sink(receiveCompletion: { status in
                switch status {
                case .failure(let error):
                    print("Failed with error: \(error)")
                    return
                case .finished:
                    print("Succeesful")
                }
            }, receiveValue: { appResult in
                if pageId == 0 {
                    self.apps.removeAll()
                }
                appResult.results.forEach { app in
                    let isFavourited = self.contains(app.trackId)
                    self.apps.append(ListItemModel(imagePath: app.artworkUrl100, title: app.trackName, info: app.description, isFavourite: isFavourited, trackId: app.trackId))
                }
                if (self.apps.count >= appResult.resultCount) {
                    self.loadingState = .noMoreData
                }
            })
            .store(in: &cancellable)
    }
    
    func appListPublisher(pageId: Int = 0, perPage: Int = 10) -> AnyPublisher<AppResult, Error> {
        let urlString = "http://www.test.com/getAppList?pageId=\(pageId)&perPage=\(perPage)"
        
        guard let url = URL(string: urlString) else {
            return Fail(error: HttpError.badURL).eraseToAnyPublisher()
        }
        
        let session = URLSession.shared
                
        return session.dataTaskPublisher(for: url)
            .tryMap { element -> Data in
                guard let response = element.response as? HTTPURLResponse,
                        (200...299).contains(response.statusCode) else {
                    throw URLError(.badServerResponse)
                }
                return element.data
            }
            .decode(type: AppResult.self, decoder: JSONDecoder())
            .eraseToAnyPublisher()
    }
}
