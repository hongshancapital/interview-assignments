//
//  AppCommand.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/7.
//

import Foundation

protocol AppCommand {
    func execute(in store: Store)
}

struct LoadHomePageCommand: AppCommand {
    func execute(in store: Store) {
        _ = LoadHomePageRequest.results
            .sink(
                receiveCompletion: { complate in
                    if case .failure(let error) = complate {
                        store.dispatch(.loadHomePageComplate(result: .failure(error)))
                    }
                }, receiveValue: { value in
                    store.dispatch(.loadHomePageComplate(result: .success(value)))
                }
            )
    }
}
