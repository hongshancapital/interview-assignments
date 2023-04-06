//
//  HearView.swift
//  testSwiftUI
//
//  Created by pchen on 2023/4/5.
//

import SwiftUI

struct HearView: View {
    
    @Binding var isSelected: Bool
    private let onSelected: (Bool) -> Void
    
    public init(
        isSelected: Binding<Bool>, onSelected: @escaping (Bool) -> Void) {
            
            self._isSelected = isSelected
            self.onSelected = onSelected
    }
    
    var body: some View {
        return Button {
            isSelected = !isSelected
            self.onSelected(isSelected)
            
        } label: {
            if isSelected {
                Image(systemName: "heart.fill").foregroundColor(Color.red)
                    .scaleEffect(1.2)
            } else {
                Image(systemName: "heart").foregroundColor(Color.gray)
            }
        }
        .animation(.easeInOut, value: isSelected)
    }
}
