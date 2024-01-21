//
//  AppCommand.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/5/3.
//

import Foundation

protocol AppCommand {
    func execute(in store: Store)
}


struct AppRefreshData: AppCommand{
    var publisherProvider: DataTaskPublisher = UrlSessionDataTaskPublisher()
    func execute(in store: Store) {
        FetchDataRequest(entity: "software", term: "chat", limit: AppConfig.dataCountPerRequest(), offset: 0, publisherProvider: publisherProvider).publisher
            .sink { complete in
                if case .failure(let error) = complete {
                    store.dispatch(.refreshDataDone(result: .failure(error)))
                }
                
            } receiveValue: { appInfoList in
                let viewModelList = appInfoList.map {AppInfoViewModel.init(with: $0)}
                store.dispatch(.refreshDataDone(result: .success(viewModelList)))
            }.store(in: &store.disposeBag)
        
    }
}

struct AppLoadMoreData: AppCommand {
    ///当前数据个数
    let curDataCount: Int
    ///下一刷数据个数
    let addDataCount: Int = AppConfig.dataCountPerRequest()
    var publisherProvider: DataTaskPublisher = UrlSessionDataTaskPublisher()
    func execute(in store: Store) {
        FetchDataRequest(entity: "software", term: "chat", limit: addDataCount, offset: curDataCount, publisherProvider: publisherProvider).publisher
            .sink { complete in
                if case .failure(let error) = complete {
                    store.dispatch(.loadNextPageDone(result: .failure(error)))
                }
                
            } receiveValue: { appInfoList in
                let viewModelList = appInfoList.map {AppInfoViewModel.init(with: $0)}
                if(viewModelList.count > 0) {
                    ///还有新数据
                    store.dispatch(.loadNextPageDone(result: .success(viewModelList)))
                } else {
                    ///没有新数据
                    store.dispatch(.endOfData)
                }
            }.store(in: &store.disposeBag)
        
    }
}
