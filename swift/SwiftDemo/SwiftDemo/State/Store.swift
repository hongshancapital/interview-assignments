//
//  Store.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation
import Combine

public class Store: ObservableObject {
    var cancellables = Set<AnyCancellable>()
    public static let shared = Store()
    @Published var appState = AppState()
    
    func dispatch(_ action: AppAction) {
        #if DEBUG
        print("[ACTION]: \(action)")
        #endif
        let result = reduce(state: appState, action: action)
        appState = result.0
        if let appCommand = result.1 {
            #if DEBUG
            print("[COMMAND]: \(appCommand)")
            #endif
            appCommand.execute(in: self)
        }
    }
    
    
    func reduce(state: AppState, action: AppAction) -> (AppState, AppCommand?) {
        var appState = state
        var appCommand: AppCommand? = nil
        switch action {
        case .initAction:
            break
        case .likeApp(let id, let like):
            if like {
                appState.appStore.likedApps.append(id)
            } else {
                appState.appStore.likedApps.removeAll(where: { $0 == id })
            }
            
            appCommand = nil
        }
        
        return (appState, appCommand)
    }
}
