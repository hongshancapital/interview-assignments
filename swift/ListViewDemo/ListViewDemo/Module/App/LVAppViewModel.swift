//
//  LVAppViewModel.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/16.
//

import SwiftUI
import BBSwiftUIKit
import Moya
import RxSwift
import RxMoya

class LVAppViewModel: ObservableObject {
    
    @Published var apps: [LVApp] = []
    
    @Published var reloadData: Bool = false
    
    @Published var reloadRows: [Int] = []

    @Published var isRefreshing: Bool = false
    
    @Published var isLoadingMore: Bool = false
    
    @Published var noMore: Bool = false

    private let disposeBag = DisposeBag()
    private var page = 1
    
    func reload() {
        page = 1
        requestSearchApp()
    }
    
    func loadMore() {
        requestSearchApp()
    }
    
    func requestSearchApp() {
        AppProvider.rx.request(.search(entity: "software", limit: 50 * page, term: "chat"))
            .asObservable()
            .mapJSON()
            .mapModels(type: LVApp.self)
            .subscribe { apps in
                self.isRefreshing = false
                self.isLoadingMore = false
                self.page+=1
                self.apps = apps
                if self.apps.count >= 100 {
                    self.noMore = true
                }else {
                    self.noMore = false
                }
            } onError: { error in
                print("requestSearchApp error -> \(error)")
            }
            .disposed(by: disposeBag)
    }
}
