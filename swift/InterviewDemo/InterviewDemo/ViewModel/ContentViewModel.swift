//
//  ContentViewModel.swift
//  InterviewDemo
//
//  Created by Chenjun Ren on 2022/4/1.
//

import Foundation

class ContentViewModel: ObservableObject {
    @Published private(set) var appInfos = [AppInfo]()
    @Published private(set) var isLoading = false
    @Published private(set) var hasMore = true
    @Published private(set) var error: ServiceError?
    
    private let appInfoRepository: AppInfoRepository
    
    init(appInfoRepository: AppInfoRepository) {
        self.appInfoRepository = appInfoRepository
        Task {
            await fetchAppInfos()
        }
    }
    
    @MainActor
    func fetchAppInfos() async {
        guard !isLoading && error == nil && hasMore else {
            return
        }
        
        isLoading = true
        switch await appInfoRepository.fetchAppInfos() {
        case .success(let fetchResult):
            appInfos += fetchResult.result
            hasMore = fetchResult.hasMore
        case .failure(let error):
            self.error = error
        }
        isLoading = false
    }
    
    // MARK: - User Actions
    
    @MainActor
    func refresh() async {
        switch await appInfoRepository.refresh() {
        case .success(let fetchResult):
            appInfos = fetchResult.result
            hasMore = fetchResult.hasMore
        case .failure(let error):
            self.error = error
        }
    }
    
    func updateLikeStatus(for item: AppInfo) {
        if let index = appInfos.firstIndex(of: item) {
            appInfos[index].isLiked.toggle()
            appInfoRepository.updateLikeStatus(byAppId: item.id)
        }
    }
    
    func fetchMoreIfNeeded(current: AppInfo) {
        // fetch more only when the last element of appInfos array appears 
        if current == appInfos.last {
            Task {
                await fetchAppInfos()
            }
        }
    }
}
