//
//  ProfileViewModel.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/23.
//

import Foundation
import Combine

class ProfileViewModel: ObservableObject {
    // Output
    @Published var isLoading = false
    
    private var cancellableSet = Set<AnyCancellable>()
    
    lazy var onLogout: (() -> Void) = { [unowned self] in
        self.isLoading = true
        Server.default.logout()
            .sink { [weak self] (completion) in
                guard let self = self else {
                    return
                }
                self.isLoading = false
                switch completion {
                case .finished: break
                case .failure(_): break
                }
            } receiveValue: {
                appState.currentUser = nil
            }
            .store(in: &self.cancellableSet)
    }
    
}
