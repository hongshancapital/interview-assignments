//
//  StorageMiddleWare.swift
//  ToDoList
//
//  Created by lilun.ios on 2022/1/29.
//

import Foundation

let storagekey = "appstate_key"

func saveAppState(appstate: AppState) async {
    Log.d("save \(appstate) to user defaults")
    if let encoded = try? JSONEncoder().encode(appstate) {
        UserDefaults.standard.set(encoded, forKey: storagekey)
        Log.d("save \(appstate) to user defaults success")
    }
}

func loadAppState() async -> AppState? {
    Log.d("load appstate from user defaults")
    if let encoded = UserDefaults.standard.object(forKey: storagekey) as? Data,
       let state = try? JSONDecoder().decode(AppState.self, from: encoded) {
        Log.d("load \(state) from user defaults success")
        return state
    }
    return nil
}

/// storage state to disk
let storage: Middleware<AppState> = { (appstate, action, dispatch) in
    if let todoAction = action as? ToDoAction {
        switch todoAction {
        case ToDoAction.`init`, .loadCacheState:
            break
        default:
            Task {
                await saveAppState(appstate: appstate)
            }
        }
    }

    Log.d("Middleware storage end")
}

let load: Middleware<AppState> = { (appstate, action, dispatch) in
    if let todoAction = action as? ToDoAction {
        switch todoAction {
        case .loadCacheState:
            Task {
                if let state = await loadAppState() {
                    DispatchQueue.main.async {
                        dispatch(ToDoAction.updateAppState(state: state))
                    }
                }
            }
        default:
            break
        }
    }
    Log.d("Middleware storage end")
}
