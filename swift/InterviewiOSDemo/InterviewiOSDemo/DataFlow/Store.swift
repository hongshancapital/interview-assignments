//
//  Store.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/6.
//

import Combine

/// APP状态机，用来维护，调度各种事件的状态
class Store: ObservableObject {
    @Published var appState = AppState()
    
    func dispatch(_ action: AppAction) {
        let result = Store.reduce(state: appState, action: action)
        appState = result.0
        if let command = result.1 {
            command.execute(in: self)
        }
    }
    
    static func reduce(state: AppState, action: AppAction) -> (AppState, AppCommand?) {
        var appState = state
        var appCommand: AppCommand?

        switch action {
        case .loadHomePage:
            if appState.homePageData.loadingHomePage {
                break
            }
            appState.homePageData.loadingHomePage = true
            appCommand = LoadHomePageCommand()
        case .loadHomePageComplate(let result):
            switch result {
            case .success(let models):
                appState.homePageData.homePageList = models
            case .failure(let error):
                print(error)
            }
        }

        return (appState, appCommand)
    }
}
