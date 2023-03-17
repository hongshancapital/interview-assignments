//
//  ArtistListViewModel.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import Combine
import SwiftUI

@MainActor class ArtistListViewModel: ObservableObject {
    
    @Published private(set) var list: [ArtistModel] = []
    
    /// 标识是否还有更多的数据
    @Published var isLoadMore = false
    @Published var error: Error? = nil
    
    let pageSize = 10
    var page = 0
    
    var isProgressing: Bool {
        return list.isEmpty
    }
    
    /// 下拉
    func pullDown() async {        
        do {
            let r = ArtistListRequest(page: 0, pageSie: pageSize)
            if case .response(let list) = try await APIClient.request(r) {
                self.page = 0
                self.list = list
                self.isLoadMore = !(list.count < pageSize)
            }
        } catch {
            self.error = error
        }
    }
    
    /// 上拉
    func pullUp() async {
        if !self.isLoadMore { return }
        
        do {
            let r = ArtistListRequest(page: page + 1, pageSie: pageSize)
            if case .response(let list) = try await APIClient.request(r) {
                self.page = page + 1
                self.list.append(contentsOf: list)
                self.isLoadMore = !(list.count < pageSize)
            }
        } catch {
            self.isLoadMore = true
            self.error = error
        }
    }
    
    func likeArtist(_ artist: ArtistModel) async {
        let req = ArtistLikeRequest(isLike: !artist.isLike, trackId: artist.trackId)
        do {
            _ = try await APIClient.request(req)
            let index = self.list.firstIndex(where: {$0 == artist})!
            self.list[index].isLike = !artist.isLike
        } catch {
            
        }
    }
    
}
