//
//  ArtWorkFetcher.swift
//  HSDemo
//

//

import Foundation
import SwiftUI
class ArtWorkFetcher: ObservableObject {
    @Published var artWorkList: [ArtWork] = []
    @Published var isLoading: Bool = true
    @Published var errorMessage: String?
    
    @Published var imageCaches: [String: Image] = [:]
    @Published var isFavourite: [String: Bool] = [:]
    
    @Published var headerRefreshing: Bool = false
    @Published var footerRefreshing: Bool = false
    @Published var noMore: Bool = false
    
    let service: APIServiceProtocol
    
    init(service: APIServiceProtocol = APIService()) {
        self.service = service
        fetchArtWorks()
    }
    
    func fetchArtWorks() {
        isLoading = true
        errorMessage = nil
        
        let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=10&term=chat")
        service.fetchArtWorks(url: url) { [unowned self] result in
            DispatchQueue.main.async {
                switch result {
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                case .success(let listData):
                  
                    self.artWorkList = listData
                }
                self.isLoading = false
                self.footerRefreshing = false
                self.noMore = (self.artWorkList.count >= 50)
            }
        }
    }
    
    func fetchMoreArtWorks() {
        let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=10&term=chat")
        service.fetchArtWorks(url: url) { [unowned self] result in
            DispatchQueue.main.async {
                switch result {
                case .failure(let error):
                    self.self.errorMessage = error.localizedDescription
                case .success(let listData):
                    let oldListData = self.artWorkList
                    self.artWorkList = oldListData + listData
                }
                withAnimation(.linear) {
                    self.isLoading = false
                    self.footerRefreshing = false
                    self.noMore = (self.artWorkList.count >= 50)
                }
            }
        }
    }
    
    // MARK: preview helpers
    
    static func errorState() -> ArtWorkFetcher {
        let fetcher = ArtWorkFetcher()
        fetcher.errorMessage = APIError.url(URLError(.notConnectedToInternet)).localizedDescription
        return fetcher
    }
    
    static func successState() -> ArtWorkFetcher {
        let fetcher = ArtWorkFetcher()
        fetcher.artWorkList = [ArtWork.example1(), ArtWork.example2()]
        
        return fetcher
    }
}
