//
//  ViewModel.swift
//  InterviewDemo
//
//  Created by Lays on 2023/3/15.
//

import Foundation

class ViewMoel: ObservableObject {
    @Published private(set) var appList: [AppMoel] = []
    @Published private(set) var isLoading = false
    @Published private(set) var hasMore = true
    @Published private(set) var error: ServiceError?
    
    private let service: AppService
    
    init(service: AppService) {
        self.service = service
        Task {
            await fetchData()
        }
    }
    
    @MainActor
    func fetchData() async {
        guard !isLoading && hasMore && error == nil else {
            return
        }
        
        isLoading = true
        switch await service.fetchData() {
        case .success(let result):
            self.appList += result.results
            self.hasMore = result.hasMore
        case .failure(let error):
            self.error = error
        }
        isLoading = false
    }
    
    @MainActor
    func refresh() async {
        switch await service.refresh() {
        case .success(let result):
            self.appList = result.results
            self.hasMore = result.hasMore
        case .failure(let error):
            self.error = error
        }
    }
    
    func updateCollectedStatus(forItem item: AppMoel) {
        if let index = self.appList.firstIndex(of: item) {
            appList[index].collected.toggle()
            service.updateCollectedStatus(byTrackId: item.trackId)
        }
    }
    
    func fetchMoreData(currentItem item: AppMoel) {
        if item == self.appList.last {
            Task {
                await fetchData()
            }
        }
    }
}
