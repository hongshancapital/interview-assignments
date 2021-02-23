//
//  AppState.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import Foundation
import SwiftUI
import os

struct AppStateUserDefaultsKey {
    static let currentUser = "AppState.currentUser"
}

class AppState: ObservableObject {
    
    private let logger = Logger(subsystem: "com.tpphha.assignment", category: "AppState")
    
    @Published var currentUser: User? {
        didSet {
            if let user = currentUser {
                let encoder = JSONEncoder()
                do {
                    let encoded = try encoder.encode(user)
                    UserDefaults.standard.setValue(encoded, forKey: AppStateUserDefaultsKey.currentUser)
                } catch _ {
                    logger.log("encode user error")
                }
            } else {
                UserDefaults.standard.setValue(nil, forKey: AppStateUserDefaultsKey.currentUser)
            }
        }
    }
    
    @Published var createdUser: User?
    
    init() {
        let userData = UserDefaults.standard.object(forKey: AppStateUserDefaultsKey.currentUser)
        let decoder = JSONDecoder()
        do {
            if let currentUserData = userData {
                currentUser = try decoder.decode(User.self, from: currentUserData as! Data)
            }
        } catch _ {
            logger.log("decode user error")
        }
    }
    
}
