//
//  InputTextField.swift
//  TodoDemo
//
//  Created by 钟逊超 on 12/5/21.
//

import Foundation
import SwiftUI


public struct InputTextField: View {
    
    private let placeholder: LocalizedStringKey
    
    @Binding private var text: String
    
    private let onEditingChanged: (Bool) -> Void
    
    private let onCommit: () -> Void
    
    private let menus: [String]
    
    private let onMenuSelected: (Int) -> Void
    
    @State private var title: String
    
    @State private var isMenuExpanded: Bool = false
    
    
    @State
    private var isShowingToolbar: Bool = false
    
    public init(placeholder: LocalizedStringKey = "",
                text: Binding<String>,
                onEditingChanged: @escaping (Bool) -> Void = { _ in },
                onCommit: @escaping () -> Void = { },
                menus: [String],
                onMenuSelected: @escaping (Int) -> Void = { _ in }) {
        self.placeholder = placeholder
        self._text = text
        self.onEditingChanged = onEditingChanged
        self.onCommit = onCommit
        self.menus = menus
        self.onMenuSelected = onMenuSelected
        self.title = menus.first ?? ""
    }
    
    public var body: some View {
        ZStack {
            HStack {
                TextField(placeholder, text: $text) { isChanged in
                    if isChanged {
                        isShowingToolbar = true
                    }
                    onEditingChanged(isChanged)
                } onCommit: {
                    isShowingToolbar = false
                    onCommit()
                }
                .textFieldStyle(RoundedBorderTextFieldStyle())
                
                GroupBox {
                    DisclosureGroup(title, isExpanded: $isMenuExpanded) {
                        ForEach(0 ..< menus.count, id:\.self) { index in
                            Text(menus[index])
                                .lineLimit(1)
                                .font(.system(size: 12))
                                .onTapGesture {
                                self.onMenuSelected(index)
                                title = menus[index]
                                isMenuExpanded.toggle()
                            }
                        }
                    }
                }.frame(width: 180, height: 40)
            }

            
            VStack {
                Spacer()
            }
        }
    }
}
