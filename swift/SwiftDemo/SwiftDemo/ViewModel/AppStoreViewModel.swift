//
//  AppStoreViewModel.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation
import Combine

class AppStoreViewModel: ObservableObject {
    public enum LoadState {
        case loading
        case loaded
        case error
    }
    
    @Published var appInfos: [AppInfo] = []
    @Published var loadState: LoadState = .loading
    @Published var isRequesting: Bool = false
    @Published var hasmore: Bool = false
    @Published var error: APIError? = nil
    @Published var showErrorAlert: Bool = false

    private var limit: Int = 10
    private var task: Task<(), Never>?
    
    deinit {
        task?.cancel()
    }
    
    public func refresh() {//模拟分页
#if DEBUG
        print("\(#function)")
#endif
        request(count: 10)
    }
    
    public func loadmore() {//模拟分页
        #if DEBUG
        print("\(#function): \(hasmore)")
        #endif
        guard hasmore else { return }
        
        request(count: limit + 10)
    }

    
    private func request(count: Int) {
#if DEBUG
        print("\(#function) \(count)")
#endif
        guard !isRequesting else { return }
        isRequesting = true
        
        task = Task {
            try? await Task.sleep(nanoseconds: 1 * 1_000_000_000)//模拟请求延迟
            do {
                let response = try await AS.debug(false).requestAsync(action: SearchAppAction(.init(entity: "software", term: "chat", limit: count)))
                await requestSuccess(response: response, count: count)
            } catch {
                await requestFailure(error: error)
            }
        }
    }
    
    @MainActor private func requestSuccess(response: SearchAppResponse, count: Int) {
        defer {
            loadState = .loaded
            isRequesting = false
        }
#if DEBUG
        print("\(#function)")
#endif
        if let infos = response.results?.map(AppInfo.init) {
            appInfos = infos
        }
        limit = count//模拟分页
        hasmore = checkHasmore()
    }
    
    @MainActor private func requestFailure(error: Error) {
        defer {
            switch loadState {
            case .loading:
                loadState = .error
            case .loaded:
                break
            case .error:
                loadState = .error
            }
            isRequesting = false
        }
        #if DEBUG
        print("\(#function): \(error.localizedDescription)")
        #endif

        self.error = error as? APIError
        if loadState == .loaded {
            self.showErrorAlert = true
        }
    }
    
    //模拟分页
    private func checkHasmore() -> Bool { limit < 20 }
}
