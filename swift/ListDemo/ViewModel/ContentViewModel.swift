//
//  ContentViewModel.swift
//  ListDemo
//
//  Created by renhe on 2022/6/24.
//

import Foundation

class ContentViewModel : ObservableObject{
    @Published var appInfos = [ResultModel.InfoModel]()
    @Published var isLoading = false
    @Published var hasMore = true
    @Published var error: LoadError?
    private var manager = ConfigManager.init()
    
    init()  {
        Task{
            await refresh()
        }
    }
    @MainActor
    func refresh() async{
        if isLoading {
           return
        }
        isLoading = true
        switch await manager.refresh() {
        case .success((let result, let hasMore)):
            self.appInfos = result
            self.hasMore = hasMore
        case .failure(let loadError):
            self.error = loadError as? LoadError
        }
        isLoading = false

    }
    @MainActor
    func loadMore() async{
        guard !isLoading && error == nil && hasMore else {
            return
        }
        isLoading = true
        switch await manager.loadMore() {
        case .success(let fetchResult):
            appInfos += fetchResult.result
            hasMore = fetchResult.hasMore
        case .failure(let error):
            self.error = error as? LoadError
        }
        isLoading = false

    }
    
    func updateLikeByTrackId(byTrackId id: String){
        if let index = appInfos.firstIndex(where: {"\($0.trackId)" == id}) {
            appInfos[index].isLiked.toggle()
        }
       var likeIds = UserDefaults.standard.stringArray(forKey: Config.keys) ?? []
       if let index = likeIds.firstIndex(of: id) {
           likeIds.remove(at: index)
       }else{
           likeIds.append(id)
       }
       UserDefaults.standard.set(likeIds, forKey: Config.keys)
   }
}
