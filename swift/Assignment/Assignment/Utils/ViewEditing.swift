//
//  ViewEditing.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/23.
//

import SwiftUI

extension View {
    func endEditing() {
        UIApplication.shared.connectedScenes
            .filter {$0.activationState == .foregroundActive}
            .map {$0 as? UIWindowScene}
            .compactMap({$0})
            .first?.windows
            .filter {$0.isKeyWindow}
            .first?.endEditing(true)
    }
}
