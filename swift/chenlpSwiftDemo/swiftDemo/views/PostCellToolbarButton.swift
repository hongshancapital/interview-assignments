//
//  PostCellToolbarButton.swift
//  swiftDemo
//
//  Created by chenlp on 2022/4/11.
//

import SwiftUI

struct PostCellToolbarButton: View {
    let image: String
    let color: Color
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 5) {
                Image(systemName: image)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 25, height: 25)
            }
        }
        .foregroundColor(color)
        .buttonStyle(BorderlessButtonStyle())
    }
}

struct PostCellToolbarButton_Previews: PreviewProvider {
    static var previews: some View {
        PostCellToolbarButton(image: "heart", color: .blue) {
            print("Click")
        }
    }
}
