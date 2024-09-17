//
//  Constants.swift
//  ToDoList
//
//  Created by berchan on 2021/12/18.
//

import Foundation

func isFirstInstall() -> Bool {
    guard let _ = UserDefaults.standard.object(forKey: "isFirstInstall") as? Date else {
        UserDefaults.standard.set(Date(), forKey: "isFirstInstall")
        return true
    }

    return false
}
