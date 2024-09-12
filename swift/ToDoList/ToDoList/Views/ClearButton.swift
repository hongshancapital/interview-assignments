//
//  ClearButton.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/20.
//

import SwiftUI

struct ClearButton: ViewModifier {
    @Binding var text: String
    
    init(_ text: Binding<String>) {
        self._text = text
    }
    
    func body(content: Content) -> some View {
        HStack {
            content
            Spacer()
            Image(systemName: "multiply.circle")
                .foregroundColor(.secondary)
                .font(.system(size: 12))
                .opacity(text.isEmpty ? 0 : 1)
                .onTapGesture {
                    self.text = ""
                }
            Spacer()
        }
    }
}
