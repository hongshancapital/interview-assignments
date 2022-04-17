//
//  CollectButton.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import SwiftUI

struct CollectButton: View {
    @Binding var isCollected: Bool
    var action: () -> Void
    var body: some View {
        Button {
            action()
        } label: {
            Image(systemName: isCollected ? "heart.fill" : "heart")
                .resizable()
                .scaledToFit()
                .foregroundColor(isCollected ? .red : .gray)
                .frame(width: 20, height: 20, alignment: .center)
                .animation(.default, value: isCollected)
        }
    }
}

struct CollectButton_Previews: PreviewProvider {
    static var previews: some View {
        CollectButton(isCollected: Binding(get: {
            return true
        }, set: { _ in
            
        })) {
            
        }
    }
}
