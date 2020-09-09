//
//  SearchBar.swift
//  SearchViewDemo
//
//  Created by Mason Sun on 2020/9/9.
//

import SwiftUI

struct SearchBar: View {
    @Binding var text: String
    @Binding var isEditing: Bool
    var commitHandler: (() -> Void)?

    var body: some View {
        HStack {
            iconImage
            textField
            closeButton
        }
        .padding(.horizontal)
        .padding(.vertical, 12)
        .background(Color.gray.opacity(0.1))
        .cornerRadius(12)
    }

    // MARK: Components

    private var iconImage: some View {
        Group {
            if !isEditing {
                Image(systemName: "magnifyingglass")
                    .foregroundColor(.gray)
            }
        }
    }

    private var textField: some View {
        TextField("Tap here for search", text: $text, onEditingChanged: { value in
            self.isEditing = value
        }, onCommit: {
            self.commitHandler?()
        })
    }

    private var closeButton: some View {
        Group {
            if isEditing {
                Button(
                    action: { self.clearContent() },
                    label: {
                        Image(systemName: "xmark")
                            .foregroundColor(.gray)
                })
            }
        }
    }

    // MARK: Intents

    private func clearContent() {
        text = ""
        isEditing = false
        // Resign first responder
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

struct SearchBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchBar(text: .constant(""), isEditing: .constant(false))
    }
}
